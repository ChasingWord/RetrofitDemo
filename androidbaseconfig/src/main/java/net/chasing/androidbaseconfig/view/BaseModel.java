package net.chasing.androidbaseconfig.view;

import android.content.Context;

public class BaseModel {
    protected Context mContext;

    public BaseModel(Context context){
        mContext = context;
    }

    public void resetContext(Context context){
        mContext = context;
    }
}
