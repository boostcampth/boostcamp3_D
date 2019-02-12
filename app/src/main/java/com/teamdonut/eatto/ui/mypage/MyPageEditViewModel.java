package com.teamdonut.eatto.ui.mypage;

import androidx.databinding.ObservableField;

public class MyPageEditViewModel {

    private MyPageEditNavigator mNavigator;

    public final ObservableField<String> userSex = new ObservableField<>();

    public MyPageEditViewModel(MyPageEditNavigator navigator) {
        this.mNavigator = navigator;
    }

    public void onShowSelectSexDialogClick() {
        mNavigator.showSelectSexDialog();
    }

}
