package com.ciklum.testing.places.component;

import com.ciklum.testing.places.R;
import com.ciklum.testing.places.component.data.Place;
import com.ciklum.testing.places.utils.Utils;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.location.Location;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * @author Alexandr Stetsko (alexandr.stetsko@innomos.com)
 */
public class PlaceAdapter extends RecyclerView.Adapter<PlaceViewHolder> {

    private ArrayList<Place> mData;
    private Location mCurrentLocation;

    public PlaceAdapter() {
        this(new ArrayList<Place>(), null);
    }

    public PlaceAdapter(ArrayList<Place> data, Location currentLocation) {
        mData = data;
        updateData(data, currentLocation);
    }

    @Override
    public PlaceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.place_item, parent, false);

        return new PlaceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PlaceViewHolder holder, int position) {
        Place place = mData.get(position);
        holder.addressTextView.setText(place.getVicinity());
        holder.nameTextView.setText(place.getName());
        ImageLoader.getInstance().displayImage(place.getIcon(), holder.photoTextView, Utils.MEMORY_CACHE_OPT);

        if (place.getDistance() == 0) {
            int distance = Utils.getDistanceInMetres(mCurrentLocation, place.getLocation().getLatitude(),
                    place.getLocation().getLongitude());
            place.setDistance(distance);
        }
        holder.distanceTextView.setText(String.valueOf(place.getDistance()) + " m");
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public final void updateData(ArrayList<Place> data, Location currentLocation) {
        if (data == null) {
            mData = new ArrayList<>();
        } else {
            mData = data;
        }
        mCurrentLocation = currentLocation;

        notifyDataSetChanged();
    }


}
