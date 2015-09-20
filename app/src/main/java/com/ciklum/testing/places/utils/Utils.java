package com.ciklum.testing.places.utils;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.util.TypedValue;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;


public final class Utils {


    /**
     * Return ability of network connection
     *
     * @param context
     * @return
     */
    public static boolean isNetworkAvailable(Context context) {
        return ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo()
                != null;
    }


    public static void startWebActivity(Context context, String url) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        context.startActivity(browserIntent);
    }

    public static void startVideoActivity(Context context, String url) {
        try {
            Intent videoIntent = new Intent(Intent.ACTION_VIEW);
            videoIntent.setDataAndType(Uri.parse(url), "video/*");
            context.startActivity(videoIntent);
        } catch (ActivityNotFoundException e) {
            new AlertDialog.Builder(context)
                    .setTitle(android.R.string.dialog_alert_title)
                    .setMessage(e.getLocalizedMessage())
                    .setCancelable(true)
                    .setPositiveButton(android.R.string.ok, null)
                    .create()
                    .show();
        }
    }

    public static void hideKeyboard(TextView textView) {
        InputMethodManager imm = (InputMethodManager)
                textView.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(textView.getWindowToken(), 0);
    }




    public static float  getFloatConstant(Resources resources, int idRes) {
        TypedValue outValue = new TypedValue();
        resources.getValue(idRes, outValue, true);
        float value = outValue.getFloat();
        return value;
    }


    /**
     * Stores activity data in the Bundle.
     */
    public static boolean isGooglePlayServicesAvailable(Activity context) {
        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(context);
        if (ConnectionResult.SUCCESS == status) {
            return true;
        } else {
            GooglePlayServicesUtil.getErrorDialog(status, context, 0).show();
            return false;
        }
    }

}
