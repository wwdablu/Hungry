package com.soumya.wwdablu.zomatobuddy;

import android.util.Log;

import timber.log.Timber;

public class BuddyReleaseTree extends Timber.Tree {

    @Override
    protected boolean isLoggable(String tag, int priority) {

        switch (priority) {

            case Log.VERBOSE:
            case Log.DEBUG:
            case Log.INFO:
                return false;
        }

        return super.isLoggable(tag, priority);
    }

    @Override
    protected void log(int priority, String tag, String message, Throwable t) {

        super.log(priority, tag + " : " + message, t);
    }
}
