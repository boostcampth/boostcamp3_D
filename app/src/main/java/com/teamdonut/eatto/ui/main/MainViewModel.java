package com.teamdonut.eatto.ui.main;

import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.teamdonut.eatto.R;
import com.teamdonut.eatto.data.model.firebase.FCMRepository;
import com.teamdonut.eatto.ui.board.BoardFragment;
import com.teamdonut.eatto.ui.home.HomeFragment;
import com.teamdonut.eatto.ui.map.MapFragment;
import com.teamdonut.eatto.ui.mypage.MyPageFragment;

import androidx.databinding.BindingMethod;
import androidx.databinding.BindingMethods;
import io.reactivex.disposables.CompositeDisposable;

@BindingMethods({
        @BindingMethod(
                type = BottomNavigationView.class,
                attribute = "onNavigationItemSelected",
                method = "setOnNavigationItemSelectedListener"
        )
})


public class MainViewModel {

    private MainNavigator navigator;
    private CompositeDisposable disposables = new CompositeDisposable();
    private FCMRepository fcmRepository = FCMRepository.getInstance();

    MainViewModel(MainNavigator navigator) {
        this.navigator = navigator;
    }

    public boolean onNavigationItemSelected(MenuItem item) {
        int itemId = item.getItemId();

        switch (itemId) {
            case R.id.menu_home: {
                navigator.changeScreen(itemId, HomeFragment.newInstance());
                return true;
            }
            case R.id.menu_map: {
                navigator.changeScreen(itemId, MapFragment.newInstance());
                return true;
            }
            case R.id.menu_board: {
                navigator.changeScreen(itemId, BoardFragment.newInstance());
                return true;
            }
            case R.id.menu_mypage: {
                navigator.changeScreen(itemId, MyPageFragment.newInstance());
                return true;
            }
            default: {
                return false;
            }
        }
    }

    public void postFcmToken(String token) {
        disposables.add(
                fcmRepository.postFCMToken(token)
                        .subscribe(data -> {
                        }, e -> {
                            e.printStackTrace();
                        })
        );
    }
}
