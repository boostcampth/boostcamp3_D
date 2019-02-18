package com.teamdonut.eatto.ui.board.search;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.teamdonut.eatto.R;
import com.teamdonut.eatto.common.util.EndlessRecyclerViewScrollListener;
import com.teamdonut.eatto.common.util.HorizontalDividerItemDecorator;
import com.teamdonut.eatto.common.util.KeyboardUtil;
import com.teamdonut.eatto.common.util.SnackBarUtil;
import com.teamdonut.eatto.databinding.BoardSearchActivityBinding;
import com.teamdonut.eatto.ui.board.BoardNavigator;
import com.teamdonut.eatto.ui.board.BoardViewModel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class BoardSearchActivity extends AppCompatActivity implements BoardNavigator {

    private BoardSearchActivityBinding binding;
    private BoardViewModel mViewModel;
    private LinearLayoutManager mBoardSearchManager;

    private EndlessRecyclerViewScrollListener scrollListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.board_search_activity);
        mViewModel = new BoardViewModel(this);
        binding.setViewmodel(mViewModel);

        initToolbar();

        initSearchResultRv();

        mViewModel.getEtKeywordHint(getApplicationContext());
        setObserver();
    }

    public void setObserver() {
        mViewModel.etKeywordHint.observe(this, (hint) -> {
            binding.etInputSearchKeyword.setHint(hint);
        });
    }

    public void initToolbar() {
        //setting Toolbar
        setSupportActionBar(binding.tbBoardSearch);

        //Toolbar nav button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_board_search, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.menu_search:

                mViewModel.getBoardSearchAdapter().updateItems(mViewModel.getDocuments());

                scrollListener.resetState();
                mViewModel.fetchAddressResult(getResources().getText(R.string.kakao_rest_api_key).toString(), binding.etInputSearchKeyword.getText().toString(), 1, 10);

                KeyboardUtil.hideKeyboard(getCurrentFocus(), getApplicationContext());
                break;
        }
        return true;
    }


    private void initSearchResultRv() {
        mBoardSearchManager = new LinearLayoutManager(this);
        mBoardSearchManager.setOrientation(RecyclerView.VERTICAL);
        binding.rvBoardSearch.setLayoutManager(mBoardSearchManager);
        binding.rvBoardSearch.addItemDecoration(new HorizontalDividerItemDecorator
                (ContextCompat.getDrawable(getApplicationContext(), R.drawable.ranking_divider), 0.03));

        scrollListener = new EndlessRecyclerViewScrollListener(mBoardSearchManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to the bottom of the list
                mViewModel.fetchAddressResult(getResources().getText(R.string.kakao_rest_api_key).toString(), binding.etInputSearchKeyword.getText().toString(), page, 10);
            }
        };
        // Adds the scroll listener to RecyclerView
        binding.rvBoardSearch.addOnScrollListener(scrollListener);

        binding.rvBoardSearch.setAdapter(mViewModel.getBoardSearchAdapter());
    }

    @Override
    protected void onDestroy() {
        mViewModel.onDestroyBoardViewModel();
        super.onDestroy();
    }

    @Override
    public void onShowSnackBar() {
        SnackBarUtil.showSnackBar(binding.rvBoardSearch, R.string.board_search_can_not_find_result);
    }



}
