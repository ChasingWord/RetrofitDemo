package net.mapout.retrofit.engine;

import com.trello.rxlifecycle2.LifecycleTransformer;

import net.mapout.retrofit.api.MapoutApi;
import net.mapout.retrofit.bean.base.Response;
import net.mapout.retrofit.bean.req.BuildingReq;
import net.mapout.retrofit.bean.res.Building;
import net.mapout.retrofit.callback.BuildingCallback;
import net.mapout.retrofit.util.RxUtil;

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
