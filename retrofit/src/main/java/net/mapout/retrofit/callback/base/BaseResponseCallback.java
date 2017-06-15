package net.mapout.retrofit.callback.base;

import net.mapout.retrofit.bean.base.Response;

/**
 * Created by Chasing on 2017/6/14.
 */
public interface BaseResponseCallback<T> extends Callback {
    void onSuccess(Response<T> response);
}
