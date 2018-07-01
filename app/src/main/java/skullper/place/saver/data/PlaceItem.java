package skullper.place.saver.data;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.Exclude;
import com.google.maps.android.clustering.ClusterItem;

import java.util.HashMap;
import java.util.Map;

/*
 * Created by skullper on 29.06.18.
 * Contact the developer - sckalper@gmail.com
 * company - A2Lab
 */

/**
 * This class describes place in {@link skullper.place.saver.screens.fragments.PlacesFragment} and
 * {@link skullper.place.saver.screens.fragments.AppMapFragment}.
 */
public class PlaceItem implements ClusterItem, Parcelable {

    private String uid;
    private String image;
    private String title;
    private double lat;
    private double lon;

    public PlaceItem() {
        //Default constructor used by Firebase Database
    }

    public PlaceItem(LatLng position, @NonNull String placeName) {
        this.lat = position.latitude;
        this.lon = position.longitude;
        this.title = placeName;
    }

    private PlaceItem(Parcel in) {
        uid = in.readString();
        image = in.readString();
        title = in.readString();
        lat = in.readDouble();
        lon = in.readDouble();
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

    public static final Creator<PlaceItem> CREATOR = new Creator<PlaceItem>() {
        @Override
        public PlaceItem createFromParcel(Parcel in) {
            return new PlaceItem(in);
        }

        @Override
        public PlaceItem[] newArray(int size) {
            return new PlaceItem[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(uid);
        dest.writeString(image);
        dest.writeString(title);
        dest.writeDouble(lat);
        dest.writeDouble(lon);
    }
}
