package net.mapout.retrofit.engine;

import com.trello.rxlifecycle2.LifecycleTransformer;

import net.mapout.retrofit.DisposableManager;
import net.mapout.retrofit.api.MapoutApi;
import net.mapout.retrofit.bean.base.Response;
import net.mapout.retrofit.bean.req.LoginReq;
import net.mapout.retrofit.bean.res.User;
import net.mapout.retrofit.callback.LoginCallback;

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
