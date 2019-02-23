package com.teamdonut.eatto.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.teamdonut.eatto.R;
import com.teamdonut.eatto.common.util.ActivityUtils;
import com.teamdonut.eatto.common.util.HorizontalDividerItemDecorator;
import com.teamdonut.eatto.databinding.HomeFragmentBinding;
import com.teamdonut.eatto.ui.map.search.MapSearchActivity;

import java.util.ArrayList;

public class HomeFragment extends Fragment implements HomeNavigator {

    private HomeFragmentBinding binding;
    private HomeViewModel mViewModel;

    private UserRankingAdapter mRankingAdapter;
    private BoardRecommendAdapter mRecommendAdapter;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.home_fragment, container, false);
        mViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);

        binding.setViewmodel(mViewModel);
        binding.setLifecycleOwner(this);

        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mViewModel.setNavigator(this);

        initRecommendRv();
        initRankingRv();

        fetchData();
    }
    
    private void fetchData() {
        mViewModel.fetchRankUsers();
        mViewModel.fetchRankUser();
        mViewModel.fetchRecommendBoards(
                ActivityUtils.getStrValueSharedPreferences(getActivity(), "gps", "longitude"),
                ActivityUtils.getStrValueSharedPreferences(getActivity(), "gps", "latitude")
        );
    }

    private void initRankingRv() {
        RecyclerView rv = binding.rvRank;
        mRankingAdapter = new UserRankingAdapter(new ArrayList<>(0), mViewModel);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext()) {
            @Override
            public boolean checkLayoutParams(RecyclerView.LayoutParams lp) {
                lp.height = (getWidth() / 9);
                return super.checkLayoutParams(lp);
            }
        };
        rv.addItemDecoration(new HorizontalDividerItemDecorator(ContextCompat.getDrawable(getContext(), R.drawable.ranking_divider), 0.03));
        rv.setHasFixedSize(true);
        rv.setLayoutManager(layoutManager);
        rv.setAdapter(mRankingAdapter);
    }

    private void initRecommendRv() {
        RecyclerView rv = binding.rvRecommend;
        mRecommendAdapter = new BoardRecommendAdapter(new ArrayList<>(0), mViewModel);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false) {
            @Override
            public boolean checkLayoutParams(RecyclerView.LayoutParams lp) {
                int dp = (int) (getResources().getDimension(R.dimen.space_medium_margin) / getResources().getDisplayMetrics().density);
                float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp * 3, getResources().getDisplayMetrics());
                lp.width = (getWidth() - (int) px) / 2;
                return super.checkLayoutParams(lp);
            }
        };
        rv.setLayoutManager(layoutManager);
        rv.setHasFixedSize(true);
        rv.setAdapter(mRecommendAdapter);
    }

    @Override
    public void goToMapSearch() {
        Intent intent = new Intent(getActivity(), MapSearchActivity.class);
        startActivity(intent);
    }
}
