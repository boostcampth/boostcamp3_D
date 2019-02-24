package com.teamdonut.eatto.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.iid.FirebaseInstanceId;
import com.teamdonut.eatto.R;
import com.teamdonut.eatto.common.util.ActivityUtils;
import com.teamdonut.eatto.databinding.MainActivityBinding;
import com.teamdonut.eatto.ui.home.HomeFragment;
import com.teamdonut.eatto.ui.map.MapFragment;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

public class MainActivity extends AppCompatActivity implements MainNavigator {

    private MainActivityBinding binding;
    private MainViewModel viewModel = new MainViewModel(this);
    private final int BOARD_ADD_REQUEST = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.main_activity);
        binding.setViewmodel(viewModel); //set viewModel.
        ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), HomeFragment.newInstance(), R.id.fl_main);
        viewModel.postFcmToken(FirebaseInstanceId.getInstance().getToken());
    }

    public void startAnimation(){
        binding.vLottiebg.setVisibility(View.VISIBLE);
        binding.lav.setVisibility(View.VISIBLE);
        binding.lav.playAnimation();
    }

    public void stopAnimation(){
        binding.lav.cancelAnimation();
        binding.vLottiebg.setVisibility(View.INVISIBLE);
        binding.lav.setVisibility(View.INVISIBLE);
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
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case BOARD_ADD_REQUEST:
                    showSnackBar(binding.flMain, R.string.board_add_success);
                    break;
            }
        }
    }

    public void showSnackBar(View view, int resId) {
        Snackbar.make(view, getResources().getText(resId).toString(), Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        switch (binding.bnvMain.getSelectedItemId()) {
            case R.id.menu_map:
                MapFragment mapFragment = (MapFragment)getSupportFragmentManager().findFragmentById(R.id.fl_main);
                if(mapFragment.getBottomSheetBehavior().getState() == BottomSheetBehavior.STATE_COLLAPSED){
                    super.onBackPressed();
                }else{
                    mapFragment.setBottomSheetExpand(false);
                }
                return;
            default:
                super.onBackPressed();
        }
    }

    public void enableBnvClick(){
        for(int i = 0; i < binding.bnvMain.getMenu().size(); i++){
            binding.bnvMain.getMenu().getItem(i).setEnabled(true);
        }
    }

    public void disableBnvClick(){
        for(int i = 0; i < binding.bnvMain.getMenu().size(); i++){
            binding.bnvMain.getMenu().getItem(i).setEnabled(false);
        }
    }
}
