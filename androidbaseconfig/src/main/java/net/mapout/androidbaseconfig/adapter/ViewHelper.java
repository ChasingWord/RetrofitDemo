package net.mapout.androidbaseconfig.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.util.Linkify;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Checkable;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

public class ViewHelper {

	private final SparseArray<View> views;
	private final Context context;
	public int position;
	private View convertView;
	public int layoutId;
	private Object associatedObject;
	private ViewGroup deaultGroup;

	protected ViewHelper(Context context, ViewGroup parent, int layoutId, int position) {
		this.context = context;
		this.position = position;
		this.layoutId = layoutId;
		this.deaultGroup = parent;
		this.views = new SparseArray<View>();
		convertView = LayoutInflater.from(context).inflate(layoutId, parent, false);
		convertView.setTag(this);
	
	}

	public static ViewHelper get(Context context, View convertView, ViewGroup parent, int layoutId) {
		return get(context, convertView, parent, layoutId, -1);
	}

	public static ViewHelper get(Context context, View convertView, ViewGroup parent, int layoutId, int position) {
		if (convertView == null) {
			return new ViewHelper(context, parent, layoutId, position);
		}

		ViewHelper existingHelper = (ViewHelper) convertView.getTag();

		if (existingHelper.layoutId != layoutId) {
			return new ViewHelper(context, parent, layoutId, position);
		}

		existingHelper.position = position;
		return existingHelper;
	}

	public <T extends View> T getView(int viewId) {
		return retrieveView(viewId);
	}

	@SuppressWarnings("unchecked")
	protected <T extends View> T retrieveView(int viewId) {
		View view = views.get(viewId);
		if (view == null) {
			view = convertView.findViewById(viewId);
			views.put(viewId, view);
		}
		return (T) view;
	}

	public ViewHelper setText(int viewId, String value) {
		TextView view = retrieveView(viewId);
		view.setText(value);
		return this;
	}

	public ViewHelper setImageResource(int viewId, int imageResId) {
		ImageView view = retrieveView(viewId);
		view.setImageResource(imageResId);
		return this;
	}

	public ViewHelper setBackgroundColor(int viewId, int color) {
		View view = retrieveView(viewId);
		view.setBackgroundColor(color);
		return this;
	}

	public ViewHelper setBackgroundRes(int viewId, int backgroundRes) {
		View view = retrieveView(viewId);
		view.setBackgroundResource(backgroundRes);
		return this;
	}

	public ViewHelper setTextColor(int viewId, int textColor) {
		TextView view = retrieveView(viewId);
		view.setTextColor(textColor);
		return this;
	}

	public ViewHelper setTextColorRes(int viewId, int textColorRes) {
		TextView view = retrieveView(viewId);
		view.setTextColor(context.getResources().getColor(textColorRes));
		return this;
	}

	public ViewHelper setImageDrawable(int viewId, Drawable drawable) {
		ImageView view = retrieveView(viewId);
		view.setImageDrawable(drawable);
		return this;
	}


	public ViewHelper setImageBitmap(int viewId, Bitmap bitmap) {
		ImageView view = retrieveView(viewId);
		view.setImageBitmap(bitmap);
		return this;
	}

	@SuppressLint("NewApi")
	public ViewHelper setAlpha(int viewId, float value) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			retrieveView(viewId).setAlpha(value);
		} else {
			AlphaAnimation alpha = new AlphaAnimation(value, value);
			alpha.setDuration(0);
			alpha.setFillAfter(true);
			retrieveView(viewId).startAnimation(alpha);
		}
		return this;
	}

	public ViewHelper setVisible(int viewId, boolean visible) {
		View view = retrieveView(viewId);
		view.setVisibility(visible ? View.VISIBLE : View.GONE);
		return this;
	}

	public ViewHelper linkify(int viewId) {
		TextView view = retrieveView(viewId);
		Linkify.addLinks(view, Linkify.ALL);
		return this;
	}

	public ViewHelper setTypeface(int viewId, Typeface typeface) {
		TextView view = retrieveView(viewId);
		view.setTypeface(typeface);
		view.setPaintFlags(view.getPaintFlags() | Paint.SUBPIXEL_TEXT_FLAG);
		return this;
	}

	public ViewHelper setTypeface(Typeface typeface, int... viewIds) {
		for (int viewId : viewIds) {
			TextView view = retrieveView(viewId);
			view.setTypeface(typeface);
			view.setPaintFlags(view.getPaintFlags() | Paint.SUBPIXEL_TEXT_FLAG);
		}
		return this;
	}

	public ViewHelper setProgress(int viewId, int progress) {
		ProgressBar view = retrieveView(viewId);
		view.setProgress(progress);
		return this;
	}

	public ViewHelper setProgress(int viewId, int progress, int max) {
		ProgressBar view = retrieveView(viewId);
		view.setMax(max);
		view.setProgress(progress);
		return this;
	}

	public ViewHelper setMax(int viewId, int max) {
		ProgressBar view = retrieveView(viewId);
		view.setMax(max);
		return this;
	}

	public ViewHelper setRating(int viewId, float rating) {
		RatingBar view = retrieveView(viewId);
		view.setRating(rating);
		return this;
	}
	public ViewHelper setRating(int viewId, float rating, int max) {
		RatingBar view = retrieveView(viewId);
		view.setMax(max);
		view.setRating(rating);
		return this;
	}

	public ViewHelper setTag(int viewId, Object tag) {
		View view = retrieveView(viewId);
		view.setTag(tag);
		return this;
	}

	public ViewHelper setTag(int viewId, int key, Object tag) {
		View view = retrieveView(viewId);
		view.setTag(key, tag);
		return this;
	}

	public ViewHelper setChecked(int viewId, boolean checked) {
		Checkable view = (Checkable) retrieveView(viewId);
		view.setChecked(checked);
		return this;
	}

	public ViewHelper setAdapter(int viewId, Adapter adapter) {
		AdapterView view = retrieveView(viewId);
		view.setAdapter(adapter);
		return this;
	}

	public ViewHelper setOnClickListener(int viewId, View.OnClickListener listener) {
		View view = retrieveView(viewId);
		view.setOnClickListener(listener);
		return this;
	}

	public ViewHelper setOnTouchListener(int viewId, View.OnTouchListener listener) {
		View view = retrieveView(viewId);
		view.setOnTouchListener(listener);
		return this;
	}

	public ViewHelper setOnLongClickListener(int viewId, View.OnLongClickListener listener) {
		View view = retrieveView(viewId);
		view.setOnLongClickListener(listener);
		return this;
	}

	public View getView() {
		return convertView;
	}

	public int getPosition() {
		if (position == -1)
			throw new IllegalStateException("Use BaseAdapterHelper constructor " + "with position if you need to retrieve the position.");
		return position;
	}

	public Object getAssociatedObject() {
		return associatedObject;
	}

	public void setAssociatedObject(Object associatedObject) {
		this.associatedObject = associatedObject;
	}

	public ViewGroup getDeaultGroup() {
		return deaultGroup;
	}
}
