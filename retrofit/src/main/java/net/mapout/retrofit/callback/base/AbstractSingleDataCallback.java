package net.mapout.retrofit.callback.base;

import net.mapout.retrofit.bean.base.Response;

/**
 * Created by Chasing on 2017/6/14.
 * 回调单个 T 类型数据
 */
public abstract class AbstractSingleDataCallback<T> implements BaseResponseCallback<T> {
    @Override
    public void onPreReq() {

    }

    @Override
    public void onSuccess(Response<T> response) {
        if (response.getItems() != null && response.getItems().size() != 0){
            onSuccessSingle(response.getItems().get(0));
        } else {
            onSuccessSingle(null);
        }
    }

    public abstract void onSuccessSingle(T data);

    @Override
    public void onPostReq() {

    }
}
