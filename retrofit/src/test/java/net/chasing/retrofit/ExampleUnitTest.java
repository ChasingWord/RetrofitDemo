package net.chasing.retrofit;

import net.chasing.retrofit.api.ApiService;
import net.chasing.retrofit.bean.res.AliAddrsBean;
import net.chasing.retrofit.bean.req.IndexRequestBean;
import net.chasing.retrofit.engine.RetrofitManager;

import org.junit.Test;

import java.io.IOException;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Response;

import static org.junit.Assert.assertEquals;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void testRetrofit() {
        ApiService service = RetrofitManager.getInstance().createRetrofitAliyunService(ApiService.class);
        Call<AliAddrsBean> requestInde = service.getIndexContent();
        try {
            Response<AliAddrsBean> execute = requestInde.execute();//同步请求，和okhttp的使用方法一样
            AliAddrsBean body = execute.body();
            System.out.println(body.getLat());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testRetrofitOne() {
        ApiService service = RetrofitManager.getInstance().createRetrofitAliyunService(ApiService.class);
        Call<AliAddrsBean> requestInde = service.getIndexContentOne("geocoding");
        try {
            Response<AliAddrsBean> execute = requestInde.execute();//同步请求，和okhttp的使用方法一样
            AliAddrsBean body = execute.body();
            System.out.println(body.getLat());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testBody() {
        IndexRequestBean indexRequestBean = new IndexRequestBean();
        indexRequestBean.setA("上海市");
        indexRequestBean.setAa("松江区");
        indexRequestBean.setAaa("车墩镇");
        ApiService service = RetrofitManager.getInstance().createRetrofitAliyunService(ApiService.class);
        Call<AliAddrsBean> requestInde = service.getIndexContentFour(indexRequestBean);
        try {
            Response<AliAddrsBean> execute = requestInde.execute();//同步请求，和okhttp的使用方法一样
            AliAddrsBean body = execute.body();
            if (body != null)
                System.out.println(body.getLat());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testPart(){
        ApiService service = RetrofitManager.getInstance().createRetrofitAliyunService(ApiService.class);
        RequestBody requestBody1 = RequestBody.create(MediaType.parse("UTF-8"), "上海市");//如果传值为null，则默认utf-8
        RequestBody requestBody2 = RequestBody.create(MediaType.parse("UTF-8"), "松江区");
        RequestBody requestBody3 = RequestBody.create(MediaType.parse("UTF-8"), "车墩镇");
//        当然，既然是requestbody类型，我们就可以用它来穿文件了。
//        RequestBody requestBody1 = RequestBody.create(MediaType.parse("UTF-8"), new File("aaa"));//如果传值为null，则默认utf——8
        Call<AliAddrsBean> requestInde = service.getIndexContentSix(requestBody1, requestBody2, requestBody3);
        try {
            Response<AliAddrsBean> execute = requestInde.execute();//同步请求，和okhttp的使用方法一样
            AliAddrsBean body = execute.body();
            if (body != null)
                System.out.println(body.getLat());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testRxjava(){
        ApiService service = RetrofitManager.getInstance().createRetrofitAliyunService(ApiService.class);
        Observable<AliAddrsBean> requestInde = service.getIndexContentEleven("苏州市");
        requestInde.subscribeOn(Schedulers.newThread())
                .subscribe(new Observer<AliAddrsBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        System.out.println("onSubscribe");
                    }

                    @Override
                    public void onNext(AliAddrsBean aliAddrsBean) {
                        System.out.println("onNext");
                        System.out.println(aliAddrsBean.getLat());
                    }

                    @Override
                    public void onError(Throwable e) {
                        System.out.println("onError");
                    }

                    @Override
                    public void onComplete() {
                        System.out.println("onComplete");
                    }
                });

    }
}