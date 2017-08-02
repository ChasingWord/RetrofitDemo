package net.chasing.retrofitdemo.view.building.present;

import android.util.Log;

import net.chasing.androidbaseconfig.view.BasePresent;
import net.chasing.retrofit.bean.res.Building;
import net.chasing.retrofit.callback.BuildingCallback;
import net.chasing.retrofitdemo.R;
import net.chasing.retrofitdemo.view.building.BuildingListAdapter;
import net.chasing.retrofitdemo.view.building.model.BuildingModel;
import net.chasing.retrofitdemo.view.building.view.BuildingView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Chasing on 2017/6/9.
 */
public class BuildingPresent extends BasePresent {
    private BuildingView mBuildingView;
    private BuildingModel mBuildingModel;

    private BuildingListAdapter mBuildingListAdapter;

    public BuildingPresent(BuildingView buildingView) {
        super(buildingView);
        mBuildingView = buildingView;
        mBuildingModel = new BuildingModel(mContext);
    }

    public void setBuildingListAdapter() {
        mBuildingListAdapter = new BuildingListAdapter(mContext, R.layout.item_building);
        mBuildingView.setRcvBuildingAdapter(mBuildingListAdapter);
    }

    @Override
    public void loadingData() {
        mBuildingModel.reqBuildingList(4, new BuildingCallback() {
            @Override
            public void onPreReq() {
                mBuildingView.showLoading(R.string.loading);
            }

            @Override
            public void onSuccessList(List<Building> buildingList) {
                if (buildingList.size() == 0) {
                    mBuildingView.showToast("暂无列表");
                    return;
                }
                List<String> buildingNameList = new ArrayList<>();
                for (Building building : buildingList) {
                    buildingNameList.add(building.getName());
                }
                mBuildingListAdapter.addAll(buildingNameList);
            }

            @Override
            public void onFailure(String msg) {
                mBuildingView.showToast(R.string.net_error);
                Log.e("error", msg);
            }

            @Override
            public void onPostReq() {
                mBuildingView.hideLoading();
            }
        }, mBuildingView.bindToLifecycle());
    }
}
