package skullper.place.saver.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;
import com.google.maps.android.ui.IconGenerator;

import skullper.place.saver.R;
import skullper.place.saver.data.PlaceItem;

/**
 * Created by skullper on 29.06.18.
 * Contact the developer - sckalper@gmail.com
 * company - A2Lab
 */

public class PlaceItemRenderer extends DefaultClusterRenderer<PlaceItem> {

    public static final int MIN_CLUSTER_SIZE = 3;

    private final IconGenerator itemIconGenerator;
    private final IconGenerator clusterIconGenerator;
    private final ImageView     itemIcon;

    @SuppressLint("InflateParams")
    public PlaceItemRenderer(Context context, GoogleMap map, ClusterManager<PlaceItem> clusterManager) {
        super(context, map, clusterManager);
        itemIconGenerator = new IconGenerator(context);
        clusterIconGenerator = new IconGenerator(context);
        //init marker view
        itemIcon = new ImageView(context);
        int markerIconSize = context.getResources().getDimensionPixelSize(R.dimen.place_marker_size);
        itemIcon.setLayoutParams(new ViewGroup.LayoutParams(markerIconSize, markerIconSize));
        itemIconGenerator.setContentView(itemIcon);
        //init cluster view
        View view = LayoutInflater.from(context).inflate(R.layout.item_cluster, null, false);
        clusterIconGenerator.setContentView(view);
    }

    @Override
    protected void onBeforeClusterItemRendered(PlaceItem item, MarkerOptions markerOptions) {
        itemIcon.setImageResource(item.getIcon());
        Bitmap icon = itemIconGenerator.makeIcon();
        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(icon)).title(item.getTitle());
    }

    @Override
    protected void onBeforeClusterRendered(Cluster<PlaceItem> cluster, MarkerOptions markerOptions) {
        Bitmap icon = clusterIconGenerator.makeIcon(String.valueOf(cluster.getSize()));
        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(icon));
    }

    @Override
    protected boolean shouldRenderAsCluster(Cluster<PlaceItem> cluster) {
        return cluster.getSize() >= MIN_CLUSTER_SIZE;
    }

}
