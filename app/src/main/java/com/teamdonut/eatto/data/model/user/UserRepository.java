package com.teamdonut.eatto.data.model.user;

import com.google.gson.JsonObject;
import com.teamdonut.eatto.common.helper.RealmDataHelper;
import com.teamdonut.eatto.data.User;
import com.teamdonut.eatto.data.model.ServiceGenerator;

import org.reactivestreams.Subscriber;

import io.reactivex.Maybe;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

import java.util.List;
import java.util.Objects;

public class UserRepository {
    private UserAPI service = ServiceGenerator.createService(UserAPI.class, ServiceGenerator.BASE);

    public static UserRepository getInstance() {
        return LazyInit.INSTANCE;
    }

    private static class LazyInit {
        private static final UserRepository INSTANCE = new UserRepository();
    }

    public Single<User> getUser() {
        return service.getUser(RealmDataHelper.getUser().getKakaoId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


    public Disposable postUser(Consumer<JsonObject> subscribeConsumer, Consumer<JsonObject> afterConsumer, User user) {
        return service.postUser(user)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .doAfterSuccess(afterConsumer)
                .subscribe(subscribeConsumer);
    }


    public Single<List<User>> getTopTenUsers() {
        return service.getTopTenUsers()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
