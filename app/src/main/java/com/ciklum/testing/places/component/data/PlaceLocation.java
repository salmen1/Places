package com.ciklum.testing.places.component.data;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author Alexandr Stetsko (alexandr.stetsko@innomos.com)
 */
@Root(name = "geometry", strict = true)
public final class PlaceLocation implements Parcelable {


    private interface Keys {
        String LATITUDE = "lat";
        String LONGITUDE = "lng";
    }

    public PlaceLocation() {
    }

    public PlaceLocation(Location location) {
        this.location = location;
    }

    @Element(name = "location")
    private Location location;

    public double getLatitude() {
        return location.latitude;
    }

    public double getLongitude() {
        return location.longitude;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeParcelable(location, flags);
    }

    public static final Creator<PlaceLocation> CREATOR = new Creator<PlaceLocation>() {
        // unpack object from Parcel
        public PlaceLocation createFromParcel(Parcel in) {
            return new PlaceLocation(in);
        }

        public PlaceLocation[] newArray(int size) {
            return new PlaceLocation[size];
        }
    };

    private PlaceLocation(Parcel parcel) {
        location = parcel.readParcelable(Location.class.getClassLoader());
    }

    @Override
    public String toString() {
        return "PlaceLocation{" +
                "latitude=" + location.latitude +
                ", longitude=" + location.longitude +
                '}';
    }


    @Root(name = "location", strict = true)
    private static class Location implements Parcelable {

        @Element(name = Keys.LATITUDE)
        private double latitude;
        @Element(name = Keys.LONGITUDE)
        private double longitude;

        public Location() {
        }

        public Location(double latitude, double longitude) {
            this.latitude = latitude;
            this.longitude = longitude;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int flags) {
            parcel.writeDouble(latitude);
            parcel.writeDouble(longitude);
        }


        public static final Creator<Location> CREATOR = new Creator<Location>() {
            // unpack object from Parcel
            public Location createFromParcel(Parcel in) {
                return new Location(in);
            }

            public Location[] newArray(int size) {
                return new Location[size];
            }
        };

        private Location(Parcel parcel) {
            latitude = parcel.readDouble();
            longitude = parcel.readDouble();
        }

    }
}
