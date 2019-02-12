package com.teamdonut.eatto.ui.mypage;

import android.os.Bundle;

import com.teamdonut.eatto.R;
import com.teamdonut.eatto.databinding.MypageEditActivityBinding;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

public class MyPageEditActivity extends AppCompatActivity implements MyPageEditNavigator {

    private MypageEditActivityBinding binding;
    private MyPageEditViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.mypage_edit_activity);
        mViewModel = new MyPageEditViewModel(this);

        binding.setViewmodel(mViewModel);

        setupToolbar();
    }

    private void setupToolbar() {
        setSupportActionBar(binding.tbMpe);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
    }


    @Override
    public void showSelectSexDialog() {
        String[] items = getResources().getStringArray(R.array.sex);

        new AlertDialog.Builder(this)
                .setTitle(R.string.mpe_dialog_sex)
                .setItems(items, (dialog, position) -> {
                    mViewModel.userSex.set(items[position]);
                })
                .show();
    }
}
