package com.ciklum.testing.places;

import com.ciklum.testing.places.component.PlaceActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

public class MainActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = new Intent(getApplicationContext(), PlaceActivity.class);
        startActivity(intent);
    }

}
