package com.example.sharechatting.ui.user;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.sharechatting.activities.MainActivity;
import com.example.sharechatting.R;
import com.example.sharechatting.api.ResultCallback;
import com.example.sharechatting.api.ScRequest;
import com.example.sharechatting.databinding.ActivityLoginBinding;
import com.example.sharechatting.domain.User;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.SavedStateViewModelFactory;
import androidx.lifecycle.ViewModelProviders;
import okhttp3.Request;


public class LoginActivity extends AppCompatActivity {
    final static String KEY_USER_ID = "user_id";
    private LoginViewModel loginViewModel;
    public String TAG = "LOGIN_ACTIVITY";
    //    private TextView text_username;
//    private TextView text_password;
//    private Button btn_login;
    ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_login); // 使用DataBinding的特殊方法来绑定视图文件
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        initPage();
        initViewModel();

        if (savedInstanceState != null) {
            loginViewModel.setUserId(savedInstanceState.getString(KEY_USER_ID).toString());
        }

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        String userid = loginViewModel.getUserId().getValue();
        outState.putString(KEY_USER_ID, userid);
    }

    private void initPage() {
//         text_username = findViewById(R.id.text_username);
//         text_password = findViewById(R.id.text_password);
//         btn_login = (Button) findViewById(R.id.btn_login);

        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user_name = binding.textUsername.getText().toString();
                String password = binding.textPassword.getText().toString();

                final User loginUser = new User(user_name, password);

                Log.d("ShareChatDebug", "onClick: " + loginUser);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        login(loginUser);
                    }
                }).start();
            }
        });

    }

    private void initViewModel() {  // 这个暂时没用
//        loginViewModel = ViewModelProviders.of(this).get(LoginViewModel.class);// 普通使用
        loginViewModel = ViewModelProviders.of(this, new SavedStateViewModelFactory(getApplication(), this))
                .get(LoginViewModel.class);

        // 单个字段的observe
//        loginViewModel.getUserId().observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(String s) {
//                binding.textUsername.setText(s);
//            }
//        });
//        loginViewModel.getPassword().observe(this, new Observer<String>() { // observe 会在activity销毁的时候自动取消
//            @Override
//            public void onChanged(String s) {
//                binding.textPassword.setText(s);
//            }
//        });
        binding.setLoginModel(loginViewModel); // 绑定
        binding.setLifecycleOwner(this);  // 实现数据到视图的observe
    }

    private void login(User loginUser) {
        ResultCallback<User> resultCallback = new ResultCallback<User>() {
            @Override
            public void onError(Request request, Exception e) {
                Log.d(TAG, "onError: "+ e);
            }

            @Override
            public void onResponse(User response) {
                Intent it = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(it);
            }
        };
        new ScRequest().post("/user/create", loginUser, resultCallback);

    }
}
