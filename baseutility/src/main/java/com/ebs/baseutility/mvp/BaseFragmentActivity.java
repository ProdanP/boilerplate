package com.ebs.baseutility.mvp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.ebs.baseutility.R;
import com.ebs.baseutility.utils.StatusBarUtil;
import com.ebs.baseutility.utils.local_broadcast.LocalBroadCastReceiver;
import com.ebs.baseutility.views.LoadingView;
import com.ebs.baseutility.views.NavigationBar;
import com.google.gson.Gson;

import butterknife.ButterKnife;
import me.yokeyword.fragmentation.SupportActivity;

public abstract class BaseFragmentActivity extends SupportActivity implements BaseView {

    protected View loadingView;
    protected SupportActivity thisActivity;
    private BroadcastReceiver receiver;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResourceId());
        ButterKnife.bind(this);
        thisActivity= this;
        try {
            createLoadingView(getRootLoadingViewResId());
        } catch (Exception e){
            e.printStackTrace();
        }
      //  StatusBarUtil.setStatusTintColor(thisActivity);
        com.jaeger.library.StatusBarUtil.setTranslucentForImageView(thisActivity, 0, null);
        getNavigation((ViewGroup) getWindow().getDecorView().getRootView());
    }

    private void getNavigation(ViewGroup parent) {
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);
            if (child instanceof ViewGroup) {
                getNavigation((ViewGroup) child);
                if (child instanceof NavigationBar) {
                    StatusBarUtil.addStatus(thisActivity,(LinearLayout) child);
                }
            }
        }
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
        return getDialogContext();
    }

    @Override
    public void onFragmentAppeared() {

    }

    @Override
    public void onFragmentCreated() {

    }

    private void createLoadingView(int resId){
        RelativeLayout rootView = findViewById(resId);
        loadingView = new LoadingView().getProgressBar(this,rootView,getLayoutResourceIdLoading());
    }
    public Context getDialogContext() {
        Context context;
        if (getParent() != null)
            context = getParent();
        else
            context = this;
        return context;
    }

    public void sendBroadCast(int action, Object o, String sender){
        Intent intent = new Intent("filter");
        intent.putExtra("action", action);
        intent.putExtra("data",new Gson().toJson(o));
        intent.putExtra("sender",sender);
        LocalBroadcastManager.getInstance(getDialogContext()).sendBroadcast(intent);
    }

    public void registerBroadCastReceiver(final LocalBroadCastReceiver broadcastReceiver){
        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                try{
                    if(thisActivity != null && intent.getExtras() != null) {
                        broadcastReceiver.onReceive((intent.getExtras().getInt("action")), intent.getExtras().getString("sender"), intent.getExtras().getString("data"));
                    }
                } catch (Exception e){e.printStackTrace();}
            }
        };
        LocalBroadcastManager.getInstance(getDialogContext()).registerReceiver(receiver, new IntentFilter("filter"));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(receiver!=null){
            LocalBroadcastManager.getInstance(getDialogContext()).unregisterReceiver(receiver);
        }
    }
}