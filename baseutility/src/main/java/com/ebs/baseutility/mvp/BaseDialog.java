package com.ebs.baseutility.mvp;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.ebs.baseutility.R;
import com.ebs.baseutility.views.LoadingView;

import butterknife.ButterKnife;

public abstract class BaseDialog extends Dialog implements BaseView {
    protected View loadingView;
    private Context context;

    public BaseDialog(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public BaseDialog(Context context, int themeResId) {
        super(context, themeResId);
        this.context = context;
        init();
    }

    protected BaseDialog(Context context, boolean cancelable, DialogInterface.OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        this.context = context;
        init();
    }

    private void init(){
        if (getWindow() != null) {
            getWindow().requestFeature(Window.FEATURE_NO_TITLE);
            getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            getWindow().getDecorView().setBackgroundResource(android.R.color.transparent);
            getWindow().setDimAmount(0.3f);
            setCanceledOnTouchOutside(false);
            setCancelable(false);
        }

        setContentView(getLayoutResourceId());
        ButterKnife.bind(this);

        DisplayMetrics displayMetrics = getContext().getResources().getDisplayMetrics();
        int width = displayMetrics.widthPixels;

        WindowManager.LayoutParams wmlp = getWindow().getAttributes();
        wmlp.width = width;
        wmlp.height = displayMetrics.heightPixels;

        createLoadingView(getRootLoadingViewResId());
    }

    @Override
    public int getRootLoadingViewResId() {
        return 0;
    }

    @Override
    public int getLayoutResourceIdLoading() {
        return R.layout.loading_view;
    }

    @Override
    public Context context() {
        return context;
    }

    @Override
    public void onFragmentAppeared() {

    }

    @Override
    public void onFragmentCreated() {

    }

    private void createLoadingView(int resId){
        RelativeLayout rootView = findViewById(resId);
        loadingView = new LoadingView().getProgressBarDialog(this,rootView,getLayoutResourceIdLoading());
        loadingView.setClickable(true);
        loadingView.setFocusable(true);
    }
}
