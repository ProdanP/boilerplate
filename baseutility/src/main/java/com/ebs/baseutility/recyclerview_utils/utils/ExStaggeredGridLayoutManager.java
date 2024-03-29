package com.ebs.baseutility.recyclerview_utils.utils;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import android.view.View;

public class ExStaggeredGridLayoutManager extends StaggeredGridLayoutManager {

    private final String TAG = getClass().getSimpleName();

    GridLayoutManager.SpanSizeLookup mSpanSizeLookup;

    public ExStaggeredGridLayoutManager(int spanCount, int orientation) {
        super(spanCount, orientation);
    }

    public GridLayoutManager.SpanSizeLookup getSpanSizeLookup() {
        return mSpanSizeLookup;
    }

    public void setSpanSizeLookup(GridLayoutManager.SpanSizeLookup spanSizeLookup) {
        mSpanSizeLookup = spanSizeLookup;
    }

    @Override
    public void onMeasure(RecyclerView.Recycler recycler, RecyclerView.State state, int widthSpec, int heightSpec) {
        for (int i = 0; i < getItemCount(); i++) {

            if(mSpanSizeLookup!=null) {
                if (mSpanSizeLookup.getSpanSize(i) > 1) {
                    try {
                        View view = recycler.getViewForPosition(i);
                        if (view != null) {
                            LayoutParams lp = (LayoutParams) view.getLayoutParams();
                            lp.setFullSpan(true);
                        }
                    } catch (Exception e) {
                       // e.printStackTrace();
                    }
                }
            }
        }
        super.onMeasure(recycler, state, widthSpec, heightSpec);
    }
}