package net.chasing.retrofitdemo.view.building.model;

import android.content.Context;

import com.trello.rxlifecycle2.LifecycleTransformer;

import net.chasing.androidbaseconfig.view.BaseModel;
import net.chasing.retrofit.RequestManager;
import net.chasing.retrofit.bean.req.BuildingReq;
import net.chasing.retrofit.callback.BuildingCallback;

/**
 * Created by Chasing on 2017/6/9.
 */
public class BuildingModel extends BaseModel {
    private RequestManager mRequestManager;

    public BuildingModel(Context context) {
        super(context);
        mRequestManager = RequestManager.getInstance();
    }

    public void reqBuildingList(int cityId, BuildingCallback callback, LifecycleTransformer lifecycleTransformer) {
        BuildingReq buildingReq = new BuildingReq();
        buildingReq.setCityId(cityId);
        mRequestManager.reqBuildingList(buildingReq, callback, lifecycleTransformer);
    }
}
