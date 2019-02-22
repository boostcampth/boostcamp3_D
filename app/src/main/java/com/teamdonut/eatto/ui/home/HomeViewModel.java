package com.teamdonut.eatto.ui.home;

import com.teamdonut.eatto.data.Board;
import com.teamdonut.eatto.data.User;
import com.teamdonut.eatto.data.model.board.BoardRepository;
import com.teamdonut.eatto.data.model.user.UserRepository;

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
    private final MutableLiveData<Integer> mAllBoardSize = new MutableLiveData<>(); //총 게시글 크기.

    private CompositeDisposable disposables = new CompositeDisposable();
    private HomeNavigator mNavigator;

    private BoardRepository mBoardRepository = BoardRepository.getInstance();
    private UserRepository mUserRepository = UserRepository.getInstance();

    public void fetchRecommendBoards(String longitude, String latitude) {
        disposables.add(mBoardRepository.getRecommendBoards(longitude, latitude)
                .subscribe(data -> {
                    if (data != null) {
                        mAllBoardSize.setValue(data.first);
                        mRecommends.setValue(data.second);
                    }
                }, e -> {
                    e.printStackTrace();
                }));
    }

    public void fetchRankUsers() {
        disposables.add(mUserRepository.getTopTenUsers()
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
        disposables.add(mUserRepository.getUser()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(data -> {
                            if (data != null) {
                                mUser.postValue(data);
                            }
                        }, Throwable::printStackTrace
                ));
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
