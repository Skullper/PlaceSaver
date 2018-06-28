package skullper.place.saver.base;

import skullper.place.saver.base.presentation.BasePresenter;
import skullper.place.saver.base.presentation.BaseView;

/*
 * Created by skullper on 28.06.18.
 * Contact the developer - sckalper@gmail.com
 * company - A2Lab
 */

/**
 * This class used when you have no need in presenter in your activity
 */
public class EmptyPresenter extends BasePresenter<BaseView> {

    public EmptyPresenter(BaseView view) {
        super(view);
    }

    @Override
    public void destroy(){
        view = null;
    }
}
