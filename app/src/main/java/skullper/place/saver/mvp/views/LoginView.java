package skullper.place.saver.mvp.views;

import skullper.place.saver.base.presentation.BaseView;

/**
 * Created by skullper on 28.06.18.
 * Contact the developer - sckalper@gmail.com
 * company - A2Lab
 */

public interface LoginView extends BaseView {

    void onLoggedIn();

    void onLoginError(String message);
}
