package com.teamdonut.eatto.ui.board.search;

import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;

import com.google.android.material.snackbar.Snackbar;
import com.teamdonut.eatto.R;
import com.teamdonut.eatto.common.util.EndlessRecyclerViewScrollListener;
import com.teamdonut.eatto.common.util.HorizontalDividerItemDecorator;
import com.teamdonut.eatto.databinding.BoardSearchActivityBinding;
import com.teamdonut.eatto.ui.board.BoardNavigator;
import com.teamdonut.eatto.ui.board.BoardViewModel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class BoardSearchActivity extends AppCompatActivity implements BoardNavigator {

    BoardSearchActivityBinding binding;
    BoardViewModel mViewModel;
    LinearLayoutManager boardSearchManager;

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

                mViewModel.getmAdapter().updateItems(mViewModel.documents);

                scrollListener.resetState();
                mViewModel.fetchAddressResult(getResources().getText(R.string.kakao_rest_api_key).toString(), binding.etInputSearchKeyword.getText().toString(), 1, 10);

                hideKeyboard();
                break;
        }
        return true;
    }
    
    public void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }

    private void initSearchResultRv() {
        boardSearchManager = new LinearLayoutManager(this);
        boardSearchManager.setOrientation(RecyclerView.VERTICAL);
        binding.rvBoardSearch.setLayoutManager(boardSearchManager);
        binding.rvBoardSearch.addItemDecoration(new HorizontalDividerItemDecorator
                (ContextCompat.getDrawable(getApplicationContext(), R.drawable.ranking_divider), 0.03));

        scrollListener = new EndlessRecyclerViewScrollListener(boardSearchManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to the bottom of the list
                mViewModel.fetchAddressResult(getResources().getText(R.string.kakao_rest_api_key).toString(), binding.etInputSearchKeyword.getText().toString(), page, 10);
            }
        };
        // Adds the scroll listener to RecyclerView
        binding.rvBoardSearch.addOnScrollListener(scrollListener);

        binding.rvBoardSearch.setAdapter(mViewModel.getmAdapter());
    }


    @Override
    protected void onDestroy() {
        mViewModel.compositeDisposableDispose();
        super.onDestroy();
    }

    @Override
    public void onShowSnackBar() {
        showSnackBar(getCurrentFocus(), R.string.board_search_can_not_find_result);
    }

    public void showSnackBar(View parentLayout, int resId) {
        Snackbar snack = Snackbar.make(parentLayout, getResources().getText(resId).toString(), Snackbar.LENGTH_SHORT);
        View view = snack.getView();
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) view.getLayoutParams();
        params.gravity = Gravity.TOP;
        view.setLayoutParams(params);
        snack.show();
    }


}
