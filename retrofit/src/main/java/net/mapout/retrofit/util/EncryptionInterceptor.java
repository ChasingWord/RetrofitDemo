package net.mapout.retrofit.util;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;

/**
 * Created by Chasing on 2017/6/12.
 * 加密拦截器
 */
public class EncryptionInterceptor implements Interceptor {
    private static final String TAG =   EncryptionInterceptor.class.getSimpleName();

    @Override
    public Response intercept(Chain chain) throws IOException {
        MediaType mediaType = MediaType.parse("text/plain; charset=utf-8");
        Request request = chain.request();
        RequestBody oldBody = request.body();
        Buffer buffer = new Buffer();
        if (oldBody != null) {
            oldBody.writeTo(buffer);
        }
        StringBuilder stringBuffer = new StringBuilder();
        String s;
        while ((s = buffer.readUtf8Line()) != null) {
            stringBuffer.append(s);
        }
        String newString = ThreeDES.encryptThreeDESECB(stringBuffer.toString().trim());
        RequestBody body = RequestBody.create(mediaType, newString);
        request = request.newBuilder()
                .header("Content-Type", body.contentType().toString())
                .header("Content-Length", String.valueOf(body.contentLength()))
//                .header("Authorization", SESSION.getInstance().getToken())
//                .header("UserAgent", "Platform/Android, Device/" + model + ", Lang/" + UserAgent.getInstance().lang + ", ScreenWidth/" + UserAgent.getInstance().width + ", ScreenHeight/" + UserAgent.getInstance().height)
//                .header("UDID", UserAgent.getInstance().UDID)
//                .header("Ver", UserAgent.getInstance().ver)
//                .header("Sign", signString)
                .method(request.method(), body)
                .build();
        return chain.proceed(request);
    }
}
