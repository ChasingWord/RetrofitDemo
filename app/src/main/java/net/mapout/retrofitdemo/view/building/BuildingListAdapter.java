package net.mapout.retrofitdemo.view.building;

import android.content.Context;

import net.mapout.androidbaseconfig.adapter.recycleradaper.RecyclerQuickAdapter;
import net.mapout.androidbaseconfig.adapter.recycleradaper.RecyclerViewHelper;
import net.mapout.retrofitdemo.R;

/**
 * Created by Chasing on 2017/6/9.
 */
public class BuildingListAdapter extends RecyclerQuickAdapter<String> {
    public BuildingListAdapter(Context context, int layoutId) {
        super(context, layoutId);
    }

    @Override
    protected void convert(int viewType, RecyclerViewHelper helper, String s) {
        helper.setText(R.id.tv_building_name, s);
    }
}
