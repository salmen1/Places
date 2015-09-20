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
    TextView positionTextView;
    TextView nameTextView;
    ImageView photoTextView;

    TextView phoneTextView;
    TextView emailTextView;

    public PlaceViewHolder(View itemView) {
        super(itemView);
        this.itemView  = itemView;
        positionTextView = (TextView)itemView.findViewById(R.id.contact_position);
        nameTextView = (TextView)itemView.findViewById(R.id.contact_name);
        photoTextView = (ImageView)itemView.findViewById(R.id.contact_photo);
        phoneTextView = (TextView)itemView.findViewById(R.id.contact_phone);
        emailTextView = (TextView)itemView.findViewById(R.id.contact_email);

        setIsRecyclable(false);
    }
}
