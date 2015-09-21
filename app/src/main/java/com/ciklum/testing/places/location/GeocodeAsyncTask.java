package com.ciklum.testing.places.location;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * @author Alexandr Stetsko (alexandr.stetsko@innomos.com)
 */
public class GeocodeAsyncTask extends AsyncTask<Location, Location, Address> {

    private static final String TAG = GeocodeAsyncTask.class.getSimpleName();
    private Context mContext;
    private GeocodeResultListener mListener;

    public GeocodeAsyncTask(Context context, GeocodeResultListener listener) {
        mContext  = context;
        mListener = listener;
    }

    @Override
    protected Address doInBackground(Location... params) {

        Location currentLocation = params[0];
        Address address = null;

        try {
            Geocoder gcd = new Geocoder(mContext, Locale.getDefault());
            List<Address> addresses = gcd.getFromLocation(currentLocation.getLatitude(), currentLocation.getLongitude(), 1);
            if (addresses.size() > 0) {
                address = addresses.get(0);
            }
        } catch (IOException e) {
            Log.e(TAG, "error getting address by lat/long", e);
        }

        return address;
    }


    @Override
    protected void onPostExecute(Address address) {
        // update the UI with the result of the reverse geocoding
        if(mListener!=null) {
            mListener.onResult(address);
        }
    }
}
