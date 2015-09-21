package com.ciklum.testing.places.component;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import com.ciklum.testing.places.R;
import com.ciklum.testing.places.component.data.Place;
import com.ciklum.testing.places.component.location.GeocodeAsyncTask;
import com.ciklum.testing.places.component.location.GeocodeResultListener;
import com.ciklum.testing.places.ui.AutoFitRecyclerView;
import com.ciklum.testing.places.utils.SimpleTextWatcher;
import com.ciklum.testing.places.utils.Utils;

import android.content.IntentSender;
import android.gesture.Gesture;
import android.gesture.GestureOverlayView;
import android.location.Address;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * @author Alexandr Stetsko (alexandr.stetsko@innomos.com)
 */
public class PlaceActivity extends FragmentActivity implements ConnectionCallbacks, OnConnectionFailedListener,
        LocationListener,
        LoaderManager.LoaderCallbacks<ArrayList<Place>> {

    protected static final String TAG = PlaceActivity.class.getSimpleName();


    private static final int LOADER_ID = 1015;

    /**
     * Camera animation zoom
     */
    public static final float MAP_CAMERA_ZOOM = 15.f;

    /**
     * The desired interval for location updates. Inexact. Updates may be more or less frequent.
     */
    public static final long UPDATE_INTERVAL_IN_MILLISECONDS = 10000;

    /**
     * The fastest rate for active location updates. Exact. Updates will never be more frequent
     * than this value.
     */
    public static final long FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS =
            UPDATE_INTERVAL_IN_MILLISECONDS / 2;


    /**
     * Provides the entry point to Google Play services.
     */
    protected GoogleApiClient mGoogleApiClient;

    /**
     * Stores parameters for requests to the FusedLocationProviderApi.
     */
    protected LocationRequest mLocationRequest;

    /**
     * Represents a geographical location.
     */
    protected Location mCurrentLocation;


    private GoogleMap mMapView;
    private boolean mSkipAnimationToMyLocation = false;

    private Button mSearchButton;
    private TextView mKeyWordTextView;
    private AutoFitRecyclerView mRecycleView;
    private View mProgressView;
    private View mInfoView;


    private Address mAddress;
    private GeocodeAsyncTask mGeocodeAsyncTask;
    private PlaceAdapter mPlaceAdapter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_places);


//        if (!Utils.isGooglePlayServicesAvailable(this)) {
//            finish();
//        }

        getSupportLoaderManager().initLoader(LOADER_ID, null, this);

        mProgressView      = findViewById(R.id.progress_view);
        mInfoView          = findViewById(R.id.info_view);
        mRecycleView       = (AutoFitRecyclerView)findViewById(R.id.recycler_view);
        mSearchButton      = (Button)findViewById(R.id.search_button);
        initEditWidgets();

        mProgressView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });

        mPlaceAdapter = new PlaceAdapter();

        mRecycleView.setHasFixedSize(false);
        mRecycleView.setAdapter(mPlaceAdapter);


        final MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                mMapView = googleMap;
                mMapView.getUiSettings().setMyLocationButtonEnabled(false);
                mMapView.setMyLocationEnabled(true);
            }
        });

        GestureOverlayView gestureOverlayView = (GestureOverlayView)findViewById(R.id.gestures);
        gestureOverlayView.setGestureVisible(false);
        gestureOverlayView.addOnGesturePerformedListener(new GestureOverlayView.OnGesturePerformedListener() {
            @Override
            public void onGesturePerformed(GestureOverlayView overlay, Gesture gesture) {
            }
        });
        gestureOverlayView.addOnGesturingListener(new GestureOverlayView.OnGesturingListener() {
            @Override
            public void onGesturingStarted(GestureOverlayView overlay) {
                mSkipAnimationToMyLocation = true;
            }

            @Override
            public void onGesturingEnded(GestureOverlayView overlay) {
            }
        });

        // Update values using data stored in the Bundle.
        updateValuesFromBundle(savedInstanceState);

        // Kick off the process of building a GoogleApiClient and requesting the LocationServices
        // API.
        buildGoogleApiClient();

        //Task for gettin address
        mGeocodeAsyncTask = new GeocodeAsyncTask(this, new ContactsGeocodeResultListener());
    }

    private void initEditWidgets(){
        mKeyWordTextView = (TextView)findViewById(R.id.edit_text_keyword);

        final View clearKeyWordView = findViewById(R.id.clear_keyword_view);


        clearKeyWordView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mKeyWordTextView.setText("");
            }
        });

        mKeyWordTextView.addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0 && clearKeyWordView.getVisibility() != View.VISIBLE) {
                    clearKeyWordView.setVisibility(View.VISIBLE);
                } else if (s.length() == 0 && clearKeyWordView.getVisibility() == View.VISIBLE) {
                    clearKeyWordView.setVisibility(View.INVISIBLE);
                }
            }
        });
    }



    @Override
    public Loader<ArrayList<Place>> onCreateLoader(int id, Bundle args) {
        Loader<ArrayList<Place>> loader = null;
        if (id == LOADER_ID) {
            String keyword = (mKeyWordTextView!=null && mKeyWordTextView.getText()!=null)?mKeyWordTextView.getText().toString():null;
            loader = new PlaceLoader(getApplicationContext(), keyword, mCurrentLocation);
        }
        return loader;
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<Place>> loader, ArrayList<Place> data) {
        mPlaceAdapter = new PlaceAdapter(data, mCurrentLocation);
        mPlaceAdapter.notifyDataSetChanged();
        mRecycleView.setAdapter(mPlaceAdapter);
        mProgressView.setVisibility(View.GONE);
        if(data==null || data.isEmpty()) {
            showInfoMessage(true);
        }
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<Place>> loader) {
        mProgressView.setVisibility(View.GONE);
    }


