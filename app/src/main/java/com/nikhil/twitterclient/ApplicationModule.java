package com.nikhil.twitterclient;

import android.app.Application;

import com.nikhil.twitterclient.network.FetchTweetDataImpl;
import com.twitter.sdk.android.core.TwitterCore;
import com.nikhil.twitterclient.network.FetchTweetData;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;

@Module
class  ApplicationModule {

    private final Application application;

    ApplicationModule(Application application) {
        this.application = application;
    }

    @Provides
    @Singleton
    Application application() {
        return application;
    }

    @Provides
    @Singleton
    FetchTweetData provideTwitterService() {
        return new FetchTweetDataImpl(TwitterCore.getInstance().getSessionManager().getActiveSession());
    }

    @Provides
    @Singleton
    Scheduler provideScheduler() {
        return AndroidSchedulers.mainThread();
    }
}
