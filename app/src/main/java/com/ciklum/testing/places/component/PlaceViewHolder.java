package com.ciklum.testing.places.component;

import com.ciklum.testing.places.R;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * @author Alexandr Stetsko (alexandr.stetsko@innomos.com)
 */
final class PlaceViewHolder extends RecyclerView.ViewHolder {

    View itemView;
    TextView nameTextView;
    ImageView photoTextView;

    TextView distanceTextView;
    TextView addressTextView;

    public PlaceViewHolder(View itemView) {
        super(itemView);
        this.itemView  = itemView;
        nameTextView = (TextView)itemView.findViewById(R.id.place_name);
        photoTextView = (ImageView)itemView.findViewById(R.id.contact_photo);
        distanceTextView = (TextView)itemView.findViewById(R.id.place_distance);
        addressTextView = (TextView)itemView.findViewById(R.id.place_address);

        setIsRecyclable(false);
    }
}
