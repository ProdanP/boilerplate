package com.ebs.baseutility.mvp;

import okhttp3.ResponseBody;

public interface InteractorListener {
    void onStartRequest(boolean showLoading);
    void onRequestFinished(boolean showLoading);
    void onRequestHttpError(boolean showError, int code, ResponseBody responseBody);
    void onRequestUnauthorized(boolean showError);
    void onRequestNotFound(boolean showError);
    void onRequestServerError(boolean showError, String error);
    void onRequestNoConnection(boolean showError);
}
