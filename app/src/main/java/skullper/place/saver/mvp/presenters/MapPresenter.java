package skullper.place.saver.mvp.presenters;

import android.support.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import skullper.place.saver.base.presentation.BasePresenter;
import skullper.place.saver.data.PlaceItem;
import skullper.place.saver.data.User;
import skullper.place.saver.mvp.views.AppMapView;
import timber.log.Timber;

import static skullper.place.saver.utils.Constantaz.PLACES_LOCATION;
import static skullper.place.saver.utils.Constantaz.TABLE_PLACES;
import static skullper.place.saver.utils.Constantaz.TABLE_USERS;
import static skullper.place.saver.utils.Constantaz.USER_PLACES_LOCATION;

/**
 * Created by skullper on 30.06.18.
 * Contact the developer - sckalper@gmail.com
 * company - A2Lab
 */

public class MapPresenter extends BasePresenter<AppMapView> {

    private final DatabaseReference database;

    public MapPresenter(AppMapView view) {
        super(view);
        database = FirebaseDatabase.getInstance().getReference();
    }

    public void savePlace(PlaceItem item) {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        String userId = currentUser.getUid();
        ValueEventListener itemCreateEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                String key = database.child(TABLE_PLACES).push().getKey();
                if (user != null) {
                    item.setUid(userId);
                    item.setImage(user.getImageUrl());
                }
                Map<String, Object> placeData = item.asMap();
                Map<String, Object> children = new HashMap<>();
                children.put(PLACES_LOCATION + key, placeData);
                children.put(USER_PLACES_LOCATION + userId + "/" + key, placeData);
                database.updateChildren(children);
                view.onPlaceSaved(item);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Timber.e("Create place error: %s code: %d", databaseError.getMessage(), //
                        databaseError.getCode());
            }
        };
        database.child(TABLE_USERS).child(userId).addListenerForSingleValueEvent(itemCreateEventListener);
    }

    public void fetchPlaces() {
        ValueEventListener itemsFetchEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) { //if places exists
                    Iterator<DataSnapshot> iterator = dataSnapshot.getChildren().iterator();
                    List<PlaceItem> items = new ArrayList<>();
                    while (iterator.hasNext()) {
                        items.add(iterator.next().getValue(PlaceItem.class));
                    }
                    if (view != null) view.onPlacesFetched(items);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Timber.e("Create place error: %s code: %d", databaseError.getMessage(), //
                        databaseError.getCode());
            }
        };
        database.child(TABLE_PLACES).addListenerForSingleValueEvent(itemsFetchEventListener);
    }

}
