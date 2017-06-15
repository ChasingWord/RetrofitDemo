/**
 * Copyright 2013 Joan Zapata
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.mapout.androidbaseconfig.adapter;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseQuickAdapter<T, H extends ViewHelper> extends BaseAdapter {

	protected static final String TAG = BaseQuickAdapter.class.getSimpleName();
	protected final Context context;
	protected int layoutResId;
	protected final List<T> data;
	protected boolean isShowProgress = false;
	protected MultiItemTypeSupport<T> mMultiItemSupport;

	public BaseQuickAdapter(Context context, int layoutResId) {
		this(context, layoutResId, null);
	}

	public BaseQuickAdapter(Context context, int layoutResId, List<T> data) {
		this.data = data == null ? new ArrayList<T>() : new ArrayList<T>(data);
		this.context = context;
		this.layoutResId = layoutResId;
	}

	public BaseQuickAdapter(Context context, ArrayList<T> data, MultiItemTypeSupport<T> multiItemSupport) {
		this.mMultiItemSupport = multiItemSupport;
		this.data = data == null ? new ArrayList<T>() : new ArrayList<T>(data);
		this.context = context;
	}

	@Override
	public int getCount() {
		int extra = isShowProgress ? 1 : 0;
		return data.size() + extra;
	}

	@Override
	public T getItem(int position) {
		if (position >= data.size())
			return null;
		return data.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public int getViewTypeCount() {
		if (mMultiItemSupport != null)
			return mMultiItemSupport.getViewTypeCount() + 1;
		return 2;
	}

	@Override
	public int getItemViewType(int position) {
		if (isShowProgress) {
			if (mMultiItemSupport != null)
				return position >= data.size() ? 0 : mMultiItemSupport.getItemViewType(position, data.get(position));
		} else {
			if (mMultiItemSupport != null)
				return mMultiItemSupport.getItemViewType(position, data.get(position));
		}

		return position >= data.size() ? 0 : 1;

	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (getItemViewType(position) == 0) {
			return createIndeterminateProgressView(convertView, parent);
		}
		final H helper = getAdapterHelper(position, convertView, parent);
		T item = getItem(position);
		helper.setAssociatedObject(item);
		convert(helper, item);
		return helper.getView();

	}

	private View createIndeterminateProgressView(View convertView, ViewGroup parent) {
		if (convertView == null) {
			FrameLayout container = new FrameLayout(context);
			container.setForegroundGravity(Gravity.CENTER);
			ProgressBar progress = new ProgressBar(context);
			container.addView(progress);
			convertView = container;
		}
		return convertView;
	}

	@Override
	public boolean isEnabled(int position) {
		return position < data.size();
	}

	public void add(T elem) {
		if (elem != null) {
			data.add(elem);
			notifyDataSetChanged();
		}
	}

	public void addAll(List<T> elem) {
		if (elem != null && !elem.isEmpty()) {
			data.addAll(elem);
			notifyDataSetChanged();
		}

	}
	public void addAllNoNotify(List<T> elem) {
		if (elem != null && !elem.isEmpty()) {
			data.addAll(elem);
		}
	}

	public void set(T oldElem, T newElem) {
		set(data.indexOf(oldElem), newElem);
	}

	public void set(int index, T elem) {
		data.set(index, elem);
		notifyDataSetChanged();
	}

	public void remove(T elem) {
		if (data != null && elem != null) {
			data.remove(elem);
			notifyDataSetChanged();
		}
	}

	public void remove(int index) {
		if (data != null) {
			data.remove(index);
			notifyDataSetChanged();
		}
	}

	public void replaceAll(List<T> elem) {
		if (data != null) {
			data.clear();
			if (elem != null) {
				data.addAll(elem);
			}
			notifyDataSetChanged();
		}
		
	}

	public boolean contains(T elem) {
		return data.contains(elem);
	}

	public void clear() {
		if (data != null) {
			data.clear();
		}
		notifyDataSetChanged();
	}

	public void showIndeterminateProgress(boolean display) {
		if (display == isShowProgress)
			return;
		isShowProgress = display;
		notifyDataSetChanged();
	}

	protected abstract void convert(H helper, T item);

	protected abstract H getAdapterHelper(int position, View convertView, ViewGroup parent);

}
