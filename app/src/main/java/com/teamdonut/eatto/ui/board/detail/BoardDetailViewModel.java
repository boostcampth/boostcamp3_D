package com.teamdonut.eatto.ui.board.detail;

import com.teamdonut.eatto.common.helper.RealmDataHelper;
import com.teamdonut.eatto.data.Board;
import com.teamdonut.eatto.data.Comment;
import com.teamdonut.eatto.model.BoardAPI;
import com.teamdonut.eatto.model.ServiceGenerator;

import java.util.List;

import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class BoardDetailViewModel extends ViewModel {

    private BoardAPI service = ServiceGenerator.createService(BoardAPI.class, ServiceGenerator.BASE);
    private CompositeDisposable disposables = new CompositeDisposable();

    private final ObservableField<Board> mBoard = new ObservableField<>();
    private final MutableLiveData<List<Comment>> comments = new MutableLiveData<>();

    public void loadComments() {
        fetchComments();
    }

    private void fetchComments() {
        if (mBoard.get() != null) {
            disposables.add(
                    service.getComments(RealmDataHelper.getUser().getKakaoId(), mBoard.get().getId())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribeOn(Schedulers.io())
                            .subscribe(data -> {
                                if (data != null) {
                                    comments.postValue(data);
                                }
                            }, e -> {
                                e.printStackTrace();
                            }));
        }
    }

    private void sendComments(Comment comment) {
        disposables.add(
                service.postComments(RealmDataHelper.getUser().getKakaoId(), comment)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .doAfterSuccess(data -> fetchComments())
                        .subscribe(data -> {
                            //log
                        }, e -> {
                            e.printStackTrace();
                        })
        );
    }

    public void onWriteCommentClick(String inputText) {
        if (mBoard.get() != null) {
            Comment comment = new Comment(mBoard.get().getId(), RealmDataHelper.getUser().getNickName(), inputText);
            sendComments(comment);
        }
    }

    @Override
    protected void onCleared() {
        disposables.dispose();
        super.onCleared();
    }

    public MutableLiveData<List<Comment>> getComments() {
        return comments;
    }

    public ObservableField<Board> getBoard() {
        return mBoard;
    }
}
