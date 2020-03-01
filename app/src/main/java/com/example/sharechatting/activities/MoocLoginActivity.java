package com.example.sharechatting.activities;

import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.view.WindowManager;

import com.example.sharechatting.R;

import androidx.annotation.Nullable;

/**
 * NavigationBar
 */
public class MoocLoginActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mooc_login);
        initView();
    }
    private void initView(){ // protected 可以在子类中调用
        initNavBar(false, "登录", false);
    }

    public void onRegisterClick(View v){

    }
}
