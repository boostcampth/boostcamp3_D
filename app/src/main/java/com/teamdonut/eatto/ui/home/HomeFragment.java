package com.teamdonut.eatto.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.teamdonut.eatto.R;
import com.teamdonut.eatto.common.RxBus;
import com.teamdonut.eatto.common.util.ActivityUtils;
import com.teamdonut.eatto.common.util.HorizontalDividerItemDecorator;
import com.teamdonut.eatto.common.util.NetworkCheckUtil;
import com.teamdonut.eatto.common.util.SnackBarUtil;
import com.teamdonut.eatto.data.Board;
import com.teamdonut.eatto.databinding.HomeFragmentBinding;
import com.teamdonut.eatto.ui.board.preview.BoardPreviewDialog;
import com.teamdonut.eatto.ui.map.search.MapSearchActivity;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class HomeFragment extends Fragment implements HomeNavigator {

    private HomeFragmentBinding binding;
    private HomeViewModel viewModel;

    private UserRankingAdapter userRankingAdapter;
    private BoardRecommendAdapter boardRecommendAdapter;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.home_fragment, container, false);
        viewModel = ViewModelProviders.of(this).get(HomeViewModel.class);

        binding.setViewmodel(viewModel);
        binding.setLifecycleOwner(this);

        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        viewModel.setNavigator(this);

        if (NetworkCheckUtil.networkCheck(getContext().getApplicationContext())) {
            initRecommendRv();
            initRankingRv();

            fetchData();
        } else {
            SnackBarUtil.showSnackBar(binding.rvRank, R.string.all_network_check);
        }
    }

    private void fetchData() {
        viewModel.fetchRankUsers();
        viewModel.fetchRankUser();
        viewModel.fetchRecommendBoards(
                ActivityUtils.getStrValueSharedPreferences(getActivity(), "gps", "longitude"),
                ActivityUtils.getStrValueSharedPreferences(getActivity(), "gps", "latitude")
        );
    }

    private void initRankingRv() {
        RecyclerView rv = binding.rvRank;
        userRankingAdapter = new UserRankingAdapter(new ArrayList<>(0), viewModel);

        rv.addItemDecoration(new HorizontalDividerItemDecorator(ContextCompat.getDrawable(getContext(), R.drawable.ranking_divider), 0.03));
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv.setAdapter(userRankingAdapter);
    }

    private void initRecommendRv() {
        RecyclerView rv = binding.rvRecommend;
        boardRecommendAdapter = new BoardRecommendAdapter(new ArrayList<>(0), viewModel);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false) {
            @Override
            public boolean checkLayoutParams(RecyclerView.LayoutParams lp) {
                lp.width = (getWidth()) / 2;
                return super.checkLayoutParams(lp);
            }
        };
        rv.setLayoutManager(layoutManager);
        rv.setHasFixedSize(true);
        rv.setAdapter(boardRecommendAdapter);
    }

    @Override
    public void goToMapSearch() {
        Intent intent = new Intent(getActivity(), MapSearchActivity.class);
        startActivity(intent);
    }

    @Override
    public void openBoardPreview(Board board) {
        final String PREVIEW_TAG = "preview";
        BoardPreviewDialog dialog = BoardPreviewDialog.newInstance(board);
        dialog.show(getChildFragmentManager(), PREVIEW_TAG);

        RxBus.getInstance().sendBus(board); //send bus
    }
}
