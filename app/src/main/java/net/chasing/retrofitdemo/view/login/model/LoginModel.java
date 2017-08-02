package net.chasing.retrofitdemo.view.login.model;

import android.content.Context;

import com.trello.rxlifecycle2.LifecycleTransformer;

import net.chasing.androidbaseconfig.view.BaseModel;
import net.chasing.retrofit.RequestManager;
import net.chasing.retrofit.bean.req.LoginReq;
import net.chasing.retrofit.callback.LoginCallback;

/**
 * Created by Chasing on 2017/6/13.
 */
public class LoginModel extends BaseModel {
    public LoginModel(Context context) {
        super(context);
    }

    public void login(String account, String pwd, LoginCallback callback, LifecycleTransformer lifecycleTransformer){
        LoginReq loginReq = new LoginReq();
        loginReq.setAccount(account);
        loginReq.setPassword(pwd);
        RequestManager.getInstance().login(loginReq, callback, lifecycleTransformer);
    }
}
