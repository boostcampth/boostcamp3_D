package com.teamdonut.eatto.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.teamdonut.eatto.R;
import com.teamdonut.eatto.common.util.ActivityUtils;
import com.teamdonut.eatto.common.util.HorizontalDividerItemDecorator;
import com.teamdonut.eatto.databinding.HomeFragmentBinding;
import com.teamdonut.eatto.ui.map.search.MapSearchActivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class HomeFragment extends Fragment implements HomeNavigator {
    private HomeFragmentBinding binding;
    private HomeViewModel mViewModel;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.home_fragment, container, false);
        mViewModel = new HomeViewModel(this);
        binding.setViewmodel(mViewModel);
        binding.setLifecycleOwner(this);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initBoardRecommendRv(binding.rvRecommendBoard);
        initUserRankingRv(binding.rvRank);
        initObserve();
        fetchData();
    }

    private void initObserve(){
        mViewModel.getBoardFlag().observe(this, data -> {
            if(data == 2){
                mViewModel.getBoardRecommendAdapter().setItem(mViewModel.getAnyBoards().getValue());
            }
        });
    }

    private void fetchData(){
        mViewModel.fetchRankUsers();
        mViewModel.fetchRankUser();
        mViewModel.fetchRecommendBoards(
                ActivityUtils.getStrValueSharedPreferences(getActivity(), "gps", "longtitude"),
                ActivityUtils.getStrValueSharedPreferences(getActivity(), "gps", "latitude")
        );
        mViewModel.fetchAnyBoards();
    }

    @Override
    public void onDestroy() {
        mViewModel.onDestroyViewModel();
        super.onDestroy();
    }

    private void initUserRankingRv(RecyclerView recyclerView) {
        RecyclerView.LayoutManager rankingManager = new LinearLayoutManager(getContext()) {
            @Override
            public boolean checkLayoutParams(RecyclerView.LayoutParams lp) {
                lp.height = (getWidth() / 9);
                return super.checkLayoutParams(lp);
            }
        };

        recyclerView.addItemDecoration(new HorizontalDividerItemDecorator(ContextCompat.getDrawable(getContext(), R.drawable.ranking_divider), 0.03));
        setRecyclerView(recyclerView, rankingManager);
    }

    private void initBoardRecommendRv(RecyclerView recyclerView) {
        RecyclerView.LayoutManager recommendBoardManager = new LinearLayoutManager(getContext()) {
            @Override
            public boolean checkLayoutParams(RecyclerView.LayoutParams lp) {
                int dp = (int) (getResources().getDimension(R.dimen.space_medium_margin) / getResources().getDisplayMetrics().density);
                float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp * 3, getResources().getDisplayMetrics());
                lp.width = (getWidth() - (int) px) / 2;
                return super.checkLayoutParams(lp);
            }
        };

        ((LinearLayoutManager) recommendBoardManager).setOrientation(LinearLayoutManager.HORIZONTAL);
        setRecyclerView(recyclerView, recommendBoardManager);
    }

    private void setRecyclerView(RecyclerView recyclerView, RecyclerView.LayoutManager layoutManager) {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
    }

    @Override
    public void goToMapSearch() {
        Intent intent = new Intent(getActivity(), MapSearchActivity.class);
        startActivity(intent);
    }
}
