package com.teamdonut.eatto.ui.home;

import com.teamdonut.eatto.common.helper.RealmDataHelper;
import com.teamdonut.eatto.data.Board;
import com.teamdonut.eatto.data.User;
import com.teamdonut.eatto.model.HomeAPI;
import com.teamdonut.eatto.model.ServiceGenerator;

import java.util.List;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class HomeViewModel extends ViewModel {

    private final MutableLiveData<Integer> boardFlag = new MutableLiveData<>(); //추천게시글 존재 여부 판단.
    private final MutableLiveData<List<Board>> mRecommends = new MutableLiveData<>(); //추천게시글
    private final MutableLiveData<List<User>> mRankings = new MutableLiveData<>(); //유저 랭킹
    private final MutableLiveData<User> mUser = new MutableLiveData<>(); //유저
    private final MutableLiveData<Integer> mAllBoardSize = new MutableLiveData<>();

    private CompositeDisposable disposables = new CompositeDisposable();
    private HomeAPI service = ServiceGenerator.createService(HomeAPI.class, ServiceGenerator.BASE);
    private HomeNavigator mNavigator;


    public void fetchRecommendBoards(String longitude, String latitude) {
        Single<List<Board>> mRecommendSingle = service.getRecommendBoards(longitude, latitude);
        Single<List<Board>> mAllSingle = service.getAllBoards();

        disposables.add(
                Single.zip(mRecommendSingle, mAllSingle,
                        (recommends, other) -> {
                            mAllBoardSize.postValue(other.size());
                            return (recommends.size() == 0) ? other : recommends;
                        })
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe(data -> {
                                    if (data != null) {
                                        mRecommends.postValue(data);
                                    }
                                }, e -> {
                                    e.printStackTrace();
                                }
                        ));
    }

    public void fetchRankUsers() {
        disposables.add(
                service.getTopTenUsers()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(data -> {
                                    if (data != null) {
                                        mRankings.postValue(data);
                                    }
                                }, e -> {
                                    e.printStackTrace();
                                }
                        )
        );
    }

    public void fetchRankUser() {
        disposables.add(
                service.getRankUser(RealmDataHelper.getUser().getKakaoId())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(data -> {
                                    if (data != null) {
                                        mUser.postValue(data);
                                    }
                                }, e -> {
                                    e.printStackTrace();
                                }
                        )
        );
    }

    public void onSearchClick() {
        mNavigator.goToMapSearch();
    }

    public MutableLiveData<User> getUser() {
        return mUser;
    }

    public void setNavigator(HomeNavigator navigator) {
        this.mNavigator = navigator;
    }

    @Override
    protected void onCleared() {
        disposables.dispose();
        super.onCleared();
    }

    public MutableLiveData<List<User>> getRankings() {
        return mRankings;
    }


    public MutableLiveData<Integer> getBoardFlag() {
        return boardFlag;
    }

    public MutableLiveData<List<Board>> getRecommends() {
        return mRecommends;
    }

    public MutableLiveData<Integer> getAllBoardSize() {
        return mAllBoardSize;
    }
}
