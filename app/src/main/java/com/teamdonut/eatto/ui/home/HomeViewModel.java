package com.teamdonut.eatto.ui.home;

import androidx.lifecycle.MutableLiveData;
import com.teamdonut.eatto.data.User;
import com.teamdonut.eatto.model.ServiceGenerator;
import com.teamdonut.eatto.model.HomeAPI;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

import java.util.List;

public class HomeViewModel {
    public MutableLiveData<List<User>> userList = new MutableLiveData<>();
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public void fetchRankUsersList(){
        HomeAPI service = ServiceGenerator.createService(HomeAPI.class);
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

    public void unSubscribeFromObservable() {
        compositeDisposable.dispose();
        compositeDisposable = null;
    }
}
