package com.teamdonut.eatto.ui.mypage;

import com.teamdonut.eatto.common.helper.RealmDataHelper;
import com.teamdonut.eatto.data.User;
import com.teamdonut.eatto.data.model.user.UserRepository;

import androidx.databinding.ObservableField;
import androidx.lifecycle.ViewModel;
import io.reactivex.disposables.CompositeDisposable;

public class MyPageViewModel extends ViewModel {

    private MyPageNavigator navigator;

    private CompositeDisposable disposables = new CompositeDisposable();

    private UserRepository userRepository = UserRepository.getInstance();

    private final ObservableField<User> user = new ObservableField<>();

    public void getUserInformation() {
        disposables.add(userRepository.getUser()
                .doOnSuccess(this::updateUserInfo)
                .subscribe(data -> {
                    if (data != null) {
                        user.set(data);
                    }
                }, e -> {
                    e.printStackTrace();
                }));
    }

    private void updateUserInfo(User user) {
        RealmDataHelper.updateUser(user);
    }

    public void onJudgeClick() {
        navigator.goJudge();
    }

    public void onProfileEditClick() {
        navigator.goToProfileEdit();
    }

    public void onLogoutClick() {
        navigator.makeUserLogout();
    }

    @Override
    protected void onCleared() {
        disposables.dispose();
        super.onCleared();
    }

    public void setNavigator(MyPageNavigator mNavigator) {
        this.navigator = mNavigator;
    }

    public ObservableField<User> getUser() {
        return user;
    }
}
