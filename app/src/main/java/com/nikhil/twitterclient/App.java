package com.nikhil.twitterclient;

import android.app.Application;
import android.support.annotation.VisibleForTesting;

import com.nikhil.twitterclient.network.FetchTweetData;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterConfig;

import net.danlew.android.joda.JodaTimeAndroid;

import javax.inject.Singleton;

import dagger.Component;
import io.reactivex.Scheduler;

public class App extends Application {

    public static final String TWITTER_KEY = "Hgt8d2nLhZdQUaIvsyxYuPhb5";
    public static final String TWITTER_SECRET = "MeliZLYavlMiyhYTs97zyTCUOzznnog9cIrgfRHfc0TbN9PkP4";

    private static BaseApplicationComponent applicationComponent;

    public static BaseApplicationComponent component() {
        return applicationComponent;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        JodaTimeAndroid.init(this);

        Twitter.initialize(new TwitterConfig.Builder(this).twitterAuthConfig(new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET)).build());

        applicationComponent = DaggerApp_ApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }

    @VisibleForTesting
    public static void overrideComponent(BaseApplicationComponent component) {
        applicationComponent = component;
    }

    @Singleton
    @Component(modules = {ApplicationModule.class})
    interface ApplicationComponent extends BaseApplicationComponent {
    }

    public interface BaseApplicationComponent {

        Application application();

        FetchTweetData twitterService();

        Scheduler scheduler();
    }
}
