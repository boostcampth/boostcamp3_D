package com.teamdonut.eatto.ui.splash;

import android.Manifest;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import com.kakao.auth.Session;
import com.teamdonut.eatto.R;
import com.teamdonut.eatto.common.util.ActivityUtils;
import com.teamdonut.eatto.common.util.GpsModule;
import com.tedpark.tedpermission.rx2.TedRx2Permission;

import java.lang.ref.WeakReference;

public class SplashActivity extends AppCompatActivity {

    private final int SPLASH_TIME = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity);

        requestLocationPermission();
    }

    private void requestLocationPermission() {
        TedRx2Permission.with(this)
                .setDeniedMessage(R.string.permission_reject)
                .setPermissions(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION)
                .request()
                .doAfterSuccess(tedPermissionResult -> {
                    checkLoginSessionIsValid();
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
            if (Session.getCurrentSession().isOpened()) {
                ActivityUtils.redirectMainActivity(this);
            } else if (Session.getCurrentSession().isClosed()) {
                ActivityUtils.redirectLoginActivity(this);
            }
        }, SPLASH_TIME);
    }
}
