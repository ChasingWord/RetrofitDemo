package net.mapout.retrofit.api;

import net.mapout.retrofit.bean.res.AliAddrsBean;
import net.mapout.retrofit.bean.req.IndexRequestBean;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * Created by Chasing on 2017/6/8.
 */
public interface ApiService {
    //实际上在get的开始已经有一个rul存在了
    //例如，我们的url是“http://gc.ditu.aliyun.com/”
    //那么get注解前就已经存在了这个url，并且使用{}替换符得到的最终url是：
    // http://gc.ditu.aliyun.com/geocoding?a=苏州市
    //参数不能为null,且不能只有url的参数，还应该包括地址的字段；正确：geocoding?a=苏州市；错误：a=苏州市
    @GET("geocoding?a=上海市&aa=松江区&aaa=车墩镇")
    Call<AliAddrsBean> getIndexContent();

    //这里需要注意的是，{}作为取代块一定不能取代参数
    //它会报异常：URL query string "a={city}" must not have replace block. For dynamic query parameters use @Query.
    //翻译：URL查询字符串“= {城市}”必须没有取代块。动态查询参数使用@Query。
    //所以，{}取代块只能替换url而不能替换参数参数应该用@query
    @GET("{parameters}?a=苏州市")
    Call<AliAddrsBean> getIndexContentOne(@Path("parameters") String parameters);

    //@Query 进行单个参数添加
    //@Query 的作用就相当于拼接字符串：a=上海市&aa=松江区&aaa=车墩镇
    @POST("geocoding?")
    Call<AliAddrsBean> getIndexContentTow(
            @Query("a") String key1,
            @Query("aa") String key2,
            @Query("aaa") String key3);

    //@QueryMap 参数集合,多个参数添加使用map
    @GET("geocoding?")
    Call<AliAddrsBean> getIndexContentThree(
            @QueryMap Map<String, Object> options
    );

    //可以通过@Body注解指定一个对象作为Http请求的请求体
    //类似于一二三级城市的参数都放在的请求体indexRequestBean里面
    //@Body 只能用于POST，不能用于GET方式
    @POST("geocoding?")
    Call<AliAddrsBean> getIndexContentFour(@Body IndexRequestBean indexRequestBean);

    //函数也可以声明为发送form-encoded（表单形式）和multipart（多部分）数据。
    //当函数有@FormUrlEncoded注解的时候，将会发送form-encoded数据，
    //每个键-值对都要被含有名字的@Field注解和提供值的对象所标注
    //每个键值对的写法都是用注解@field标识的，表单形式的数据
    @FormUrlEncoded
    @POST("geocoding?")
    Call<AliAddrsBean> getIndexContentFive(
            @Field("a") String city,
            @Field("aa") String citys,
            @Field("aaa") String cityss
    );

    //当函数有@Multipart注解的时候，将会发送multipart数据，
    // Parts都使用@Part注解进行声明
    //Multipart parts要使用Retrofit的众多转换器之一或者实现RequestBody来处理自己的序列化。
    //这个可以用于传文件,可以改变传值的编码，默认utf_8
    @Multipart
    @POST("geocoding?")
    Call<AliAddrsBean> getIndexContentSix(
            @Part("a") RequestBody city,
            @Part("aa") RequestBody citya,
            @Part("aaa") RequestBody cityaa);

    //可以使用@Headers注解给函数设置静态的header
    // 如果我们每次都要给服务器一些固定参数,例如:版本号，请求接口版本，key等。我们就可以用它来设置在http请求的头里。
    @Headers({"key:web_service_key","web_vsersion:1.01","app_version:1.02"})
    @GET("geocoding?")
    Call<AliAddrsBean> getIndexContentSeven(
            @Query("a") String city
    );

    //可以使用@Header注解动态的更新一个请求的header。必须给@Header提供相应的参数，
    //如果参数的值为空header将会被忽略，否则就调用参数值的toString()方法并使用返回结果
    @GET("geocoding?")
    Call<AliAddrsBean> getIndexContentNine(
            @Header("a") String city
    );

    //我们适配Rxjava的时候，只需要将返回结果的call变成Rxjava的被订阅者Observable即可
    @GET("geocoding?")
    Observable<AliAddrsBean> getIndexContentEleven(
            @Query("a") String city);
}
