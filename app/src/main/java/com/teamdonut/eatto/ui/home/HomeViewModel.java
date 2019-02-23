package com.teamdonut.eatto.ui.home;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.teamdonut.eatto.data.Board;
import com.teamdonut.eatto.data.User;
import com.teamdonut.eatto.data.model.board.BoardRepository;
import com.teamdonut.eatto.data.model.user.UserRepository;
import io.reactivex.disposables.CompositeDisposable;

import java.util.List;

public class HomeViewModel extends ViewModel {

    private final MutableLiveData<List<Board>> recommendBoards = new MutableLiveData<>(); //추천게시글
    private final MutableLiveData<List<User>> rankingUsers = new MutableLiveData<>(); //유저 랭킹
    private final MutableLiveData<User> user = new MutableLiveData<>(); //유저
    private final MutableLiveData<Integer> allBoardsSize = new MutableLiveData<>(); //총 게시글 크기.

    private CompositeDisposable disposables = new CompositeDisposable();
    private HomeNavigator mNavigator;

    private BoardRepository mBoardRepository = BoardRepository.getInstance();
    private UserRepository mUserRepository = UserRepository.getInstance();

    public void fetchRecommendBoards(String longitude, String latitude) {
        disposables.add(mBoardRepository.getRecommendBoards(longitude, latitude)
                .subscribe(data -> {
                    if (data != null) {
                        allBoardsSize.postValue(data.first);
                        recommendBoards.postValue(data.second);
                    }
                }, e -> {
                    e.printStackTrace();
                }));
    }

    public void fetchRankUsers() {
        disposables.add(mUserRepository.getTopTenUsers()
                .subscribe(data -> {
                            if (data != null) {
                                rankingUsers.postValue(data);
                            }
                        }, e -> {
                            e.printStackTrace();
                        }
                )
        );
    }

    public void fetchRankUser() {
        disposables.add(mUserRepository.getRankUser()
                .subscribe(data -> {
                            if (data != null) {
                                user.postValue(data);
                            }
                        }
                ));
    }

    public void onSearchClick() {
        mNavigator.goToMapSearch();
    }

    public MutableLiveData<User> getUser() {
        return user;
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
        return rankingUsers;
    }

    public MutableLiveData<List<Board>> getRecommends() {
        return recommendBoards;
    }

    public MutableLiveData<Integer> getAllBoardSize() {
        return allBoardsSize;
    }
}
