package com.wswenyue.handlerdemo;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;

public class HandlerThreadDemo extends HandlerThread {
    public static Handler mHandler;

    public HandlerThreadDemo(String name) {
        super(name);
    }

    public HandlerThreadDemo(String name, int priority) {
        super(name, priority);
    }

    @Override
    protected void onLooperPrepared() {
        mHandler = new Handler(getLooper()) {
            @Override
            public void handleMessage(Message msg) {
                //...
            }
        };
    }

}
