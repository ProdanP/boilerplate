package com.ebs.baseutility.rest;

public class Params {
    private boolean showLoadingonStart;
    private boolean showLoadingonFinish;
    private boolean showHttpError;
    private boolean showUnauthorizedError;
    private boolean showNotFoundError;
    private boolean showServerError;
    private boolean showNoConnection;

    public Params() {
        this.showLoadingonStart = true;
        this.showLoadingonFinish = true;
        this.showHttpError = true;
        this.showUnauthorizedError = true;
        this.showServerError = true;
        this.showNoConnection = true;
        this.showNotFoundError = true;
    }

    public static Params onlyLoading() {
        return new Params(true,
                true,
                false,
                false,
                false,
                false, true);
    }

    public static Params nothing() {
        return new Params(false,
                false,
                false,
                false,
                false,
                false, true);
    }

    public static Params noLoading() {
        return new Params(false,
                false,
                true,
                true,
                true,
                true, true);
    }

    public static Params noHttpNoUnauthorized() {
        return new Params(true,
                true,
                false,
                false,
                true,
                true, true);
    }

    public static Params noHttp() {
        return new Params(true,
                true,
                false,
                true,
                true,
                true, true);
    }

    public static Params noNotFound() {
        return new Params(true,
                true,
                true,
                true,
                true,
                true, false);
    }

    public static Params noError() {
        return new Params(true,
                true,
                false,
                false,
                false,
                true, false);
    }

    private Params(boolean showLoadingonStart, boolean showLoadingonFinish,
                   boolean showHttpError, boolean showUnauthorizedError,
                   boolean showServerError, boolean showNoConnection, boolean showNotFoundError) {
        this.showLoadingonStart = showLoadingonStart;
        this.showLoadingonFinish = showLoadingonFinish;
        this.showHttpError = showHttpError;
        this.showUnauthorizedError = showUnauthorizedError;
        this.showServerError = showServerError;
        this.showNoConnection = showNoConnection;
        this.showNotFoundError = showNotFoundError;
    }

    boolean isShowLoadingonStart() {
        return showLoadingonStart;
    }

    boolean isShowLoadingonFinish() {
        return showLoadingonFinish;
    }

    boolean isShowHttpError() {
        return showHttpError;
    }

    boolean isShowUnauthorizedError() {
        return showUnauthorizedError;
    }

    boolean isShowServerError() {
        return showServerError;
    }

    boolean isShowNoConnection() {
        return showNoConnection;
    }

    boolean isShowNotFoundError() {
        return showNotFoundError;
    }
}
