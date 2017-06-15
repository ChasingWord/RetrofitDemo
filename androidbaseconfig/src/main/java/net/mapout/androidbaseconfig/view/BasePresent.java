package net.mapout.androidbaseconfig.view;

import android.content.Context;
import android.content.Intent;

public class BasePresent implements LifeCycle {
    private BaseView baseView;
    protected Context mContext;

    /**
     * activity调用
     *
     * @param baseView
     */
    public BasePresent( BaseView baseView ) {
        this.baseView = baseView;
        this.mContext = (Context) baseView;
    }

    /**
     * fragment调用
     *
     * @param context
     * @param baseView
     */
    public BasePresent( Context context, BaseView baseView){
        this.baseView = baseView;
        this.mContext = context;
    }

    public void resetContext(Context context){
        mContext = context;
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onDestroy() {

    }

    public void handleIntent(Intent intent) {
    }

    public void loadingData() {

    }
}
