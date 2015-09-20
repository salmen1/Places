package com.ciklum.testing.places.component.location;

import android.location.Address;

/**
 * @author Alexandr Stetsko (alexandr.stetsko@innomos.com)
 */
public interface GeocodeResultListener {

    void onResult(Address address);
}
