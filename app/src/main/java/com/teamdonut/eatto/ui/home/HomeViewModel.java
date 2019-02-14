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
    private CompositeDisposable disposables = new CompositeDisposable();
    private HomeAPI service = ServiceGenerator.createService(HomeAPI.class, ServiceGenerator.BASE);

    public void fetchRecommendBoardList() {
        disposables.add(
                service.getRecommendBoards()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(data -> {
                                    boardList.setValue(data);
                                }, e -> {
                                    e.printStackTrace();
                                }
                        )
        );
    }

    public void fetchRankUsersList() {
        disposables.add(
                service.getTopTenUsers()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(data -> {
                                    userList.setValue(data);
                                }, e -> {
                                    e.printStackTrace();
                                }
                        )
        );
    }

    public void onDestroyViewModel() {
        disposables.dispose();
    }
}
