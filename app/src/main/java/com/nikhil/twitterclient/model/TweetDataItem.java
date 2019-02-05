package com.nikhil.twitterclient.model;

import org.parceler.Parcel;

@Parcel
public class TweetDataItem {

    String createdAt;
    String text;
    UserData userData;

    public TweetDataItem() {
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getText() {
        return text;
    }

    public UserData getUserData() {
        return userData;
    }


    public TweetDataItem(String createdAt, String text, UserData userData) {
        this.createdAt = createdAt;
        this.text = text;
        this.userData = userData;
    }
}
