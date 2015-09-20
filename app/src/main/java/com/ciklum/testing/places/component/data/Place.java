package com.ciklum.testing.places.component.data;


import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author Alexandr Stetsko (alexandr.stetsko@innomos.com)
 */
@Root(name="result", strict=false)
public final class Place implements Parcelable {


    private interface Keys {
        String ID = "id";
        String NAME = "name";
        String VICINITY = "vicinity";
        String ICON = "icon";
        String GEOMETRY = "geometry";
    }

    @Element(name = Keys.ID)
    private String id;
    @Element(name = Keys.NAME)
    private String name;
    @Element(name = Keys.ICON)
    private String icon;
    @Element(name = Keys.VICINITY)
    private String vicinity;

    @Element(name = Keys.GEOMETRY)
    private PlaceLocation placelocation;

    private int distance;

    public Place() {
    }

    public Place(String id, String name, String icon, String vicinity, PlaceLocation placelocation) {
        this.id = id;
        this.name = name;
        this.icon = icon;
        this.vicinity = vicinity;
        this.placelocation = placelocation;
    }

    public String getName() {
        return name;
    }

    public String getIcon() {
        return icon;
    }

    public String getVicinity() {
        return vicinity;
    }
    public PlaceLocation getLocation() {
        return placelocation;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeString(id);
        parcel.writeString(name);
        parcel.writeString(icon);
        parcel.writeString(vicinity);
        parcel.writeInt(distance);
        parcel.writeParcelable(placelocation, flags);
    }

    public static final Creator<Place> CREATOR = new Creator<Place>() {
        // unpack object from Parcel
        public Place createFromParcel(Parcel in) {
            return new Place(in);
        }

        public Place[] newArray(int size) {
            return new Place[size];
        }
    };

    private Place(Parcel parcel) {
        id  = parcel.readString();
        name  = parcel.readString();
        icon = parcel.readString();
        vicinity = parcel.readString();
        distance = parcel.readInt();
        placelocation = parcel.readParcelable(PlaceLocation.class.getClassLoader());
    }


    @Override
    public String toString() {
        return "Place{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", icon='" + icon + '\'' +
                ", vicinity='" + vicinity + '\'' +
                ", location=" + placelocation +
                '}';
    }
}

