package com.teamdonut.eatto.ui.board;

import android.os.Bundle;

import com.teamdonut.eatto.R;
import com.teamdonut.eatto.common.RxBus;
import com.teamdonut.eatto.data.Board;
import com.teamdonut.eatto.databinding.BoardDetailActivityBinding;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

public class BoardDetailActivity extends AppCompatActivity implements BoardNavigator{

    private BoardDetailActivityBinding binding;
    private BoardViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.board_detail_activity);
        mViewModel = new BoardViewModel(this);
        binding.setViewmodel(mViewModel);

        initRxBus();
    }

    public void initRxBus() {
        RxBus.getInstance().getBus().subscribe((data)->{

            if(data instanceof Board) {
                binding.setBoard((Board)data);
            }

        }).dispose();

    }

    //액티비티 종료
    @Override
    public void onBoardDetailExitClick() {
        finish();
    }
}
