package com.ebs.baseutility.strip_page;


import androidx.fragment.app.Fragment;

public class StripFragmentModel {
    private Fragment fragment;
    private int resImg;
    private String text;
    private String textIcon;

    public int getResImg() {
        return resImg;
    }

    public void setResImg(int resImg) {
        this.resImg = resImg;
    }

    public Fragment getFragment() {

        return fragment;
    }

    public void setFragment(Fragment fragment) {
        this.fragment = fragment;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTextIcon() {
        return textIcon;
    }

    public void setTextIcon(String textIcon) {
        this.textIcon = textIcon;
    }
}
