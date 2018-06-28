package skullper.place.saver.base.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;
import skullper.place.saver.base.fragment.BaseFragment;
import skullper.place.saver.base.presentation.BasePresenter;
import skullper.place.saver.base.presentation.BaseView;

/*
  Created by pugman on 28.06.18.
  Contact the developer - sckalper@gmail.com
  company - A2Lab
 */

/**
 * Class designed as parent for all Activities created in project.
 *
 * @param <P> presenter for created activity. The instance should be passed in {@link #createPresenter()} method
 */
public abstract class BaseActivity<P extends BasePresenter> extends AppCompatActivity implements BaseView {

    protected P presenter;

    /**
     * Place your layout resource as return parameter
     *
     * @return resourceId of layout which designed for current fragment
     */
    protected abstract @LayoutRes
    int getLayoutId();

    /**
     * You should create an instance of your presenter here. After that you can use {@link #presenter}
     * everywhere in created activity
     *
     * @return an instance of presenter for created activity
     */
    protected abstract P createPresenter();

    /**
     * This method responsible for the same as {@link android.app.Activity#onCreate}
     * <p>
     * Initialize all your views here. No need to write ButterKnife.bind() method here because it's
     * already added in BaseActivity's {@link #onCreate(Bundle)} method.
     */
    protected abstract void initViews();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        ButterKnife.bind(this);
        presenter = createPresenter();
        initViews();
    }

    @Override
    protected void onDestroy() {
        presenter.destroy();
        super.onDestroy();
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void logOut() {
        // TODO: 28.06.18 implement user logout case
    }

    /**
     * Use this to replace fragment in current container with allowing state loss
     *
     * @param containerId view where fragment will be replaced
     * @param fragment    new fragment
     */
    protected void replaceFragment(@IdRes int containerId, @NonNull BaseFragment fragment) {
        getSupportFragmentManager().beginTransaction()//
                .replace(containerId, fragment, fragment.tag()).commitAllowingStateLoss();
    }

    /**
     * Use this to add fragment into backstack above existing fragment allowing state loss
     *
     * @param containerId view where fragment will be added
     * @param fragment    new fragment
     */
    protected void addFragment(@IdRes int containerId, @NonNull BaseFragment fragment) {
        getSupportFragmentManager().beginTransaction()//
                .add(containerId, fragment, fragment.tag()).addToBackStack(fragment.tag()) //
                .commitAllowingStateLoss();
    }

    /**
     * Start new activity and finish current
     */
    protected void startAffinity(Intent intent) {
        startActivity(intent);
        finish();
    }

}
