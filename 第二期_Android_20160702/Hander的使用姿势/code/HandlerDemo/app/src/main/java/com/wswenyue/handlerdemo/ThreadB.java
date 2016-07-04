package com.wswenyue.handlerdemo;

import android.os.Message;
import android.util.Log;

public class ThreadB extends Thread {

    private static final String TAG = "ThreadB";

    public ThreadB() {
    }

    @Override
    public void run() {
        Log.e(TAG, "send message to threadA");
        Message msg = ThreadA.mHandlerA.obtainMessage();
        msg.what = 0x110;
        msg.obj = "Hello ThreadA, I am ThreadB";
        msg.sendToTarget();
    }
}
