package com.soumya.wwdablu.hungry

import timber.log.Timber.DebugTree

class HungryDebugTree : DebugTree() {
    override fun createStackElementTag(element: StackTraceElement): String {
        return super.createStackElementTag(element) + "[" + element.lineNumber + "]"
    }
}