package com.ciklum.testing.places.component.data;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author Alexandr Stetsko (alexandr.stetsko@innomos.com)
 */
@Root(name = "photo", strict = false)
public class Photo implements Parcelable {

    private interface Keys {

        String PHOTO_REFERENCE = "photo_reference";
        String WIDTH = "width";
        String HEIGHT = "height";
    }

    @Element(name = Keys.PHOTO_REFERENCE)
    private String photoReference;

    @Element(name = Keys.WIDTH)
    private int width;
    @Element(name = Keys.HEIGHT)
    private int height;

    public Photo() {
    }

    public Photo(String photoReference, int width, int height) {
        this.photoReference = photoReference;
        this.width = width;
        this.height = height;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeInt(width);
        parcel.writeInt(height);
        parcel.writeString(photoReference);
    }

    public static final Creator<Photo> CREATOR = new Creator<Photo>() {
        // unpack object from Parcel
        public Photo createFromParcel(Parcel in) {
            return new Photo(in);
        }

        public Photo[] newArray(int size) {
            return new Photo[size];
        }
    };

    private Photo(Parcel parcel) {
        width = parcel.readInt();
        height = parcel.readInt();
        photoReference = parcel.readString();
    }
}
