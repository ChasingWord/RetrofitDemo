package net.mapout.retrofit;

import com.trello.rxlifecycle2.LifecycleTransformer;

import net.mapout.retrofit.bean.base.BaseReq;
import net.mapout.retrofit.bean.req.BuildingReq;
import net.mapout.retrofit.bean.req.LoginReq;
import net.mapout.retrofit.callback.BuildingCallback;
import net.mapout.retrofit.callback.LoginCallback;
import net.mapout.retrofit.callback.base.Callback;
import net.mapout.retrofit.engine.BuildingEngine;
import net.mapout.retrofit.engine.SysEngine;

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