package com.teamdonut.eatto.ui.mypage.judge;

import android.os.Bundle;
import android.view.MenuItem;

import com.teamdonut.eatto.R;
import com.teamdonut.eatto.databinding.MypageJudgeActivityBinding;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

public class MyPageJudgeActivity extends AppCompatActivity {
    private MypageJudgeActivityBinding binding;
    private MyPageJudgeViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mypage_judge_activity);
        binding = DataBindingUtil.setContentView(this, R.layout.mypage_judge_activity);

        mViewModel = ViewModelProviders.of(this).get(MyPageJudgeViewModel.class);

        initToolbar();
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
        setSupportActionBar(binding.tbMpj);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
    }
}
