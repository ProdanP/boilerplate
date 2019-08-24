package com.ebs.baseutility.views.text_views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;

import com.ebs.baseutility.R;


public class EditTextStyled extends androidx.appcompat.widget.AppCompatEditText {

    public static final String ANDROID_SCHEMA = "http://schemas.android.com/apk/res/android";
    public static String fontName = "";
    public static boolean fontType = false;

    public EditTextStyled(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public EditTextStyled(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.EditTextStyled);
        final int N = a.getIndexCount();
        for (int i = 0; i < N; ++i) {
            int attr = a.getIndex(i);
            if (attr == R.styleable.EditTextStyled_fontName_edit) {
                fontName = a.getString(attr);
                applyStyledFont(fontName, context);
            }

            if (attr == R.styleable.EditTextStyled_fancyText_edit) {
                fontType = a.getBoolean(attr, false);
            }
        }
        a.recycle();
    }

    public void applyStyledFont(String fontName, Context context) {
        Typeface customFont = selectTypeface(context, fontName);
        setTypeface(customFont);
        setIncludeFontPadding(false);
    }

    private Typeface selectTypeface(Context context, String fName) {
        return FontManager.getInstance().getTypeface(fName + ".ttf", context);
    }
}