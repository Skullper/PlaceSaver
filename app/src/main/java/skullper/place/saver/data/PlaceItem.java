package skullper.place.saver.data;

import android.support.annotation.DrawableRes;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

import skullper.place.saver.R;

/**
 * Created by skullper on 29.06.18.
 * Contact the developer - sckalper@gmail.com
 * company - A2Lab
 */

public class PlaceItem implements ClusterItem{

    private LatLng latLng;
    private String title;

    public PlaceItem(LatLng latLng, String title) {
        this.latLng = latLng;
        this.title = title;
    }

    @Override
    public LatLng getPosition() {
        return latLng;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getSnippet() {
        return null;
    }

    @DrawableRes
    public int getIcon(){
        return R.drawable.pug;
    }
}
