package net.chasing.androidbaseconfig.adapter.recycleradaper;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

public class BaseRecylerViewHolderNew extends RecyclerView.ViewHolder {


    public BaseRecylerViewHolderNew(RecyclerView recyclerView,View itemView) {
        super(itemView);
        ViewGroup.LayoutParams lp = itemView.getLayoutParams() == null ? new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT) : itemView.getLayoutParams();
        if (recyclerView.getLayoutManager().canScrollHorizontally()) {
            lp.width = recyclerView.getWidth() - recyclerView.getPaddingLeft() - recyclerView.getPaddingRight();

            itemView.setLayoutParams(lp);
        }
    }
}
