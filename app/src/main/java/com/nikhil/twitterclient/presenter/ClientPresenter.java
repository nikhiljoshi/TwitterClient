package com.nikhil.twitterclient.presenter;

import com.nikhil.twitterclient.R;
import com.nikhil.twitterclient.model.TweetDataItem;
import com.nikhil.twitterclient.network.FetchTweetData;
import com.nikhil.twitterclient.views.TweetView;

import java.lang.ref.WeakReference;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Scheduler;

public class ClientPresenter {

    private final FetchTweetData mFetchTweetData;
    private final Scheduler scheduler;
    private WeakReference<TweetView> view;
    private List<TweetDataItem> tweetDataItems;

    @Inject
    public ClientPresenter(FetchTweetData fetchTweetData, Scheduler scheduler) {
        this.mFetchTweetData = fetchTweetData;
        this.scheduler = scheduler;
    }

    public void initialise(TweetView view) {
        this.view = new WeakReference<>(view);
        mFetchTweetData.getUserDetails()
                .observeOn(scheduler)
                .subscribe(user -> {
                    mFetchTweetData.setCurrentUser(user);
                    refreshTweets();
                });
    }

    public void refreshTweets() {
        mFetchTweetData.getTweetItems()
                .observeOn(scheduler)
                .subscribe(timelineItems -> {
                    this.tweetDataItems = timelineItems;
                    TweetView view = this.view.get();
                    if (view != null) {
                        if (timelineItems.size() > 0) {
                            view.showTimeline(timelineItems);
                        } else {
                            view.showNoTweets();
                        }
                    }
                });
    }

    public void tweet(String tweetText) {
        tweetDataItems.add(0, new TweetDataItem("0s", tweetText, mFetchTweetData.getCurrentUser()));
        view.get().showTimeline(tweetDataItems);
        mFetchTweetData.sendTweet(tweetText)
                .observeOn(scheduler)
                .subscribe(
                        x -> {
                            TweetView view = this.view.get();
                            if (view != null) {
                                view.showMessage(R.string.tweet_success);
                            }
                        },
                        e -> {
                            TweetView view = this.view.get();
                            if (view != null) {
                                view.showMessage(R.string.failed_msg);
                            }
                        });
    }
}
