package com.teamdonut.eatto.ui.map;

import androidx.databinding.ObservableBoolean;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.google.android.gms.maps.model.LatLng;
import com.teamdonut.eatto.common.RxBus;
import com.teamdonut.eatto.data.Board;
import com.teamdonut.eatto.data.Filter;
import com.teamdonut.eatto.data.model.board.BoardRepository;
import io.reactivex.disposables.CompositeDisposable;

import java.util.List;

public class MapViewModel extends ViewModel {

    private MapNavigator mNavigator;

    RxBus rxbus = RxBus.getInstance();
    private CompositeDisposable disposables = new CompositeDisposable();

    private final MutableLiveData<Board> mOpenBoardEvent = new MutableLiveData<>();
    private final MutableLiveData<List<Board>> boards = new MutableLiveData<>();

    private BoardRepository boardRepository = BoardRepository.getInstance();
    private Filter filter;

    public final ObservableBoolean isSheetExpanded = new ObservableBoolean(false);

    public void loadBoards() {
        checkBus();
        fetchSearchBoards();
    }

    private void checkBus() {
        rxbus.getBus()
                .subscribe(data -> {
                    if (data instanceof Filter) {
                        filter = (Filter) data;
                    }
                })
                .dispose();
    }

    public void fetchAreaBoards(LatLng leftLatLng, LatLng rightLatLng) {
        disposables.add(boardRepository.getAreaBoards(leftLatLng, rightLatLng)
                .subscribe(data -> {
                            boards.setValue(data);
                        }
                        , e -> {
                        }));

    }

    private void fetchSearchBoards() {
        if (filter != null) {
            disposables.add(boardRepository.getSearchBoards(filter)
                    .subscribe(data -> {
                        boards.postValue(data);
                    }, e -> {
                    }));
        }
    }

    public void onScrollButtonClick() {
        if (!isSheetExpanded.get()) {
            mNavigator.setBottomSheetExpand(true);
        } else {
            mNavigator.setBottomSheetExpand(false);
        }
    }

    public void onStopViewModel() {
        rxbus.resetBus();
    }


    @Override
    protected void onCleared() {
        disposables.dispose();
        super.onCleared();
    }

    //검색 버튼 리스너
    public void onSearchClick() {
        mNavigator.goToMapSearch();
    }

    //게시물 추가 리스너
    public void onClickBoardAdd() {
        if (mNavigator != null) {
            mNavigator.goToBoardAdd();
        }
    }

    public void resetFilter() {
        this.filter = null;
    }

    public void onClickSetMyPosition() {
        mNavigator.startLocationUpdates();
    }

    public MutableLiveData<Board> getOpenBoardEvent() {
        return mOpenBoardEvent;
    }

    public void setNavigator(MapNavigator navigator) {
        this.mNavigator = navigator;
    }

    public MutableLiveData<List<Board>> getBoards() {
        return boards;
    }

    public Filter getFilter() {
        return filter;
    }
}
