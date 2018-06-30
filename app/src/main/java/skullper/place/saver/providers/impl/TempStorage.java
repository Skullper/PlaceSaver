package skullper.place.saver.providers.impl;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;

import skullper.place.saver.App;
import skullper.place.saver.providers.TempStorageProvider;

/*
  Created by pugman on 18.01.18.
  Contact the developer - sckalper@gmail.com
  company - A2Lab
 */

/**
 * Class for fast saving small data using SharedPreference
 */
@SuppressWarnings("unused")
public final class TempStorage implements TempStorageProvider {

    private static volatile TempStorage       instance;
    @NonNull
    private final           SharedPreferences preferences;

    private TempStorage() {
        //Preventing a reflection api
        if (instance != null) {
            throw new IllegalStateException("Use getInstance() method to get the single instance of this class.");
        }
        preferences = PreferenceManager.getDefaultSharedPreferences(App.getContext());
    }

    public static TempStorage getInstance() {
        //Lazy initialization
        if (instance == null) {
            //Double locking
            synchronized(TempStorage.class) {
                if (instance == null) instance = new TempStorage();
            }
        }
        return instance;
    }

    @Override
    public void clearAllPreferences() {
        preferences.edit().clear().apply();
    }

    @Override
    public void saveEmail(@NonNull String email) {
        preferences.edit().putString(PREF_EMAIL, email).apply();
    }

    @NonNull
    @Override
    public String getEmail() {
        return preferences.getString(PREF_EMAIL, "");
    }

    @Override
    public void saveAvatarPhotoUrl(@NonNull String url) {
        preferences.edit().putString(PREF_IMAGE_URL, url).apply();
    }

    @NonNull
    @Override
    public String getAvatarPhotoUrl() {
        return preferences.getString(PREF_IMAGE_URL, "");
    }
}
