package com.teamdonut.eatto.ui.map.search;

import android.os.Bundle;
import android.view.MenuItem;

import com.teamdonut.eatto.R;
import com.teamdonut.eatto.common.RxBus;
import com.teamdonut.eatto.common.util.ActivityUtils;
import com.teamdonut.eatto.databinding.MapSearchActivityBinding;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.core.view.GravityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

public class MapSearchActivity extends AppCompatActivity implements MapSearchNavigator {

    private MapSearchActivityBinding binding;
    private MapSearchViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.map_search_activity);

        mViewModel = ViewModelProviders.of(this).get(MapSearchViewModel.class);
        mViewModel.setNavigator(this);

        MotionLayout motionLayout = binding.layoutFilter.clThis;


        binding.setViewmodel(mViewModel);
        binding.setLifecycleOwner(this);
        fetch();
        initObserver();
        initToolbar();
    }

    public void fetch() {
        String longitude = ActivityUtils.getStrValueSharedPreferences(getApplicationContext(), "gps", "longitude");
        String latitude = ActivityUtils.getStrValueSharedPreferences(getApplicationContext(), "gps", "latitude");
        mViewModel.fetchEtKeywordHint(getResources().getString(R.string.kakao_rest_api_key), longitude, latitude, getResources().getString(R.string.all_default_address));
    }

    /**
     * Activity Observe search action.
     */
    private void initObserver() {
        mViewModel.getSearchCondition().observe(this, data -> {
            RxBus.getInstance().sendBus(data); //send data to mapFragment.
            finish();
        });

        mViewModel.getEtKeywordHint().observe(this, data -> {
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
