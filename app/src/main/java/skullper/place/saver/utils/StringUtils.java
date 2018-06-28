package skullper.place.saver.utils;

import android.support.annotation.StringRes;

import java.util.Locale;

import skullper.place.saver.App;

/*
 * Created by skullper on 28.06.18.
 * Contact the developer - sckalper@gmail.com
 * company - A2Lab
 */

/**
 * Helper class designed to simplify string manipulations in project
 */
public final class StringUtils {

    private StringUtils() {
        throw new IllegalStateException("U cannot create instance of Utils class");
    }

    public static String getString(@StringRes int resourceId) {
        return App.getContext().getString(resourceId);
    }

    public static String format(@StringRes int resourceId, Object... args) {
        return String.format(Locale.US, getString(resourceId), args);
    }
}
