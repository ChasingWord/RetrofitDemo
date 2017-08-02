package net.chasing.retrofit.engine;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import net.chasing.retrofit.util.EncryptionInterceptor;
import net.chasing.retrofit.BuildConfig;
import net.chasing.retrofit.common.Config;
import net.chasing.retrofit.util.JsonConverterFactory;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Chasing on 2017/6/8.
 * Retrofit管理类
 */
public class RetrofitManager {
    private static final RetrofitManager retrofitManage = new RetrofitManager();
    private Retrofit mAliyunRetrofit;
    private Retrofit mMapoutRetrofit;

    private RetrofitManager() {
        mAliyunRetrofit = new Retrofit.Builder()
                .baseUrl("http://gc.ditu.aliyun.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        mMapoutRetrofit = new Retrofit.Builder()
                .client(getMapoutHttpClient())
                .baseUrl(Config.ROOT_URL)
                .addConverterFactory(JsonConverterFactory.create())  //它是一个Json数据转化类
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())  //这个类是为了与RxJava衔接而提供的
                .build();
    }

    public static RetrofitManager getInstance() {
        return retrofitManage;
    }

    public <T>T createRetrofitAliyunService(Class<T> clazz) {
        return mAliyunRetrofit.create(clazz);
    }

    public <T>T createRetrofitService(Class<T> clazz){
        return mMapoutRetrofit.create(clazz);
    }

    private OkHttpClient getMapoutHttpClient() {
//        Authenticator mAuthenticator = new Authenticator() {
//            @Override public Request authenticate(Route route, Response response)
//                    throws IOException {
//                Your.sToken = service.refreshToken();
//                return response.request().newBuilder()
//                        .addHeader("Authorization", newAccessToken)
//                        .build();
//            }
//        };

        // log用拦截器
        HttpLoggingInterceptor loggingInterceptor= new HttpLoggingInterceptor();
        // 开发模式记录整个body，否则只记录基本信息如返回200，http协议版本等
        if (BuildConfig.DEBUG) {
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        } else {
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
        }

        return new OkHttpClient().newBuilder()
//                .authenticator(mAuthenticator)                  //设置请求头token刷新（token过时时进行刷新）
                .retryOnConnectionFailure(true)                   //设置出现错误进行重新连接
                .connectTimeout(3000, TimeUnit.MILLISECONDS)           //添加超时
                .writeTimeout(3000, TimeUnit.MILLISECONDS)
                .readTimeout(3000, TimeUnit.MILLISECONDS)
                .addInterceptor(loggingInterceptor)
                .addNetworkInterceptor(new EncryptionInterceptor())        //添加拦截器，加密
                .build();
    }
}
