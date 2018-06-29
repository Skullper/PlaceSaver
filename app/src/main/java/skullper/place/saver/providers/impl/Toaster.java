package skullper.place.saver.providers.impl;

import android.os.Build;
import android.support.annotation.StringRes;
import android.widget.Toast;

import java.util.Locale;

import skullper.place.saver.App;
import skullper.place.saver.providers.ShortMessageProvider;

import static skullper.place.saver.utils.StringUtils.getString;

/*
  Created by pugman on 23.01.18.
  Contact the developer - sckalper@gmail.com
  company - A2Lab
 */

/**
 * Class designed to replace the toast's routine creating and displaying mechanism
 */
public final class Toaster implements ShortMessageProvider {

    private static volatile Toaster instance;

    private Toaster() {
        //Preventing a reflection api
        if (instance != null) {
            throw new IllegalStateException("Use getInstance() method to get the single instance of this class.");
        }
    }

    public static Toaster getInstance() {
        //Lazy initialization
        if (instance == null) {
            //Double locking
            synchronized(Toaster.class) {
                if (instance == null) instance = new Toaster();
            }
        }
        return instance;
    }

    @Override
    public void toast(@StringRes int resourceId) {
        String text = getString(resourceId);
        toast(text);
    }

    @Override
    public void toast(@StringRes int resourceId, Object... args) {
        String formatted = String.format(getCurrentLocale(), getString(resourceId), args);
        toast(formatted);
    }

    @Override
    public void toast(String text) {
        Toast.makeText(App.getContext(), text, Toast.LENGTH_SHORT).show();
    }

    private Locale getCurrentLocale() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return App.getContext().getResources().getConfiguration().getLocales().get(0);
        } else {
            //noinspection deprecation
            return App.getContext().getResources().getConfiguration().locale;
        }
    }
}
