package com.ciklum.testing.places;

import com.nostra13.universalimageloader.cache.disc.naming.FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import android.app.Application;
import android.content.Context;

/**
 * @author Alexandr Stetsko (alexandr.stetsko@innomos.com)
 */
public class PlaceApplication extends Application {

    private static ImageLoaderConfiguration config;
    @Override
    public void onCreate() {
        super.onCreate();
        //Initialization imageLoader
        ImageLoader.getInstance().init(getConfig(getApplicationContext()));
    }

    public static ImageLoaderConfiguration getConfig(Context context) {
        if (config == null) {
            config = new ImageLoaderConfiguration.Builder(context)
                    .threadPriority(Thread.NORM_PRIORITY - 2)
                    .denyCacheImageMultipleSizesInMemory()
                    .diskCacheFileNameGenerator(new FileNameGenerator() {
                        @Override
                        public String generate(String imageUri) {
                            return imageUri;
                        }
                    })
                    .threadPoolSize(2)
                    .tasksProcessingOrder(QueueProcessingType.LIFO)
                    .build();
        }
        return config;
    }
}
