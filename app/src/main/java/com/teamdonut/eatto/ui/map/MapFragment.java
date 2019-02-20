package com.teamdonut.eatto.ui.map;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.common.util.Strings;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.maps.android.clustering.ClusterManager;
import com.teamdonut.eatto.R;
import com.teamdonut.eatto.common.util.ActivityUtils;
import com.teamdonut.eatto.common.util.GpsModule;
import com.teamdonut.eatto.common.util.SnackBarUtil;
import com.teamdonut.eatto.data.Board;
import com.teamdonut.eatto.data.Filter;
import com.teamdonut.eatto.databinding.MapFragmentBinding;
import com.teamdonut.eatto.ui.board.BoardAddActivity;
import com.teamdonut.eatto.ui.board.BoardPreviewDialog;
import com.teamdonut.eatto.ui.map.bottomsheet.MapBoardAdapter;
import com.teamdonut.eatto.ui.map.search.MapSearchActivity;
import com.tedpark.tedpermission.rx2.TedRx2Permission;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class MapFragment extends Fragment implements MapNavigator, OnMapReadyCallback {

    private MapFragmentBinding binding;

    private MapViewModel mViewModel;
    private BottomSheetBehavior bottomSheetBehavior;

    private GoogleMap mMap;
    private ClusterManager<Board> mClusterManager;
    private CameraPosition mPreviousCameraPosition;

    private BoardPreviewDialog dialog;

    private MapBoardAdapter mAdapter;

    private final int BOARD_ADD_REQUEST = 100;
    private final int DEFAULT_ZOOM = 15;
    private boolean IS_MARKERCLICK = false;
    private final LatLng DEFAULT_LOCATION = new LatLng(37.566467, 126.978174); // 서울 시청

    private final String PREVIEW_TAG = "preview";

    public static MapFragment newInstance() {
        return new MapFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(MapViewModel.class);
        mViewModel.setNavigator(this);

        initOpenBoardObserver();
        initBoardsObserver();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.map_fragment, container, false);
        binding.setViewmodel(mViewModel);
        binding.setLifecycleOwner(this);

        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initBottomSheetBehavior();
        initMapView(savedInstanceState);
        initRecyclerView();
    }

    @Override
    public void onResume() {
        super.onResume();
        mViewModel.loadBoards();
    }

    @Override
    public void onStop() {
        super.onStop();
        mViewModel.onStopViewModel();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mViewModel.getBoards().removeObservers(this);
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
        String strLatitude = ActivityUtils.getStrValueSharedPreferences(getActivity(), "gps", "latitude");
        String strLongitude = ActivityUtils.getStrValueSharedPreferences(getActivity(), "gps", "longitude");
        double latitude = (Strings.isEmptyOrWhitespace(strLatitude) ? DEFAULT_LOCATION.latitude : Double.parseDouble(strLatitude));
        double longitude = (Strings.isEmptyOrWhitespace(strLatitude) ? DEFAULT_LOCATION.longitude : Double.parseDouble(strLongitude));

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude), DEFAULT_ZOOM));
    }

    @Override
    public void goToBoardAdd() {
        Intent intent = new Intent(getContext(), BoardAddActivity.class);
        startActivityForResult(intent, BOARD_ADD_REQUEST);
    }

    @Override
    public void goToMapSearch() {
        Intent intent = new Intent(getActivity(), MapSearchActivity.class);
        startActivity(intent);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        setMyPosition();
        initCluster();

        mMap.setOnMarkerClickListener(mClusterManager);
        mMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {
                // 맵 로딩 콜백
            }
        });
    }

    private void initBottomSheetBehavior() {
        bottomSheetBehavior = BottomSheetBehavior.from(binding.mapBottomSheet.clMapBottomSheet);
        bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                switch (newState) {
                    case BottomSheetBehavior.STATE_EXPANDED: {
                        mViewModel.isSheetExpanded.set(true);
                        break;
                    }
                    case BottomSheetBehavior.STATE_COLLAPSED: {
                        mViewModel.isSheetExpanded.set(false);
                        IS_MARKERCLICK = false;
                        break;
                    }
                    default: {
                        return;
                    }
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                //nothing to do.
            }
        });
    }

    private void openBoardPreview(Board board) {
        dialog = BoardPreviewDialog.newInstance(board);
        dialog.show(getChildFragmentManager(), PREVIEW_TAG);
    }

    private void initBoardsObserver() {
        mViewModel.getBoards().observe(this, data -> {
            Filter filter = mViewModel.getFilter(); //get Filter from viewModel.

            if (data.size() > 0) { //it could be search result / location result.
                mAdapter.updateItems(data);

                if (filter != null) { //if search result
                    setBottomSheetExpand(true);
                }
                mClusterManager.clearItems();
                mClusterManager.addItems(data);
                mClusterManager.cluster();
            } else if (filter != null) { //data size is 0
                SnackBarUtil.showSnackBar(binding.colMap, R.string.board_search_can_not_find_result);
            }

            mViewModel.resetFilter();
        });
    }

    private void initOpenBoardObserver() {
        mViewModel.getOpenBoardEvent().observe(this, board -> {
            openBoardPreview(board);
        });
    }

    private void initRecyclerView() {
        RecyclerView rv = binding.mapBottomSheet.rvBoard;
        mAdapter = new MapBoardAdapter(new ArrayList<>(), mViewModel);
        DividerItemDecoration itemDecoration = new DividerItemDecoration(rv.getContext(), 1);
        itemDecoration.setDrawable(ContextCompat.getDrawable(getActivity().getApplicationContext(), R.drawable.map_board_divider));

        rv.setHasFixedSize(true);
        rv.addItemDecoration(itemDecoration);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv.setAdapter(mAdapter);
    }

    private void initMapView(@Nullable Bundle savedInstanceState) {
        binding.mv.onCreate(savedInstanceState);
        binding.mv.onResume();
        binding.mv.getMapAsync(this);
    }

    private void initCluster() {
        mClusterManager = new ClusterManager<>(getActivity(), mMap);
        mPreviousCameraPosition = mMap.getCameraPosition();

        mClusterManager.setOnClusterItemClickListener(data -> {
            IS_MARKERCLICK = true;
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(data.getPosition(), DEFAULT_ZOOM));
            data.setSelect(true);
            mAdapter.notifyDataSetChanged();
            binding.mapBottomSheet.rvBoard.getLayoutManager().scrollToPosition(mAdapter.getItemPosition(data));
            setBottomSheetExpand(true);
            return true;
        });

        mMap.setOnCameraIdleListener(() -> {
            if(!IS_MARKERCLICK) {
                mViewModel.fetchBoards(mMap.getProjection().getVisibleRegion().nearLeft, mMap.getProjection().getVisibleRegion().farRight);
                if (mClusterManager.getRenderer() instanceof GoogleMap.OnCameraIdleListener) {
                    ((GoogleMap.OnCameraIdleListener) mClusterManager.getRenderer()).onCameraIdle();
                }

                CameraPosition position = mMap.getCameraPosition();
                if (mPreviousCameraPosition == null || mPreviousCameraPosition.zoom != position.zoom) {
                    mPreviousCameraPosition = mMap.getCameraPosition();
                }
            }
        });
    }
}
