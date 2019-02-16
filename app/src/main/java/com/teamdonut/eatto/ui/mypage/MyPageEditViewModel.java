package com.teamdonut.eatto.ui.mypage;

import android.util.Log;

import com.google.android.gms.common.util.Strings;
import com.teamdonut.eatto.data.User;
import com.teamdonut.eatto.model.ServiceGenerator;
import com.teamdonut.eatto.model.UserAPI;

import androidx.databinding.ObservableInt;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import io.realm.Realm;

public class MyPageEditViewModel extends ViewModel {

    private MyPageEditNavigator mNavigator;

    private UserAPI service = ServiceGenerator.createService(UserAPI.class, ServiceGenerator.BASE);
    private CompositeDisposable disposables = new CompositeDisposable();

    private Realm realm = Realm.getDefaultInstance();

    private User user = realm.copyFromRealm(realm.where(User.class).findFirst());

    public final ObservableInt userSex = new ObservableInt(user.getSex());
    public final ObservableInt userAge = new ObservableInt(user.getAge());

    //whether user info is submitted or not.
    private MutableLiveData<Boolean> isSubmitted = new MutableLiveData<>();

    public void submitUserInformation() {
        disposables.add(new CompositeDisposable(
                service.postUserInfo(user)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doAfterSuccess(data -> {
                            isSubmitted.setValue(true);
                        } )
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(data-> {
                                    //print response log
                                }
                                , e -> {
                                    e.printStackTrace();
                                }
                        ))
        );
    }

    public void onSubmitUserClick(String nickName) {
        if (isSubmissionFormIsRight(nickName, userAge.get())) {
            user.setNickName(nickName);
            user.setSex(userSex.get());
            user.setAge(userAge.get());

            submitUserInformation();
        }
    }

    /**
     * 닉네임과 나이의 형식이 맞는 지 확인한다.
     */
    private Boolean isSubmissionFormIsRight(String nickName, int age) {
        return (age > 14 && age < 81) && !Strings.isEmptyOrWhitespace(nickName);
    }

    public void onAgeTextChanged(CharSequence s, int start, int before, int count) {
        String input = s.toString();

        if (!Strings.isEmptyOrWhitespace(input)) {
            userAge.set(Integer.parseInt(input));
        } else {
            userAge.set(0); //invalid(default) user age.
        }
    }

    public void onShowSelectSexDialogClick() {
        mNavigator.showSelectSexDialog();
    }

    public void onSelectPhotoClick() {
        mNavigator.selectPhoto();
    }

    public User getUser() {
        return user;
    }

    @Override
    protected void onCleared() {
        realm.close();
        disposable.dispose();
        super.onCleared();
    }

    public MutableLiveData<Boolean> getIsSubmitted() {
        return isSubmitted;
    }

    public void setmNavigator(MyPageEditNavigator mNavigator) {
        this.mNavigator = mNavigator;
    }
}
