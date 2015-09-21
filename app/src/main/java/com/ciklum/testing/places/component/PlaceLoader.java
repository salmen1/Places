package com.ciklum.testing.places.component;

import com.ciklum.testing.places.Constants;
import com.ciklum.testing.places.data.Place;
import com.ciklum.testing.places.data.PlaceProvider;

import android.content.Context;
import android.location.Location;
import android.support.v4.content.AsyncTaskLoader;

import java.util.ArrayList;

/**
 * @author Alexandr Stetsko (alexandr.stetsko@innomos.com)
 */
final class PlaceLoader extends AsyncTaskLoader<ArrayList<Place>> {

    private static final String PLACES_API_KEY = "&key="+ Constants.PLACES_API_KEY;
    private static final String REQUEST_KEYWORD = "&keyword=%s";
    private static final String REQUEST_LOCATION = "&location=%s";
    private static final String REQUEST_URL = "https://maps.googleapis.com/maps/api/place/nearbysearch/xml?"
            + "rankby=distance&types=establishment";

    private String mKeyWord;
    private Location mLocation;


    /**
     * Stores away the application context associated with context.
     * Since Loaders can be used across multiple activities it's dangerous to
     * store the context directly; always use {@link #getContext()} to retrieve
     * the Loader's Context, don't use the constructor argument directly.
     * The Context returned by {@link #getContext} is safe to use across
     * Activity instances.
     *
     * @param context used to retrieve the application context.
     */
    public PlaceLoader(Context context, String keyWord, Location location) {
        super(context);
        mKeyWord = keyWord;
        mLocation = location;
    }

    public void updateRequestData(String keyWord, Location location) {
        mKeyWord = keyWord;
        mLocation = location;
    }

    @Override
    public ArrayList<Place> loadInBackground() {
        PlaceProvider provider = new PlaceProvider();
        String url = getUrl();

        return provider.load(url);
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
    }

    private String getUrl() {
        String url = REQUEST_URL;

        if (mKeyWord != null && !mKeyWord.isEmpty()) {
            url += String.format(REQUEST_KEYWORD, mKeyWord);
        }

        if (mLocation != null) {
            String locationString = mLocation.getLatitude() + "," + mLocation.getLongitude();
            url += String.format(REQUEST_LOCATION, locationString);
        }

        url += PLACES_API_KEY;

        return url;
    }
}
