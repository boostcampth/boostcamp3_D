package com.teamdonut.eatto.ui.main;

import android.os.Bundle;
import android.util.Log;

import com.google.android.material.snackbar.Snackbar;
import com.teamdonut.eatto.R;
import com.teamdonut.eatto.databinding.MainActivityBinding;
import com.teamdonut.eatto.common.util.ActivityUtils;
import com.teamdonut.eatto.common.RxBus;
import com.teamdonut.eatto.ui.home.HomeFragment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

public class MainActivity extends AppCompatActivity implements MainNavigator {

    private MainActivityBinding binding;
    private MainViewModel mViewModel = new MainViewModel(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.main_activity);
        binding.setViewmodel(mViewModel); //set viewModel.
        ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), HomeFragment.newInstance(), R.id.fl_main);
    }

    @Override
    public void changeScreen(int itemId, Fragment fragment) {
        if (itemId != binding.bnvMain.getSelectedItemId()) { //같은 탭을 누르지 않았을 경우만 이동.
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), fragment, R.id.fl_main);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        RxBus.getInstance().getBus()
                .subscribe(result -> {

                    if (result instanceof String) {
                        if (result.toString().equals(getResources().getText(R.string.board_add_end).toString())) {
                            Log.d("result test", result.toString());
                            Snackbar.make(binding.flMain, R.string.main_snack_bar, Snackbar.LENGTH_SHORT).show();
                        }
                    }

                }, err -> err.printStackTrace());
    }

    @Override
    protected void onDestroy() {
        RxBus.setInstanceToNull();
        super.onDestroy();
    }
}
