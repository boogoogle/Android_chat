package com.example.sharechatting.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.sharechatting.R;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 1. 延迟3s
 * 2. 跳转页面
 */
public class WelcomeActivity extends BaseActivity {
    private Timer mTimer;
    private String TAG = "TAGWelcomeActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        init();
    }

    private void init(){
        mTimer = new Timer();
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() { // 主线程
                Log.e(TAG, Thread.currentThread().toString());
                toLogin();
            }
        }, 10);

    }
    private void toMain(){
        Intent it = new Intent(this, MainActivity.class);
        startActivity(it);
        finish();
    }
    private void toLogin(){
        Intent it = new Intent(this, MoocLoginActivity.class);
        startActivity(it);
        finish();
    }
}








