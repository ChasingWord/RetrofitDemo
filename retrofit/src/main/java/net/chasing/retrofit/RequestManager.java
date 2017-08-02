package net.chasing.retrofit;

import com.trello.rxlifecycle2.LifecycleTransformer;

import net.chasing.retrofit.bean.base.BaseReq;
import net.chasing.retrofit.bean.req.BuildingReq;
import net.chasing.retrofit.bean.req.LoginReq;
import net.chasing.retrofit.callback.BuildingCallback;
import net.chasing.retrofit.callback.LoginCallback;
import net.chasing.retrofit.engine.BuildingEngine;
import net.chasing.retrofit.engine.SysEngine;
import net.chasing.retrofit.callback.base.Callback;

/**
 * Created by Chasing on 2017/6/9.
 * 请求管理类
 */
public class RequestManager {
    private static RequestManager mRequestManager = new RequestManager();

    private static BuildingEngine mBuildingEngine;
    private static SysEngine mSysEngine;

    private RequestManager(){

    }

    public static RequestManager getInstance(){
        return mRequestManager;
    }

    public void judgeParameter(BaseReq baseReq, Callback callback, LifecycleTransformer lifecycleTransformer){
        if (baseReq == null)
            throw new RuntimeException("req can't be null");
        if (callback == null)
            throw new RuntimeException("callback can't be null");
        if (lifecycleTransformer == null)
            throw new RuntimeException("lifecycleTransformer can't be null");
    }

    public void reqBuildingList(BuildingReq req, final BuildingCallback callback, LifecycleTransformer lifecycleTransformer) {
        judgeParameter(req, callback, lifecycleTransformer);
        if (mBuildingEngine == null){
            mBuildingEngine = new BuildingEngine();
        }
        mBuildingEngine.reqBuildingList(req, callback, lifecycleTransformer);
    }

    public void login(LoginReq req, final LoginCallback callback, LifecycleTransformer lifecycleTransformer){
        judgeParameter(req, callback, lifecycleTransformer);
        if (mSysEngine == null){
            mSysEngine = new SysEngine();
        }
        mSysEngine.login(req, callback, lifecycleTransformer);
    }
}