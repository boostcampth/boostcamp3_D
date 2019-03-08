package com.teamdonut.eatto.ui.mypage;

import com.google.android.gms.common.util.Strings;
import com.teamdonut.eatto.common.helper.RealmDataHelper;
import com.teamdonut.eatto.data.User;
import com.teamdonut.eatto.data.model.user.UserRepository;

import androidx.databinding.ObservableInt;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import io.reactivex.disposables.CompositeDisposable;

public class MyPageEditViewModel extends ViewModel {

    private MyPageEditNavigator navigator;

    private CompositeDisposable disposables = new CompositeDisposable();

    private User user = RealmDataHelper.getUser();


    private final ObservableInt userSex = new ObservableInt(user.getSex());
    private final ObservableInt userAge = new ObservableInt(user.getAge());
    private final MutableLiveData<Boolean> isSubmitted = new MutableLiveData<>();

    private UserRepository userRepository = UserRepository.getInstance();


    public void submitUserInformation() {
        disposables.add(userRepository.postUser(user)
                .doAfterSuccess(data -> {
                    isSubmitted.setValue(true);
                })
                .subscribe((data) -> {

                }, e -> {
                    e.printStackTrace();
                }));
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
        navigator.showSelectSexDialog();
    }

    public void onSelectPhotoClick() {
        navigator.selectPhoto();
    }

    public User getUser() {
        return user;
    }

    @Override
    protected void onCleared() {
        disposables.dispose();
        super.onCleared();
    }

    public MutableLiveData<Boolean> getIsSubmitted() {
        return isSubmitted;
    }

    public void setNavigator(MyPageEditNavigator mNavigator) {
        this.navigator = mNavigator;
    }

    public ObservableInt getUserSex() {
        return userSex;
    }

    public ObservableInt getUserAge() {
        return userAge;
    }
}
