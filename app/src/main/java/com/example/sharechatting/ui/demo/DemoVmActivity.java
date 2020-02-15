package com.example.sharechatting.ui.demo;

import android.os.Bundle;

import com.example.sharechatting.R;
import com.example.sharechatting.databinding.ActivityDemoViewModelBinding;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.SavedStateViewModelFactory;
import androidx.lifecycle.ViewModelProviders;

public class DemoVmActivity extends AppCompatActivity {
    ActivityDemoViewModelBinding binding;
    MyViewModel myViewModel;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_demo_view_model);
        myViewModel = ViewModelProviders.of(this, new SavedStateViewModelFactory(getApplication(), this)).get(MyViewModel.class);
        binding.setVm(myViewModel);
        binding.setLifecycleOwner(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        myViewModel.save();
    }
}
