package skullper.place.saver.screens.fragments;

import android.support.annotation.NonNull;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import butterknife.BindView;
import skullper.place.saver.R;
import skullper.place.saver.adapters.PlaceAdapter;
import skullper.place.saver.base.EmptyPresenter;
import skullper.place.saver.base.fragment.BaseFragment;
import skullper.place.saver.data.PlaceItem;
import skullper.place.saver.providers.impl.Toaster;
import skullper.place.saver.screens.MainActivity;
import skullper.place.saver.utils.StringUtils;
import skullper.place.saver.utils.TabFragment;
import skullper.place.saver.utils.decoration.VerticalItemDecoration;

import static skullper.place.saver.utils.Constantaz.TABLE_USER_PLACES;

/*
 * Created by skullper on 29.06.18.
 * Contact the developer - sckalper@gmail.com
 * company - A2Lab
 */

/**
 * This fragment used for displaying places created by user in map.
 */
public class PlacesFragment extends BaseFragment<MainActivity, EmptyPresenter> implements TabFragment{

    private PlaceAdapter adapter;
    private Query        userPostsQuery;
    private ValueEventListener valueEventListener;

    @BindView(R.id.rv_places)
    RecyclerView rvPoints;
    @BindView(R.id.pb_places)
    ProgressBar  pbPlaces;
    @BindView(R.id.tv_places_title)
    TextView     tvPlacesTitle;

    @Override
    public String tag() {
        return PlacesFragment.class.getSimpleName();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_places;
    }

    @Override
    protected EmptyPresenter createPresenter() {
        return new EmptyPresenter(this);
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    protected void initViews(View rootView) {
        Toaster.getInstance().toast(R.string.places_loading);
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        userPostsQuery = database.child(TABLE_USER_PLACES).child(user.getUid()).orderByChild("title");
        checkItemSize();
        FirebaseRecyclerOptions<PlaceItem> options = new FirebaseRecyclerOptions.Builder<PlaceItem>() //
                .setQuery(userPostsQuery, PlaceItem.class) //
                .build();
        adapter = new PlaceAdapter(options, activity, item -> activity.openMap(item));
        initRecycler();
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        adapter.stopListening();
        userPostsQuery.removeEventListener(valueEventListener);
        super.onStop();
    }

    @Override
    public String getToolbarTitle() {
        return StringUtils.getString(R.string.places_toolbar_title);
    }

    private void initRecycler() {
        rvPoints.setLayoutManager(new LinearLayoutManager(activity));
        rvPoints.setItemAnimator(new DefaultItemAnimator());
        rvPoints.addItemDecoration(new VerticalItemDecoration());
        rvPoints.setHasFixedSize(true);
        rvPoints.setAdapter(adapter);
    }

    /**
     * Теж рахую це як костиль, оскільки не знайшов інших методів для того, щоб дістати к-сть
     * отриманих елементів. Намагався і через userPostQuery, i через FirebaseRecyclerOptions()
     * options.getSnapshots().isEmpty() - але не працювало. Тому так. Але потім зрозумів, що це
     * навіть краще :) Бо якщо дані з бази видаляти вручну, то UI обновиться як треба.
     */
    private void checkItemSize() {
        valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                pbPlaces.setVisibility(View.GONE);
                if (dataSnapshot.getChildrenCount() <= 0) {
                    tvPlacesTitle.setVisibility(View.VISIBLE);
                    tvPlacesTitle.setOnClickListener(v -> activity.openMap(null));
                } else {
                    tvPlacesTitle.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                //Ignored
            }
        };
        userPostsQuery.addValueEventListener(valueEventListener);
    }
}
