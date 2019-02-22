package com.teamdonut.eatto.data.model.board;

import androidx.lifecycle.MutableLiveData;
import com.google.android.gms.maps.model.LatLng;
import com.teamdonut.eatto.common.helper.RealmDataHelper;
import com.teamdonut.eatto.data.Board;
import com.teamdonut.eatto.data.Filter;
import com.teamdonut.eatto.data.model.ServiceGenerator;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import java.util.List;

public class BoardRepository {
    private BoardAPI service = ServiceGenerator.createService(BoardAPI.class, ServiceGenerator.BASE);

    public static BoardRepository getInstance() {
        return LazyInit.INSTANCE;
    }

    private static class LazyInit {
        private static final BoardRepository INSTANCE = new BoardRepository();
    }

    public Disposable getAreaBoards(MutableLiveData<List<Board>> boards, LatLng leftLatLng, LatLng rightLatLng) {
        return service.getAreaBoards(leftLatLng.longitude, leftLatLng.latitude, rightLatLng.longitude, rightLatLng.latitude)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(data -> {
                            boards.setValue(data);
                        },
                        e -> {
                            e.printStackTrace();
                        });
    }

    public Disposable getSearchBoards(MutableLiveData<List<Board>> boards, Filter filter){
        return service.getSearchBoards(RealmDataHelper.getUser().getKakaoId(),
                filter.getKeyword(), filter.getMinTime(), filter.getMaxTime(), filter.getMinAge(), filter.getMaxAge(),
                filter.getMaxPeople(), filter.getBudget())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(data -> {
                            boards.postValue(data);
                        },
                        e -> {
                            e.printStackTrace();
                        });
    }

}
