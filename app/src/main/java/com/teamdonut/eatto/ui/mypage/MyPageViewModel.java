package com.teamdonut.eatto.ui.mypage;

import com.teamdonut.eatto.common.helper.RealmDataHelper;
import com.teamdonut.eatto.data.User;
import com.teamdonut.eatto.model.ServiceGenerator;
import com.teamdonut.eatto.model.UserAPI;

import androidx.databinding.ObservableField;
import androidx.lifecycle.ViewModel;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import io.realm.Realm;

public class MyPageViewModel extends ViewModel {

    private MyPageNavigator mNavigator;

    private UserAPI service = ServiceGenerator.createService(UserAPI.class, ServiceGenerator.BASE);
    private CompositeDisposable disposables = new CompositeDisposable();

    private Realm realm = Realm.getDefaultInstance();

    private ObservableField<User> user = new ObservableField<>();

    public void getUserInformation() {
        disposables.add(
                service.getUserInfo(RealmDataHelper.getAccessId())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnSuccess(data -> updateUserInfo(data))
                        .subscribe(data -> {
                                    user.set(data);
                                }
                                , e -> {
                                    e.printStackTrace();
                                }
                        )
        );
    }

    private void updateUserInfo(User user) {
        RealmDataHelper.updateUser(realm, user);
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

    public void setmNavigator(MyPageNavigator mNavigator) {
        this.mNavigator = mNavigator;
    }

    public ObservableField<User> getUser() {
        return user;
    }
}
