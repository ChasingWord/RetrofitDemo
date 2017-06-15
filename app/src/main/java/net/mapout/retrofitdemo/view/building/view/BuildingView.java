package net.mapout.retrofitdemo.view.building.view;

import net.mapout.androidbaseconfig.view.BaseView;
import net.mapout.retrofitdemo.view.building.BuildingListAdapter;

/**
 * Created by Chasing on 2017/6/9.
 */
public interface BuildingView extends BaseView {
    void setRcvBuildingAdapter(BuildingListAdapter buildingAdapter);
}
