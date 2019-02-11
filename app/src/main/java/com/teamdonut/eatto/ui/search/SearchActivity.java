package com.teamdonut.eatto.ui.search;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.google.android.gms.common.util.Strings;
import com.teamdonut.eatto.R;
import com.teamdonut.eatto.common.util.ActivityUtils;
import com.teamdonut.eatto.databinding.SearchActivityBinding;

public class SearchActivity extends AppCompatActivity implements SearchNavigator {

    private SearchActivityBinding binding;
    private SearchViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.search_activity);
        mViewModel = new SearchViewModel(this);
        binding.setViewmodel(mViewModel);

        setupToolbar();
    }

    private void setupToolbar() {
        setSupportActionBar(binding.toolbarSearch);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    public void goSearch() {
        //search
        saveRecentKeyword();
    }

    /**
     * 최근 검색어를 sharedPreference 에 저장.
     */
    private void saveRecentKeyword() {
        String keyword = binding.etSearch.getText().toString();

        if (!Strings.isEmptyOrWhitespace(keyword)) {
            ActivityUtils.saveStrValueSharedPreferences(this, "keyword", "content", keyword);
        }
    }


    @Override
    public void openNavigationView() {
        binding.dlSearch.openDrawer(GravityCompat.END);
    }

    @Override
    public void closeNavigationView() {
        binding.dlSearch.closeDrawer(GravityCompat.END);
    }

}
