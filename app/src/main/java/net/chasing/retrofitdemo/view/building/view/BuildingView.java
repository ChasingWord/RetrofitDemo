package net.chasing.retrofitdemo.view.building.view;

import net.chasing.androidbaseconfig.view.BaseView;
import net.chasing.retrofitdemo.view.building.BuildingListAdapter;

/**
 * Created by Chasing on 2017/6/9.
 */
public interface BuildingView extends BaseView {
    void setRcvBuildingAdapter(BuildingListAdapter buildingAdapter);
}
