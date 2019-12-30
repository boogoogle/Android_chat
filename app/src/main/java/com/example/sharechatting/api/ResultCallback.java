package com.example.sharechatting.api;


import java.lang.reflect.Type;
import java.lang.reflect.ParameterizedType;

import com.google.gson.internal.$Gson$Types;

import okhttp3.Request;


/**
 * 抽象类,用于请求成功后的回调
 */
public abstract class ResultCallback<T> {
    Type mType; // 请求数据的返回类型,包含常见的(Bean, List)等

    public ResultCallback() {
        mType = getSuperclassTypeParameter(this.getClass());
    }


    /**
     * 通过反射得到想要的返回类型
     * @param subclass
     * @return
     */
    static Type getSuperclassTypeParameter(Class<?> subclass) {
        Type superclass = subclass.getGenericSuperclass(); // 返回父类,并包含父类的参数化类型

        // 这个判断啥意思 ?
        if(superclass instanceof Class) {
            throw new RuntimeException("Missing type parameter");
        }

        ParameterizedType parameterized = (ParameterizedType) superclass;
        return $Gson$Types.canonicalize(parameterized.getActualTypeArguments()[0]); // T
    }

    /**
     * 在请求之前的方法，一般用于加载框展示
     *
     * @param request
     */
    public void onBefore(Request request) {
    }

    /**
     * 在请求之后的方法，一般用于加载框隐藏
     */
    public void onAfter() {
    }

    /**
     * 请求失败的时候
     *
     * @param request
     * @param e
     */
    public abstract void onError(Request request, Exception e);

    /**
     *
     * @param response
     */
    public abstract void onResponse(T response);


}
