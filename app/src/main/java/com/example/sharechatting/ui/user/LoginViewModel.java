package com.example.sharechatting.ui.user;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class LoginViewModel extends ViewModel {
    private MutableLiveData<String> userId;
    private MutableLiveData<String> password;

    public LoginViewModel(){
        userId = new MutableLiveData<>();
        password = new MutableLiveData<>();

        userId.setValue("zhangsan");
        password.setValue("123");
    }

    public LiveData<String> getUserId(){return userId;};
    public LiveData<String> getPassword(){ return password;};
}
