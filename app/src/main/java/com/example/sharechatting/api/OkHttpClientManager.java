package com.example.sharechatting.api;

import android.net.UrlQuerySanitizer;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.example.sharechatting.domain.ResponseBean;
import com.example.sharechatting.domain.User;
import com.google.gson.Gson;
import com.google.gson.internal.$Gson$Types;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.CookieStore;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import okhttp3.Cache;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OkHttpClientManager {

    private static OkHttpClientManager mInstance;
    OkHttpClient mOkHttpClient = new OkHttpClient();
    private final HashMap<String, List<Cookie>> cookieStore = new HashMap<>();
    Handler mDelivery = new Handler(Looper.getMainLooper());
    Gson mGson = new Gson();
    private ResultCallback<String> DEFAULT_RESULT_CALLBACK = new ResultCallback<String>() {
        @Override
        public void onError(Request request, Exception e) {

        }

        @Override
        public void onResponse(String response) {

        }
    };


    public static OkHttpClientManager getInstance(){
        if(mInstance == null) {
            synchronized (OkHttpClientManager.class){
                if(mInstance == null) {
                    mInstance = new OkHttpClientManager();
                }
            }
        }
        return mInstance;
    }


    public OkHttpClientManager() {
        OkHttpClient.Builder mOkHttpClientBuilder = new OkHttpClient.Builder()
                            .connectTimeout(15, TimeUnit.SECONDS)
                            .writeTimeout(20, TimeUnit.SECONDS)
                            .readTimeout(20, TimeUnit.SECONDS)
                            .cookieJar(new CookieJar() {
                                @Override
                                public void saveFromResponse(@NotNull HttpUrl httpUrl, @NotNull List<Cookie> list) {
                                    cookieStore.put(httpUrl.host(), list);
                                }

                                @NotNull
                                @Override
                                public List<Cookie> loadForRequest(@NotNull HttpUrl httpUrl) {
                                     List<Cookie> cookies = cookieStore.get(httpUrl.host());
                                     return cookies != null ? cookies : new ArrayList<Cookie>();
                                }
                            });

        Handler mDelivery = new Handler(Looper.getMainLooper());
        Gson mGson = new Gson();
    }

    /**
     *
     * @param uri
     * @param body  post 请求体
     * @param tag
     */
    public void postAsync(String uri, Object body , final ResultCallback callback, Object tag){
//        FormBody.Builder formBodyBuilder = new FormBody.Builder();

        // 通过反射拿到body对应的类 & 属性
        // 这里拿类仿佛也没啥用啊
        // 这里没必要用getGenericSuperclass拿到父类的参数化类型吧 ???
//        Class bodyClass = body.getClass();
//
//        Field[] fields = bodyClass.getDeclaredFields();
//
//
//
//        for (Field field : fields) {
//            try {
//                field.setAccessible(true); // 不然field.get()无法读取,因为field有可能是private
//                formBodyBuilder.add(field.toString(),field.get(body).toString());
//                Log.d("ttt", field.toString() + "--"+field.get(body).toString());
//            } catch (IllegalAccessException e) {
//                e.printStackTrace();
//            }
//        }
//
//        FormBody formBody = formBodyBuilder.build();
//
//        MediaType FORM_TYPE = MediaType.parse("multipart/form-data");

        String bodyJson = mGson.toJson(body);
        RequestBody requestBody = FormBody.create(MediaType.parse("application/json;charset=utf-8"),bodyJson);

        Request request = new Request.Builder()
                .url(uri)
                .post(requestBody)
                .build();

        deliveryResult(callback, request);
    }
    private void deliveryResult(ResultCallback<?> callback, Request request) {
        if(callback == null) {
            callback = DEFAULT_RESULT_CALLBACK;
        }

        final ResultCallback resultCallback = callback;

        final Type callbackMtype = getSuperclassTypeParameter(callback.getClass());


        // UI thread

//        callback.onBefore(request); // 这里为啥这样写 ?
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                final String responseMessage = response.message();
                final String responseBody = response.body().string();
                Log.d("ttt", responseBody);
                if(response.code() == 200){ // 网络连接的状态码
                    ResponseBean rb =  mGson.fromJson(responseBody, ResponseBean.class);
                    if(rb.getCode() == "S200" && false) {
                        sendSuccessResultCallback(responseBody, resultCallback);
                    } else {

//                        Log.d("ttt", rb.getData().toString());
                        String j = mGson.toJson(rb.getData());
                        Object o = mGson.fromJson(j, callbackMtype);
                        sendSuccessResultCallback(o, resultCallback);
                    }
                }else {
                    Exception exception=new Exception(response.code()+":"+responseMessage);
                       sendFailedStringCallback(response.request(), exception, resultCallback);
                }
            }
        });
    }
    /**
     * 处理请求成功的回调信息方法
     * @param object 服务器响应信息
     * @param callback 回调类
     */
    private void sendSuccessResultCallback(final Object object, final ResultCallback callback) {
        mDelivery.post(new Runnable() {
            @Override
            public void run() {
                callback.onResponse(object);
                callback.onAfter();
            }
        });
    }

    private void sendFailedStringCallback(final Object object,Exception e, ResultCallback resultCallback){

    }

    /**
     * 通过反射得到想要的返回类型
     * @param subclass
     * @return
     */
    static Type getSuperclassTypeParameter(Class<?> subclass) {
        Type superclass = subclass.getGenericSuperclass(); // 返回父类,并包含父类的参数化类型

        // 这个判断啥意思 ?
        if(superclass instanceof Class) {
            throw new RuntimeException("Missing type parameter");
        }

        ParameterizedType parameterized = (ParameterizedType) superclass;
        return $Gson$Types.canonicalize(parameterized.getActualTypeArguments()[0]); // T
    }

}
