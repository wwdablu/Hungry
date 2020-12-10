package com.soumya.wwdablu.zomatobuddy

import timber.log.Timber.DebugTree

class BuddyDebugTree : DebugTree() {
    override fun createStackElementTag(element: StackTraceElement): String {
        return super.createStackElementTag(element) + "[" + element.lineNumber + "]"
    }
}