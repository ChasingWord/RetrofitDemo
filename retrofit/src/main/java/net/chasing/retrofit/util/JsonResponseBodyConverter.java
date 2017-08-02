package net.chasing.retrofit.util;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;

import java.io.IOException;
import java.io.StringReader;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * Created by Chasing on 2017/6/12.
 * 数据接收处理类
 * 在convert接收数据时进行解密
 */
public class JsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {
    private final Gson mGson;
    private final TypeAdapter<T> adapter;

    /**
     * 构造器
     */
    public JsonResponseBodyConverter(Gson gson, TypeAdapter<T> adapter) {
        this.mGson = gson;
        this.adapter = adapter;
    }

    /**
     * 转换(并进行解密)
     *
     * @param responseBody
     * @return
     * @throws IOException
     */
    @Override
    public T convert(ResponseBody responseBody) throws IOException {
        try {
            String response = responseBody.string();
            String decodeString = ThreeDES.decryptThreeDESECB(response);
            JsonReader jsonReader = mGson.newJsonReader(new StringReader(decodeString));
            try {
                return adapter.read(jsonReader);
            } finally {
                responseBody.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
