package com.xjstest.mvp.presenter;

import com.xjstest.mvp.model.LoginModel;
import com.xjstest.mvp.model.LoginModelImpl;
import com.xjstest.mvp.view.LoginView;

/**
 * 1 完成presenter的实现。这里面主要是Model层和View层的交互和操作。
 * 2  presenter里面还有个OnLoginFinishedListener，
 * 其在Presenter层实现，给Model层回调，更改View层的状态，
 * 确保 Model层不直接操作View层。如果没有这一接口在LoginPresenterImpl实现的话，
 * LoginPresenterImpl只有View和Model的引用那么Model怎么把结果告诉View呢？
 * @author 徐金山
 * @version 1.0
 */
public class LoginPresenterImpl implements LoginPresenter, OnLoginFinishedListener {
    private LoginView loginView;
    private LoginModel loginModel;

    public LoginPresenterImpl(LoginView loginView) {
        this.loginView = loginView;
        this.loginModel = new LoginModelImpl();
    }

    /**
     * 登录处理
     * @param username 登录名
     * @param password 登录密码
     */
    public void validateCredentials(String username, String password) {
        if (loginView != null) {
            loginView.showProgress();
        }

        loginModel.login(username, password, this);
    }

    public void onDestroy() {
        loginView = null;
    }

    public void onUsernameError() {
        if (loginView != null) {
            loginView.setUsernameError();
            loginView.hideProgress();
        }
    }

    public void onPasswordError() {
        if (loginView != null) {
            loginView.setPasswordError();
            loginView.hideProgress();
        }
    }

    /**
     * 登录成功的处理
     */
    public void onSuccess() {
        if (loginView != null) {
            loginView.navigateToHome();
        }
    }
}
