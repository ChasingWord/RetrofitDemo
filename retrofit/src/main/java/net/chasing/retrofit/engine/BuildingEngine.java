package net.chasing.retrofit.engine;

import com.trello.rxlifecycle2.LifecycleTransformer;

import net.chasing.retrofit.callback.BuildingCallback;
import net.chasing.retrofit.api.MapoutApi;
import net.chasing.retrofit.bean.base.Response;
import net.chasing.retrofit.bean.req.BuildingReq;
import net.chasing.retrofit.bean.res.Building;
import net.chasing.retrofit.util.RxUtil;

import io.reactivex.Observable;

/**
 * Created by Chasing on 2017/6/9.
 */
public class BuildingEngine {
    public void reqBuildingList(BuildingReq req, final BuildingCallback callback, LifecycleTransformer lifecycleTransformer) {
        MapoutApi mapoutRetrofit = RetrofitManager.getInstance().createRetrofitService(MapoutApi.class);
        Observable<Response<Building>> responseObservable = mapoutRetrofit.getBuildingList(req);
        responseObservable.compose(lifecycleTransformer);
        RxUtil.toSubscribe(responseObservable, callback);
    }
}
