package net.mapout.retrofitdemo.view.login.present;

import android.content.Intent;

import net.mapout.androidbaseconfig.view.BasePresent;
import net.mapout.retrofit.bean.base.Response;
import net.mapout.retrofit.bean.res.User;
import net.mapout.retrofit.callback.LoginCallback;
import net.mapout.retrofit.util.MobileInfo;
import net.mapout.retrofitdemo.R;
import net.mapout.retrofitdemo.view.building.BuildingActivity;
import net.mapout.retrofitdemo.view.login.model.LoginModel;
import net.mapout.retrofitdemo.view.login.view.LoginView;

/**
 * Created by Chasing on 2017/6/13.
 */
public class LoginPresent extends BasePresent {
    private LoginView mLoginView;
    private LoginModel mLoginModel;

    public LoginPresent(LoginView loginView) {
        super(loginView);
        mLoginView = loginView;
        mLoginModel = new LoginModel(mContext);
    }
    
    public void login(String user, String pwd){
        mLoginModel.login(user, pwd, new LoginCallback() {
            @Override
            public void onPreReq() {
                mLoginView.showLoading(R.string.loading);
            }

            @Override
            public void onSuccess(Response<User> response) {
                if (response.getItems() == null || response.getItems().size() == 0) {
                    mLoginView.showToast("登录失败");
                    return;
                }
                User user = response.getItems().get(0);
                MobileInfo.getInstance().setUserInfo(response.getSession(), user.getId());

                Intent intent = new Intent(mContext, BuildingActivity.class);
                mLoginView.startActivity(intent);
            }

            @Override
            public void onFailure(String msg) {
                mLoginView.showToast(msg);
            }

            @Override
            public void onPostReq() {
                mLoginView.hideLoading();
            }
        }, mLoginView.bindToLifecycle());
    }

    @Override
    public void loadingData() {
        super.loadingData();
    }
}
