package com.teamdonut.eatto.ui.board.preview;

import com.teamdonut.eatto.common.helper.RealmDataHelper;
import com.teamdonut.eatto.data.Board;
import com.teamdonut.eatto.data.BoardAddInformation;
import com.teamdonut.eatto.model.BoardAPI;
import com.teamdonut.eatto.model.ServiceGenerator;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class BoardPreviewViewModel extends ViewModel {

    private BoardAPI service = ServiceGenerator.createService(BoardAPI.class, ServiceGenerator.BASE);
    private CompositeDisposable disposable = new CompositeDisposable();

    private final MutableLiveData<Board> mBoard = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isSubmitted = new MutableLiveData<>();

    private void sendParticipation(int boardId) {
        disposable.add(
                service.joinBoard(RealmDataHelper.getUser().getKakaoId(), new BoardAddInformation(boardId))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(data -> {
                                    if (data.get("code").getAsInt() == 200) {
                                        isSubmitted.postValue(true);
                                    }
                                },
                                e -> {
                                    e.printStackTrace();
                                })
        );
    }

    public void setBoardValue(Board board) {
        if (board != null) {
            mBoard.setValue(board);
        }
    }

    public void onJoinClick() {
        if(mBoard.getValue() !=null && (mBoard.getValue().getCurrentPerson() < mBoard.getValue().getMaxPerson())) {
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
