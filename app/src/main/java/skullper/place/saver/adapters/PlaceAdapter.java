package skullper.place.saver.adapters;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import butterknife.BindView;
import butterknife.ButterKnife;
import skullper.place.saver.R;
import skullper.place.saver.data.PlaceItem;

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

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     */
    public PlaceAdapter(@NonNull FirebaseRecyclerOptions<PlaceItem> options, //
                        @NonNull OnPlaceSelectedListener listener) {
        super(options);
        this.listener = listener;
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
        attachItemSelectListener(holder);
        return holder;
    }

    private void attachItemSelectListener(PlaceViewHolder holder) {
        holder.itemView.setOnClickListener(view -> {
            int selectedPosition = holder.getAdapterPosition();
            if (selectedPosition != RecyclerView.NO_POSITION) {
                listener.onPlaceSelected(getItem(selectedPosition));
            }
        });
    }

    protected class PlaceViewHolder extends RecyclerView.ViewHolder {

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
