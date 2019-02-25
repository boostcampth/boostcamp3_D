package com.teamdonut.eatto.ui.splash;

import android.Manifest;
import android.os.Bundle;
import android.os.Handler;

import com.kakao.auth.ISessionCallback;
import com.kakao.auth.Session;
import com.kakao.util.exception.KakaoException;
import com.kakao.util.helper.log.Logger;
import com.teamdonut.eatto.R;
import com.teamdonut.eatto.common.util.ActivityUtils;
import com.teamdonut.eatto.common.util.GpsModule;
import com.teamdonut.eatto.databinding.SplashActivityBinding;
import com.tedpark.tedpermission.rx2.TedRx2Permission;

import java.lang.ref.WeakReference;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

public class SplashActivity extends AppCompatActivity {

    private final int SPLASH_TIME = 2000;
    private SplashActivityBinding binding;

    private ISessionCallback iSessionCallback = new ISessionCallback() {
        @Override
        public void onSessionOpened() {
            ActivityUtils.redirectMainActivity(SplashActivity.this);
        }

        @Override
        public void onSessionOpenFailed(KakaoException exception) {
            if (exception != null) {
                Logger.e(exception);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.splash_activity);

        Session.getCurrentSession().addCallback(iSessionCallback);

        requestLocationPermission();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Session.getCurrentSession().removeCallback(iSessionCallback);
    }


    private void requestLocationPermission() {
        TedRx2Permission.with(this)
                .setDeniedMessage(R.string.all_permission_reject)
                .setPermissions(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION)
                .request()
                .doAfterSuccess(tedPermissionResult -> {
                    checkLoginSessionIsValid();
                    binding.mlMain.transitionToEnd();
                })
                .subscribe(tedPermissionResult -> {
                    if (tedPermissionResult.isGranted()) {
                        GpsModule gpsModule = new GpsModule(new WeakReference<>(this), null);
                        gpsModule.startLocationUpdates();
                    }
                }, throwable -> {
                });
    }

    /**
     * 지정한 Splash 시간 이후에 세션 유효여부를 확인.
     * opened -> MainActivity 로 이동.
     * closed -> LoginActivity 로 이동.
     */
    private void checkLoginSessionIsValid() {
        new Handler().postDelayed(() -> {
            if (!Session.getCurrentSession().checkAndImplicitOpen()) {
                ActivityUtils.redirectLoginActivity(this);
                finish();
            }
        }, SPLASH_TIME);
    }
}
