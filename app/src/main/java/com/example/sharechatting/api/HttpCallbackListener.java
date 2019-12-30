package com.example.sharechatting.api;

/**
 * 定义个接口 ???
 */
public interface HttpCallbackListener {
    void onFinish(String response);
    void onError(Exception e);
}
