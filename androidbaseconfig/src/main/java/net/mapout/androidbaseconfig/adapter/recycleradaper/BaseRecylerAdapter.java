package net.mapout.androidbaseconfig.adapter.recycleradaper;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseRecylerAdapter<T, H extends RecyclerViewHelper> extends RecyclerView.Adapter<ViewHolder> {

    protected List<T> datas;
    protected Context context;
    private BaseRecyclerMultitemTypeSupport baseRecyclerMultitemTypeSupport;
    private int layoutId;
    //    private BaseRecylerViewHolder recylerViewHolderHelper;
    private boolean isMultitemType;

    private ItemClickListener itemClickListener;
    private ItemLongClickListener itemLongClickListener;
    private ViewGroup.LayoutParams itemParams;


    /**
     * click
     *
     */
    public interface ItemClickListener {
        void onItemClick(View itemView, int position);
    }

    /**
     * long click
     */
    public interface ItemLongClickListener {
        boolean onItemLongClick(View itemView, int position);
    }


    public BaseRecylerAdapter(Context context, int layoutId/*BaseRecylerViewHolder recylerViewHolderHelper*/, List<T> datas) {
        this.context = context;
        this.datas = datas == null ? new ArrayList<T>() : new ArrayList<T>(datas);
//        this.recylerViewHolderHelper = recylerViewHolderHelper;
        this.layoutId = layoutId;
    }


    public BaseRecylerAdapter(Context context, List<T> datas, BaseRecyclerMultitemTypeSupport baseRecyclerMultitemTypeSupport) {
        this.context = context;
        this.datas = datas == null ? new ArrayList<T>() : new ArrayList<T>(datas);
        this.baseRecyclerMultitemTypeSupport = baseRecyclerMultitemTypeSupport;
        this.isMultitemType = Boolean.TRUE;
    }

    public void setItemHeight(int itemHeight) {
        itemParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,itemHeight);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int type) {

        if (isMultitemType) {
            int layoutId = baseRecyclerMultitemTypeSupport.getLayoutId(type);
            View view = LayoutInflater.from(context).inflate(layoutId, viewGroup, false);
            if( type == 0 ){
                if( itemParams != null ) {
                    view.setLayoutParams(itemParams);
                }
            }
            return new BaseRecylerViewHolder(view);
        } else {
            View view = LayoutInflater.from(context).inflate(layoutId, viewGroup, false);
            return new BaseRecylerViewHolder(view);
//            return  recylerViewHolderHelper;
        }

    }


    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
        T t = datas.get(position);
        int itemViewType = getItemViewType(position);
//        if (viewHolder instanceof  BaseRecylerViewHolder){
//            BaseRecylerViewHolder holder = (BaseRecylerViewHolder)viewHolder;
//            H helper = getAdapterHelper(position, holder.itemView);
//            holder.convert(t,helper);
//        }
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClick(viewHolder, position);
            }
        });

        viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return onItemLongClick(viewHolder, position);
            }
        });

        H helper = getAdapterHelper(position, viewHolder.itemView);
        if (isMultitemType && !baseRecyclerMultitemTypeSupport.isConvert(t, position)) {
            return;
        }

        convert(itemViewType, helper, t);


    }


    protected void setBaseRecyclerMultitemTypeSupport(BaseRecyclerMultitemTypeSupport baseRecyclerMultitemTypeSupport) {
        this.isMultitemType = Boolean.TRUE;
        this.baseRecyclerMultitemTypeSupport = baseRecyclerMultitemTypeSupport;
    }


    @Override
    public int getItemCount() {
        return datas.size();
    }


    @Override
    public int getItemViewType(int position) {
        T t = datas.get(position);
        return isMultitemType ? baseRecyclerMultitemTypeSupport.getItemViewType(t, position)
                : super.getItemViewType(position);

    }

    public void add(T elem) {
        if (elem != null) {
            int index = datas.size();
            datas.add(elem);
            notifyItemInserted(index);
        }
    }

    public void addAll(List<T> elem) {
        if (elem != null && !elem.isEmpty()) {
            int index = datas.size();
            datas.addAll(elem);
            notifyDataSetChanged();
        }

    }

    public void addAllNoNotify(List<T> elem) {
        if (elem != null && !elem.isEmpty()) {
            datas.addAll(elem);
        }
    }

    public void set(T oldElem, T newElem) {
        set(datas.indexOf(oldElem), newElem);
    }

    public void set(int index, T elem) {
        datas.set(index, elem);
        notifyItemChanged(index);
    }

    public void remove(T elem) {
        if (datas != null && elem != null) {
            int index = datas.indexOf(elem);
            datas.remove(elem);
            notifyItemRemoved(index);
        }
    }

    public void remove(int index) {
        if (datas != null) {
            datas.remove(index);
            notifyItemRemoved(index);
        }
    }

    public void replaceAll(List<T> elem) {
        if (datas != null) {
            datas.clear();
            if (elem != null) {
                datas.addAll(elem);
            }
            notifyDataSetChanged();
        }

    }

    public T getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public boolean contains(T elem) {
        return datas.contains(elem);
    }

    public void clear() {
        if (datas != null) {
            datas.clear();
        }
        notifyDataSetChanged();
    }

    private void onItemClick(ViewHolder viewHolder, int position) {
        if (itemClickListener != null) {
            View view = viewHolder.itemView;
            itemClickListener.onItemClick(view, position);
        }
    }

    private boolean onItemLongClick(ViewHolder viewHolder, int position) {
        if (itemLongClickListener != null) {
            View view = viewHolder.itemView;
            return itemLongClickListener.onItemLongClick(view, position);
        }
        return false;
    }


    public void setItemClickListener(ItemClickListener clickListener) {
        this.itemClickListener = clickListener;
    }

    public void setItemLongClickListener(ItemLongClickListener longClickListener) {
        this.itemLongClickListener = longClickListener;
    }

    protected abstract H getAdapterHelper(int position, View itemView);

    protected abstract void convert(int viewType, RecyclerViewHelper helper, T t);


}
