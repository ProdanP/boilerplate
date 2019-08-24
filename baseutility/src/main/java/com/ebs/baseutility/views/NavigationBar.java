package com.ebs.baseutility.views;

import android.content.Context;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;

public class NavigationBar extends LinearLayout {
    public NavigationBar(Context context) {
        super(context);
        init();
    }

    public NavigationBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public NavigationBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        setOrientation(LinearLayout.VERTICAL);
    }
}
