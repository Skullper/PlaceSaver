package skullper.place.saver.screens.fragments;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
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
        implements OnMapReadyCallback, GoogleMap.OnMapLongClickListener, LocationHelper.OnCurrentLocationListener {

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
        map.setOnMapLongClickListener(this);
        checkLocationPermission();
    }

    @Override
    public void onMapLongClick(LatLng latLng) {
        showPlaceCreationDialog(latLng);
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
        map.moveCamera(CameraUpdateFactory.newLatLng(coordinates));
        map.animateCamera(CameraUpdateFactory.zoomTo(15));
    }

    private void fetchUserLocation() {
        progressBar.setVisibility(View.VISIBLE);
        helper.retrieveLocation();
    }

    private void showPlaceCreationDialog(LatLng latLng) {
        View view = View.inflate(activity, R.layout.dialog_create_place, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(activity).setView(view).setCancelable(false);
        AlertDialog dialog = builder.create();
        dialog.show();
        EditText etName = view.findViewById(R.id.et_dialog_place);
        view.findViewById(R.id.btn_dialog_place_cancel).setOnClickListener(v -> dialog.dismiss());
        view.findViewById(R.id.btn_dialog_place_ok).setOnClickListener(v -> {
            // TODO: 29.06.18 save to db
            String placeName = etName.getText().toString();
            if (!placeName.isEmpty() && !placeName.contains(" ")) {
                map.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory //
                        .fromBitmap(createMarkerBitmap())).title(placeName).position(latLng));
                dialog.dismiss();
            } else {
                Toast.makeText(activity, R.string.dialog_place_no_name_exception, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private Bitmap createMarkerBitmap() {
        int markerSize = activity.getResources().getDimensionPixelSize(R.dimen.place_marker_size);
        Bitmap bitmap = BitmapFactory.decodeResource(activity.getResources(), R.drawable.ic_gbk);
        Bitmap markerBitmap = Bitmap.createScaledBitmap(bitmap, markerSize, markerSize, false);
        bitmap.recycle();
        return markerBitmap;
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
