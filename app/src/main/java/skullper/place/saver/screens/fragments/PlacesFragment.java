package skullper.place.saver.screens.fragments;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import butterknife.BindView;
import skullper.place.saver.R;
import skullper.place.saver.base.EmptyPresenter;
import skullper.place.saver.base.fragment.BaseFragment;
import skullper.place.saver.screens.MainActivity;
import skullper.place.saver.utils.decoration.VerticalItemDecoration;

/*
 * Created by skullper on 29.06.18.
 * Contact the developer - sckalper@gmail.com
 * company - A2Lab
 */

/**
 * This fragment used for displaying places created by user in map.
 */
public class PlacesFragment extends BaseFragment<MainActivity, EmptyPresenter> {

    @BindView(R.id.rv_points)
    RecyclerView rvPoints;

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

    @Override
    protected void initViews(View rootView) {
        rvPoints.setLayoutManager(new LinearLayoutManager(activity));
        rvPoints.setItemAnimator(new DefaultItemAnimator());
        rvPoints.addItemDecoration(new VerticalItemDecoration());
        rvPoints.setHasFixedSize(true);
        // TODO: 29.06.18 Add adapter
    }
}
