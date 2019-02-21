package com.teamdonut.eatto.ui.mypage.judge;

import com.teamdonut.eatto.common.helper.RealmDataHelper;
import com.teamdonut.eatto.model.MyPageAPI;
import com.teamdonut.eatto.model.ServiceGenerator;

import java.util.ArrayList;

import androidx.lifecycle.ViewModel;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class MyPageJudgeViewModel extends ViewModel {
    private JudgeAdapter judgeAdapter = new JudgeAdapter(new ArrayList<>());

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

    public void onDestroyViewModel() {
        disposables.dispose();
    }

    public JudgeAdapter getJudgeAdapter() {
        return judgeAdapter;
    }
}
