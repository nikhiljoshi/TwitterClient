package com.nikhil.twitterclient.model;

import org.parceler.Parcel;

@Parcel
public class UserData {

    String name;
    String screenName;
    String profileImageUrl;

    public UserData() {
    }

    public String getName() {
        return name;
    }

    public String getScreenName() {
        return screenName;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }


    public UserData(String name, String screenName, String profileImageUrl) {
        this.name = name;
        this.screenName = screenName;
        this.profileImageUrl = profileImageUrl;
    }
}
