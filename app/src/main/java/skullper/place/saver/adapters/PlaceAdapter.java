package skullper.place.saver.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import skullper.place.saver.R;

/*
 * Created by skullper on 29.06.18.
 * Contact the developer - sckalper@gmail.com
 * company - A2Lab
 */

/**
 * Adapter class responsible for displaying places in {@link skullper.place.saver.screens.fragments.PlacesFragment}
 */
public class PlaceAdapter extends RecyclerView.Adapter<PlaceAdapter.PlaceViewHolder> {

    private final List<Object> items;

    public PlaceAdapter(@NonNull List<Object> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public PlaceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()) //
                .inflate(R.layout.item_place, parent, false);
        return new PlaceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlaceViewHolder holder, int position) {
        // TODO: 29.06.18 Bind data
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class PlaceViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_item_place_title)
        TextView tvTitle;
        @BindView(R.id.tv_item_place_lat)
        TextView tvLat;
        @BindView(R.id.tv_item_place_lon)
        TextView tvLon;

        public PlaceViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(Object item) {
            // TODO: 29.06.18 init views
        }
    }
}
