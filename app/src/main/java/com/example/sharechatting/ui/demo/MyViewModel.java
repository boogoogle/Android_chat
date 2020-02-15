package com.example.sharechatting.ui.demo;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.example.sharechatting.R;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.SavedStateHandle;

public class MyViewModel extends AndroidViewModel {
    SavedStateHandle handle;

    // 访问资源key
    String key = getApplication().getResources().getString(R.string.data_demo_key);
    String shpName = getApplication().getResources().getString(R.string.data_shp_name);

    private LiveData<Integer> count;
    // 这里的LiveData是配合SavedStateHandle handle 使用的
    // 所以不能用MutableLiveData count 来定义数据了



    public MyViewModel(@NonNull Application application, SavedStateHandle handle) {
        super(application);
        this.handle = handle;

        if(!handle.contains(key)){
            load();
        }
    }
    public void load(){
        SharedPreferences shp = getApplication().getSharedPreferences(shpName, Context.MODE_PRIVATE);
        int x = shp.getInt(key, 0);
        handle.set(key,x);
    }

    public void save(){
        SharedPreferences shp = getApplication().getSharedPreferences(shpName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = shp.edit();
        editor.putInt(key, getCount().getValue());
        editor.apply();
    }


    public LiveData<Integer> getCount() {
        return handle.getLiveData(key);
    }
    public void setCount(int x) {
        handle.set(key, getCount().getValue() + x);
    }
}
