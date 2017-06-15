package net.mapout.retrofit.bean.req;

import net.mapout.retrofit.bean.base.BaseReq;

/**
 * Created by Chasing on 2017/6/13.
 */
public class LoginReq extends BaseReq {

    private String account;
    private String password;

    public LoginReq() {
        super(10001);
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
