package com.xjstest.mvp.view;

/**
 * 登录View的接口，实现类也就是登录的activity
 * @author 徐金山
 * @version 1.0
 */
public interface LoginView {
    void showProgress();
    void hideProgress();
    void setUsernameError();
    void setPasswordError();
    void navigateToHome();
}
