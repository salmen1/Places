package com.ciklum.testing.places.component;

import com.ciklum.testing.places.R;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author Alexandr Stetsko (alexandr.stetsko@innomos.com)
 */
public class PlaceAdapter extends  RecyclerView.Adapter<PlaceViewHolder>  {

    @Override
    public PlaceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.place_item, parent, false);

        return new PlaceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PlaceViewHolder holder, int position) {
    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
