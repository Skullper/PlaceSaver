package skullper.place.saver.mvp.presenters;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import skullper.place.saver.base.presentation.BasePresenter;
import skullper.place.saver.mvp.views.PlacesView;

import static skullper.place.saver.utils.Constantaz.TABLE_USER_PLACES;

/**
 * Created by skullper on 01.07.18.
 * Contact the developer - sckalper@gmail.com
 * company - A2Lab
 */

public class PlacesPresenter extends BasePresenter<PlacesView> {
    private final DatabaseReference database;

    public PlacesPresenter(PlacesView view) {
        super(view);
        database = FirebaseDatabase.getInstance().getReference();
    }

    @SuppressWarnings("ConstantConditions")
    public void getPlaces(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        Query query = database.child(TABLE_USER_PLACES).child(user.getUid()).orderByChild("title");
        view.onPlacesFetched(query);
    }

}
