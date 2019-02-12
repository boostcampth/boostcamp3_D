package com.teamdonut.eatto.ui.mypage;

import android.Manifest;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;

import com.teamdonut.eatto.R;
import com.teamdonut.eatto.databinding.MypageEditActivityBinding;

import com.tedpark.tedpermission.rx2.TedRx2Permission;

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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                finish();
                return true;
            }
            default: {
                return super.onOptionsItemSelected(item);
            }
        }
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
    private void requestStoragePermission() {
        TedRx2Permission.with(this)
                .setDeniedMessage(R.string.all_permission_reject)
                .setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .request()
                .doOnError(e -> e.printStackTrace())
                .subscribe(tedPermissionResult -> {
                    if (tedPermissionResult.isGranted()) {
                        openBottomPicker();
                    }
                });
    }
}
