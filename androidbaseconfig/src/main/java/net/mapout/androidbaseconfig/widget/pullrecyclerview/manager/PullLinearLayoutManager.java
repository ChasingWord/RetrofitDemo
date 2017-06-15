package net.mapout.androidbaseconfig.widget.pullrecyclerview.manager;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import net.mapout.androidbaseconfig.widget.pullrecyclerview.listener.OverScrollListener;


public class PullLinearLayoutManager extends LinearLayoutManager {

    private OverScrollListener mListener;

    public PullLinearLayoutManager(Context context) {
        super(context);
    }

    public PullLinearLayoutManager(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
    }

    public PullLinearLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public int scrollVerticallyBy(int dy, RecyclerView.Recycler recycler, RecyclerView.State state) {
        int scrollRange = super.scrollVerticallyBy(dy, recycler, state);

        mListener.overScrollBy(dy - scrollRange);

        return scrollRange;
    }

    /**
     * 设置滑动过度监听
     *
     * @param listener
     */
    public void setOverScrollListener(OverScrollListener listener) {
        mListener = listener;
    }

}
