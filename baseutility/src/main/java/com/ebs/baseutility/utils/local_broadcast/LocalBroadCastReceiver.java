package com.ebs.baseutility.utils.local_broadcast;

public interface LocalBroadCastReceiver {
    void onReceive(int action, String sender, String json);
}
