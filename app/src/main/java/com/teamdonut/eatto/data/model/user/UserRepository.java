package com.teamdonut.eatto.data.model.user;

import com.google.gson.JsonObject;
import com.teamdonut.eatto.common.helper.RealmDataHelper;
import com.teamdonut.eatto.data.User;
import com.teamdonut.eatto.data.model.ServiceGenerator;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

import java.util.List;

public class UserRepository {
    private UserAPI service = ServiceGenerator.createService(UserAPI.class, ServiceGenerator.BASE);

    public static UserRepository getInstance() {
        return LazyInit.INSTANCE;
    }

    private static class LazyInit {
        private static final UserRepository INSTANCE = new UserRepository();
    }

    public Disposable getUser(Consumer<User> subscribeConsumer, Consumer<User> afterConsumer) {
        return service.getUser(RealmDataHelper.getUser().getKakaoId())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .doAfterSuccess(afterConsumer)
                .subscribe(subscribeConsumer);
    }


    public Disposable postUser(Consumer<JsonObject> subscribeConsumer, Consumer<JsonObject> afterConsumer, User user) {
        return service.postUser(user)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .doAfterSuccess(afterConsumer)
                .subscribe(subscribeConsumer);
    }


    public Disposable getTopTenUser(Consumer<List<User>> subscribeConsumer) {
        return service.getTopTenUsers()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(subscribeConsumer);
    }
}
