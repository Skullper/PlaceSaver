package skullper.place.saver.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import skullper.place.saver.R;
import skullper.place.saver.data.PlaceItem;
import skullper.place.saver.providers.impl.Toaster;
import timber.log.Timber;

import static skullper.place.saver.utils.Constantaz.TABLE_PLACES;

/*
 * Created by skullper on 29.06.18.
 * Contact the developer - sckalper@gmail.com
 * company - A2Lab
 */

/**
 * Adapter class responsible for displaying places in {@link skullper.place.saver.screens.fragments.PlacesFragment}
 */
public class PlaceAdapter extends FirebaseRecyclerAdapter<PlaceItem, PlaceAdapter.PlaceViewHolder> {

    private final OnPlaceSelectedListener listener;
    private final Context                 context;

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     */
    public PlaceAdapter(@NonNull FirebaseRecyclerOptions<PlaceItem> options, //
                        @NonNull Context context, @NonNull OnPlaceSelectedListener listener) {
        super(options);
        this.listener = listener;
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull PlaceViewHolder holder, int position, @NonNull PlaceItem model) {
        holder.bind(model);
    }

    @NonNull
    @Override
    public PlaceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()) //
                .inflate(R.layout.item_place, parent, false);
        PlaceViewHolder holder = new PlaceViewHolder(view);
        attachItemClickListeners(holder);
        return holder;
    }

    private void attachItemClickListeners(PlaceViewHolder holder) {
        holder.itemView.setOnClickListener(view -> {
            int selectedPosition = holder.getAdapterPosition();
            if (selectedPosition != RecyclerView.NO_POSITION) {
                listener.onPlaceSelected(getItem(selectedPosition));
            }
        });
        holder.itemView.setOnLongClickListener(v -> {
            int selectedPosition = holder.getAdapterPosition();
            if (selectedPosition != RecyclerView.NO_POSITION) {
                createDeletionDialog(selectedPosition);
            }
            return false;
        });
    }

    private void createDeletionDialog(int position) {
        new AlertDialog.Builder(context) //
                .setTitle(R.string.places_remove_dialog_title) //
                .setMessage(R.string.places_remove_dialog_message) //
                .setPositiveButton(R.string.places_remove_dialog_btn_ok, (dialog, which) -> {
                    removeItemFromAllPlaces(getRef(position).getKey());
                    getRef(position).removeValue((databaseError, databaseReference) -> {
                        Toaster.getInstance().toast(R.string.places_item_removed);
                        //notifyItemRemoved was not working properly. Replaces pre last item by last item
                        notifyDataSetChanged();
                    });
                    dialog.dismiss();
                }).setNegativeButton(R.string.places_remove_dialog_btn_cancel, //
                ((dialog, which) -> dialog.dismiss())) //
                .create().show();
    }

    private ValueEventListener onPlaceDeleteEvent;

    private void removeItemFromAllPlaces(String key) {
        Query allPlaces = FirebaseDatabase.getInstance().getReference().child(TABLE_PLACES);
        onPlaceDeleteEvent = new ValueEventListener() {
            @SuppressWarnings("ConstantConditions")
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snap : dataSnapshot.getChildren())
                    if (snap.getKey().contentEquals(key)) {
                        snap.getRef().removeValue();
                    }
                    //No need in this listener anymore
                    allPlaces.removeEventListener(onPlaceDeleteEvent);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Timber.e(databaseError.toException());
            }
        };
        allPlaces.addListenerForSingleValueEvent(onPlaceDeleteEvent);
    }

    public static class PlaceViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_item_place_title)
        TextView tvTitle;
        @BindView(R.id.tv_item_place_lat)
        TextView tvLat;
        @BindView(R.id.tv_item_place_lon)
        TextView tvLon;

        PlaceViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @SuppressLint("SetTextI18n")
        public void bind(PlaceItem item) {
            tvTitle.setText(item.getTitle());
            tvLat.setText(Double.toString(item.getLat()));
            tvLon.setText(Double.toString(item.getLon()));
        }
    }
}
