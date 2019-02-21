package com.teamdonut.eatto.ui.mypage.judge;

import androidx.lifecycle.ViewModel;
import com.google.gson.JsonObject;
import com.teamdonut.eatto.common.helper.RealmDataHelper;
import com.teamdonut.eatto.model.MyPageAPI;
import com.teamdonut.eatto.model.ServiceGenerator;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

import java.util.ArrayList;

public class MyPageJudgeViewModel extends ViewModel {
    private JudgeAdapter judgeAdapter = new JudgeAdapter(new ArrayList<>(), this);

    private MyPageAPI service = ServiceGenerator.createService(MyPageAPI.class, ServiceGenerator.BASE);

    private CompositeDisposable disposables = new CompositeDisposable();

    public void fetchBoards() {
        disposables.add(
                service.getJudgeBoards(RealmDataHelper.getUser().getKakaoId())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(data -> {
                            judgeAdapter.updateItems(data);
                                }, e -> {
                                    e.printStackTrace();
                                }
                        )
        );
    }

    public void judgeBoard(long writerId, int boardId, int score) {
        JsonObject object = new JsonObject();
        object.addProperty("writer_id", writerId);
        object.addProperty("board_id", boardId);
        object.addProperty("score", score);
        disposables.add(
                service.judgeBoard(RealmDataHelper.getUser().getKakaoId(),
                        object)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(data -> {
                                }, e -> {
                                    e.printStackTrace();
                                }
                        )
        );
    }

    public void onDestroyViewModel() {
        disposables.dispose();
    }

    public JudgeAdapter getJudgeAdapter() {
        return judgeAdapter;
    }
}
