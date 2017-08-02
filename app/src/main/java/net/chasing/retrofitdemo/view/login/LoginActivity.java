package net.chasing.retrofitdemo.view.login;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import net.chasing.androidbaseconfig.view.BaseActivity;
import net.chasing.retrofitdemo.R;
import net.chasing.retrofitdemo.view.login.present.LoginPresent;
import net.chasing.retrofitdemo.view.login.view.LoginView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Chasing on 2017/6/13.
 */
public class LoginActivity extends BaseActivity implements LoginView {
    @BindView(R.id.btn_login)
    Button login;
    @BindView(R.id.et_account)
    EditText user;
    @BindView(R.id.et_pwd)
    EditText pwd;

    private LoginPresent mLoginPresent;

    @Override
    protected int setContentView() {
        return R.layout.ac_login;
    }

    @Override
    protected void initInjector() {
        ButterKnife.bind(this);
        mLoginPresent = new LoginPresent(this);
        basePresent = mLoginPresent;
    }

    @Override
    protected void handleIntent() {

    }

    @Override
    protected void initView() {

    }

    @Override
    protected void setListener() {
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLoginPresent.login(user.getText().toString(), pwd.getText().toString());
            }
        });
    }

    @Override
    protected void loadingData() {
        mLoginPresent.loadingData();
    }
}
