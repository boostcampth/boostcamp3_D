package com.teamdonut.eatto.ui.mypage.judge;

import android.os.Bundle;
import android.view.MenuItem;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.teamdonut.eatto.R;
import com.teamdonut.eatto.databinding.MypageJudgeActivityBinding;

import java.util.ArrayList;

public class MyPageJudgeActivity extends AppCompatActivity {

    private MypageJudgeActivityBinding binding;
    private MyPageJudgeViewModel viewModel;

    private JudgeAdapter judgeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mypage_judge_activity);
        binding = DataBindingUtil.setContentView(this, R.layout.mypage_judge_activity);
        viewModel = ViewModelProviders.of(this).get(MyPageJudgeViewModel.class);

        binding.setViewmodel(viewModel);
        binding.setLifecycleOwner(this);

        initJudgeRv();
        initToolbar();
        initJudgeObserve();
        viewModel.fetchJudgeBoards();
    }

    private void initJudgeObserve() {
        viewModel.getSubmitJudge().observe(this, data ->{
            judgeAdapter.removeItem(data);
        });
    }

    public void initJudgeRv() {
        RecyclerView rv = binding.rvJudge;
        judgeAdapter = new JudgeAdapter(new ArrayList<>(0), viewModel);

        DividerItemDecoration itemDecoration = new DividerItemDecoration(rv.getContext(), 1);
        itemDecoration.setDrawable(ContextCompat.getDrawable(this, R.drawable.board_divider));

        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.addItemDecoration(itemDecoration);
        rv.setAdapter(judgeAdapter);
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
