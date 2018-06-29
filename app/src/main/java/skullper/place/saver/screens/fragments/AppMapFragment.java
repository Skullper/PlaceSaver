package skullper.place.saver.screens.fragments;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import butterknife.BindView;
import skullper.place.saver.R;
import skullper.place.saver.base.EmptyPresenter;
import skullper.place.saver.base.fragment.BaseFragment;
import skullper.place.saver.screens.MainActivity;
import skullper.place.saver.utils.LocationHelper;

/**
 * Created by skullper on 29.06.18.
 * Contact the developer - sckalper@gmail.com
 * company - A2Lab
 */

public class AppMapFragment extends BaseFragment<MainActivity, EmptyPresenter> //
        implements OnMapReadyCallback, LocationHelper.OnCurrentLocationListener {

    private static final int RC_PERMISSIONS = 785;

    @BindView(R.id.map)
    MapView     mapView;
    @BindView(R.id.pb_map)
    ProgressBar progressBar;

    private GoogleMap      map;
    private LocationHelper helper;

    @Override
    public String tag() {
        return AppMapFragment.class.getSimpleName();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_map;
    }

    @Override
    protected EmptyPresenter createPresenter() {
        return new EmptyPresenter(this);
    }

    @Override
    protected void initViews(View rootView) {
        helper = new LocationHelper(activity, this);
        mapView.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        checkLocationPermission();
    }

    @Override
    public void onLocationReady(Location location) {
        progressBar.setVisibility(View.GONE);
        Toast.makeText(activity, R.string.map_loading_toast, Toast.LENGTH_SHORT).show();
        selectCurrentLocation(location);
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
        map.addMarker(new MarkerOptions().position(coordinates).title("Current position"));
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(coordinates, 15));
        map.animateCamera(CameraUpdateFactory.zoomIn());
    }

    private void fetchUserLocation() {
        progressBar.setVisibility(View.VISIBLE);
        helper.retrieveLocation();
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
