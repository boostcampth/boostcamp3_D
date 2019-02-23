package com.teamdonut.eatto.ui.board.detail;

import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.teamdonut.eatto.common.helper.RealmDataHelper;
import com.teamdonut.eatto.data.Board;
import com.teamdonut.eatto.data.Comment;
import com.teamdonut.eatto.data.model.comment.CommentRepository;
import io.reactivex.disposables.CompositeDisposable;

import java.util.List;

public class BoardDetailViewModel extends ViewModel {

    private CommentRepository commentRepository = CommentRepository.getInstance();
    private CompositeDisposable disposables = new CompositeDisposable();

    private final ObservableField<Board> board = new ObservableField<>();
    private final MutableLiveData<List<Comment>> comments = new MutableLiveData<>();

    public void loadComments() {
        fetchComments();
    }

    private void fetchComments() {
        if (board.get() != null) {
            disposables.add(commentRepository.getComments(board.get().getId())
                    .subscribe(data -> {
                        if (data != null) {
                            comments.postValue(data);
                        }
                    }));
        }
    }

    private void sendComments(Comment comment) {
        disposables.add(commentRepository.postComment(comment)
                .doAfterSuccess(data -> {
                    fetchComments();
                })
                .subscribe());
    }

    public void onWriteCommentClick(String inputText) {
        if (board.get() != null) {
            Comment comment = new Comment(board.get().getId(), RealmDataHelper.getUser().getNickName(), inputText);
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
        return board;
    }
}
