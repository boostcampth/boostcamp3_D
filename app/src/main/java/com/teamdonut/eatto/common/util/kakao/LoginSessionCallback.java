package com.teamdonut.eatto.common.util.kakao;

import android.util.Log;

import com.kakao.auth.ISessionCallback;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.ApiErrorCode;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.MeV2ResponseCallback;
import com.kakao.usermgmt.response.MeV2Response;
import com.kakao.util.exception.KakaoException;
import com.kakao.util.helper.log.Logger;
import com.teamdonut.eatto.ui.login.LoginNavigator;

public class LoginSessionCallback implements ISessionCallback {

    private LoginNavigator navigator;

    public LoginSessionCallback(LoginNavigator navigator) {
        this.navigator = navigator;
    }

    @Override
    public void onSessionOpened() {
        requestMe();
    }

    @Override
    public void onSessionOpenFailed(KakaoException exception) {
        if (exception != null) {
            Logger.e(exception);
        }
    }

    private void requestMe() {
        UserManagement.getInstance().me(new MeV2ResponseCallback() {
            @Override
            public void onSessionClosed(ErrorResult errorResult) {
                navigator.redirectLoginActivity();
            }

            @Override
            public void onFailure(ErrorResult errorResult) {
                Logger.d("failed to get user info. msg = $errorResult");

                if (errorResult.getErrorCode() == ApiErrorCode.CLIENT_ERROR_CODE) {
                    Logger.d("error failed.");
                } else {
                    navigator.redirectLoginActivity();
                }
            }

            @Override
            public void onSuccess(MeV2Response result) {
                if (result != null) {
                    //여기서 서버로 사용자의 정보를 보냄.
                    navigator.redirectMainActivity();
                }
            }
        });
    }
}
