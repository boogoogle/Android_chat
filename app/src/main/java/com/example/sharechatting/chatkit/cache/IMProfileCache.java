package com.example.sharechatting.chatkit.cache;

import android.content.Context;

/**
 * Created by wli on 16/2/25.
 * 用户信息缓存
 * 流程：
 * 1、如果内存中有则从内存中获取
 * 2、如果内存中没有则从 db 中获取
 * 3、如果 db 中没有则通过调用开发者设置的回调 LCChatProfileProvider.fetchProfiles 来获取
 * 同时获取到的数据会缓存到内存与 db
 */

public class IMProfileCache {



    public static IMProfileCache profileCache;
    public static synchronized IMProfileCache getInstance() {
        if (null == profileCache) {
            profileCache = new IMProfileCache();
        }
        return profileCache;
    }





}
