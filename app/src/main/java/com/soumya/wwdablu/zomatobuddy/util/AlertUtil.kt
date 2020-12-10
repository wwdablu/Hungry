package com.soumya.wwdablu.zomatobuddy.util

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.text.TextUtils
import com.soumya.wwdablu.zomatobuddy.R

object AlertUtil {
    /**
     * Provides more finer control over the dialog to be displayed to the user
     * @param context
     * @param title
     * @param message
     * @param posTitle
     * @param posListener
     * @param neuTitle
     * @param neuListener
     * @param negTitle
     * @param negListener
     */
    /**
     * Display an alert dialog with a OK button but with no callback.
     * @param context Provide the context of the caller
     * @param title Provide the title of the dialog
     * @param message Provide the message to be displayed in the dialog
     */
    @JvmOverloads
    fun showAlert(context: Context, title: String, message: String,
                  posTitle: String = context.getString(R.string.OK), posListener: DialogInterface.OnClickListener? = null,
                  neuTitle: String? =
                          null, neuListener: DialogInterface.OnClickListener? = null,
                  negTitle: String? = null, negListener: DialogInterface.OnClickListener? = null) {
        val dialogBuilder = AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)

        //Set the positive button
        dialogBuilder.setPositiveButton(posTitle, posListener)

        //Set the neutral button
        if (TextUtils.isEmpty(neuTitle)) {
            dialogBuilder.setNeutralButton(neuTitle, neuListener)
        }

        //Set the negative button
        if (TextUtils.isEmpty(negTitle)) {
            dialogBuilder.setNegativeButton(negTitle, negListener)
        }

        //Show the alert dialog
        dialogBuilder.create().show()
    }
}