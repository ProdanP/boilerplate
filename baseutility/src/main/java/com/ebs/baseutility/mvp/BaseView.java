package com.ebs.baseutility.mvp;

import android.content.Context;
import androidx.annotation.LayoutRes;

import okhttp3.ResponseBody;

public interface BaseView {
    @LayoutRes
    int getLayoutResourceId();
    @LayoutRes
    int getLayoutResourceIdLoading();
    int getRootLoadingViewResId();

    void onShowLoading();
    void onHideLoading();

    void onShowHttpError(boolean onShow, int code, ResponseBody responseBody);
    void onShowServerError(boolean onShow, String error);
    void onShowConnectionError(boolean onShow);
    void onUnAuthorizedError(boolean onShow);
    void onNotFoundError(boolean onShow);

    void onFragmentCreated();
    void onFragmentAppeared();
    Context context();
}
