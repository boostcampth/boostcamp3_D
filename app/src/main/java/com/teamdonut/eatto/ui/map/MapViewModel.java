package com.teamdonut.eatto.ui.map;

import android.view.View;

import com.teamdonut.eatto.common.RxBus;
import com.teamdonut.eatto.common.helper.RealmDataHelper;
import com.teamdonut.eatto.data.Board;
import com.teamdonut.eatto.data.Filter;
import com.teamdonut.eatto.model.BoardAPI;
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

    public final ObservableBoolean isSheetExpanded = new ObservableBoolean(false);

    private BoardAPI service = ServiceGenerator.createService(BoardAPI.class, ServiceGenerator.BASE);
    private CompositeDisposable disposables = new CompositeDisposable();

    private MutableLiveData<List<Board>> mItems = new MutableLiveData<>();
    private Filter filter;

    public void loadBoards() {
        checkBus();
        fetchBoards();
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

    private void fetchBoards() {
        if (filter != null) {
            disposables.add(service.getBoards(RealmDataHelper.getAccessId(),
                    filter.getKeyword(), filter.getMinTime(), filter.getMaxTime(), filter.getMinAge(), filter.getMaxAge(),
                    filter.getMaxPeople(), filter.getBudget())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(data -> {
                                mItems.postValue(data);
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

    public MutableLiveData<List<Board>> getItems() {
        return mItems;
    }

    @Override
    protected void onCleared() {
        disposables.dispose();
        super.onCleared();
    }

    public void setNavigator(MapNavigator navigator) {
        this.mNavigator = navigator;
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
}
