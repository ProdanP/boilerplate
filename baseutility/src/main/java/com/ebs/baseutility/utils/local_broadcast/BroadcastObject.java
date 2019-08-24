package com.ebs.baseutility.utils.local_broadcast;

public class BroadcastObject {
    private int action;
    private String json;
    private String sender;

    public BroadcastObject(int action, String json, String sender) {
        this.json = json;
        this.sender = sender;
        this.action = action;
    }

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public int getAction() {
        return action;
    }
}
