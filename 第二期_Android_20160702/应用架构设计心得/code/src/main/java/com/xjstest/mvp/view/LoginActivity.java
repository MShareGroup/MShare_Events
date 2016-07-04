package com.xjstest.mvp.view;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.xjstest.mvp.R;
import com.xjstest.mvp.presenter.LoginPresenter;
import com.xjstest.mvp.presenter.LoginPresenterImpl;


/**
 * MVP模式中View层对应一个activity，这里是登录的activity
 * @author 徐金山
 * @version 1.0
 */
public class LoginActivity extends Activity implements LoginView, View.OnClickListener {
    /** 圈形进度条 */
    private LinearLayout progressBar;
    /** 用户名输入框 */
    private EditText username;
    /** 密码输入框 */
    private EditText password;
    /** 登录按钮 */
    private Button loginButton;
    /** 表示器 */
    private LoginPresenter presenter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        findViews();
        setListener();
        setPresenter();
    }

    /**
     * 设置presenter
     */
    private void setPresenter() {
        presenter = new LoginPresenterImpl(this);
    }

    /**
     * 获取控件对象
     */
    private void findViews() {
        progressBar = (LinearLayout) findViewById(R.id.progress);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        loginButton = (Button)findViewById(R.id.loginButton);
    }

    /**
     * 监听绑定
     */
    private void setListener() {
        loginButton.setOnClickListener(this);
    }

    public void onClick(View v) {
        presenter.validateCredentials(username.getText().toString(), password.getText().toString());
    }

    protected void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }

    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

    public void setUsernameError() {
        username.setError(getString(R.string.username_error));
    }

    public void setPasswordError() {
        password.setError(getString(R.string.password_error));
    }

    /**
     * 进行页面迁移处理
     */
    public void navigateToHome() {
        hideProgress();
        Toast.makeText(this,"login success",Toast.LENGTH_SHORT).show();
        // startActivity(new Intent(this, MainActivity.class));
        // finish();
    }
}
