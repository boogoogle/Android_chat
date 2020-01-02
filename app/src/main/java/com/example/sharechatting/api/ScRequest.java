package com.example.sharechatting.api;

import android.app.Person;
import android.util.Log;

import com.example.sharechatting.domain.BaseResponse;
import com.example.sharechatting.domain.User;
import com.google.gson.Gson;

import java.io.IOException;

import javax.xml.transform.Result;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ScRequest {
    private static OkHttpClient client;
    private static Request request;
    private static String baseUrl = "http://172.16.181.10:8809/person";// 172.16.181.10---10.21.6.64


    static {
        client = new OkHttpClient();
    }


    public void get(String url){

        request = new Request.Builder().url(url).build();

        Log.d("sharechatde", url);

        try {
            Response response = client.newCall(request).execute();
            String responseData = response.body().string();
            Log.d("sharechatde", responseData);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void post(Object o, ResultCallback resultCallback) {
        try {


            new OkHttpClientManager().postAsync(baseUrl,o, resultCallback,null);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
