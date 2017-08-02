package net.chasing.androidbaseconfig.view;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.widget.Toast;

import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.RxLifecycle;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.android.RxLifecycleAndroid;

import net.chasing.androidbaseconfig.widget.dialog.ProgressDialog;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.subjects.BehaviorSubject;

public abstract class BaseActivity extends AppCompatActivity implements BaseView, LifecycleProvider<ActivityEvent> {
    protected BasePresent basePresent;
    protected ProgressDialog dialog;
    private List<Toast> toasts = new ArrayList<>();

    private final BehaviorSubject<ActivityEvent> lifecycleSubject = BehaviorSubject.create();

    @Override
    @NonNull
    @CheckResult
    public final Observable<ActivityEvent> lifecycle() {
        return lifecycleSubject.hide();
    }

    @Override
    @NonNull
    @CheckResult
    public final <T> LifecycleTransformer<T> bindUntilEvent(@NonNull ActivityEvent event) {
        return RxLifecycle.bindUntilEvent(lifecycleSubject, event);
    }

    @Override
    @NonNull
    @CheckResult
    public final <T> LifecycleTransformer<T> bindToLifecycle() {
        return RxLifecycleAndroid.bindActivity(lifecycleSubject);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        lifecycleSubject.onNext(ActivityEvent.CREATE);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
//            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
//        }
        int layoutResID = setContentView();
        setContentView(layoutResID);
        initInjector();
        handleIntent();
        initView();
        setListener();
        loadingData();

    }

    @Override
    protected void onStart() {
        super.onStart();
        lifecycleSubject.onNext(ActivityEvent.START);
        if (basePresent != null)
            basePresent.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        lifecycleSubject.onNext(ActivityEvent.RESUME);
        if (basePresent != null)
            basePresent.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        lifecycleSubject.onNext(ActivityEvent.PAUSE);
        hideLoading();
        if (basePresent != null)
            basePresent.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        lifecycleSubject.onNext(ActivityEvent.STOP);
        if (basePresent != null)
            basePresent.onStop();
        for (Toast toast : toasts) {
            if (toast != null)
                toast.cancel();
        }
        toasts.clear();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        lifecycleSubject.onNext(ActivityEvent.DESTROY);
        if (basePresent != null)
            basePresent.onDestroy();
    }

    @Override
    public void showToast(int msgResId) {
        showToast(getString(msgResId));
    }

    @Override
    public void showToast(String msg) {
//        if (toast == null)
//        {
//            toast = Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT);
//        } else {
//            toast.setText(msg);
//        }
//        toast.show();
        Toast toast = Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT);
        toast.show();
        toasts.add(toast);
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
        if (!this.isFinishing())
            dialog.show(ft, "dialog");
    }

    @Override
    public void hideLoading() {
        if (dialog != null && dialog.getShowsDialog() && !this.isFinishing()) {
            dialog.dismiss();
        }
    }

    @Override
    public FragmentManager getFrManager() {
        return getFragmentManager();
    }

    /**
     * 切换Fragment
     *
     * @param resId             容器ID
     * @param currentFragment   当前Fragment
     * @param fragmentClassName 目标Fragment全限定类名 eg:xxx.yyy.a.class
     * @return
     */
    protected Fragment switchFragment(int resId, Fragment currentFragment,
                                      String fragmentClassName) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        if (currentFragment != null) {
            transaction.hide(currentFragment);
        }
        Fragment toFragment = getFragmentManager().findFragmentByTag(
                fragmentClassName);
        if (toFragment == null) {
            toFragment = Fragment.instantiate(getApplicationContext(), fragmentClassName);
            transaction.add(resId, toFragment, fragmentClassName);
        } else {
            transaction.show(toFragment);
        }
        transaction.commit();
        return toFragment;
    }

    /**
     * 设置布局
     *
     * @return
     */
    protected abstract int setContentView();

    /**
     * 初始化present
     */
    protected abstract void initInjector();

    /**
     * 获取intent传递参数
     */
    protected abstract void handleIntent();

    /**
     * findByView
     */
    protected abstract void initView();

    /**
     * 设置监听器
     */
    protected abstract void setListener();

    /**
     * 加载数据
     */
    protected abstract void loadingData();

    public void requestPermission(String... permission){
        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions.requestEach(permission)
                .subscribe(new Consumer<Permission>() {
                    @Override
                    public void accept(@NonNull Permission permission) throws Exception {
                        if (permission.granted){
                            permissionGranted(permission.name);
                        } else {
                            permissionDeclined(permission.name);
                        }
                    }
                });
    }

    //权限接受
    protected void permissionGranted(String permission){

    }

    //权限拒绝
    protected void permissionDeclined(String permission){

    }
}
