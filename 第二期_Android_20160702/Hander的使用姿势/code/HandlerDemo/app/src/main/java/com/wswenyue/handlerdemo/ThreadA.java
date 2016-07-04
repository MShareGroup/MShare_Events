package com.wswenyue.handlerdemo;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

public class ThreadA extends Thread {
    private static final String TAG = "ThreadA";
    public static Handler mHandlerA;

    public ThreadA() {

    }

    @Override
    public void run() {
        Looper.prepare();
        mHandlerA = new Handler(Looper.myLooper()) {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 0x110) {
                    Log.e(TAG, "收到消息>>" + msg.obj.toString());
                }
            }
        };
        Looper.loop();
    }

}
