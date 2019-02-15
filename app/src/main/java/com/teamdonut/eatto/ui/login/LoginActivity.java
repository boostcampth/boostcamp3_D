package com.teamdonut.eatto.ui.login;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import com.kakao.auth.Session;
import com.teamdonut.eatto.R;
import com.teamdonut.eatto.common.util.ActivityUtils;
import com.teamdonut.eatto.common.helper.RealmDataHelper;
import com.teamdonut.eatto.common.util.kakao.LoginSessionCallback;
import com.teamdonut.eatto.data.User;
import com.teamdonut.eatto.databinding.LoginActivityBinding;
import io.realm.Realm;

public class LoginActivity extends AppCompatActivity implements LoginNavigator {

    private LoginActivityBinding binding;
    private LoginSessionCallback callback;

    private Realm realm;


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

        realm = Realm.getDefaultInstance();

        initCallback();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
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

    @Override
    public void saveUser(long kakaoId, String name, String photo) {
        if (realm.where(User.class).count() == 0) { //if user is already existed.
            RealmDataHelper.insertUser(realm, kakaoId, name, photo);
        } else {
            RealmDataHelper.updateUser(realm, name, 0, photo);
        }
    }
}
