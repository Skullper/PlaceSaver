package skullper.place.saver.base.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import skullper.place.saver.base.activity.BaseActivity;
import skullper.place.saver.base.presentation.BasePresenter;
import skullper.place.saver.base.presentation.BaseView;

/*
  Created by pugman on 18.01.18.
  Contact the developer - sckalper@gmail.com
  company - A2Lab
 */

/**
 * Class designed as parent for all Fragments created in project.
 *
 * @param <A> parent activity for created fragment. Info: {@link #activity}
 * @param <P> presenter for created fragment. The instance should be passed in {@link #createPresenter()} method
 */
public abstract class BaseFragment<A extends BaseActivity, P extends BasePresenter> extends Fragment implements BaseView {

    protected P presenter;

    /**
     * Use this instead of {@link android.app.Fragment#getActivity}
     */
    protected A activity;

    /**
     * @return fragment's tag
     */
    public abstract String tag();

    /**
     * Place your layout resource as return parameter
     *
     * @return resourceId of layout which designed for current fragment
     */
    protected abstract @LayoutRes
    int getLayoutId();

    /**
     * You should create an instance of your presenter here. After that you can use {@link #presenter}
     * everywhere in created fragment
     *
     * @return an instance of presenter for created fragment
     */
    protected abstract P createPresenter();

    /**
     * This method responsible for the same as {@link Fragment#onCreateView(LayoutInflater, ViewGroup, Bundle)}
     * <p>
     * Initialize all your views here. No need to write ButterKnife.bind() method here because it's
     * already added in BaseFragment's {@link #onCreateView(LayoutInflater, ViewGroup, Bundle)} method.
     *
     * @param rootView already inflated view
     */
    protected abstract void initViews(View rootView);

    private View     rootView;
    private Unbinder unBinder;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        //noinspection unchecked
        activity = (A) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null || presenter == null) {
            rootView = inflater.inflate(getLayoutId(), container, false);
            unBinder = ButterKnife.bind(this, rootView);
            presenter = createPresenter();
            initViews(rootView);
        }
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (unBinder != null) unBinder.unbind();
    }

    @Override
    public void onDestroy() {
        presenter.destroy();
        super.onDestroy();
    }

    @Nullable
    @Override
    public Context getContext() {
        return activity;
    }

}
