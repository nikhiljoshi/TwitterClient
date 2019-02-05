package com.nikhil.twitterclient.views;

import android.support.annotation.StringRes;

import com.nikhil.twitterclient.model.TweetDataItem;

import java.util.List;

public interface TweetView {

    void showTimeline(List<TweetDataItem> tweetDataItems);

    void showNoTweets();

    void showMessage(@StringRes int messageResId);
}
