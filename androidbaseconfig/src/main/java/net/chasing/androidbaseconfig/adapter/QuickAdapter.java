package net.chasing.androidbaseconfig.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public abstract class QuickAdapter<T> extends BaseQuickAdapter<T, ViewHelper> {

	public QuickAdapter(Context context, int layoutResId) {
		super(context, layoutResId);
	}

	public QuickAdapter(Context context, int layoutResId, List<T> data) {
		super(context, layoutResId, data);
	}

	public QuickAdapter(Context context, ArrayList<T> data, MultiItemTypeSupport<T> multiItemSupport) {
		super(context, data, multiItemSupport);
	}

	protected ViewHelper getAdapterHelper(int position, View convertView, ViewGroup parent) {
		if (mMultiItemSupport != null) {
			return ViewHelper.get(context, convertView, parent, mMultiItemSupport.getLayoutId(position, data.get(position)), position);
		} else {
			return ViewHelper.get(context, convertView, parent, layoutResId, position);
		}
	}

}
