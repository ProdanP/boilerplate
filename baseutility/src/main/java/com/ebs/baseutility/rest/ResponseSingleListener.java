package com.ebs.baseutility.rest;

import com.ebs.baseutility.mvp.InteractorListener;
import com.ebs.baseutility.rest.connectivity.NoConnectivityException;

import io.reactivex.observers.DisposableSingleObserver;
import retrofit2.HttpException;

public class ResponseSingleListener<T> extends DisposableSingleObserver<T> {
    private InteractorListener interactorListener;
    private Params params;

    public ResponseSingleListener(InteractorListener interactorListener, Params params) {
        this.interactorListener = interactorListener;
        this.params = params;
    }

    public ResponseSingleListener(InteractorListener interactorListener) {
        this.interactorListener = interactorListener;
        this.params = new Params();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (interactorListener != null) {
            interactorListener.onStartRequest(params.isShowLoadingonStart());
        }
    }

    @Override
    public void onSuccess(T t) {
        if (interactorListener != null) {
            interactorListener.onRequestFinished(params.isShowLoadingonFinish());
        }
    }

    @Override
    public void onError(Throwable e) {
        if (interactorListener != null) {
            interactorListener.onRequestFinished(params.isShowLoadingonFinish());
        }
        if(e instanceof NoConnectivityException){
            if (interactorListener != null) {
                interactorListener.onRequestNoConnection(params.isShowNoConnection());
            }
        } else {
            if (e instanceof HttpException) {
                HttpException httpException = (HttpException) e;
                if(httpException.response().code() == 401){
                    if (interactorListener != null) {
                        interactorListener.onRequestUnauthorized(params.isShowUnauthorizedError());
                    }
                } else if(httpException.response().code() == 404){
                    if (interactorListener != null) {
                        interactorListener.onRequestNotFound(params.isShowNotFoundError());
                    }
                } else {
                    if (interactorListener != null) {
                        interactorListener.onRequestHttpError(params.isShowHttpError(), httpException.response().code(), ((HttpException) e).response().errorBody());
                    }
                }
            } else {
                if (interactorListener != null) {
                    interactorListener.onRequestServerError(params.isShowServerError(), e.getMessage());
                }
            }
        }
        e.printStackTrace();
    }
}
