package net.chasing.androidbaseconfig.widget.dialog;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;


public abstract class BaseDialog extends DialogFragment {
	protected View mContentView;
	private float mDimAmount = 0.35f;
	private DismissListener dismissListener;
	public BaseDialog() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (savedInstanceState == null) {
			setStyle(DialogFragment.STYLE_NO_TITLE, 0);
		}

		Bundle bundle = getArguments();
		if (bundle != null) {
			mDimAmount = bundle.getFloat("alpha", mDimAmount);
		}

		getExtraParams();
	}

	public void setDialogAlpha(float alpha) {
		Bundle b = buildArguments();
		b.putFloat("alpha", alpha);
		setArguments(b);
	}

	public void setDismissListener(DismissListener dismissListener) {
		this.dismissListener = dismissListener;
	}

	@Override
	public void onActivityCreated(Bundle arg0) {
		super.onActivityCreated(arg0);

		Dialog dialog = getDialog();
		if (dialog != null) {
			Window window = dialog.getWindow();
			window.setBackgroundDrawableResource(android.R.color.transparent);
			WindowManager.LayoutParams params = window.getAttributes();

			params.gravity = Gravity.CENTER;
			params.dimAmount = mDimAmount;
			window.setAttributes(params);
			dialog.setCanceledOnTouchOutside(false);
		}
		initComponents(getContentView());
        setListener();
	}

	@Override
	public void show(FragmentManager manager, String tag) {
		Fragment fragment = manager.findFragmentByTag(tag);
		if (fragment != null) {
			manager.beginTransaction().remove(fragment).commitAllowingStateLoss();
		}
		show(manager.beginTransaction(), tag);
	}

	@Override
	public int show(FragmentTransaction transaction, String tag) {
		transaction.add(this, tag);
		// transaction.addToBackStack(null);
		return transaction.commitAllowingStateLoss();
	}


	@Override
	public void onDismiss(DialogInterface dialog) {
		super.onDismiss(dialog);
		if( dismissListener != null ){
			dismissListener.dismiss();
		}
	}

	final protected Bundle buildArguments() {
		Bundle b = getArguments();
		if (b == null) {
			b = new Bundle();
		}
		return b;
	}

	 final protected View getContentView() {
	        return mContentView;
	    }

	protected abstract void getExtraParams();

	protected abstract void initComponents(View view);

	protected abstract void processLogic();

	protected abstract void setListener();

	public static interface DismissListener {
		void dismiss();
	}
}
