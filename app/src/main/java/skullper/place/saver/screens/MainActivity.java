package skullper.place.saver.screens;

import android.support.design.widget.TabLayout;

import butterknife.BindView;
import skullper.place.saver.R;
import skullper.place.saver.base.EmptyPresenter;
import skullper.place.saver.base.activity.BaseActivity;
import skullper.place.saver.base.fragment.BaseFragment;
import skullper.place.saver.data.PlaceItem;
import skullper.place.saver.screens.fragments.AppMapFragment;
import skullper.place.saver.screens.fragments.PlacesFragment;

/**
 * Created by skullper on 29.06.18.
 * Contact the developer - sckalper@gmail.com
 * company - A2Lab
 */

public class MainActivity extends BaseActivity<EmptyPresenter> implements TabLayout.OnTabSelectedListener {

    private enum Tabs {
        PLACES(0), MAP(1), PROFILE(2);
        private final int position;

        Tabs(int position) {
            this.position = position;
        }
    }
    private PlaceItem placeItem = null;

    @BindView(R.id.tabs_main)
    TabLayout tableLayout;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected EmptyPresenter createPresenter() {
        return new EmptyPresenter(this);
    }

    @Override
    protected void initViews() {
        initTabs();
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        Tabs selectedTab = Tabs.values()[tab.getPosition()];
        BaseFragment fragment = null;
        switch (selectedTab) {
            case PLACES:
                fragment = new PlacesFragment();
                break;
            case MAP:
                fragment = AppMapFragment.newInstance(placeItem);
                break;
            case PROFILE:
                fragment = null;
                break;
        }
        replaceFragment(R.id.container_main, fragment);
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    private void initTabs() {
        tableLayout.addOnTabSelectedListener(this);
        tableLayout.addTab(tableLayout.newTab().setText(R.string.tab_list));
        tableLayout.addTab(tableLayout.newTab().setText(R.string.tab_map));
        tableLayout.addTab(tableLayout.newTab().setText(R.string.tab_profile));
    }

    public void openMap(PlaceItem item) {
        placeItem = item;
        TabLayout.Tab mapTab = tableLayout.getTabAt(Tabs.MAP.position);
        if (mapTab != null) mapTab.select();
        placeItem = null;
    }
}
