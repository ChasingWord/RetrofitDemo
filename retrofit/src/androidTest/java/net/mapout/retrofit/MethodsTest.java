package net.mapout.retrofit;

import android.test.InstrumentationTestCase;

import net.mapout.retrofit.api.ApiService;
import net.mapout.retrofit.api.MapoutApi;
import net.mapout.retrofit.bean.res.AliAddrsBean;
import net.mapout.retrofit.bean.res.Building;
import net.mapout.retrofit.bean.req.BuildingReq;
import net.mapout.retrofit.bean.req.IndexRequestBean;
import net.mapout.retrofit.bean.base.Response;
import net.mapout.retrofit.util.MobileInfo;

import java.io.IOException;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;

/**
 * Created by sangfor on 2016/8/26.
 */
public class MethodsTest extends InstrumentationTestCase {

}
