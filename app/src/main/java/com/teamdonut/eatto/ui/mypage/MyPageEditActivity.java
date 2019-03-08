package com.teamdonut.eatto.ui.mypage;

import android.Manifest;
import android.os.Bundle;
import android.view.MenuItem;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.teamdonut.eatto.R;
import com.teamdonut.eatto.common.util.GlideApp;
import com.teamdonut.eatto.common.util.NetworkCheckUtil;
import com.teamdonut.eatto.common.util.SnackBarUtil;
import com.teamdonut.eatto.databinding.MypageEditActivityBinding;
import com.tedpark.tedpermission.rx2.TedRx2Permission;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import gun0912.tedbottompicker.TedBottomPicker;

public class MyPageEditActivity extends AppCompatActivity implements MyPageEditNavigator {

    private MypageEditActivityBinding binding;
    private MyPageEditViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.mypage_edit_activity);

        viewModel = ViewModelProviders.of(this).get(MyPageEditViewModel.class);
        viewModel.setNavigator(this);

        binding.setViewmodel(viewModel);
        binding.setLifecycleOwner(this);

        initObserver();
        initToolbar();

        if(!NetworkCheckUtil.networkCheck(getApplicationContext())) {
            SnackBarUtil.showSnackBar(binding.civProfile, R.string.all_network_check);
        }
    }

    private void initObserver() {
        viewModel.getIsSubmitted().observe(this, isSubmitted -> {
            if (isSubmitted) {
                finish();
            }
        });
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

    private void initToolbar() {
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
                .setTitle(R.string.all_sex)
                .setItems(items, (dialog, position) -> {
                    viewModel.getUserSex().set(position); //integer type
                })
                .show();
    }

    @Override
    public void selectPhoto() {
        requestStoragePermission();
    }

    private void openBottomPicker() {
        new TedBottomPicker.Builder(this)
                .setOnImageSelectedListener(uri -> {
                    GlideApp.with(this)
                            .load(uri)
                            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                            .transforms(new CenterCrop())
                            .into(binding.civProfile);
                })
                .setImageProvider((imageView, imageUri) -> {
                    GlideApp.with(this)
                            .load(imageUri)
                            .skipMemoryCache(true)
                            .transforms(new CenterCrop())
                            .into(imageView);
                })
                .create()
                .show(getSupportFragmentManager());
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
