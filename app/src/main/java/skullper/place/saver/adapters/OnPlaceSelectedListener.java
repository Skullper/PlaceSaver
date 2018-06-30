package skullper.place.saver.adapters;

/*
 * Created by skullper on 30.06.18.
 * Contact the developer - sckalper@gmail.com
 * company - A2Lab
 */

import skullper.place.saver.data.PlaceItem;

/**
 * Use to inform UI when item was selected in {@link PlaceAdapter}
 */
public interface OnPlaceSelectedListener {

    void onPlaceSelected(PlaceItem item);

}
