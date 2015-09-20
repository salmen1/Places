package com.ciklum.testing.places.component;

import com.ciklum.testing.places.R;
import com.ciklum.testing.places.component.data.Place;

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

    public PlaceAdapter() {
        this(new ArrayList<Place>());
    }

    public PlaceAdapter(ArrayList<Place> data) {
        mData = data;
        updateData(data);
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
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public final void updateData(ArrayList<Place> data) {
        if (data == null) {
            mData = new ArrayList<>();
        } else {
            mData = data;
        }
        notifyDataSetChanged();
    }
}
