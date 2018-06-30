package skullper.place.saver.screens.fragments;

import android.content.Intent;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

import butterknife.OnClick;
import skullper.place.saver.R;
import skullper.place.saver.base.EmptyPresenter;
import skullper.place.saver.base.fragment.BaseFragment;
import skullper.place.saver.screens.MainActivity;
import skullper.place.saver.screens.SplashActivity;
import skullper.place.saver.utils.StringUtils;
import skullper.place.saver.utils.TabFragment;

/**
 * Created by skullper on 30.06.18.
 * Contact the developer - sckalper@gmail.com
 * company - A2Lab
 */

public class ProfileFragment extends BaseFragment<MainActivity, EmptyPresenter> implements TabFragment {

    @Override
    public String tag() {
        return ProfileFragment.class.getSimpleName();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_profile;
    }

    @Override
    protected EmptyPresenter createPresenter() {
        return new EmptyPresenter(this);
    }

    @Override
    protected void initViews(View rootView) {
        //Empty impl
    }

    @Override
    public String getToolbarTitle() {
        return StringUtils.getString(R.string.profile_toolbar_title);
    }

    @OnClick(R.id.btn_profile_log_out)
    void logOut() {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(activity, SplashActivity.class));
        activity.finish();
    }

}
