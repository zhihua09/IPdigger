package com.example.tom.ipdigger.util;

/**
 * Created by Tom on 2016/2/11.
 */
public interface HttpCallbackListener {
    void onFinish(String response);

    void onError(Exception e);
}
