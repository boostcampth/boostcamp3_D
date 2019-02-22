package com.teamdonut.eatto.ui.mypage;

import androidx.databinding.ObservableField;
import androidx.lifecycle.ViewModel;
import com.teamdonut.eatto.common.helper.RealmDataHelper;
import com.teamdonut.eatto.data.User;
import com.teamdonut.eatto.data.model.user.UserRepository;
import io.reactivex.disposables.CompositeDisposable;
import io.realm.Realm;

public class MyPageViewModel extends ViewModel {

    private MyPageNavigator mNavigator;

    private CompositeDisposable disposables = new CompositeDisposable();

    private Realm realm = Realm.getDefaultInstance();

    private UserRepository userRepository = UserRepository.getInstance();

    private final ObservableField<User> user = new ObservableField<>();

    public void getUserInformation() {
        disposables.add(userRepository.getUser(data -> {
            user.set(data);
        }, data -> {
            updateUserInfo(data);
        }));
    }

    private void updateUserInfo(User user) {
        RealmDataHelper.updateUser(user);
    }

    public void onJudgeClick() {
        mNavigator.goJudge();
    }

    public void onProfileEditClick() {
        mNavigator.goToProfileEdit();
    }

    public void onLogoutClick() {
        mNavigator.makeUserLogout();
    }

    @Override
    protected void onCleared() {
        realm.close();
        disposables.dispose();
        super.onCleared();
    }

    public void setNavigator(MyPageNavigator mNavigator) {
        this.mNavigator = mNavigator;
    }

    public ObservableField<User> getUser() {
        return user;
    }
}
