package com.teamdonut.eatto.ui.map.search;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import com.appyvet.materialrangebar.RangeBar;
import com.teamdonut.eatto.R;
import com.teamdonut.eatto.common.RxBus;
import com.teamdonut.eatto.common.util.ActivityUtils;
import com.teamdonut.eatto.databinding.MapSearchActivityBinding;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class MapSearchActivity extends AppCompatActivity implements MapSearchNavigator {

    private MapSearchActivityBinding binding;
    private MapSearchViewModel viewModel;

    private MapKeywordAdapter mapKeywordAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.map_search_activity);

        viewModel = ViewModelProviders.of(this).get(MapSearchViewModel.class);
        viewModel.setNavigator(this);

        binding.setViewmodel(viewModel);
        binding.setLifecycleOwner(this);

        fetchKeywordHint();

        initToolbar();
        initRecentKeywordRv();
        initEditSearch();
        initRangeBar();

        initSearchObserver();
        initKeywordHintObserver();
        initResetObserver();
    }

    private void initRangeBar() {
        RangeBar rb = binding.layoutFilter.rbFilterAge;
        rb.setOnRangeBarChangeListener(new RangeBar.OnRangeBarChangeListener() {
            @Override
            public void onRangeChangeListener(RangeBar rangeBar, int leftPinIndex, int rightPinIndex, String leftPinValue, String rightPinValue) {
                viewModel.getMinAge().set(Integer.parseInt(leftPinValue));
                viewModel.getMaxAge().set(Integer.parseInt(rightPinValue));
            }
        });
    }

    private void initResetObserver() {
        viewModel.getResetFilter().observe(this, action -> {
            binding.layoutFilter.rbFilterAge.setRangePinsByValue(15, 80);
            binding.layoutFilter.acsTimeMin.setSelection(0);
            binding.layoutFilter.acsTimeMax.setSelection(23);
        });
    }

    private void initEditSearch() {
        binding.etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    viewModel.onGoSearchClick(v.getText().toString());
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    protected void onDestroy() {
        binding.rvRecentKeyword.setAdapter(null);
        super.onDestroy();
    }

    public void fetchKeywordHint() {
        String longitude = ActivityUtils.getStrValueSharedPreferences(getApplicationContext(), "gps", "longitude");
        String latitude = ActivityUtils.getStrValueSharedPreferences(getApplicationContext(), "gps", "latitude");
        viewModel.fetchEtKeywordHint(getResources().getString(R.string.kakao_rest_api_key), longitude, latitude, getResources().getString(R.string.all_default_address));
    }

    /**
     * Activity Observe search action.
     */
    private void initSearchObserver() {
        viewModel.getFilter().observe(this, data -> {
            RxBus.getInstance().sendBus(data); //send data to mapFragment.
            finish();
        });
    }

    private void initKeywordHintObserver() {
        viewModel.getEtKeywordHint().observe(this, data -> {
            binding.etSearch.setHint(data);
        });
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

    private void initToolbar() {
        setSupportActionBar(binding.toolbarSearch);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initRecentKeywordRv() {
        RecyclerView rv = binding.rvRecentKeyword;
        mapKeywordAdapter = new MapKeywordAdapter(viewModel.fetchKeywords(), true, viewModel);

        DividerItemDecoration itemDecoration = new DividerItemDecoration(rv.getContext(), 1);
        itemDecoration.setDrawable(ContextCompat.getDrawable(this, R.drawable.board_divider));

        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.addItemDecoration(itemDecoration);
        rv.setAdapter(mapKeywordAdapter);
    }

    @Override
    public void openNavigationView() {
        binding.dlSearch.openDrawer(GravityCompat.END);
    }

    @Override
    public void closeNavigationView() {
        binding.dlSearch.closeDrawer(GravityCompat.END);
    }

    @Override
    public void onBackPressed() {
        if (binding.dlSearch.isDrawerOpen(binding.nvSearch)) {
            closeNavigationView();  //if drawer is opened, close drawer.
        } else {
            super.onBackPressed();
        }
    }
}
