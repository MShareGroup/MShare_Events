package com.wswenyue.handlerdemo;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import java.lang.ref.WeakReference;

/**
 * 实现Handler的子类或者使用static修饰内部类。
 * 静态的内部类不会持有外部类的引用，所以activity不会被泄露。
 * 如果你要在Handler内调用外部activity类的方法的话，
 * 可以让Handler持有外部activity类的弱引用，这样也不会有泄露activity的风险。
 * <p/>
 * 如果要实例化一个超出activity生命周期的内部类对象，避免使用非静态的内部类。
 * 建议使用静态内部类并且在内部类中持有外部类的弱引用。
 */
public class SecureActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secure);

        mHandler.postDelayed(sRunnable, 1000 * 60 * 10);

        finish();
    }

    private final SecureHandler mHandler = new SecureHandler(this);

    /**
     * 关于匿名类造成的泄露问题，我们可以用static修饰这个匿名类对象解决这个问题，
     * 因为静态的匿名类也不会持有它外部类的引用。
     */
    private static final Runnable sRunnable = new Runnable() {
        @Override
        public void run() {
            //...
        }
    };

    private static class SecureHandler extends Handler {
        private final WeakReference<SecureActivity> mActivity;

        public SecureHandler(SecureActivity activity) {
            mActivity = new WeakReference<SecureActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            SecureActivity activity = mActivity.get();
            if (activity != null) {
                // ...
            }
        }
    }

}
