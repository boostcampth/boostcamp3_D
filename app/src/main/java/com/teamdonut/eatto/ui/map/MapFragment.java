package com.teamdonut.eatto.ui.map;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.gms.common.util.Strings;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.*;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.maps.android.clustering.ClusterItem;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;
import com.teamdonut.eatto.R;
import com.teamdonut.eatto.common.RxBus;
import com.teamdonut.eatto.common.util.ActivityUtils;
import com.teamdonut.eatto.common.util.GpsModule;
import com.teamdonut.eatto.common.util.SnackBarUtil;
import com.teamdonut.eatto.data.Board;
import com.teamdonut.eatto.data.Filter;
import com.teamdonut.eatto.databinding.MapFragmentBinding;
import com.teamdonut.eatto.ui.board.BoardAddActivity;
import com.teamdonut.eatto.ui.board.preview.BoardPreviewDialog;
import com.teamdonut.eatto.ui.map.bottomsheet.MapBoardAdapter;
import com.teamdonut.eatto.ui.map.search.MapSearchActivity;
import com.tedpark.tedpermission.rx2.TedRx2Permission;

import java.lang.ref.WeakReference;
import java.util.ArrayList;


public class MapFragment extends Fragment implements MapNavigator, OnMapReadyCallback {

    private MapFragmentBinding binding;

    private MapViewModel viewModel;
    private BottomSheetBehavior bottomSheetBehavior;

    private GoogleMap map;
    private ClusterManager<Board> clusterManager;
    private CameraPosition previousCameraPosition;

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
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.map_fragment, container, false);
        viewModel = ViewModelProviders.of(this).get(MapViewModel.class);
        viewModel.setNavigator(this);
        binding.setViewmodel(viewModel);
        binding.setLifecycleOwner(this);

        initOpenBoardObserver();
        initBoardsObserver();
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
        viewModel.loadBoards();
        binding.mv.onResume();
    }

    @Override
    public void onStop() {
        super.onStop();
        viewModel.onStopViewModel();
        binding.mv.onStop();
    }

    @Override
    public void onDestroy() {
        binding.mv.onDestroy();
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
                }, e -> {
                });
    }

    @Override
    public void setMyPosition() {
        String strLatitude = ActivityUtils.getStrValueSharedPreferences(getActivity(), "gps", "latitude");
        String strLongitude = ActivityUtils.getStrValueSharedPreferences(getActivity(), "gps", "longitude");
        double latitude = (Strings.isEmptyOrWhitespace(strLatitude) ? DEFAULT_LOCATION.latitude : Double.parseDouble(strLatitude));
        double longitude = (Strings.isEmptyOrWhitespace(strLatitude) ? DEFAULT_LOCATION.longitude : Double.parseDouble(strLongitude));

        map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude), DEFAULT_ZOOM));
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
        map = googleMap;
        setMyPosition();
        initCluster();

        map.setOnMarkerClickListener(clusterManager);
        map.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
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
                        viewModel.isSheetExpanded.set(true);
                        break;
                    }
                    case BottomSheetBehavior.STATE_COLLAPSED: {
                        viewModel.isSheetExpanded.set(false);
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

        RxBus.getInstance().sendBus(board); //send bus
    }

    private void initBoardsObserver() {
        viewModel.getBoards().observe(this, data -> {
            Filter filter = viewModel.getFilter(); //get Filter from viewModel.

            if (data.size() > 0) { //it could be search result / location result.
                mAdapter.updateItems(data);

                if (filter != null) { //if search result
                    setBottomSheetExpand(true);
                }
                clusterManager.clearItems();
                clusterManager.addItems(data);
                clusterManager.cluster();
            } else if (filter != null) { //data size is 0
                SnackBarUtil.showSnackBar(binding.colMap, R.string.board_search_can_not_find_result);
            }

            viewModel.resetFilter();
        });
    }

    private void initOpenBoardObserver() {
        viewModel.getOpenBoardEvent().observe(this, board -> {
            openBoardPreview(board);
        });
    }

    private void initRecyclerView() {
        RecyclerView rv = binding.mapBottomSheet.rvBoard;
        mAdapter = new MapBoardAdapter(new ArrayList<>(), viewModel);
        DividerItemDecoration itemDecoration = new DividerItemDecoration(rv.getContext(), 1);
        itemDecoration.setDrawable(ContextCompat.getDrawable(getActivity().getApplicationContext(), R.drawable.board_divider));

        rv.setHasFixedSize(true);
        rv.addItemDecoration(itemDecoration);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv.setAdapter(mAdapter);
    }

    private void initMapView(@Nullable Bundle savedInstanceState) {
        binding.mv.getMapAsync(this);
        binding.mv.onCreate(savedInstanceState);
    }
    private Bitmap createDrawableFromView(Context context, View view) {

        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        view.measure(displayMetrics.widthPixels, displayMetrics.heightPixels);
        view.layout(0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels);
        view.buildDrawingCache();
        Bitmap bitmap = Bitmap.createBitmap(view.getMeasuredWidth(), view.getMeasuredHeight(), Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);

        return bitmap;
    }


    private void initCluster() {
        clusterManager = new ClusterManager<>(getActivity(), map);
        previousCameraPosition = map.getCameraPosition();

        clusterManager.setRenderer(new DefaultClusterRenderer(this.getActivity(), map, clusterManager){
            @Override
            protected void onClusterItemRendered(ClusterItem clusterItem, Marker marker) {
                super.onClusterItemRendered(clusterItem, marker);
                View marker_root_view = LayoutInflater.from(getContext()).inflate(R.layout.custom_marker, null);
                marker.setIcon(BitmapDescriptorFactory.fromBitmap(createDrawableFromView(getContext(), marker_root_view)));
                return;
            }

            @Override
            protected void onBeforeClusterItemRendered(ClusterItem item, MarkerOptions markerOptions) {
                super.onBeforeClusterItemRendered(item, markerOptions);
                View marker_root_view = LayoutInflater.from(getContext()).inflate(R.layout.custom_marker, null);
                markerOptions.icon(BitmapDescriptorFactory.fromBitmap(createDrawableFromView(getContext(), marker_root_view)));
                return;
            }
        });

        clusterManager.setOnClusterItemClickListener(data -> {
            IS_MARKERCLICK = true;
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(data.getPosition(), DEFAULT_ZOOM));
            data.setSelect(true);
            mAdapter.notifyDataSetChanged();
            binding.mapBottomSheet.rvBoard.getLayoutManager().scrollToPosition(mAdapter.getItemPosition(data));
            setBottomSheetExpand(true);
            return true;
        });

        clusterManager.setOnClusterClickListener(data ->{
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(data.getPosition(), map.getCameraPosition().zoom + 1));
            return true;
        });

        map.setOnCameraIdleListener(() -> {
            if(!IS_MARKERCLICK) {
                viewModel.fetchAreaBoards(map.getProjection().getVisibleRegion().nearLeft, map.getProjection().getVisibleRegion().farRight);
                if (clusterManager.getRenderer() instanceof GoogleMap.OnCameraIdleListener) {
                    ((GoogleMap.OnCameraIdleListener) clusterManager.getRenderer()).onCameraIdle();
                }

                CameraPosition position = map.getCameraPosition();
                if (previousCameraPosition == null || previousCameraPosition.zoom != position.zoom) {
                    previousCameraPosition = map.getCameraPosition();
                }
            }
        });
    }

    public BottomSheetBehavior getBottomSheetBehavior() {
        return bottomSheetBehavior;
    }
}
