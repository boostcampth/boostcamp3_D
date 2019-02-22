package com.teamdonut.eatto.data.model.comment;

import androidx.lifecycle.MutableLiveData;
import com.google.gson.JsonObject;
import com.teamdonut.eatto.common.helper.RealmDataHelper;
import com.teamdonut.eatto.data.Comment;
import com.teamdonut.eatto.data.model.ServiceGenerator;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

import java.util.List;

public class CommentRepository {
    private CommentAPI service = ServiceGenerator.createService(CommentAPI.class, ServiceGenerator.BASE);

    public static CommentRepository getInstance() {
        return LazyInit.INSTANCE;
    }

    private static class LazyInit {
        private static final CommentRepository INSTANCE = new CommentRepository();
    }

    public Disposable getComments(MutableLiveData<List<Comment>> comments, int boardId) {
        return service.getComments(RealmDataHelper.getUser().getKakaoId(), boardId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(data -> {
                    if (data != null) {
                        comments.postValue(data);
                    }
                }, e -> {
                    e.printStackTrace();
                });
    }

    public Disposable postComment(Comment comment, Consumer<JsonObject> consumer){
        return service.postComments(RealmDataHelper.getUser().getKakaoId(), comment)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .doAfterSuccess(consumer)
                .subscribe(data -> {
                    //log
                }, e -> {
                    e.printStackTrace();
                });
    }

}
