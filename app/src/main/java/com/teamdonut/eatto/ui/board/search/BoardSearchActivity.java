package com.teamdonut.eatto.ui.board.search;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import com.google.android.gms.common.util.Strings;
import com.teamdonut.eatto.R;
import com.teamdonut.eatto.common.RxBus;
import com.teamdonut.eatto.common.util.ActivityUtils;
import com.teamdonut.eatto.common.util.EndlessRecyclerViewScrollListener;
import com.teamdonut.eatto.common.util.HorizontalDividerItemDecorator;
import com.teamdonut.eatto.common.util.KeyboardUtil;
import com.teamdonut.eatto.common.util.SnackBarUtil;
import com.teamdonut.eatto.data.kakao.Document;
import com.teamdonut.eatto.databinding.BoardSearchActivityBinding;
import com.teamdonut.eatto.ui.board.BoardNavigator;
import com.teamdonut.eatto.ui.board.BoardViewModel;

import java.util.ArrayList;

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

    private RxBus rxbus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.board_search_activity);
        mViewModel = new BoardViewModel(this);
        binding.setViewmodel(mViewModel);

        fetch();
        initToolbar();
        initSearchResultRv();
        initObserver();
        initRxBus();
        initKeyboardSearchListener();
    }

    public void fetch() {
        String longitude = ActivityUtils.getStrValueSharedPreferences(getApplicationContext(), "gps", "longitude");
        String latitude = ActivityUtils.getStrValueSharedPreferences(getApplicationContext(), "gps", "latitude");
        mViewModel.fetchEtKeywordHint(getResources().getString(R.string.kakao_rest_api_key), longitude, latitude, getResources().getString(R.string.all_default_address));
    }

    public void initObserver() {
        mViewModel.etKeywordHint.observe(this, (hint) -> {
            binding.etInputSearchKeyword.setHint(hint);
        });
    }

    public void initRxBus() {

        rxbus = RxBus.getInstance();

        rxbus.getBus().subscribe(position -> {

                    if (position instanceof Integer) {
                        int curPosition = (int) position;
                        Intent resultIntent = new Intent();
                        Document document = mViewModel.getBoardSearchAdapter().getItem(curPosition);
                        resultIntent.putExtra("placeName", document.getPlaceName());
                        resultIntent.putExtra("addressName", document.getAddressName());
                        resultIntent.putExtra("x", document.getX());
                        resultIntent.putExtra("y", document.getY());
                        setResult(RESULT_OK, resultIntent);
                        finish();
                    }

                }
                ,
                e -> e.printStackTrace()
                );
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
                search();
                break;
        }
        return true;
    }

    public void search() {
        if (Strings.isEmptyOrWhitespace(binding.etInputSearchKeyword.getText().toString())) {
            SnackBarUtil.showSnackBar(getCurrentFocus(), R.string.board_search_please_insert);
        } else {
            mViewModel.setBoardSearchAdapter(new BoardSearchAdapter(new ArrayList<>()));
            binding.rvBoardSearch.setAdapter(mViewModel.getBoardSearchAdapter());
            scrollListener.resetState();
            mViewModel.fetchAddressResult(getResources().getText(R.string.kakao_rest_api_key).toString(), binding.etInputSearchKeyword.getText().toString(), 1, 10);
        }
        KeyboardUtil.hideSoftKeyboard(getCurrentFocus(), getApplicationContext());
    }

    public void initKeyboardSearchListener() {
        binding.etInputSearchKeyword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    search();
                    return true;
                }
                return false;
            }
        });
    }

    private void initSearchResultRv() {
        mBoardSearchManager = new LinearLayoutManager(this);
        mBoardSearchManager.setOrientation(RecyclerView.VERTICAL);
        mBoardSearchManager.setSmoothScrollbarEnabled(true);
        mBoardSearchManager.setItemPrefetchEnabled(true);
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
        rxbus.resetBus();
        mViewModel.onDestroyViewModel();
        super.onDestroy();
    }

    @Override
    public void onShowSnackBar() {
        SnackBarUtil.showSnackBar(binding.rvBoardSearch, R.string.board_search_can_not_find_result);
    }


}
