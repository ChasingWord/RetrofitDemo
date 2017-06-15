package net.mapout.retrofitdemo.view.building;

import android.Manifest;
import android.os.Build;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;

import net.mapout.androidbaseconfig.view.BaseActivity;
import net.mapout.androidbaseconfig.widget.pullrecyclerview.decoration.HorizontalDividerItemDecoration;
import net.mapout.retrofitdemo.R;
import net.mapout.retrofitdemo.view.building.present.BuildingPresent;
import net.mapout.retrofitdemo.view.building.view.BuildingView;

import io.reactivex.functions.Consumer;

/**
 * Created by Chasing on 2017/6/9.
 */
public class BuildingActivity extends BaseActivity implements BuildingView {
    private BuildingPresent mBuildingPresent;

    private RecyclerView mRcvBuilding;

    @Override
    protected int setContentView() {
        return R.layout.ac_building;
    }

    @Override
    protected void initInjector() {
        mBuildingPresent = new BuildingPresent(this);
        basePresent = mBuildingPresent;
    }

    @Override
    protected void handleIntent() {

    }

    @Override
    protected void initView() {
        initRcv();
    }

    private void initRcv() {
        mRcvBuilding = (RecyclerView) findViewById(R.id.rcv_building);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        mRcvBuilding.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this).color(R.color.divider_line).build());
        mRcvBuilding.setLayoutManager(manager);
        mBuildingPresent.setBuildingListAdapter();
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void loadingData() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermission(Manifest.permission_group.SENSORS, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        } else {
            mBuildingPresent.loadingData();
        }
    }

    @Override
    protected void permissionGranted(String permission) {
        switch (permission) {
            case Manifest.permission_group.SENSORS:
                Log.e("Permission", " SENSORS granted");
                break;
            case Manifest.permission.ACCESS_COARSE_LOCATION:
                Log.e("Permission", " LOCATION granted");
                mBuildingPresent.loadingData();
                break;
            case Manifest.permission.WRITE_EXTERNAL_STORAGE:
                Log.e("Permission", " WRITE_EXTERNAL_STORAGE granted");
                break;
        }
    }

    @Override
    protected void permissionDeclined(String permission) {
        switch (permission) {
            case Manifest.permission_group.SENSORS:
                Log.e("Permission", " SENSORS Declined");
                break;
            case Manifest.permission.ACCESS_COARSE_LOCATION:
                Log.e("Permission", " LOCATION granted");
                break;
            case Manifest.permission.WRITE_EXTERNAL_STORAGE:
                Log.e("Permission", " WRITE_EXTERNAL_STORAGE granted");
                break;
        }
    }

    @Override
    public void setRcvBuildingAdapter(BuildingListAdapter buildingAdapter) {
        mRcvBuilding.setAdapter(buildingAdapter);
    }
}
