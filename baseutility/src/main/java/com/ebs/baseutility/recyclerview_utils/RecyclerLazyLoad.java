package com.ebs.baseutility.recyclerview_utils;

import android.content.Context;
import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

import com.ebs.baseutility.recyclerview_utils.adapters.BaseAdapterRecycler;
import com.ebs.baseutility.recyclerview_utils.adapters.HeaderAndFooterRecyclerViewAdapter;
import com.ebs.baseutility.recyclerview_utils.listeners.EndlessRecyclerOnScrollListener;
import com.ebs.baseutility.recyclerview_utils.listeners.LoadResponseListener;
import com.ebs.baseutility.recyclerview_utils.utils.RecyclerViewUtils;
import com.ebs.baseutility.recyclerview_utils.views.LoadingFooter;

import java.util.List;

public class RecyclerLazyLoad {
    private int STANDARD_FEEDLIMIT = 20;
    private int page = 1;
    private int limit = STANDARD_FEEDLIMIT;
    private RecyclerView recyclerView;
    private LoadResponseListener loadResponseListener;
    private boolean isMoreDataAvailable = true;
    private Context context;
    private RecyclerView.Adapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private View emptyView;
    private @LayoutRes
    int resourceLayout ;
    private @IdRes
    int ResourceIdRoot ;
    private @IdRes
    int ResourceIdProgress;
    private boolean endlessScroll = true;

    public void setAdapter(Context context, RecyclerView recyclerView, RecyclerView.Adapter adapter) {
        this.adapter = adapter;
        this.context = context;
        this.recyclerView = recyclerView;

        HeaderAndFooterRecyclerViewAdapter headerAndFooterRecyclerViewAdapter = new HeaderAndFooterRecyclerViewAdapter(this.adapter);
        this.recyclerView.setAdapter(headerAndFooterRecyclerViewAdapter);
        setScroll();
    }

    public void setSwipeRefreshLayout(SwipeRefreshLayout swipeRefreshLayout) {
        this.swipeRefreshLayout = swipeRefreshLayout;
        setRefresh();
    }

    public void setLoadResponseListener(LoadResponseListener loadResponseListener) {
        this.loadResponseListener = loadResponseListener;
    }

    public void disableRecyclerViewScroll(final boolean disable) {
        if(recyclerView != null) {
            recyclerView.setLayoutFrozen(disable);
        }
    }

    public void setEmptyView(View emptyView) {
        this.emptyView = emptyView;
    }

    public void setResourceLoading(@LayoutRes final int resourceLayout, @IdRes final int ResourceIdRoot, @IdRes final int ResourceIdProgress){
        this.resourceLayout = resourceLayout;
        this.ResourceIdRoot = ResourceIdRoot;
        this.ResourceIdProgress = ResourceIdProgress;
    }

    private void setScroll() {
        recyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener() {
            @Override
            public void onLoadNextPage(View view) {
                super.onLoadNextPage(view);
                System.out.println("LOad next page-------------------------"+RecyclerViewUtils.getFooterViewState(recyclerView));
                if(endlessScroll) {
                    if (!isMoreDataAvailable) {
                        RecyclerViewUtils.setFooterViewState(context, recyclerView, LoadingFooter.State.TheEnd, resourceLayout, ResourceIdRoot, ResourceIdProgress);
                    }

                    LoadingFooter.State state = RecyclerViewUtils.getFooterViewState(recyclerView);
                    if (state == LoadingFooter.State.Loading) {
                        return;
                    }
                    if (isMoreDataAvailable) {
                        RecyclerViewUtils.setFooterViewState(context, recyclerView, LoadingFooter.State.Loading, resourceLayout, ResourceIdRoot, ResourceIdProgress);
                        if (loadResponseListener != null) loadResponseListener.onLoadMore();
                    } else {
                        RecyclerViewUtils.setFooterViewState(context, recyclerView, LoadingFooter.State.TheEnd, resourceLayout, ResourceIdRoot, ResourceIdProgress);
                    }
                }
            }
        });
    }

    private void setRefresh(){
        if(swipeRefreshLayout != null) {
            swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    if(endlessScroll) {
                        RecyclerViewUtils.setFooterViewState(context, recyclerView, LoadingFooter.State.TheEnd, resourceLayout,ResourceIdRoot,ResourceIdProgress);
                    }
                    try {
                        ((BaseAdapterRecycler) adapter).setEmptyView(null);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    reset();
                    loadResponseListener.onRefresh();
                }
            });
        }
    }

    @SuppressWarnings("unchecked")
    private void addItems(List items, List<?> tempItems, int countItems) {
        if(emptyView != null){
            if(adapter instanceof BaseAdapterRecycler){
                ((BaseAdapterRecycler) adapter).setEmptyView(emptyView);
            }
        }
        int positionStart = items.size() + 1;
        items.addAll(tempItems);
        adapter.notifyItemRangeInserted(positionStart, items.size());
        page ++;

        if(!isMoreDataAvailable){
            if(endlessScroll) {
                RecyclerViewUtils.setFooterViewState(context, recyclerView, LoadingFooter.State.TheEnd, resourceLayout, ResourceIdRoot, ResourceIdProgress);
            }
        } else {
            if (countItems < limit) {
                isMoreDataAvailable = false;
                if(endlessScroll) {
                    RecyclerViewUtils.setFooterViewState(context, recyclerView, LoadingFooter.State.TheEnd, resourceLayout,ResourceIdRoot,ResourceIdProgress);
                }
            } else {
                if(endlessScroll) {
                    RecyclerViewUtils.setFooterViewState(context, recyclerView, LoadingFooter.State.Normal, resourceLayout,ResourceIdRoot,ResourceIdProgress);
                }
            }
        }
    }
    public final void addItems(List items, List<?> tempItems, int countItems, boolean clearItems) {
        if(clearItems){
            ((BaseAdapterRecycler)adapter).clear();
        }
        addItems(items, tempItems, countItems);
    }

    public void reset(){
        if(endlessScroll) {
            RecyclerViewUtils.setFooterViewState(context, recyclerView, LoadingFooter.State.TheEnd, resourceLayout,ResourceIdRoot,ResourceIdProgress);
        }
        ((BaseAdapterRecycler) adapter).setEmptyView(null);
        ((BaseAdapterRecycler)adapter).clear();
        setMoreDataAvailable();
        setPage(1);
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getSTANDARD_FEEDLIMIT() {
        return STANDARD_FEEDLIMIT;
    }

    private void setMoreDataAvailable() {
        isMoreDataAvailable = true;
    }
    public void setNoMoreDataAvailable() {
        isMoreDataAvailable = false;
    }

    public boolean isMoreDataAvailable() {
        return isMoreDataAvailable;
    }
}
