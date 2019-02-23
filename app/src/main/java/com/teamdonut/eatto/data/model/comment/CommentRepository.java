package com.teamdonut.eatto.data.model.comment;

import com.google.firebase.iid.FirebaseInstanceId;
import com.teamdonut.eatto.common.helper.RealmDataHelper;
import com.teamdonut.eatto.data.Comment;
import com.teamdonut.eatto.data.model.ServiceGenerator;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class CommentRepository {
    private CommentAPI service = ServiceGenerator.createService(CommentAPI.class, ServiceGenerator.BASE);

    public static CommentRepository getInstance() {
        return LazyInit.INSTANCE;
    }

    private static class LazyInit {
        private static final CommentRepository INSTANCE = new CommentRepository();
    }

    public Single<List<Comment>> getComments(int boardId) {
        return service.getComments(RealmDataHelper.getUser().getKakaoId(), boardId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io());
    }

    public Single postComment(Comment comment) {
        return service.postComments(RealmDataHelper.getUser().getKakaoId(), FirebaseInstanceId.getInstance().getToken(), comment)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io());
    }

}
