package com.soumya.wwdablu.zomatobuddy;

import timber.log.Timber;

public class BuddyDebugTree extends Timber.DebugTree {

    @Override
    protected String createStackElementTag(StackTraceElement element) {
        return super.createStackElementTag(element) + "[" + element.getLineNumber() + "]";
    }
}
