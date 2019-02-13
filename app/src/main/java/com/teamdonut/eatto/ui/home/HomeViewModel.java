package com.teamdonut.eatto.ui.home;

import androidx.lifecycle.MutableLiveData;
import com.teamdonut.eatto.data.User;
import com.teamdonut.eatto.model.ServiceGenerator;
import com.teamdonut.eatto.model.UserAPI;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

import java.util.ArrayList;

public class HomeViewModel {
    public MutableLiveData<ArrayList<User>> userList = new MutableLiveData<>();

    public void fetchRankUsersList(){
        UserAPI service = ServiceGenerator.createService(UserAPI.class);
        CompositeDisposable compositeDisposable = new CompositeDisposable();
        compositeDisposable.add(
                service.getTopTenUsers().subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe((data) -> {
                                    userList.setValue(data);
                                }, (e) -> {
                                    e.printStackTrace();
                                }
                        )
        );
    }
}
