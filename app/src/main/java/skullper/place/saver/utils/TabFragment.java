package skullper.place.saver.utils;

/*
 * Created by skullper on 30.06.18.
 * Contact the developer - sckalper@gmail.com
 * company - A2Lab
 */

/**
 * Used when fragment started through tab layout and toolbar title should be updated.
 * Created also to avoid appearances of fragment's toolbar titles in
 * {@link skullper.place.saver.screens.MainActivity}
 */
public interface TabFragment {

    String getToolbarTitle();
}
