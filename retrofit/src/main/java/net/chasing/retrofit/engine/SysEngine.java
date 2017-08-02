package net.chasing.retrofit.engine;

import com.trello.rxlifecycle2.LifecycleTransformer;

import net.chasing.retrofit.bean.req.LoginReq;
import net.chasing.retrofit.bean.res.User;
import net.chasing.retrofit.callback.LoginCallback;
import net.chasing.retrofit.DisposableManager;
import net.chasing.retrofit.api.MapoutApi;
import net.chasing.retrofit.bean.base.Response;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Chasing on 2017/6/13.
 */
public class SysEngine {
    public void login(LoginReq req, final LoginCallback callback, LifecycleTransformer lifecycleTransformer) {
        MapoutApi mapoutApi = RetrofitManager.getInstance().createRetrofitService(MapoutApi.class);
        Observable<Response<User>> responseObservable = mapoutApi.login(req);
        responseObservable.compose(lifecycleTransformer);
        responseObservable.retry(2)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<User>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        DisposableManager.getInstance().add(d);
                        callback.onPreReq();
                    }

                    @Override
                    public void onNext(Response<User> userResponse) {
                        callback.onSuccess(userResponse);
                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onFailure(e.getMessage());
                        callback.onPostReq();
                    }

                    @Override
                    public void onComplete() {
                        callback.onPostReq();
                    }
                });
    }
}
