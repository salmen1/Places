package com.ciklum.testing.places.component;

import com.ciklum.testing.places.Constants;
import com.ciklum.testing.places.R;
import com.ciklum.testing.places.data.Photo;
import com.ciklum.testing.places.data.Place;
import com.ciklum.testing.places.utils.Utils;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * @author Alexandr Stetsko (alexandr.stetsko@innomos.com)
 */
class PhotoAdapter extends PagerAdapter {

    private static final String URL ="https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photoreference="
            + "%s&key="+ Constants.PLACES_API_KEY;

    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private Place mPlace;



    public PhotoAdapter(Context context, Place place) {
        mContext = context;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mPlace = place;
    }

    @Override
    public int getCount() {
        if(mPlace.getPhotos()==null) {
            return 0;
        }
        return mPlace.getPhotos().size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((LinearLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View itemView = mLayoutInflater.inflate(R.layout.pager_item, container, false);

        ImageView imageView = (ImageView) itemView.findViewById(R.id.image_view);

        Photo photo = mPlace.getPhotos().get(position);
        String url = String.format(URL, photo.getPhotoReference());
        ImageLoader.getInstance().displayImage(url, imageView, Utils.MEMORY_CACHE_OPT);

        container.addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }
}
