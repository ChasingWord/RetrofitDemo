package net.chasing.androidbaseconfig.view;

import android.app.FragmentManager;
import android.content.Intent;

import com.trello.rxlifecycle2.LifecycleTransformer;

public interface BaseView {
    /**
     * show toast
     * @param msgResId
     */
    void showToast(int msgResId);

    /**
     * show toast
     * @param msg
     */
    void showToast(String msg);

    /**
     * show loading
     * @param msgResId 消息id
     */
    void showLoading(int msgResId);

    /**
     * hide loading
     */
    void hideLoading();

    void startActivity(Intent intent);

    void startActivityForResult(Intent intent, int reqCode);

    FragmentManager getFrManager();

    <T> LifecycleTransformer<T> bindToLifecycle();
}
