package net.mapout.androidbaseconfig.adapter;

import android.content.Context;

import java.util.List;

public abstract class EnhancedQuickAdapter<T> extends QuickAdapter<T> {

    public EnhancedQuickAdapter(Context context, int layoutResId) {
        super(context, layoutResId);
    }

    public EnhancedQuickAdapter(Context context, int layoutResId, List<T> data) {
        super(context, layoutResId, data);
    }

    @Override
    protected final void convert(ViewHelper helper, T item) {
        boolean itemChanged = helper.getAssociatedObject() == null || !helper.getAssociatedObject().equals(item);
        helper.setAssociatedObject(item);
        convert(helper, item, itemChanged);
    }

    protected abstract void convert(ViewHelper helper, T item, boolean itemChanged);
}
