package com.wswenyue.handlerdemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;

public class MainActivity extends Activity {
    private ThreadLocal<Boolean> mThreadLocal = new ThreadLocal<>();
    public static final String TAG_THREADLOCAL = "ThreadLocal";
    private static final String TAG = "MainActivity";
    public static Handler mUIHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0x112) {
                Log.e(TAG, "收到消息>>" + msg.obj.toString());
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * ThreadLocal test
     *
     * @param view
     */
    public void threadClick(View view) {
        mThreadLocal.set(true);
        Log.e(TAG_THREADLOCAL, Thread.currentThread().getName() + "##Value>>" + mThreadLocal.get());
        new Thread("Thread#1#") {
            @Override
            public void run() {
                mThreadLocal.set(false);
                Log.e(TAG_THREADLOCAL, Thread.currentThread().getName() + "##Value>>" + mThreadLocal.get());
            }
        }.start();
        new Thread("Thread#2#") {
            @Override
            public void run() {
                Log.e(TAG_THREADLOCAL, Thread.currentThread().getName() + "##Value>>" + mThreadLocal.get());
            }
        }.start();

    }


    /**
     * 子线程之间通过Handler通信
     *
     * @param view
     */
    public void testHandlerThread(View view) {
        ThreadA threadA = new ThreadA();
        threadA.start();

        ThreadB threadB = new ThreadB();
        threadB.start();
    }

    /**
     * 子线程给UI(main)线程发送消息
     *
     * @param view
     */
    public void sendMsgToMain(View view) {
        HiUIThread hiUIThread = new HiUIThread();
        hiUIThread.start();
    }

    /**
     * 非静态的内部类或者匿名类会隐式的持有其外部类的引用,造成内存泄漏
     *
     * @param view
     */
    public void gotoLeakActivity(View view) {
        Intent i = new Intent(this, LeakActivity.class);
        startActivity(i);
    }

    /**
     * 解决方案
     *
     * @param view
     */
    public void gotoSecureActivity(View view) {
        Intent i = new Intent(this, SecureActivity.class);
        startActivity(i);
    }


}
