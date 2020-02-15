package com.example.sharechatting.chatkit;
import android.content.Context;
import android.text.TextUtils;

import cn.leancloud.AVException;
import cn.leancloud.AVLogger;
import cn.leancloud.AVOSCloud;
import cn.leancloud.callback.AVCallback;
import cn.leancloud.im.v2.AVIMClient;
import cn.leancloud.im.v2.AVIMException;
import cn.leancloud.im.v2.callback.AVIMClientCallback;
import cn.leancloud.utils.StringUtil;

public final class Chatkit {
    private String currentUserId;

    private Chatkit(){}

    public static void init(Context context){
        AVOSCloud.setLogLevel(AVLogger.Level.DEBUG);
        // 初始化leancloud
        AVOSCloud.initialize(context,
                "6RMuubE0NyLH4rkCeCcy8eQX-gzGzoHsz",
                "lcVhdGzvs9nSGbkkSDufy5mp",
                "https://6rmuube0.lc-cn-n1-shared.com");
    }

    /**
   * 开启实时聊天
   *
   * @param userId
   * @param callback
   */
  public void open(final String userId, final AVIMClientCallback callback) {
    open(userId, null, callback);
  }

  /**
   * 开启实时聊天
   * @param userId 实时聊天的 clientId
   * @param tag 单点登录标示
   * @param callback
   */
  public void open(final String userId, String tag, final AVIMClientCallback callback) {
    if (TextUtils.isEmpty(userId)) {
      throw new IllegalArgumentException("userId can not be empty!");
    }
    if (null == callback) {
      throw new IllegalArgumentException("callback can not be null!");
    }

    AVIMClientCallback openCallback = new AVIMClientCallback() {
      @Override
      public void done(final AVIMClient avimClient, AVIMException e) {
        if (null == e) {
          currentUserId = userId;
//          LCIMProfileCache.getInstance().initDB(AVOSCloud.getContext(), userId);
//          LCIMConversationItemCache.getInstance().initDB(AVOSCloud.getContext(), userId, new AVCallback() {
//            @Override
//            protected void internalDone0(Object o, AVException e) {
//              callback.internalDone(avimClient, e);
//            }
//          });
        } else {
          callback.internalDone(avimClient, e);
        }
      }
    };

    if (StringUtil.isEmpty(tag)) {
      AVIMClient.getInstance(userId).open(openCallback);
    } else {
      AVIMClient.getInstance(userId, tag).open(openCallback);
    }
  }

  /**
   * 获取当前的实时聊天的用户
   *
   * @return
   */
  public String getCurrentUserId() {
    return currentUserId;
  }

  /**
   * 获取当前的 AVIMClient 实例
   *
   * @return
   */
  public AVIMClient getClient() {
    if (!TextUtils.isEmpty(currentUserId)) {
      return AVIMClient.getInstance(currentUserId);
    }
    return null;
  }


}
