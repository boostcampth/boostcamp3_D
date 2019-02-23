package com.teamdonut.eatto.ui.mypage.judge;

import com.google.gson.JsonObject;
import com.teamdonut.eatto.data.Board;
import com.teamdonut.eatto.data.model.board.BoardRepository;

import java.util.List;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import io.reactivex.disposables.CompositeDisposable;

public class MyPageJudgeViewModel extends ViewModel {

    private CompositeDisposable disposables = new CompositeDisposable();

    private final MutableLiveData<List<Board>> judgeBoards = new MutableLiveData<>();
    private final MutableLiveData<Board> submitJudge = new MutableLiveData<>();

    private BoardRepository boardRepository = BoardRepository.getInstance();


    public void fetchJudgeBoards() {
        disposables.add(boardRepository.getJudgeBoards()
                .subscribe(data -> {
                    judgeBoards.postValue(data);
                }));
    }

    public void sendJudgeResult(Board board, int score) {
        JsonObject judge = new JsonObject();
        judge.addProperty("writer_id", board.getWriterId());
        judge.addProperty("board_id", board.getId());
        judge.addProperty("score", score);

        disposables.add(boardRepository.putJudgeBoards(judge)
                .doAfterSuccess(data -> submitJudge.setValue(board))
                .subscribe(data -> {
                }, e -> {
                    e.printStackTrace();
                }));
    }

    public MutableLiveData<List<Board>> getJudgeBoards() {
        return judgeBoards;
    }

    public MutableLiveData<Board> getSubmitJudge() {
        return submitJudge;
    }
}
