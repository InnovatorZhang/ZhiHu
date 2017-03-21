package com.zhang.zhihu.util;

/**
 * Created by 张 on 2017/3/15.
 */

public interface HttpCallbackListener {
    void onFinish(String response);

    void onError(Exception e);
}
