package com.teamdonut.eatto.data.model.board;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.JsonObject;
import com.teamdonut.eatto.common.helper.RealmDataHelper;
import com.teamdonut.eatto.data.Board;
import com.teamdonut.eatto.data.Filter;
import com.teamdonut.eatto.data.model.ServiceGenerator;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
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

    public Disposable getAreaBoards(Consumer<List<Board>> subscribeConsumer, LatLng leftLatLng, LatLng rightLatLng) {
        return service.getAreaBoards(leftLatLng.longitude, leftLatLng.latitude, rightLatLng.longitude, rightLatLng.latitude)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(subscribeConsumer);
    }

    public Disposable getSearchBoards(Consumer<List<Board>> subscribeConsumer, Filter filter){
        return service.getSearchBoards(RealmDataHelper.getUser().getKakaoId(),
                filter.getKeyword(), filter.getMinTime(), filter.getMaxTime(), filter.getMinAge(), filter.getMaxAge(),
                filter.getMaxPeople(), filter.getBudget())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(subscribeConsumer);
    }

    public Disposable getTodayBoards(Consumer<List<Board>> subscribeConsumer, Consumer<List<Board>> afterConsumer){
        return service.getTodayBoards()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doAfterSuccess(afterConsumer)
                .subscribe(subscribeConsumer);
    }

    public Disposable getRecommendBoards(Consumer<List<Board>> subscribeConsumer, Consumer<List<Board>> afterConsumer, String longitude, String latitude){
        return service.getRecommendBoards(longitude, latitude)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doAfterSuccess(afterConsumer)
                .subscribe(subscribeConsumer);
    }

    public Disposable getUserCreatedBoard(Consumer<List<Board>> subscribeConsumer){
        return service.getUserCreatedBoard(RealmDataHelper.getUser().getKakaoId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscribeConsumer);
    }


    public Disposable getUserParticipatedBoard(Consumer<List<Board>> subscribeConsumer){
        return service.getUserParticipatedBoard(RealmDataHelper.getUser().getKakaoId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscribeConsumer);
    }

    public Disposable postBoard(Consumer<JsonObject> subscribeConsumer, Board board){
        return service.postBoard(board)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscribeConsumer);
    }

    public Disposable getJudgeBoards(Consumer<List<Board>> subscribeConsumer){
        return service.getJudgeBoards(RealmDataHelper.getUser().getKakaoId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscribeConsumer);
    }

    public Disposable putJudgeBoards(Consumer<JsonObject> subscribeConsumer, JsonObject jsonObject){
        return service.putJudgeBoard(RealmDataHelper.getUser().getKakaoId(), jsonObject)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscribeConsumer);
    }

    public Disposable joinBoard(Consumer<JsonObject> subscribeConsumer, JsonObject jsonObject){
        return service.joinBoard(RealmDataHelper.getUser().getKakaoId(), jsonObject)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscribeConsumer);
    }

}
