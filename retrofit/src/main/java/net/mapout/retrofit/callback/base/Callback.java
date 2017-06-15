package net.mapout.retrofit.callback.base;

/**
 * Created by Chasing on 2017/6/14.
 */
public interface Callback {
    void onPreReq();
    void onFailure(String msg);
    void onPostReq();
}
