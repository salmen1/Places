package com.ciklum.testing.places.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;

/**
 * @author Alexandr Stetsko (alexandr.stetsko@innomos.com)
 */
public class AutoFitRecyclerView extends RecyclerView {

    private StaggeredGridLayoutManager manager;
    private int numColumn;

    public AutoFitRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);

        if (attrs != null) {
            // Read android:columnWidth from xml
            int[] attrsArray = {
                    android.R.attr.numColumns
            };
            TypedArray array = context.obtainStyledAttributes(attrs, attrsArray);
            numColumn = array.getInteger(0, -1);
            array.recycle();
        }

        updateLayoutManager();
    }

    protected void onMeasure(int widthSpec, int heightSpec) {
        super.onMeasure(widthSpec, heightSpec);
        if (numColumn > 0) {
            manager.setSpanCount(numColumn);
        }
    }

    /**
     * Reset StaggeredGridLayoutManager and setting to recycleView again
     */
    public final void updateLayoutManager() {
        manager = new StaggeredGridLayoutManager(numColumn==0?1:numColumn, OrientationHelper.VERTICAL);
        setLayoutManager(manager);
    }
}
