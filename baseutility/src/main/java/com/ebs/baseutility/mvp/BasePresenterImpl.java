package com.ebs.baseutility.mvp;

import io.reactivex.disposables.CompositeDisposable;
import okhttp3.ResponseBody;

public class BasePresenterImpl<V extends BaseView> implements BasePresenter<V> {
    protected CompositeDisposable compositeDisposable = new CompositeDisposable();
    protected V view;

    public BasePresenterImpl() {
    }

    @Override
    public void onAttach(V view) {
        this.view = view;
    }

    @Override
    public void onDestroyed() {
        if (!compositeDisposable.isDisposed())
            compositeDisposable.dispose();
        compositeDisposable.clear();
    }

    @Override
    public void onStartRequest(boolean showLoading) {
        if (showLoading)
            view.onShowLoading();
    }

    @Override
    public void onRequestFinished(boolean showLoading) {
        if (showLoading)
            view.onHideLoading();
    }

    @Override
    public void onRequestHttpError(boolean showError, int code, ResponseBody responseBody) {
        view.onShowHttpError(showError, code, responseBody);
    }

    @Override
    public void onRequestServerError(boolean showError, String error) {
        view.onShowServerError(showError, error);
    }

    @Override
    public void onRequestNoConnection(boolean showError) {
        view.onShowConnectionError(showError);
    }

    @Override
    public void onRequestUnauthorized(boolean showError) {
        view.onUnAuthorizedError(showError);
    }

    @Override
    public void onRequestNotFound(boolean showError) {
        view.onNotFoundError(showError);
    }
}
