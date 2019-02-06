package com.teamdonut.eatto.ui.map;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.teamdonut.eatto.R;
import com.teamdonut.eatto.databinding.MapFragmentBinding;
import com.teamdonut.eatto.ui.board.BoardAddActivity;
import com.teamdonut.eatto.ui.map.bottomsheet.MapBottomSheetViewModel;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import com.teamdonut.eatto.R;
import com.teamdonut.eatto.databinding.MapFragmentBinding;
import net.daum.mf.map.api.MapView;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;


public class MapFragment extends Fragment implements MapNavigator {

    private MapFragmentBinding binding;
    private MapViewModel mViewModel;
    private MapBottomSheetViewModel mBottomSheetViewModel;

    private BottomSheetBehavior bottomSheetBehavior;

    public static MapFragment newInstance() {
        Bundle args = new Bundle();

        MapFragment fragment = new MapFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.map_fragment, container, false);

        mViewModel = new MapViewModel(getContext().getApplicationContext());
        mViewModel.setNavigator(this);
        mBottomSheetViewModel = new MapBottomSheetViewModel(this);
        binding.setViewmodel(mViewModel);
        binding.setBottomsheetviewmodel(mBottomSheetViewModel);

        bottomSheetBehavior = BottomSheetBehavior.from(binding.mapBottomSheet.clMapBottomSheet);

        //레이아웃에 지도 추가
        MapView mapView = new MapView(getActivity());
        binding.flMapView.addView(mapView);

        return binding.getRoot();
    }

    @Override
    public void onDestroy() {
        mViewModel.onFragmentDestroyed();
        super.onDestroy();
    }

    @Override
    public void setBottomSheetExpand(Boolean state) {
        if (state) { //expand bottom sheet.
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        } else {
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        }
    }

    @Override
    public void addBoard() {
        Intent intent = new Intent(getContext(), BoardAddActivity.class);
        startActivity(intent);
    }


}
