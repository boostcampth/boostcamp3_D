package com.teamdonut.eatto.ui.mypage;

import androidx.lifecycle.ViewModel;

public class MyPageViewModel extends ViewModel {

    MyPageNavigator mNavigator;

    MyPageViewModel(MyPageNavigator navigator) {
        this.mNavigator = navigator;
    }

    public void onProfileEditClick() {
        mNavigator.goToProfileEdit();
    }

    public void onLogoutClick() {
       mNavigator.makeUserLogout();
    }
}
