package net.mapout.retrofit.util;

import net.mapout.retrofit.DisposableManager;
import net.mapout.retrofit.bean.base.Response;
import net.mapout.retrofit.callback.base.BaseResponseCallback;
import net.mapout.retrofit.common.ResultCode;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Chasing on 2017/6/13.
 */
public class RxUtil {
    public static <T> void toSubscribe(Observable<Response<T>> o, final BaseResponseCallback<T> abstractCallback) {
        o.retry(2)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<T>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        DisposableManager.getInstance().add(d);
                        abstractCallback.onPreReq();
                    }

                    @Override
                    public void onNext(Response<T> t) {
                        if (t.getResultCode() == ResultCode.SUCCESS){
                            abstractCallback.onSuccess(t);
                        } else {
                            abstractCallback.onFailure(t.getResultMsg());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        abstractCallback.onFailure(e.getMessage());
                        abstractCallback.onPostReq();
                    }

                    @Override
                    public void onComplete() {
                        abstractCallback.onPostReq();
                    }
                });
    }
}
