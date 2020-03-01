package com.example.sharechatting.Views;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.InputType;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.example.sharechatting.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Reference input_icon
 * String input_hint
 * boolean is_password
 */

public class InputView extends FrameLayout {
    private int input_icon;
    private String input_hint;
    private boolean is_password;

    private View mView;
    private ImageView mIvIcon;
    private EditText mEtText;



    public InputView(@NonNull Context context) {
        super(context);
        init(context, null);
    }

    public InputView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);

    }

    public InputView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);

    }

    public InputView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);

    }

    private void init(Context context, AttributeSet attrs){
        if(attrs == null)return;
        // 获取自定义属性
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.InputView);

        // 获取不到用户传入的input_icon时,使用默认的图片
        input_icon = typedArray.getResourceId(R.styleable.InputView_input_icon, R.drawable.icon_login_admin_default);
        input_hint = typedArray.getString(R.styleable.InputView_input_hint);
        is_password = typedArray.getBoolean(R.styleable.InputView_is_password, false); // 获取不到is_password的时候,默认值false
        // 使用完成后释放
        typedArray.recycle();

        // 绑定layout布局
        mView = LayoutInflater.from(context).inflate(R.layout.input_view, this,false);
        mIvIcon = mView.findViewById(R.id.iv_icon);
        mEtText = mView.findViewById(R.id.et_text);

        // 布局关联属性
        mIvIcon.setImageResource(input_icon);
        mEtText.setHint(input_hint);
        mEtText.setInputType(is_password ? InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD : InputType.TYPE_CLASS_PHONE);

        addView(mView);
    }

    /**
     * 返回输入的内容
     * @return
     */
    public String getInputStr(){
        return mEtText.getText().toString().trim();
    }
}
