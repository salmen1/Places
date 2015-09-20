package com.ciklum.testing.places.component;

import android.content.Context;
import android.content.Loader;

/**
 * @author Alexandr Stetsko (alexandr.stetsko@innomos.com)
 */
final class PlaceLoader extends Loader<String> {

    /**
     * Stores away the application context associated with context.
     * Since Loaders can be used across multiple activities it's dangerous to
     * store the context directly; always use {@link #getContext()} to retrieve
     * the Loader's Context, don't use the constructor argument directly.
     * The Context returned by {@link #getContext} is safe to use across
     * Activity instances.
     *
     * @param context used to retrieve the application context.
     */
    public PlaceLoader(Context context) {
        super(context);
    }

}
