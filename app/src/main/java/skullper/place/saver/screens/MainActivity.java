package skullper.place.saver.screens;

import android.support.design.widget.TabLayout;

import butterknife.BindView;
import skullper.place.saver.R;
import skullper.place.saver.base.EmptyPresenter;
import skullper.place.saver.base.activity.BaseActivity;

/**
 * Created by skullper on 29.06.18.
 * Contact the developer - sckalper@gmail.com
 * company - A2Lab
 */

public class MainActivity extends BaseActivity<EmptyPresenter> implements TabLayout.OnTabSelectedListener{

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
        // TODO: 29.06.18 Open fragment according to selected tab
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    private void initTabs(){
        tableLayout.addOnTabSelectedListener(this);
        tableLayout.addTab(tableLayout.newTab().setText(R.string.tab_list));
        tableLayout.addTab(tableLayout.newTab().setText(R.string.tab_map));
        tableLayout.addTab(tableLayout.newTab().setText(R.string.tab_profile));
    }
}
