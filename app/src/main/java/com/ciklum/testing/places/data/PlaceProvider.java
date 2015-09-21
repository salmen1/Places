package com.ciklum.testing.places.data;

import com.ciklum.testing.places.Constants;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import android.support.annotation.NonNull;
import android.util.Log;

import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

/**
 * @author Alexandr Stetsko (alexandr.stetsko@innomos.com)
 */
public final class PlaceProvider {

    private static final String TAG = PlaceProvider.class.getSimpleName();


    public PlaceProvider() {
    }

    public ArrayList<Place> load(@NonNull String placeUrl) {

        ArrayList<Place> places = null;
        try {
            URL url = new URL(placeUrl);
            Serializer serializer = new Persister();

            URLConnection con = url.openConnection();
            con.setConnectTimeout(Constants.SERVER_CONNECTION_TIMEOUT);
            con.setReadTimeout(Constants.SERVER_CONNECTION_TIMEOUT);

            PlaceSearchResponse response = serializer.read(PlaceSearchResponse.class, con.getInputStream());

            places = response!=null?response.places:null;
//            for (Place place : places) {
//                Log.i(TAG, "place: " + place + " \n");
//            }
        } catch (Exception e) {
            Log.i(TAG, "error getting data", e);
        }
        return places;
    }

    @Root(name = "PlaceSearchResponse", strict = false)
    private static class PlaceSearchResponse {

        @ElementList(inline = true, name = "result")
        private ArrayList<Place> places;
    }
}
