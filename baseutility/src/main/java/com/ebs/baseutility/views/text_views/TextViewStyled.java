package com.ebs.baseutility.views.text_views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;

import com.ebs.baseutility.R;


public class TextViewStyled extends androidx.appcompat.widget.AppCompatTextView {

    public static final String ANDROID_SCHEMA = "http://schemas.android.com/apk/res/android";
    public static String fontName ="";
    public static boolean fontType =false;

    public TextViewStyled(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public TextViewStyled(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.TextViewStyled);
        final int N = a.getIndexCount();
        for (int i = 0; i < N; ++i) {
            int attr = a.getIndex(i);
            if (attr == R.styleable.TextViewStyled_fontName_text) {
                fontName = a.getString(attr);
                applyStyledFont(fontName, context);
            }

            if (attr == R.styleable.TextViewStyled_fancyText_text) {
                fontType = a.getBoolean(attr, false);
            }
        }
        setIncludeFontPadding(false);
        a.recycle();

    }

    public void applyStyledFont(String fontName, Context context) {
       // int textStyle = attrs.getAttributeIntValue(ANDROID_SCHEMA, "textStyle", Typeface.NORMAL);
        Typeface customFont = selectTypeface(context, fontName);
        setTypeface(customFont);
        setIncludeFontPadding(false);
    }

    private Typeface selectTypeface(Context context, String fName) {
       /* switch (textStyle) {
            case Typeface.BOLD: // bold
                return FontManager.getInstance().getTypeface(fName+".ttf", context);
            case Typeface.ITALIC: // medium
                return FontManager.getInstance().getTypeface(fName+".ttf", context);
            case Typeface.BOLD_ITALIC: // bold italic
                return FontManager.getInstance().getTypeface(fName+".ttf", context);
            case Typeface.NORMAL: // regular
                return FontManager.getInstance().getTypeface(fName+".ttf", context);

            default:
                return FontManager.getInstance().getTypeface(fName+".ttf", context);
        }*/
       return FontManager.getInstance().getTypeface(fName+".ttf", context);
    }
}