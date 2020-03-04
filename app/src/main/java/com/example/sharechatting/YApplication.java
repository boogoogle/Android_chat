package com.example.sharechatting;

import android.app.Application;

import com.blankj.utilcode.util.Utils;

/**
 * 全局文件,应用打开时,该文件会运行
 */
public class YApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();


        Utils.init(this);
    }
}
