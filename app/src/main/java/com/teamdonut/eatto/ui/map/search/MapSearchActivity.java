package com.teamdonut.eatto.ui.map.search;

import android.os.Bundle;

import com.google.android.gms.common.util.Strings;
import com.teamdonut.eatto.R;
import com.teamdonut.eatto.common.helper.RealmDataHelper;
import com.teamdonut.eatto.databinding.MapSearchActivityBinding;


import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.databinding.DataBindingUtil;
import io.realm.Realm;

public class MapSearchActivity extends AppCompatActivity implements MapSearchNavigator {

    private MapSearchActivityBinding binding;
    private MapSearchViewModel mViewModel;

    private Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.map_search_activity);
        mViewModel = new MapSearchViewModel(this);
        binding.setViewmodel(mViewModel);

        realm = Realm.getDefaultInstance();

        setupToolbar();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
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
     * 최근 검색어 저장
     */
    private void saveRecentKeyword() {
        String input = binding.etSearch.getText().toString();

        if (!Strings.isEmptyOrWhitespace(input)) {
            RealmDataHelper.insertKeyword(realm, input);
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
