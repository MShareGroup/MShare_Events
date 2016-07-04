package com.xjstest.mvp.presenter;

/**
 * 登录的　Presenter 的接口，实现类为LoginPresenterImpl，完成登录的验证，以及销毁当前view
 * @author 徐金山
 * @version 1.0
 */
public interface LoginPresenter {
    /**
     * 登录验证处理
     * @param username 登录名
     * @param password 登录密码
     */
    void validateCredentials(String username, String password);

    void onDestroy();
}
