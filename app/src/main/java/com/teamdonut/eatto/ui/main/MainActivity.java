package com.teamdonut.eatto.ui.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import com.teamdonut.eatto.R;
import com.teamdonut.eatto.databinding.MainActivityBinding;
import com.teamdonut.eatto.util.ActivityUtils;

public class MainActivity extends AppCompatActivity implements MainNavigator {

    private MainActivityBinding binding;
    private MainViewModel viewModel = new MainViewModel(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.main_activity);
        binding.setViewmodel(viewModel); //set viewModel.
    }

    @Override
    public void changeScreen(Fragment fragment) {
        ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), fragment, R.id.frame_main);
    }
}
