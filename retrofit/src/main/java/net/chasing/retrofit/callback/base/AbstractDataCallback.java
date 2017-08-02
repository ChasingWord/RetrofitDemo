package net.chasing.retrofit.callback.base;

import net.chasing.retrofit.bean.base.Response;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Chasing on 2017/6/9.
 * 回调 List<T> 类型数据
 */
public abstract class AbstractDataCallback<T> implements BaseResponseCallback<T> {
    @Override
    public void onPreReq() {

    }

    @Override
    public void onSuccess(Response<T> response) {
        if (response.getItems() != null){
            onSuccessList(response.getItems());
        } else {
            onSuccessList(new ArrayList<T>());
        }
    }

    public abstract void onSuccessList(List<T> dataList);

    @Override
    public void onPostReq() {

    }
}
