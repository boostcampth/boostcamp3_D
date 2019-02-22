package com.teamdonut.eatto.ui.board.preview;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.google.gson.JsonObject;
import com.teamdonut.eatto.data.Board;
import com.teamdonut.eatto.data.model.board.BoardRepository;
import io.reactivex.disposables.CompositeDisposable;

public class BoardPreviewViewModel extends ViewModel {

    private CompositeDisposable disposable = new CompositeDisposable();

    private final MutableLiveData<Board> mBoard = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isSubmitted = new MutableLiveData<>();

    private BoardRepository boardRepository = BoardRepository.getInstance();

    private void sendParticipation(int boardId) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("board_id", boardId);

        disposable.add(boardRepository.joinBoard(data -> {
            if (data.get("code").getAsInt() == 200) {
                isSubmitted.postValue(true);
            }
        }, jsonObject));
    }

    public void setBoardValue(Board board) {
        if (board != null) {
            mBoard.setValue(board);
        }
    }

    public void onJoinClick() {
        if (mBoard.getValue() != null && (mBoard.getValue().getCurrentPerson() < mBoard.getValue().getMaxPerson())) {
            sendParticipation(mBoard.getValue().getId());
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
        return mBoard;
    }

    public MutableLiveData<Boolean> getIsSubmitted() {
        return isSubmitted;
    }
}
