package com.teamdonut.eatto.ui.home;

import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.teamdonut.eatto.R;
import com.teamdonut.eatto.databinding.HomeFragmentBinding;

public class HomeFragment extends Fragment {
    private HomeFragmentBinding binding;
    private HomeViewModel mViewModel;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.home_fragment, container, false);
        mViewModel = new HomeViewModel();
        binding.setViewmodel(mViewModel);
        initRecommendBoardRv();
        return binding.getRoot();
    }

    void initRecommendBoardRv() {
        RecyclerView.LayoutManager recommendBoardManager = new LinearLayoutManager(this.getContext()) {
            @Override
            public boolean checkLayoutParams(RecyclerView.LayoutParams lp) {
                int dp = (int) (getResources().getDimension(R.dimen.space_medium_margin) / getResources().getDisplayMetrics().density);
                float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp * 3, getResources().getDisplayMetrics());
                lp.width = (getWidth() - (int) px) / 2;
                return super.checkLayoutParams(lp);
            }
        };
        ((LinearLayoutManager) recommendBoardManager).setOrientation(LinearLayoutManager.HORIZONTAL);
        binding.rvRecommendBoard.setHasFixedSize(true);
        binding.rvRecommendBoard.setLayoutManager(recommendBoardManager);
    }

}
