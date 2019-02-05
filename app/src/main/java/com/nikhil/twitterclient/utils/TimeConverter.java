package com.nikhil.twitterclient.utils;

import com.nikhil.twitterclient.model.TweetDataItem;
import com.nikhil.twitterclient.model.UserData;
import com.twitter.sdk.android.core.models.Tweet;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Hours;
import org.joda.time.Minutes;
import org.joda.time.Seconds;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.List;

public final class TimeConverter {

    public static final String DATE_TIME_FORMAT = "EEE MMM dd HH:mm:ss Z yyyy";

    private TimeConverter() {}

    public static List<TweetDataItem> fromTweets(List<Tweet> tweets, DateTime now) {
        List<TweetDataItem> tweetDataItems = new ArrayList<>();
        for (Tweet t : tweets) {
            UserData u = new UserData(t.user.name, t.user.screenName, t.user.profileImageUrl);
            tweetDataItems.add(new TweetDataItem(TimeConverter.dateToAge(t.createdAt, now), t.text, u));
        }
        return tweetDataItems;
    }

    private static String dateToAge(String createdAt, DateTime now) {
        if (createdAt == null) {
            return "";
        }

        DateTimeFormatter dtf = DateTimeFormat.forPattern(DATE_TIME_FORMAT);
        try {
            DateTime created = dtf.parseDateTime(createdAt);

            if (Seconds.secondsBetween(created, now).getSeconds() < 60) {
                return Seconds.secondsBetween(created, now).getSeconds() + "s";
            } else if (Minutes.minutesBetween(created, now).getMinutes() < 60) {
                return Minutes.minutesBetween(created, now).getMinutes() + "m";
            } else if (Hours.hoursBetween(created, now).getHours() < 24) {
                return Hours.hoursBetween(created, now).getHours() + "h";
            } else {
                return Days.daysBetween(created, now).getDays() + "d";
            }
        } catch (IllegalArgumentException e) {
            return "";
        }
    }
}
