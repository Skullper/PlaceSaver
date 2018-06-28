package skullper.place.saver.utils;

import android.support.annotation.NonNull;
import android.util.Log;

import timber.log.Timber;
/*
 * Created by skullper on 28.06.18.
 * Contact the developer - sckalper@gmail.com
 * company - A2Lab
 */

/**
 * Timber tree which will be work in release build
 */
public class TimberCrashReportingTree extends Timber.Tree {

    @Override
    protected void log(int priority, String tag, @NonNull String message, Throwable t) {
        if (priority == Log.VERBOSE || priority == Log.DEBUG || priority == Log.INFO) {
            return;
        }
        //noinspection StatementWithEmptyBody
        if (t != null && isExceptionPriority(priority)) {
            // TODO: 28.06.18 Send to bug tracker
        }
    }

    private boolean isExceptionPriority(int priority) {
        return priority == Log.ERROR;
    }
}
