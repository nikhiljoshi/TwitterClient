package com.nikhil.twitterclient.network;

import com.nikhil.twitterclient.model.TweetDataItem;
import com.nikhil.twitterclient.model.UserData;

import java.util.List;

import io.reactivex.Observable;

public interface FetchTweetData {

    Observable<List<TweetDataItem>> getTweetItems();

    Observable<UserData> getUserDetails();

    Observable<Boolean> sendTweet(String tweetText);

    UserData getCurrentUser();

    void setCurrentUser(UserData userData);
}
