package com.teamdonut.eatto.ui.board;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.teamdonut.eatto.R;
import com.teamdonut.eatto.databinding.BoardDetailActivityBinding;

public class BoardDetailActivity extends AppCompatActivity {

    private BoardDetailActivityBinding binding;
    private BoardViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.board_detail_activity);
        mViewModel = new BoardViewModel();
        binding.setViewmodel(mViewModel);
    }
}
