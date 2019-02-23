package com.teamdonut.eatto.ui.board.detail;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.teamdonut.eatto.R;
import com.teamdonut.eatto.common.RxBus;
import com.teamdonut.eatto.common.util.KeyboardUtil;
import com.teamdonut.eatto.data.Board;
import com.teamdonut.eatto.databinding.BoardDetailActivityBinding;
import com.teamdonut.eatto.ui.board.BoardNavigator;

import java.util.ArrayList;

public class BoardDetailActivity extends AppCompatActivity implements BoardNavigator {

    private BoardDetailActivityBinding binding;
    private BoardDetailViewModel viewModel;
    private BoardCommentAdapter boardCommentAdapter;

    @Override
    protected void onResume() {
        super.onResume();
        viewModel.loadComments();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.board_detail_activity);
        viewModel = ViewModelProviders.of(this).get(BoardDetailViewModel.class);

        binding.setViewmodel(viewModel);
        binding.setLifecycleOwner(this);

        checkBus();
        initToolbar();
        initCommentRv();
        initCommentObserver();
    }

    private void checkBus() {
        RxBus.getInstance().getBus()
                .subscribe(data -> {
                    if (data instanceof Board) {
                        Log.d("address", ((Board) data).getAppointedTime());
                        viewModel.getBoard().set((Board) data);
                    }
                })
                .dispose();
    }

    private void initToolbar() {
        setSupportActionBar(binding.tbBoardDetail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

    }

    @Override
    protected void onDestroy() {
        viewModel.getComments().removeObservers(this);
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
        boardCommentAdapter = new BoardCommentAdapter(new ArrayList<>(), viewModel);

        DividerItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        itemDecoration.setDrawable(ContextCompat.getDrawable(this, R.drawable.comment_divider));

        rv.setHasFixedSize(true);
        rv.addItemDecoration(itemDecoration);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(boardCommentAdapter);
    }

    private void initCommentObserver() {
        viewModel.getComments().observe(this, data -> {
            KeyboardUtil.hideSoftKeyboard(binding.nsvDetail, this);
            binding.etComment.setText("");

            boardCommentAdapter.updateItems(data);
            binding.rvComment.scrollToPosition(boardCommentAdapter.getItemCount()-1);
        });
    }
}
