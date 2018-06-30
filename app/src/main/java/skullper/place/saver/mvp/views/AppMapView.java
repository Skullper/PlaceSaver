package skullper.place.saver.mvp.views;

import android.support.annotation.NonNull;

import java.util.List;

import skullper.place.saver.base.presentation.BaseView;
import skullper.place.saver.data.PlaceItem;

/**
 * Created by skullper on 30.06.18.
 * Contact the developer - sckalper@gmail.com
 * company - A2Lab
 */

public interface AppMapView extends BaseView {

    void onPlaceSaved(@NonNull PlaceItem item);

    void onPlacesFetched(List<PlaceItem> items);
}
