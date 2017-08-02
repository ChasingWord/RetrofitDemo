package net.mapout.androidbaseconfig.view;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.trello.rxlifecycle2.components.RxFragment;

import net.mapout.androidbaseconfig.widget.dialog.ProgressDialog;

import java.util.ArrayList;
import java.util.List;


public abstract class BaseFragment extends RxFragment implements BaseView{
    protected BasePresent basePresent;
    protected ProgressDialog dialog;
    private List<Toast> toasts = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflateView(inflater);
        return v;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        initView(view);
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initInjector();
        getBundle(buildArguments());
        setListener();
        loadingData();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        hideLoading();
    }

    @Override
    public void onStop() {
        super.onStop();
        for (Toast toast : toasts) {
            if (toast != null)
                toast.cancel();
        }
        toasts.clear();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (basePresent != null)
            basePresent.onDestroy();
    }

    private final Bundle buildArguments() {
        Bundle b = getArguments();
        if (b == null) {
            b = new Bundle();
        }
        return b;
    }

    /**
     * 得到上下文
     *
     * @return
     */
    public Context getContext() {
        return getActivity();
    }

    @Override
    public void showToast(int msgResId) {
        showToast(getString(msgResId));
    }

    @Override
    public void showToast(String msg) {
        if (getContext() == null) {
            return;
        }
        Toast toast = Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT);
        toasts.add(toast);
        toast.show();
    }

    @Override
    public void showLoading(int msgResId) {
        FragmentManager fg = getFragmentManager();
        Fragment f = fg.findFragmentByTag("dialog");
        FragmentTransaction ft = fg.beginTransaction();
        if (f != null) {
            ft.remove(f);
        }
        if (dialog == null) {
            dialog = new ProgressDialog();
        }
        dialog.setCancelable(true);
        dialog.setMessage(getString(msgResId));
        if (getActivity() != null && !getActivity().isFinishing())
            dialog.show(ft, "dialog");
    }

    @Override
    public void hideLoading() {
        if (dialog != null && dialog.getShowsDialog() && getActivity() != null && !getActivity().isFinishing()) {
            dialog.dismiss();
        }
    }

    @Override
    public FragmentManager getFrManager() {
        return getFragmentManager();
    }

    /**
     * 初始化布局
     *
     * @param inflater
     * @return
     */
    protected abstract View inflateView(LayoutInflater inflater);

    /**
     * findByView
     *
     * @param view
     */
    protected abstract void initView(View view);

    /**
     * 初始化present
     */
    protected abstract void initInjector();

    /**
     * 设置监听器
     */
    protected abstract void setListener();

    /**
     * 得到Activity传进来的值
     *
     * @param bundle
     */
    public abstract void getBundle(Bundle bundle);

    /**
     * 加载数据
     */
    protected abstract void loadingData();
}
