package net.mapout.retrofit.api;

import net.mapout.retrofit.bean.req.BuildingReq;
import net.mapout.retrofit.bean.base.Response;
import net.mapout.retrofit.bean.req.LoginReq;
import net.mapout.retrofit.bean.res.Building;
import net.mapout.retrofit.bean.res.User;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by Chasing on 2017/6/9.
 */
public interface MapoutApi {
    @POST("build?")
    Observable<Response<Building>> getBuildingList(@Body BuildingReq req);

    @POST("sys?")
    Observable<Response<User>> login(@Body LoginReq req);
}
