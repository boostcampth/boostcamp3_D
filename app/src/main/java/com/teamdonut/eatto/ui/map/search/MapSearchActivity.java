package com.teamdonut.eatto.ui.map.search;

import android.os.Bundle;
import android.view.MenuItem;

import com.teamdonut.eatto.R;
import com.teamdonut.eatto.common.RxBus;
import com.teamdonut.eatto.databinding.MapSearchActivityBinding;

import androidx.appcompat.app.AppCompatActivity;
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

        binding.setViewmodel(mViewModel);
        binding.setLifecycleOwner(this);

        initObserver();
        initToolbar();
    }

    /**
     * Activity Observe search action.
     */
    private void initObserver() {
        mViewModel.getSearchCondition().observe(this, searchCondition-> {
                RxBus.getInstance().sendBus(searchCondition); //send data to mapFragment.
                finish();
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
