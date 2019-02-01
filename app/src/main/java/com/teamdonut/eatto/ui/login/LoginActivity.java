package com.teamdonut.eatto.ui.login;

import android.content.Intent;
import android.os.Bundle;

import com.kakao.auth.Session;
import com.teamdonut.eatto.R;
import com.teamdonut.eatto.databinding.LoginActivityBinding;
import com.teamdonut.eatto.util.ActivityUtils;
import com.teamdonut.eatto.util.kakao.LoginSessionCallback;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

public class LoginActivity extends AppCompatActivity implements LoginNavigator {

    private LoginActivityBinding binding;
    private LoginSessionCallback callback;


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        //로그인 결과를 세션이 받음.
        if (Session.getCurrentSession().handleActivityResult(requestCode, resultCode, data)) {
            return;
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.login_activity);

        initCallback();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Session.getCurrentSession().removeCallback(callback);
    }

    private void initCallback() {
        callback = new LoginSessionCallback(this);
        Session.getCurrentSession().addCallback(callback);
    }


    @Override
    public void redirectLoginActivity() {
        ActivityUtils.redirectLoginActivity(this);
    }

    @Override
    public void redirectMainActivity() {
        ActivityUtils.redirectMainActivity(this);
    }
}
