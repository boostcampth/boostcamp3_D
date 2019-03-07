package com.teamdonut.eatto.ui.home;

import com.teamdonut.eatto.data.Board;
import com.teamdonut.eatto.data.User;
import com.teamdonut.eatto.data.model.board.BoardRepository;
import com.teamdonut.eatto.data.model.user.UserRepository;

import java.util.List;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import io.reactivex.disposables.CompositeDisposable;

public class HomeViewModel extends ViewModel {

    private final MutableLiveData<List<Board>> recommendBoards = new MutableLiveData<>(); //추천게시글
    private final MutableLiveData<List<User>> rankingUsers = new MutableLiveData<>(); //유저 랭킹
    private final MutableLiveData<User> user = new MutableLiveData<>(); //유저
    private final MutableLiveData<Integer> allBoardsSize = new MutableLiveData<>(); //총 게시글 크기.

    private CompositeDisposable disposables = new CompositeDisposable();
    private HomeNavigator navigator;

    private BoardRepository boardRepository = BoardRepository.getInstance();
    private UserRepository userRepository = UserRepository.getInstance();

    public void fetchRecommendBoards(String longitude, String latitude) {
        disposables.add(boardRepository.getRecommendBoards(longitude, latitude)
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
        disposables.add(userRepository.getTopTenUsers()
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
        disposables.add(userRepository.getRankUser()
                .subscribe(data -> {
                            if (data != null) {
                                user.postValue(data);
                            }
                        }, e -> {
                            e.printStackTrace();
                        }
                ));
    }

    public void onPreviewClick(Board board) {
        navigator.openBoardPreview(board);
    }

    public void onSearchClick() {
        navigator.goToMapSearch();
    }

    public MutableLiveData<User> getUser() {
        return user;
    }

    public void setNavigator(HomeNavigator navigator) {
        this.navigator = navigator;
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
