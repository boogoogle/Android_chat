package com.example.sharechatting.api;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ScRequest {
    private static OkHttpClient client;
    private static Request request;
    private static String baseUrl = "http://192.168.0.103:8810";// 172.16.181.10---10.21.6.64


    static {
        client = new OkHttpClient();
    }


    public void get(String url){
        request = new Request.Builder().url(url).build();
        try {
            Response response = client.newCall(request).execute();
            String responseData = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param o body体
     * @param resultCallback  回调函数
     */
    public void post(String path, Object o, ResultCallback resultCallback) {
        try {
            new OkHttpClientManager().postAsync(baseUrl + path,o, resultCallback,null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
