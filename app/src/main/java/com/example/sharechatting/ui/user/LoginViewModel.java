package com.example.sharechatting.ui.user;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;
import okhttp3.Request;

public class LoginViewModel extends ViewModel {

    private MutableLiveData<String> userId;  //私有变量,通过getter/setter 来读取/修改
    private MutableLiveData<String> password;

    private SavedStateHandle mState;

    public LoginViewModel(SavedStateHandle savedStateHandle){
        mState = savedStateHandle;

        userId = new MutableLiveData<>();
        password = new MutableLiveData<>();

        userId.setValue("");
        password.setValue("");
    }

    public LiveData<String> getUserId(){return userId;}
    public LiveData<String> getPassword(){ return password;}

    public void setUserId(String id) {
        userId.setValue(id);
    }

    public void setPassword(String pswd) {
        password.setValue(pswd);
    }
    public void triggerJump(){

    }

    public void login() {
        // 不提倡在ViewModel里进行startActivity之类的页面操作
        // 所以类似操作还是在activity中添加监听吧
        // 不要直接在xml里面@click了

    }
}
