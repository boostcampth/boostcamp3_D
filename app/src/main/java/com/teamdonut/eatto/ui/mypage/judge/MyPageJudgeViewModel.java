package com.teamdonut.eatto.ui.mypage.judge;

import androidx.lifecycle.ViewModel;
import com.google.gson.JsonObject;
import com.teamdonut.eatto.data.model.board.BoardRepository;
import io.reactivex.disposables.CompositeDisposable;

import java.util.ArrayList;

public class MyPageJudgeViewModel extends ViewModel {
    private JudgeAdapter judgeAdapter = new JudgeAdapter(new ArrayList<>(), this);

    private CompositeDisposable disposables = new CompositeDisposable();

    private BoardRepository boardRepository = BoardRepository.getInstance();

    public void fetchBoards() {
        disposables.add(boardRepository.getJudgeBoards()
                .subscribe(data -> {
                    judgeAdapter.updateItems(data);
                }));
    }

    public void judgeBoard(long writerId, int boardId, int score) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("writer_id", writerId);
        jsonObject.addProperty("board_id", boardId);
        jsonObject.addProperty("score", score);
        disposables.add(boardRepository.putJudgeBoards(jsonObject)
                .subscribe(data -> {
                }));
    }

    public void onDestroyViewModel() {
        disposables.dispose();
    }

    public JudgeAdapter getJudgeAdapter() {
        return judgeAdapter;
    }
}
