package com.teamdonut.eatto.ui.map;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.teamdonut.eatto.R;
import com.teamdonut.eatto.databinding.MapFragmentBinding;

import net.daum.mf.map.api.MapView;


public class MapFragment extends Fragment {

    private MapFragmentBinding binding;
    private MapViewModel mViewModel;

    public static MapFragment newInstance() {

        Bundle args = new Bundle();

        MapFragment fragment = new MapFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.map_fragment, container, false);
        View view = binding.getRoot();

        mViewModel = new MapViewModel();
        binding.setViewmodel(mViewModel);

        //레이아웃에 지도 추가
        MapView mapView = new MapView(getActivity());
        binding.flMapView.addView(mapView);

        return view;
    }


}