//    private void  addPlacesToMap(ArrayList<Place> places) {
//        if(mMapView!=null) {
//            mMapView.clear();
//            if (places != null) {
//                for (Place place : places) {
//                    mMapView.addMarker(new MarkerOptions()
//                            .position(new LatLng(place.getLocation().getLatitude(), place.getLocation().getLongitude()))
//                            .draggable(false));
//                }
//            }
//        }
//    }

    /**
     * Call user when click by button search
     * @param view in layout
     */
    public void onSearchClick(View view) {
        boolean isNetworkAvailable = Utils.isNetworkAvailable(getApplicationContext());

        if(isNetworkAvailable) {
            mProgressView.setVisibility(View.VISIBLE);
            mInfoView.setVisibility(View.GONE);

            String keyWord = mKeyWordTextView.getText() != null ? mKeyWordTextView.getText().toString() : null;

            mKeyWordTextView.clearFocus();
            Loader<ArrayList<Place>> loaderPlace = getSupportLoaderManager().getLoader(LOADER_ID);

            mPlaceAdapter.updateData(null, mCurrentLocation);
            PlaceLoader placeLoader = (PlaceLoader) loaderPlace;
            placeLoader.updateRequestData(keyWord, mCurrentLocation);
            placeLoader.forceLoad();
        }else {
            showInfoMessage(false);
        }
    }

    /**
     * Call user when click by button find me
     * @param view in layout
     */
    public void findMe(View view) {
        mSkipAnimationToMyLocation = false;
        if (mCurrentLocation != null) {
            animateToMyPosition();
        } else {
            showLocationWarningDialogIfNeed();
        }
        if (mGoogleApiClient.isConnected()) {
            startLocationUpdates();
        }
    }



    private void showLocationWarningDialogIfNeed() {
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(mLocationRequest);

        //**************************
        builder.setAlwaysShow(true); //this is the key ingredient
        //**************************

        PendingResult<LocationSettingsResult> result =
                LocationServices.SettingsApi.checkLocationSettings(mGoogleApiClient, builder.build());
        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(LocationSettingsResult result) {
                final Status status = result.getStatus();
                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:
                        // All location settings are satisfied. The client can initialize location
                        // requests here.
                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        // Location settings are not satisfied. But could be fixed by showing the user
                        // a dialog.
                        try {
                            // Show the dialog by calling startResolutionForResult(),
                            // and check the result in onActivityResult().
                            status.startResolutionForResult(PlaceActivity.this, 1000);
                        } catch (IntentSender.SendIntentException e) {
                            // Ignore the error.
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        // Location settings are not satisfied. However, we have no way to fix the
                        // settings so we won't show the dialog.
                        break;
                }
            }
        });
    }



    /**
     * Updates fields based on data stored in the bundle.
     *
     * @param savedInstanceState The activity state saved in the Bundle.
     */
    private void updateValuesFromBundle(Bundle savedInstanceState) {
        Log.i(TAG, "Updating values from bundle");
    }

    /**
     * Builds a GoogleApiClient. Uses the {@code #addApi} method to request the
     * LocationServices API.
     */
    protected synchronized void buildGoogleApiClient() {
        Log.i(TAG, "Building GoogleApiClient");
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        createLocationRequest();
    }

    /**
     * Sets up the location request. Android has two location request settings:
     * {@code ACCESS_COARSE_LOCATION} and {@code ACCESS_FINE_LOCATION}. These settings control
     * the accuracy of the current location. This sample uses ACCESS_FINE_LOCATION, as defined in
     * the AndroidManifest.xml.
     * <p/>
     * When the ACCESS_FINE_LOCATION setting is specified, combined with a fast update
     * interval (5 seconds), the Fused Location Provider API returns location updates that are
     * accurate to within a few feet.
     * <p/>
     * These settings are appropriate for mapping applications that show real-time location
     * updates.
     */
    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(UPDATE_INTERVAL_IN_MILLISECONDS);
        mLocationRequest.setFastestInterval(FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);

        showLocationWarningDialogIfNeed();

    }


    /**
     * Requests location updates from the FusedLocationApi.
     */
    protected void startLocationUpdates() {
        LocationServices.FusedLocationApi.requestLocationUpdates(
                mGoogleApiClient, mLocationRequest, this);
    }


    private void animateToMyPosition() {
        if(mCurrentLocation!=null && !mSkipAnimationToMyLocation) {
            double latitude  = mCurrentLocation.getLatitude();
            double longitude = mCurrentLocation.getLongitude();
            LatLng latLng = new LatLng(latitude, longitude);
            if(mMapView!=null) {
                mMapView.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                mMapView.animateCamera(CameraUpdateFactory.zoomTo(MAP_CAMERA_ZOOM));
            }
        }
    }

    /**
     * Removes location updates from the FusedLocationApi.
     */
    protected void stopLocationUpdates() {
        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mGoogleApiClient.isConnected()) {
            startLocationUpdates();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mGoogleApiClient.isConnected()) {
            stopLocationUpdates();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        mGoogleApiClient.disconnect();
    }

    private void showInfoMessage(boolean noResults) {
        ImageView infoImageView = ((ImageView) mInfoView.findViewById(R.id.contact_info_image));
        TextView infoMessageView = ((TextView) mInfoView.findViewById(R.id.contact_info_text));
        int idText = 0, idImage = 0;


        if(noResults) {
            idImage = R.drawable.no_results_icon;
            idText = R.string.places_not_found;
        }else {
            idText  = R.string.no_connection;
            idImage = R.drawable.network_error_icon;
        }

        if(idText>0) {
            infoMessageView.setText(idText);
        }
        if(idImage>0) {
            infoImageView.setImageResource(idImage);
        }
        mInfoView.setVisibility(View.VISIBLE);
    }

    /**
     * Runs when a GoogleApiClient object successfully connects.
     */
    @Override
    public void onConnected(Bundle connectionHint) {
        Log.i(TAG, "Connected to GoogleApiClient");

        mCurrentLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        animateToMyPosition();
        startLocationUpdates();
    }

    /**
     * Callback that fires when the location changes.
     */
    @Override
    public void onLocationChanged(Location location) {
        mCurrentLocation = location;
        animateToMyPosition();

        if(mGeocodeAsyncTask == null){
            mGeocodeAsyncTask =  new GeocodeAsyncTask(this, new ContactsGeocodeResultListener());
        }
        if(mGeocodeAsyncTask.getStatus() == AsyncTask.Status.PENDING) {
            mGeocodeAsyncTask.execute(mCurrentLocation);
        }
    }

    @Override
    public void onConnectionSuspended(int cause) {
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {
        Log.i(TAG, "Connection failed: ConnectionResult.getErrorCode() = " + result.getErrorCode());
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
    }


    private class  ContactsGeocodeResultListener implements GeocodeResultListener {
        @Override
        public void onResult(Address address) {
            mAddress = address;
            if(mAddress!=null) {
                if(mGoogleApiClient!=null && mGoogleApiClient.isConnected()) {
                    stopLocationUpdates();
                }
                mGeocodeAsyncTask = null;
            }
        }
    }


}
