package com.teamdonut.eatto.ui.mypage.judge;

import android.os.Bundle;
import android.view.MenuItem;

import com.teamdonut.eatto.R;
import com.teamdonut.eatto.databinding.MypageJudgeActivityBinding;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MyPageJudgeActivity extends AppCompatActivity {

    private MypageJudgeActivityBinding binding;
    private MyPageJudgeViewModel mViewModel;

    private JudgeAdapter mJudgeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mypage_judge_activity);
        binding = DataBindingUtil.setContentView(this, R.layout.mypage_judge_activity);
        mViewModel = ViewModelProviders.of(this).get(MyPageJudgeViewModel.class);

        binding.setViewmodel(mViewModel);
        binding.setLifecycleOwner(this);

        initJudgeRv();
        initToolbar();
        initJudgeObserve();
        mViewModel.fetchJudgeBoards();
    }

    private void initJudgeObserve() {
        mViewModel.getSubmitJudge().observe(this, data ->{
            mJudgeAdapter.removeItem(data);
        });
    }

    public void initJudgeRv() {
        RecyclerView rv = binding.rvJudge;
        mJudgeAdapter = new JudgeAdapter(new ArrayList<>(0), mViewModel);

        DividerItemDecoration itemDecoration = new DividerItemDecoration(rv.getContext(), 1);
        itemDecoration.setDrawable(ContextCompat.getDrawable(this, R.drawable.board_divider));

        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.addItemDecoration(itemDecoration);
        rv.setAdapter(mJudgeAdapter);
        rv.setHasFixedSize(true);
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
