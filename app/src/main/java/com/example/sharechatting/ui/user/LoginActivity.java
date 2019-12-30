package com.example.sharechatting.ui.user;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import okhttp3.Connection;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.sharechatting.R;
import com.example.sharechatting.api.OkHttpClientManager;
import com.example.sharechatting.api.ScRequest;
import com.example.sharechatting.domain.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class LoginActivity extends AppCompatActivity {
    private LoginViewModel loginViewModel;
    private TextView text_username;
    private TextView text_password;
    private Button btn_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initPage();
        initViewModel();

    }
    private void initPage(){
         text_username = findViewById(R.id.text_username);
         text_password = findViewById(R.id.text_password);
         btn_login = (Button) findViewById(R.id.btn_login);

         btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user_name = text_username.getText().toString();
                String password = text_password.getText().toString();

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
    private void initViewModel(){  // 这个暂时没用
        loginViewModel = ViewModelProviders.of(this).get(LoginViewModel.class);
        loginViewModel.getUserId().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                text_username.setText(s);
            }
        });
        loginViewModel.getPassword().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                text_password.setText(s);
            }
        });
    }
    private void login(User loginUser) {
//        new ScRequest().post();
//            String url = "http://172.16.181.10:8888/maven_web/loginServlet?name=zhangsan&password=123";
            new ScRequest().post(loginUser);
            new OkHttpClientManager().postAsync(
                    "http://10.21.6.64:8809/person",
                    loginUser,
                    null,
                    false
            );

    }
}
