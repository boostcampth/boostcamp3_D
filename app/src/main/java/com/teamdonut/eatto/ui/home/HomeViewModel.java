package com.teamdonut.eatto.ui.home;

import androidx.lifecycle.MutableLiveData;
import com.teamdonut.eatto.data.Board;
import com.teamdonut.eatto.data.User;
import com.teamdonut.eatto.model.HomeAPI;
import com.teamdonut.eatto.model.ServiceGenerator;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

import java.util.List;

public class HomeViewModel {
    public MutableLiveData<List<User>> userList = new MutableLiveData<>();
    public MutableLiveData<List<Board>> boardList = new MutableLiveData<>();

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public void fetchRecommendBoardList() {
        HomeAPI service = ServiceGenerator.createService(HomeAPI.class);
        compositeDisposable.add(
                service.getRecommendBoards().subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(boards -> {
                                    boardList.setValue(boards);
                                }, (e) -> {
                                    e.printStackTrace();
                                }
                        )
        );
    }

    public void fetchRankUsersList() {
        HomeAPI service = ServiceGenerator.createService(HomeAPI.class);
        compositeDisposable.add(
                service.getTopTenUsers().subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(users -> {
                                    userList.setValue(users);
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
