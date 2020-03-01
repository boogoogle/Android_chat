package com.example.sharechatting.activities;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sharechatting.R;

import androidx.annotation.IdRes;
import androidx.appcompat.app.AppCompatActivity;

/**
 * 描述所有Activity的共性
 */
public class BaseActivity extends AppCompatActivity {
    private ImageView mIvBack, mIvMe;
    private TextView mTvTitle;

    // 简化findViewById
    protected <T extends View> T fd(@IdRes int id) {
        return findViewById(id);
    }

    protected void initNavBar(boolean isShowBack, String title, boolean isShowMe){
        mIvBack = fd(R.id.iv_back);
        mIvMe = fd(R.id.iv_me);
        mTvTitle = fd(R.id.tv_title);

        mIvBack.setVisibility(isShowBack ? View.VISIBLE : View.GONE);
        mIvMe.setVisibility(isShowMe ? View.VISIBLE : View.GONE);
        mTvTitle.setText(title);

        mIvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }
}
