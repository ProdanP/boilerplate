package com.ebs.baseutility.mvp;

public interface BasePresenter<V extends BaseView> extends InteractorListener {
    void onDestroyed();

    void onAttach(V view);
}
