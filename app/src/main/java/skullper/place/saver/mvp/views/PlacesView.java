package skullper.place.saver.mvp.views;

import com.google.firebase.database.Query;

import skullper.place.saver.base.presentation.BaseView;

/**
 * Created by skullper on 01.07.18.
 * Contact the developer - sckalper@gmail.com
 * company - A2Lab
 */

public interface PlacesView extends BaseView {

    void onPlacesFetched(Query query);
}
