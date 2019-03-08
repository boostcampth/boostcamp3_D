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
import com.teamdonut.eatto.common.util.ActivityUtils;
import com.teamdonut.eatto.common.util.EndlessRecyclerViewScrollListener;
import com.teamdonut.eatto.common.util.HorizontalDividerItemDecorator;
import com.teamdonut.eatto.common.util.KeyboardUtil;
import com.teamdonut.eatto.common.util.NetworkCheckUtil;
import com.teamdonut.eatto.common.util.SnackBarUtil;
import com.teamdonut.eatto.data.kakao.Document;
import com.teamdonut.eatto.databinding.BoardSearchActivityBinding;
import com.teamdonut.eatto.ui.board.BoardNavigator;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class BoardSearchActivity extends AppCompatActivity implements BoardNavigator {

    private BoardSearchActivityBinding binding;
    private BoardSearchViewModel viewModel;

    private EndlessRecyclerViewScrollListener scrollListener;

    private BoardSearchAdapter searchAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.board_search_activity);
        viewModel = ViewModelProviders.of(this).get(BoardSearchViewModel.class);
        viewModel.setNavigator(this);

        binding.setViewmodel(viewModel);
        binding.setLifecycleOwner(this);

        initToolbar();
        initSearchResultRv();
        initObserver();
        initSearchObserver();
        initKeyboardSearchListener();

        fetchHint();
    }

    public void fetchHint() {
        String longitude = ActivityUtils.getStrValueSharedPreferences(getApplicationContext(), "gps", "longitude");
        String latitude = ActivityUtils.getStrValueSharedPreferences(getApplicationContext(), "gps", "latitude");
        viewModel.fetchEtKeywordHint(getResources().getString(R.string.kakao_rest_api_key), longitude, latitude, getResources().getString(R.string.all_default_address));
    }

    public void initSearchObserver() {
        viewModel.getDocuments().observe(this, data -> {
            if (data.size() == 0) {
                SnackBarUtil.showSnackBar(binding.rvBoardSearch, R.string.board_search_can_not_find_result);
            } else {
                searchAdapter.addItems(data.subList(searchAdapter.getItemCount(), data.size()));
            }
        });
    }

    public void initObserver() {
        viewModel.getEtKeywordHint().observe(this, data -> {
            binding.etInputSearchKeyword.setHint(data);
        });
    }

    @Override
    public void sendDocument(Document document) {
        Intent resultIntent = new Intent();
        resultIntent.putExtra("placeName", document.getPlaceName());
        resultIntent.putExtra("addressName", document.getAddressName());
        resultIntent.putExtra("x", document.getX());
        resultIntent.putExtra("y", document.getY());
        setResult(RESULT_OK, resultIntent);
        finish();
    }

    public void initToolbar() {
        setSupportActionBar(binding.tbBoardSearch);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);  //Toolbar nav button
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
                if (NetworkCheckUtil.networkCheck(getApplicationContext())) {
                    search();
                } else {
                    KeyboardUtil.hideSoftKeyboard(getCurrentFocus(), getApplicationContext());
                    SnackBarUtil.showSnackBar(binding.rvBoardSearch, R.string.all_network_check);
                }
                break;
        }
        return true;
    }

    public void search() {
        String input = binding.etInputSearchKeyword.getText().toString();

        if (Strings.isEmptyOrWhitespace(input)) {
            SnackBarUtil.showSnackBar(getCurrentFocus(), R.string.board_search_please_insert);
        } else {
            searchAdapter.removeAllItems();
            scrollListener.resetState();
            viewModel.fetchAddressResult(getResources().getText(R.string.kakao_rest_api_key).toString(), input, 1, 15);
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
        RecyclerView rv = binding.rvBoardSearch;
        searchAdapter = new BoardSearchAdapter(new ArrayList<>(), viewModel);
        searchAdapter.setHasStableIds(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setSmoothScrollbarEnabled(true);
        layoutManager.setItemPrefetchEnabled(true);

        scrollListener = new EndlessRecyclerViewScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to the bottom of the list
                if (!viewModel.isEndDocuments()) {
                    viewModel.fetchAddressResult(getText(R.string.kakao_rest_api_key).toString(), binding.etInputSearchKeyword.getText().toString(), page, 15);
                }
            }
        };

        rv.addOnScrollListener(scrollListener);
        rv.setLayoutManager(layoutManager);
        rv.setHasFixedSize(true);
        rv.addItemDecoration(new HorizontalDividerItemDecorator
                (ContextCompat.getDrawable(getApplicationContext(), R.drawable.ranking_divider), 0.03));
        rv.setAdapter(searchAdapter);
    }
}
