package com.soumya.wwdablu.zomatobuddy.util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;

import com.soumya.wwdablu.zomatobuddy.R;

import io.reactivex.annotations.NonNull;
import io.reactivex.annotations.Nullable;

public class AlertUtil {

    /**
     * Display an alert dialog with a OK button but with no callback.
     * @param context Provide the context of the caller
     * @param title Provide the title of the dialog
     * @param message Provide the message to be displayed in the dialog
     */
    public static void showAlert(@NonNull Context context, @NonNull String title, @NonNull String message) {

        showAlert(context, title, message, context.getString(R.string.OK), null,
                null, null, null, null);
    }

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
    public static void showAlert(@NonNull Context context, @NonNull String title, @NonNull String message,
                                 @NonNull String posTitle, @Nullable DialogInterface.OnClickListener posListener,
                                 @Nullable String neuTitle, @Nullable DialogInterface.OnClickListener neuListener,
                                 @Nullable String negTitle, @Nullable DialogInterface.OnClickListener negListener) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message);

        //Set the positive button
        dialogBuilder.setPositiveButton(posTitle, posListener);

        //Set the neutral button
        if(TextUtils.isEmpty(neuTitle)) {
            dialogBuilder.setNeutralButton(neuTitle, neuListener);
        }

        //Set the negative button
        if(TextUtils.isEmpty(negTitle)) {
            dialogBuilder.setNegativeButton(negTitle, negListener);
        }

        //Show the alert dialog
        dialogBuilder.create().show();
    }
}
