package com.teamdonut.eatto.ui.map;

import android.view.View;

import com.google.android.gms.maps.model.LatLng;
import com.teamdonut.eatto.common.RxBus;
import com.teamdonut.eatto.common.helper.RealmDataHelper;
import com.teamdonut.eatto.data.Board;
import com.teamdonut.eatto.data.Filter;
import com.teamdonut.eatto.model.MapAPI;
import com.teamdonut.eatto.model.ServiceGenerator;

import java.util.List;

import androidx.databinding.ObservableBoolean;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class MapViewModel extends ViewModel {

    private MapNavigator mNavigator;

    private MapAPI service = ServiceGenerator.createService(MapAPI.class, ServiceGenerator.BASE);
    private CompositeDisposable disposables = new CompositeDisposable();

    private final MutableLiveData<Board> mOpenBoardEvent = new MutableLiveData<>();

    private MutableLiveData<List<Board>> boards = new MutableLiveData<>();

    private Filter filter;

    public final ObservableBoolean isSheetExpanded = new ObservableBoolean(false);

    public void loadBoards() {
        checkBus();
        fetchSearchBoards();
    }

    private void checkBus() {
        RxBus.getInstance().getBus()
                .subscribe(data -> {
                    if (data instanceof Filter) {
                        filter = (Filter) data;
                    }
                })
                .dispose();
    }

    public void fetchBoards(LatLng leftLatLng, LatLng rightLatLng) {
        disposables.add(service.getBoards(leftLatLng.longitude, leftLatLng.latitude, rightLatLng.longitude, rightLatLng.latitude)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(data -> {
                            boards.setValue(data);
                        },
                        e -> {
                            e.printStackTrace();
                        })
        );
    }

    private void fetchSearchBoards() {
        if (filter != null) {
            disposables.add(service.getSearchBoards(RealmDataHelper.getUser().getKakaoId(),
                    filter.getKeyword(), filter.getMinTime(), filter.getMaxTime(), filter.getMinAge(), filter.getMaxAge(),
                    filter.getMaxPeople(), filter.getBudget())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(data -> {
                                boards.postValue(data);
                            },
                            e -> {
                                e.printStackTrace();
                            })
            );
        }
    }

    public void onScrollButtonClick() {
        if (!isSheetExpanded.get()) {
            mNavigator.setBottomSheetExpand(true);
        } else {
            mNavigator.setBottomSheetExpand(false);
        }
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

    public void onClickSetMyPosition(View view) {
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
}
