package com.nikhil.twitterclient.network;

import android.util.Log;

import com.nikhil.twitterclient.model.UserData;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterApiClient;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.core.models.User;
import com.nikhil.twitterclient.model.TweetDataItem;
import com.nikhil.twitterclient.utils.TimeConverter;

import org.joda.time.DateTime;

import java.util.List;

import javax.inject.Singleton;

import io.reactivex.Observable;

@Singleton
public class FetchTweetDataImpl extends TwitterApiClient implements FetchTweetData {

    private static final String TAG = FetchTweetDataImpl.class.getSimpleName();

    private UserData currentUserData;

    public FetchTweetDataImpl(TwitterSession session) {
        super(session);
    }

    public Observable<List<TweetDataItem>> getTweetItems() {
        return Observable.create(subscriber -> {
            Callback<List<Tweet>> callback = new Callback<List<Tweet>>() {
                @Override
                public void success(Result<List<Tweet>> result) {
                    Log.i(TAG, "Got the tweets, buddy!");
                    subscriber.onNext(TimeConverter.fromTweets(result.data, DateTime.now()));
                }

                @Override
                public void failure(TwitterException e) {
                    Log.e(TAG, e.getMessage(), e);
                    subscriber.onError(e);
                }
            };

            getStatusesService().homeTimeline(null, null, null, null, null, null, null).enqueue(callback);
        });
    }

    public Observable<UserData> getUserDetails() {
        return Observable.create(subscriber -> {
            Callback<User> callback = new Callback<User>() {
                @Override
                public void success(Result<User> result) {
                    Log.i(TAG, "Success!");
                    subscriber.onNext(new UserData(result.data.name, result.data.screenName, result.data.profileImageUrl));
                }

                @Override
                public void failure(TwitterException e) {
                    Log.e(TAG, e.getMessage(), e);
                    subscriber.onError(e);
                }
            };

            getService(FetchUserDetails.class).show(
                    TwitterCore.getInstance().getSessionManager().getActiveSession().getUserId()).enqueue(callback);
        });
    }

    public Observable<Boolean> sendTweet(String tweetText) {
        return Observable.create(subscriber -> {
            Callback<Tweet> callback = new Callback<Tweet>() {
                @Override
                public void success(Result<Tweet> result) {
                    Log.i(TAG, "tweeted");
                    subscriber.onNext(true);
                }

                @Override
                public void failure(TwitterException e) {
                    Log.e(TAG, e.getMessage(), e);
                    subscriber.onError(e);
                }
            };

            getStatusesService().update(tweetText, null, null, null, null, null, null, null, null).enqueue(callback);
        });
    }

    @Override
    public UserData getCurrentUser() {
        return null;
    }

    @Override
    public void setCurrentUser(UserData userData) {

    }
}
