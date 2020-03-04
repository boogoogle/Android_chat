package com.example.sharechatting.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.sharechatting.R;
import com.example.sharechatting.Views.InputView;
import com.example.sharechatting.utils.UserUtils;

/**
 * NavigationBar
 */
public class MoocRegisterActivity extends BaseActivity {

    InputView mInputPhone, mInputPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mooc_register);
        initView();
    }
    private void initView(){ // protected 可以在子类中调用
        initNavBar(false, "登录", false);

        mInputPhone = fd(R.id.input_phone);
        mInputPassword = fd(R.id.input_password);
    }

    public void onLoginClick(View v){
        Intent it = new Intent(this, MoocLoginActivity.class);
        startActivity(it);
//        finish();

    }
    public void onCommitClick(View v){
        String phone = mInputPhone.getInputStr();
        String password = mInputPassword.getInputStr();
        if(!UserUtils.validateLogin(this, phone, password)){
            return;
        }
        Intent it = new Intent(this, MainActivity.class);
        startActivity(it);
        finish(); // 关闭当前页面
    }
}
