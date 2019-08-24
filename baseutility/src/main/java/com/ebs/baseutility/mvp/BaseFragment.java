package com.ebs.baseutility.mvp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.ebs.baseutility.R;
import com.ebs.baseutility.utils.StatusBarUtil;
import com.ebs.baseutility.utils.local_broadcast.BroadcastObject;
import com.ebs.baseutility.utils.local_broadcast.LocalBroadCastReceiver;
import com.ebs.baseutility.views.LoadingView;
import com.ebs.baseutility.views.NavigationBar;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.ButterKnife;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Action;
import io.reactivex.schedulers.Schedulers;
import me.yokeyword.fragmentation.SupportFragment;


public abstract class BaseFragment extends SupportFragment implements BaseView {

    private BroadcastReceiver receiver;
    protected View view;
    protected View loadingView;
    protected View requestLoadingView;
    protected LayoutInflater inflater;
    protected boolean fragmentVisible;
    protected boolean isSupportVisible;
    protected List<BroadcastObject> broadcastObjects= new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hideSoftInput();
    }


    @Override
    public int getRootLoadingViewResId() {
        return 0;
    }

    @Override
    public int getLayoutResourceIdLoading() {
        return R.layout.loading_view;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.inflater = inflater;
        view = inflater.inflate(getLayoutResourceId(), container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setNavigation((ViewGroup) view);
        try {
            createLoadingView(getRootLoadingViewResId());
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setRequestLoadingView(loadingView);
    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        if(!fragmentVisible){
            fragmentVisible = true;
            Flowable.timer(300, TimeUnit.MILLISECONDS)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnComplete(new Action() {
                        @Override
                        public void run() {
                            onFragmentCreated();
                            onFragmentAppeared();
                        }
                    })
                    .subscribe();
        } else {
            Flowable.timer(300, TimeUnit.MILLISECONDS)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnComplete(new Action() {
                        @Override
                        public void run() {
                            onFragmentAppeared();
                        }
                    })
                    .subscribe();
        }
        isSupportVisible = true;
    }

    @Override
    public void onSupportInvisible() {
        super.onSupportInvisible();
        isSupportVisible = false;
    }

    @Override
    public void onFragmentCreated() {

    }

    @Override
    public void onFragmentAppeared() {
        broadcastObjects.clear();
    }

    @Override
    public Context context() {
        return getContext();
    }

    public void setRequestLoadingView(View requestLoadingView) {
        this.requestLoadingView = requestLoadingView;
    }

    private void setNavigation(ViewGroup parent) {
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);
            if (child instanceof ViewGroup) {
                setNavigation((ViewGroup) child);
                if (child instanceof NavigationBar) {
                    StatusBarUtil.addStatus(getActivity(),(LinearLayout) child);
                    break;
                }
            }
        }
    }
    private void createLoadingView(int resId){
        try {
            RelativeLayout rootView = view.findViewById(resId);
            if(rootView == null){
                loadingView = new LoadingView().getProgressBar(getActivity(), view, getLayoutResourceIdLoading());
            } else {
                loadingView = new LoadingView().getProgressBar(getActivity(), rootView, getLayoutResourceIdLoading());
            }
        } catch (Exception E){
            //
        }

    }

    public void registerBroadCastReceiver(final LocalBroadCastReceiver broadcastReceiver){
        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if(getActivity() != null && intent.getExtras() != null) {
                    if(intent.getExtras().get("action") != null && intent.getExtras().get("data") != null) {
                        broadcastObjects.add(new BroadcastObject(intent.getExtras().getInt("action"), intent.getExtras().getString("data"), intent.getExtras().getString("sender")));
                    }
                    if(broadcastReceiver != null){
                        try{
                            broadcastReceiver.onReceive((intent.getExtras().getInt("action")), intent.getExtras().getString("sender"), intent.getExtras().getString("data"));
                        } catch (Exception e){e.printStackTrace();}
                    }
                }
            }
        };
        LocalBroadcastManager.getInstance(_mActivity).registerReceiver(receiver, new IntentFilter("filter"));
    }

    public void sendBroadCast(int action, Object o, String sender){
        Intent intent = new Intent("filter");
        intent.putExtra("action", action);
        intent.putExtra("data", new Gson().toJson(o));
        intent.putExtra("sender", sender);
        LocalBroadcastManager.getInstance(_mActivity).sendBroadcast(intent);
    }

    public void makeLoadingViewClickable(){
        if(requestLoadingView != null){
            requestLoadingView.setClickable(true);
            requestLoadingView.setFocusable(true);

        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        try{
            hideSoftInput();
        } catch (Exception e){
            e.printStackTrace();
        }
        if(receiver!=null){
            LocalBroadcastManager.getInstance(_mActivity).unregisterReceiver(receiver);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(requestLoadingView != null){
            if(requestLoadingView.isClickable()){
                requestLoadingView.setClickable(false);
            }
            if(requestLoadingView.isFocusable()){
                requestLoadingView.setFocusable(false);
            }
        }
    }
}