package com.ciklum.testing.places.component;

import com.ciklum.testing.places.R;
import com.ciklum.testing.places.component.data.Place;
import com.viewpagerindicator.CirclePageIndicator;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * @author Alexandr Stetsko (alexandr.stetsko@innomos.com)
 */

public class PlaceDetailsViewer extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Place place = getIntent().getParcelableExtra("place");

        PhotoAdapter customPagerAdapter = new PhotoAdapter(getApplicationContext(), place);

        TextView placeNameView = (TextView)findViewById(R.id.place_name);
        TextView placeVicilityView = (TextView)findViewById(R.id.place_vicinity);
        TextView placeDistanceView = (TextView)findViewById(R.id.place_distance);

        placeNameView.setText(place.getName());
        placeVicilityView.setText(place.getVicinity());
        placeDistanceView.setText(String.format(getResources().getString(R.string.distance_to_place), place.getDistance()));

        CirclePageIndicator titleIndicator = (CirclePageIndicator)findViewById(R.id.indicator);

        if(place.getPhotos()==null || place.getPhotos().isEmpty()) {
            ImageView noImageView = (ImageView) findViewById(R.id.no_image);
            noImageView.setVisibility(View.VISIBLE);
            titleIndicator.setVisibility(View.GONE);
        }


        ViewPager mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(customPagerAdapter);

        titleIndicator.setViewPager(mViewPager);
    }
}
