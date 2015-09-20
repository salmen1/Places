package com.ciklum.testing.places.component;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.MapFragment;

import com.ciklum.testing.places.R;

/**
 * @author Alexandr Stetsko (alexandr.stetsko@innomos.com)
 */
public final class PlaceMapFragment extends MapFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        setMapTransparent((ViewGroup) view);
        return view;
    }

    private void setMapTransparent(ViewGroup group) {
        int childCount = group.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = group.getChildAt(i);
            if (child instanceof ViewGroup) {
                setMapTransparent((ViewGroup) child);
            } else if (child instanceof SurfaceView) {
                child.setBackgroundColor(getResources().getColor(R.color.transparent));
            }
        }
    }
}
