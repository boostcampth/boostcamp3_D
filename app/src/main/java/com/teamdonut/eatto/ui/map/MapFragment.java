package com.teamdonut.eatto.ui.map;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.teamdonut.eatto.ui.board.BoardAddActivity;
import com.teamdonut.eatto.ui.board.BoardDetailActivity;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.teamdonut.eatto.R;
import com.teamdonut.eatto.common.util.ActivityUtils;
import com.teamdonut.eatto.common.util.GpsModule;
import com.teamdonut.eatto.databinding.MapFragmentBinding;
import com.teamdonut.eatto.ui.map.bottomsheet.MapBottomSheetViewModel;
import com.teamdonut.eatto.ui.map.search.MapSearchActivity;
import com.tedpark.tedpermission.rx2.TedRx2Permission;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

import java.lang.ref.WeakReference;


public class MapFragment extends Fragment implements MapNavigator {

    private MapFragmentBinding binding;
    private MapViewModel mViewModel;
    private MapBottomSheetViewModel mBottomSheetViewModel;

    private BottomSheetBehavior bottomSheetBehavior;
    private MapView mapView;
    private final int BOARD_ADD_REQUEST = 100;

    public static MapFragment newInstance() {
        return new MapFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.map_fragment, container, false);
        mViewModel = new MapViewModel(this);
        mBottomSheetViewModel = new MapBottomSheetViewModel(this);
        binding.setViewmodel(mViewModel);
        binding.setBottomsheetviewmodel(mBottomSheetViewModel);

        bottomSheetBehavior = BottomSheetBehavior.from(binding.mapBottomSheet.clMapBottomSheet);

        //레이아웃에 지도 추가
        mapView = new MapView(getActivity());
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
    public void startLocationUpdates() {
        TedRx2Permission.with(getActivity())
                .setDeniedMessage(R.string.all_permission_reject)
                .setPermissions(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION)
                .request()
                .subscribe(tedPermissionResult -> {
                    if (tedPermissionResult.isGranted()) {
                        GpsModule gpsModule = new GpsModule(new WeakReference<>(getContext()), this);
                        gpsModule.startLocationUpdates();
                    }
                }, throwable -> {
                });
    }

    @Override
    public void setMyPosition() {
        float latitude = Float.valueOf(ActivityUtils.getStrValueSharedPreferences(getActivity(), "gps", "latitude"));
        float longitude = Float.valueOf(ActivityUtils.getStrValueSharedPreferences(getActivity(), "gps", "longitude"));

        mapView.setMapCenterPointAndZoomLevel(MapPoint.mapPointWithGeoCoord(latitude, longitude), 2, true);
    }

    @Override
    public void goToBoardWrite() {
        Intent intent = new Intent(getContext(), BoardAddActivity.class);
        startActivityForResult(intent, BOARD_ADD_REQUEST);
    }

    @Override
    public void goToSearch() {
        Intent intent = new Intent(getActivity(), MapSearchActivity.class);
        startActivity(intent);
    }
}
