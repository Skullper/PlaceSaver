package skullper.place.saver.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import timber.log.Timber;

/*
 * Created by skullper on 29.06.18.
 * Contact the developer - sckalper@gmail.com
 * company - A2Lab
 */

/**
 * Class responsible for location fetching. It uses {@link LocationManager} for this purpose.
 */
@SuppressLint("MissingPermission")
public class LocationHelper implements LocationListener {

    private final Activity                    activity;
    private final OnCurrentLocationListener   listener;
    private final FusedLocationProviderClient locationClient;
    private final LocationManager             locationManager;

    public interface OnCurrentLocationListener {
        void onLocationReady(Location location);
    }

    public LocationHelper(@NonNull Activity activity, @NonNull OnCurrentLocationListener listener) {
        this.activity = activity;
        this.listener = listener;
        this.locationClient = LocationServices.getFusedLocationProviderClient(activity);
        this.locationManager = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);
    }

    @Override
    public void onLocationChanged(Location location) {
        listener.onLocationReady(location);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        //Empty impl
    }

    @Override
    public void onProviderEnabled(String provider) {
        //Empty impl
    }

    @Override
    public void onProviderDisabled(String provider) {
        //Empty impl
    }

    public void retrieveLocation() {
        locationClient.getLastLocation().addOnSuccessListener(activity, location -> {
            if (location != null) {
                Timber.e("Location client");
                listener.onLocationReady(location);
            } else {
                getCurrentLocation();
            }
        });
    }

    private void getCurrentLocation() {
        // Get location from GPS if it's available
        Location location;
        location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        // Location wasn't found, check the next most accurate place for the current location
        if (location == null) {
            Criteria criteria = new Criteria();
            criteria.setAccuracy(Criteria.ACCURACY_COARSE);
            locationManager.requestSingleUpdate(criteria, this, activity.getMainLooper());
        } else {
            listener.onLocationReady(location);
        }
    }

}
