package com.teamdonut.eatto.ui.board.preview;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.google.gson.JsonObject;
import com.teamdonut.eatto.data.Board;
import com.teamdonut.eatto.data.model.board.BoardRepository;
import io.reactivex.disposables.CompositeDisposable;

public class BoardPreviewViewModel extends ViewModel {

    private CompositeDisposable disposable = new CompositeDisposable();

    private final MutableLiveData<Board> board = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isSubmitted = new MutableLiveData<>();

    private BoardRepository boardRepository = BoardRepository.getInstance();

    private void sendParticipation(int boardId) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("board_id", boardId);

        disposable.add(boardRepository.postParticipateBoard(jsonObject)
                .subscribe(data -> {
                    if (data.get("code").getAsInt() == 200) {
                        isSubmitted.postValue(true);
                    }
                }));
    }

    public void setBoardValue(Board board) {
        if (board != null) {
            this.board.setValue(board);
        }
    }

    public void onJoinClick() {
        if (board.getValue() != null && (board.getValue().getCurrentPerson() < board.getValue().getMaxPerson())) {
            sendParticipation(board.getValue().getId());
        }
    }

    public void onCloseClick() {
        isSubmitted.setValue(false);
    }

    @Override
    protected void onCleared() {
        disposable.dispose();
        super.onCleared();
    }

    public MutableLiveData<Board> getBoard() {
        return board;
    }

    public MutableLiveData<Boolean> getIsSubmitted() {
        return isSubmitted;
    }
}
