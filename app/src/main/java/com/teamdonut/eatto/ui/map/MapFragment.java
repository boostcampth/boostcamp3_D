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

    private MapFragmentBinding mapFragmentBinding;
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
        mapFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.map_fragment,container,false);
        View view = mapFragmentBinding.getRoot();

        mViewModel = new MapViewModel();
        mapFragmentBinding.setViewmodel(mViewModel);

        //get AppKey
        String appKey= getAppKey(getContext());

        MapView mapView = new MapView(getActivity());
        mapFragmentBinding.flMapView.addView(mapView);



        return view;
    }


    private String getAppKey(Context context) {
        String apiKey = null;

        try {
            String e = context.getPackageName();
            ApplicationInfo ai = context
                    .getPackageManager()
                    .getApplicationInfo(e, PackageManager.GET_META_DATA);
            Bundle bundle = ai.metaData;
            if(bundle != null) {
                apiKey = bundle.getString("com.kakao.sdk.AppKey");
            }
        } catch (Exception e) {
            Log.d("getAppKeyError", "Caught non-fatal exception while retrieving apiKey: " + e);
        }

        return apiKey;
    }




}
