package com.teamdonut.eatto.ui.board.detail;

import android.os.Bundle;
import android.view.MenuItem;

import com.teamdonut.eatto.R;
import com.teamdonut.eatto.common.RxBus;
import com.teamdonut.eatto.common.util.KeyboardUtil;
import com.teamdonut.eatto.data.Board;
import com.teamdonut.eatto.databinding.BoardDetailActivityBinding;
import com.teamdonut.eatto.ui.board.BoardNavigator;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class BoardDetailActivity extends AppCompatActivity implements BoardNavigator {

    private BoardDetailActivityBinding binding;
    private BoardDetailViewModel mViewModel;
    private BoardCommentAdapter mAdapter;

    @Override
    protected void onResume() {
        super.onResume();
        mViewModel.loadComments();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.board_detail_activity);
        mViewModel = ViewModelProviders.of(this).get(BoardDetailViewModel.class);

        binding.setViewmodel(mViewModel);
        binding.setLifecycleOwner(this);

        initToolbar();
        initCommentRv();
        initCommentObserver();
    }

    private void initToolbar() {
        setSupportActionBar(binding.tbBoardDetail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

    }

    @Override
    protected void onDestroy() {
        mViewModel.getComments().removeObservers(this);
        super.onDestroy();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                onBackPressed();
                return true;
            }
            default: {
                return super.onOptionsItemSelected(item);
            }
        }
    }

    private void initCommentRv() {
        RecyclerView rv = binding.rvComment;
        mAdapter = new BoardCommentAdapter(new ArrayList<>(), mViewModel);

        DividerItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        itemDecoration.setDrawable(ContextCompat.getDrawable(this, R.drawable.comment_divider));

        rv.setHasFixedSize(true);
        rv.addItemDecoration(itemDecoration);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(mAdapter);
    }

    private void initCommentObserver() {
        mViewModel.getComments().observe(this, data -> {
            KeyboardUtil.hideSoftKeyboard(binding.nsvDetail, this);
            binding.etComment.setText("");

            mAdapter.updateItems(data);
            binding.rvComment.scrollToPosition(mAdapter.getItemCount()-1);
        });
    }
}
