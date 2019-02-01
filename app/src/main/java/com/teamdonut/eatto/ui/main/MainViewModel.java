package com.teamdonut.eatto.ui.main;

import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.teamdonut.eatto.R;

import androidx.databinding.BindingMethod;
import androidx.databinding.BindingMethods;
import com.teamdonut.eatto.ui.map.MapFragment;

@BindingMethods({
        @BindingMethod(
                type = BottomNavigationView.class,
                attribute = "app:onNavigationItemSelected",
                method = "setOnNavigationItemSelectedListener"
        )
})


public class MainViewModel {

    private MainNavigator navigator;

    MainViewModel(MainNavigator navigator) {
        this.navigator = navigator;
    }

    public boolean onNavigationItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_home: {
                // 이렇게 호출해주시면 됩니다.
                //   navigator.changeScreen(HomeFragment.newInstance());
                return true;
            }
            case R.id.menu_map: {
                navigator.changeScreen(MapFragment.newInstance());
                return true;
            }
            case R.id.menu_board: {
                return true;
            }
            case R.id.menu_mypage: {
                return true;
            }
            default: {
                return false;
            }
        }
    }
}
