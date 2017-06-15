package net.mapout.retrofitdemo.view.building.model;

import android.content.Context;

import com.trello.rxlifecycle2.LifecycleTransformer;

import net.mapout.androidbaseconfig.view.BaseModel;
import net.mapout.retrofit.RequestManager;
import net.mapout.retrofit.bean.req.BuildingReq;
import net.mapout.retrofit.callback.BuildingCallback;

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
