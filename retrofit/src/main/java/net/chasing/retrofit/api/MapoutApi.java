package net.chasing.retrofit.api;

import net.chasing.retrofit.bean.base.Response;
import net.chasing.retrofit.bean.req.BuildingReq;
import net.chasing.retrofit.bean.req.LoginReq;
import net.chasing.retrofit.bean.res.User;
import net.chasing.retrofit.bean.res.Building;

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
