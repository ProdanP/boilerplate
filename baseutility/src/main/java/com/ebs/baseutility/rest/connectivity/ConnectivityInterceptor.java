package com.ebs.baseutility.rest.connectivity;


import android.content.Context;
import androidx.annotation.NonNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

public class ConnectivityInterceptor implements Interceptor {

    private Context mContext;

    public ConnectivityInterceptor(Context context) {
        mContext = context;
    }

    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        if (!NetworkUtil.isOnline(mContext)) {
            throw new NoConnectivityException();
        }

        okhttp3.Request.Builder builder = chain.request().newBuilder();
        return chain.proceed(builder.build());
    }

}

