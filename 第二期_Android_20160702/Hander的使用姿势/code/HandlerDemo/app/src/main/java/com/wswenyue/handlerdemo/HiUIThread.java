package com.wswenyue.handlerdemo;

import android.os.Message;
import android.util.Log;

public class HiUIThread extends Thread {
    private static final String TAG = "HiUIThread";

    @Override
    public void run() {
        Log.e(TAG, "send message to MainActivity");
        Message message = MainActivity.mUIHandler.obtainMessage();
        message.what = 0x112;
        message.obj = "ha ha I am HiUIThread";
        message.sendToTarget();

    }
}
