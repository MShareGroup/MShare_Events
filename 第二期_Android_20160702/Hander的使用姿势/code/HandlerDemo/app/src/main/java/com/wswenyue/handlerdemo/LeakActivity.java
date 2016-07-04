package com.wswenyue.handlerdemo;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

/**
 * 当activity被finish的时候，延迟发送的消息仍然会存活在UI线程的消息队列中，
 * 直到10分钟后它被处理掉。这个消息持有activity的Handler的引用，
 * Handler又隐式的持有它的外部类(这里就是LeakActivity)的引用。
 * 这个引用会一直存在直到这个消息被处理，所以垃圾回收机制就没法回收这个activity，
 * 内存泄露就发生了.
 * 非静态的匿名类会隐式的持有外部类的引用，所以context会被泄露掉
 * <p/>
 * 在Java中，非静态的内部类或者匿名类会隐式的持有其外部类的引用，而静态的内部类则不会。
 */
public class LeakActivity extends Activity {
    private final Handler mLeakyHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            //...
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leak);

        mLeakyHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //...
            }
        }, 1000 * 60 * 10);
        //...
        finish();

    }


}
