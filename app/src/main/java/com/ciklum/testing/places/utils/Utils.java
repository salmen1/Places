package com.ciklum.testing.places.utils;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;

import com.nostra13.universalimageloader.core.DisplayImageOptions;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.location.Location;
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


    public static final DisplayImageOptions MEMORY_CACHE_OPT = new DisplayImageOptions.Builder()
            .cacheInMemory(true)
            .cacheOnDisk(false)
            .resetViewBeforeLoading(true)
            .build();


    public static final int getDistanceInMetres(final Location location, final double lat2, final double lng2) {
        if(location!=null) {
            float[] dist = new float[1];
            Location.distanceBetween(location.getLatitude(), location.getLongitude(), lat2, lng2, dist);
            return (int) dist[0];
        }
        return 0;
    }

}
