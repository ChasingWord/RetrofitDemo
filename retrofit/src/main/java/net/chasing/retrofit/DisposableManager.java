package net.chasing.retrofit;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by Chasing on 2017/6/14.
 * 订阅取消管理类
 */
public class DisposableManager {
    private static DisposableManager mDisposableManager = new DisposableManager();
    private CompositeDisposable mCompositeDisposable;

    private DisposableManager() {
        mCompositeDisposable = new CompositeDisposable();
    }

    public static DisposableManager getInstance() {
        return mDisposableManager;
    }

    public void add(Disposable disposable) {
        mCompositeDisposable.add(disposable);
    }

    public void clear() {
        mCompositeDisposable.clear();
    }
}
