package com.teamdonut.eatto.data.model.board;

import android.util.Pair;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.JsonObject;
import com.teamdonut.eatto.common.helper.RealmDataHelper;
import com.teamdonut.eatto.data.Board;
import com.teamdonut.eatto.data.Filter;
import com.teamdonut.eatto.data.model.ServiceGenerator;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
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

    public Single<List<Board>> getAreaBoards(LatLng leftLatLng, LatLng rightLatLng) {
        return service.getAreaBoards(leftLatLng.longitude, leftLatLng.latitude, rightLatLng.longitude, rightLatLng.latitude)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io());
    }


    public Single<List<Board>> getSearchBoards(Filter filter) {
        return service.getSearchBoards(RealmDataHelper.getUser().getKakaoId(),
                filter.getKeyword(), filter.getMinTime(), filter.getMaxTime(), filter.getMinAge(), filter.getMaxAge(),
                filter.getMaxPeople(), filter.getBudget())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io());
    }

    public Single<Pair<Integer, List<Board>>> getRecommendBoards(String longitude, String latitude) {
        Single<List<Board>> recommendSingle = service.getRecommendBoards(longitude, latitude);
        Single<List<Board>> todaySingle = service.getTodayBoards();

        return Single.zip(recommendSingle, todaySingle,
                (recommends, other) -> {
                    return (recommends.size() == 0) ? new Pair<>(other.size(), other) : new Pair<>(other.size(), recommends);
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<List<Board>> getOwnBoard() {
        return service.getOwnBoard(RealmDataHelper.getUser().getKakaoId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


    public Single<List<Board>> getUserParticipatedBoard() {
        return service.getParticipatedBoard(RealmDataHelper.getUser().getKakaoId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<JsonObject> postBoard(Board board) {
        return service.postBoard(board)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<List<Board>> getJudgeBoards() {
        return service.getJudgeBoards(RealmDataHelper.getUser().getKakaoId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<JsonObject> putJudgeBoards(JsonObject jsonObject) {
        return service.putJudgeBoard(RealmDataHelper.getUser().getKakaoId(), jsonObject)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<JsonObject> postParticipateBoard(JsonObject jsonObject) {
        return service.postParticipateBoard(RealmDataHelper.getUser().getKakaoId(), jsonObject)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

}
