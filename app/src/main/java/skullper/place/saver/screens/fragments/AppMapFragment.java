package skullper.place.saver.screens.fragments;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.maps.android.clustering.ClusterManager;

import java.util.List;

import butterknife.BindView;
import skullper.place.saver.R;
import skullper.place.saver.base.fragment.BaseFragment;
import skullper.place.saver.data.PlaceItem;
import skullper.place.saver.mvp.presenters.MapPresenter;
import skullper.place.saver.mvp.views.AppMapView;
import skullper.place.saver.providers.impl.Toaster;
import skullper.place.saver.screens.MainActivity;
import skullper.place.saver.utils.LocationHelper;
import skullper.place.saver.utils.PlaceItemRenderer;

import static skullper.place.saver.utils.PlaceItemRenderer.MIN_CLUSTER_SIZE;

/**
 * Created by skullper on 29.06.18.
 * Contact the developer - sckalper@gmail.com
 * company - A2Lab
 */

public class AppMapFragment extends BaseFragment<MainActivity, MapPresenter> //
        implements OnMapReadyCallback, GoogleMap.OnMapLongClickListener, //
        LocationHelper.OnCurrentLocationListener, AppMapView, //
        GoogleMap.OnMarkerClickListener {

    private static final int RC_PERMISSIONS = 785;

    @BindView(R.id.map)
    MapView     mapView;
    @BindView(R.id.pb_map)
    ProgressBar progressBar;

    private GoogleMap                 map;
    private LocationHelper            helper;
    private ClusterManager<PlaceItem> clusterManager;

    @Override
    public String tag() {
        return AppMapFragment.class.getSimpleName();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_map;
    }

    @Override
    protected MapPresenter createPresenter() {
        return new MapPresenter(this);
    }

    @Override
    protected void initViews(View rootView) {
        helper = new LocationHelper(activity, this);
        mapView.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        initClusterManager();
        presenter.fetchPlaces();
        map.setOnMapLongClickListener(this);
        map.setOnMarkerClickListener(clusterManager);
        map.setOnCameraIdleListener(clusterManager);
        checkLocationPermission();
    }

    @Override
    public void onMapLongClick(LatLng latLng) {
        showPlaceCreationDialog(latLng);
    }

    @Override
    public void onLocationReady(Location location) {
        progressBar.setVisibility(View.GONE);
        Toaster.getInstance().toast(R.string.map_loading_toast);
        selectCurrentLocation(location);
    }

    private boolean isInfoWindowShown = false;

    @Override
    public boolean onMarkerClick(Marker marker) {
        if (isInfoWindowShown) {
            marker.hideInfoWindow();
        } else {
            marker.showInfoWindow();
        }
        isInfoWindowShown = !isInfoWindowShown;
        return true;
    }

    @Override
    public void onPlaceSaved(@NonNull PlaceItem item) {
        addPlace(item);
    }

    @Override
    public void onPlacesFetched(List<PlaceItem> items) {
        for (PlaceItem item : items) {
            addPlace(item);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, //
                                           @NonNull int[] grantResults) {
        if (requestCode == RC_PERMISSIONS) {
            for (int grantResult : grantResults) {
                if (grantResult == PackageManager.PERMISSION_GRANTED) {
                    fetchUserLocation();
                } else {
                    requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, //
                            Manifest.permission.ACCESS_FINE_LOCATION}, RC_PERMISSIONS);
                }
            }
        }
    }

    private void checkLocationPermission() {
        if ((ActivityCompat.checkSelfPermission(mapView.getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) //
                != PackageManager.PERMISSION_GRANTED) && (ActivityCompat.checkSelfPermission(mapView.getContext(), Manifest.permission.ACCESS_FINE_LOCATION) //
                != PackageManager.PERMISSION_GRANTED)) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, //
                    Manifest.permission.ACCESS_FINE_LOCATION}, RC_PERMISSIONS);
        } else {
            fetchUserLocation();
        }
    }

    private void selectCurrentLocation(Location location) {
        LatLng coordinates = new LatLng(location.getLatitude(), location.getLongitude());
        addPlace(new PlaceItem(coordinates, "Current position"));
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(coordinates, 15));
    }

    private void fetchUserLocation() {
        progressBar.setVisibility(View.VISIBLE);
        helper.retrieveLocation();
    }

    private void initClusterManager() {
        clusterManager = new ClusterManager<>(activity, map);
        PlaceItemRenderer renderer = new PlaceItemRenderer(activity, map, clusterManager);
        renderer.setMinClusterSize(MIN_CLUSTER_SIZE);
        clusterManager.setRenderer(renderer);
        clusterManager.setAnimation(true);
        clusterManager.setOnClusterItemClickListener(placeItem -> {
            clusterManager.getMarkerCollection().setOnMarkerClickListener(this);
            return false;
        });
    }

    private void showPlaceCreationDialog(LatLng latLng) {
        View view = View.inflate(activity, R.layout.dialog_create_place, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(activity).setView(view).setCancelable(false);
        AlertDialog dialog = builder.create();
        dialog.show();
        EditText etName = view.findViewById(R.id.et_dialog_place);
        view.findViewById(R.id.btn_dialog_place_cancel).setOnClickListener(v -> dialog.dismiss());
        view.findViewById(R.id.btn_dialog_place_ok).setOnClickListener(v -> {
            String placeName = etName.getText().toString();
            if (!placeName.isEmpty()) {
                presenter.savePlace(new PlaceItem(latLng, placeName));
                dialog.dismiss();
            } else {
                Toaster.getInstance().toast(R.string.dialog_place_no_name_exception);
            }
        });
    }

    private void addPlace(PlaceItem item) {
        clusterManager.addItem(item);
        clusterManager.cluster();
    }

    //Required by Google MapView
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mapView.onCreate(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        mapView.onPause();
        super.onPause();
    }

    @Override
    public void onStop() {
        mapView.onStop();
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        mapView.onDestroy();
        super.onDestroyView();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }
}
