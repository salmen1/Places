package com.ciklum.testing.places.component;

import com.ciklum.testing.places.R;
import com.ciklum.testing.places.component.data.Place;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

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

        ViewPager mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(customPagerAdapter);
    }
}
