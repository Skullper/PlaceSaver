package skullper.place.saver;

import android.app.Application;
import android.content.Context;

import skullper.place.saver.utils.TimberCrashReportingTree;
import timber.log.Timber;

/**
 * Created by skullper on 28.06.18.
 * Contact the developer - sckalper@gmail.com
 * company - A2Lab
 */

public class App extends Application {

    private static volatile App instance;

    public static Context getContext(){
        return instance.getApplicationContext();
    }

    @Override
    public void onCreate(){
        super.onCreate();
        instance = this;
        //Set up logging
        if(BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        } else {
            Timber.plant(new TimberCrashReportingTree());
        }
    }
}
