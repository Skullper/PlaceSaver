package skullper.place.saver.data;

import android.support.annotation.NonNull;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.Exclude;
import com.google.maps.android.clustering.ClusterItem;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by skullper on 29.06.18.
 * Contact the developer - sckalper@gmail.com
 * company - A2Lab
 */

public class PlaceItem implements ClusterItem {

    private double lat;
    private double lon;
    private String title;
    private String image;
    private String uid;

    public PlaceItem() {
        //Default constructor used by Firebase Database
    }

    public PlaceItem(LatLng position, @NonNull String placeName) {
        this.lat = position.latitude;
        this.lon = position.longitude;
        this.title = placeName;
    }

    @Exclude
    @Override
    public LatLng getPosition() {
        return new LatLng(lat, lon);
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Exclude
    @Override
    public String getSnippet() {
        return null;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    @Exclude
    public Map<String, Object> asMap() {
        Map<String, Object> result = new HashMap<>();
        result.put("uid", uid);
        result.put("image", image);
        result.put("title", title);
        result.put("lat", lat);
        result.put("lon", lon);
        return result;
    }
}
