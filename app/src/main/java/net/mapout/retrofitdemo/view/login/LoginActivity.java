package net.mapout.retrofitdemo.view.login;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import net.mapout.androidbaseconfig.view.BaseActivity;
import net.mapout.retrofitdemo.R;
import net.mapout.retrofitdemo.view.login.present.LoginPresent;
import net.mapout.retrofitdemo.view.login.view.LoginView;

/**
 * Created by Chasing on 2017/6/13.
 */
public class LoginActivity extends BaseActivity implements LoginView {
    private Button login;
    private EditText user, pwd;

    private LoginPresent mLoginPresent;

    @Override
    protected int setContentView() {
        return R.layout.ac_login;
    }

    @Override
    protected void initInjector() {
        mLoginPresent = new LoginPresent(this);
        basePresent = mLoginPresent;
    }

    @Override
    protected void handleIntent() {

    }

    @Override
    protected void initView() {
        login = (Button) findViewById(R.id.btn_login);
        user = (EditText) findViewById(R.id.et_account);
        pwd = (EditText) findViewById(R.id.et_pwd);
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
