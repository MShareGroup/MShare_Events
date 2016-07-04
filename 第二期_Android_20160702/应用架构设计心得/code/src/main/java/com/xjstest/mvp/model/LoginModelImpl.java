package com.xjstest.mvp.model;

import android.os.Handler;
import android.text.TextUtils;

import com.xjstest.mvp.presenter.OnLoginFinishedListener;

/**
 * 延时模拟登录（2s），如果名字或者密码为空则登录失败，否则登录成功
 * @author 徐金山
 * @version 1.0
 */
public class LoginModelImpl implements LoginModel {
    /**
     * 登录处理
     * @param username 登录名
     * @param password 登录密码
     * @param listener 表示器
     */
    public void login(final String username, final String password, final OnLoginFinishedListener listener) {
        new Handler().postDelayed(new Runnable() {
            public void run() {
                // 业务处理错误标识（true：存在业务错误；false：不存在业务错误）
                boolean error = false;
                // 判断“登录名”是否有效
                if (TextUtils.isEmpty(username)){
                    listener.onUsernameError();//model层里面回调listener
                    error = true;
                }
                // 判断“登录密码”是否有效
                else if (TextUtils.isEmpty(password)){
                    listener.onPasswordError();
                    error = true;
                }
                // 判断业务处理是否成功
                if (!error){
                    listener.onSuccess();
                }
            }
        }, 2000);
    }
}
