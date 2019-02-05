package com.nikhil.twitterclient.ui.activities;

import com.nikhil.twitterclient.App;

import dagger.Component;

@PerActivity
@Component(dependencies = App.BaseApplicationComponent.class)
interface ActivityComponent {

    void inject(MainActivity activity);
}
