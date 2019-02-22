package com.teamdonut.eatto.ui.home;

import androidx.databinding.ObservableInt;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.teamdonut.eatto.common.helper.RealmDataHelper;
import com.teamdonut.eatto.data.Board;
import com.teamdonut.eatto.data.User;
import com.teamdonut.eatto.data.model.board.BoardRepository;
import com.teamdonut.eatto.data.model.user.UserRepository;
import io.reactivex.disposables.CompositeDisposable;

import java.util.ArrayList;
import java.util.List;

public class HomeViewModel extends ViewModel {
    private MutableLiveData<List<Board>> anyBoards = new MutableLiveData<>();
    private MutableLiveData<User> user = new MutableLiveData<>();
    private MutableLiveData<Integer> boardFlag = new MutableLiveData<>();
    private ObservableInt anyBoardsSize = new ObservableInt(0);

    private UserRankingAdapter userRankingAdapter = new UserRankingAdapter(new ArrayList<>());
    private BoardRecommendAdapter boardRecommendAdapter = new BoardRecommendAdapter(new ArrayList<>());

    private UserRepository userRepository = UserRepository.getInstance();
    private BoardRepository boardRepository = BoardRepository.getInstance();

    private CompositeDisposable disposables = new CompositeDisposable();
    private HomeNavigator mNavigator;

    public HomeViewModel(HomeNavigator navigator) {
        this.mNavigator = navigator;
        boardFlag.setValue(0);
    }

    public void fetchRecommendBoards(String longitude, String latitude) {
        disposables.add(boardRepository.getRecommendBoards(data -> {
            boardRecommendAdapter.setItem(data);
        }, data -> {
            if (data.size() == 0) {
                boardFlag.setValue(boardFlag.getValue() + 1);
            }
        }, longitude, latitude));
    }

    public void fetchAnyBoards() {
        disposables.add(boardRepository.getTodayBoards(data -> {
            anyBoards.setValue(data);
            anyBoardsSize.set(data.size());
        }, data -> {
            boardFlag.setValue(boardFlag.getValue() + 1);
        }));
    }

    public void fetchRankUsers() {
        disposables.add(userRepository.getTopTenUser(data -> {
            userRankingAdapter.updateItems(data);
        }));
    }

    public void fetchRankUser() {
        disposables.add(userRepository.getUser(data -> {
            user.postValue(data);
        }, data -> {
            updateUserInfo(data);
        }));
    }

    private void updateUserInfo(User user) {
        RealmDataHelper.updateUser(user);
    }

    public void onSearchClick() {
        mNavigator.goToMapSearch();
    }

    public void onDestroyViewModel() {
        disposables.dispose();
    }

    public MutableLiveData<List<Board>> getAnyBoards() {
        return anyBoards;
    }

    public MutableLiveData<User> getUser() {
        return user;
    }

    public MutableLiveData<Integer> getBoardFlag() {
        return boardFlag;
    }

    public UserRankingAdapter getUserRankingAdapter() {
        return userRankingAdapter;
    }

    public BoardRecommendAdapter getBoardRecommendAdapter() {
        return boardRecommendAdapter;
    }

    public ObservableInt getAnyBoardsSize() {
        return anyBoardsSize;
    }
}
