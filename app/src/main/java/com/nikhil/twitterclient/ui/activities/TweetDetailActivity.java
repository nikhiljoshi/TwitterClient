package com.nikhil.twitterclient.ui.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.StringRes;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.InputFilter;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.nikhil.twitterclient.App;
import com.nikhil.twitterclient.R;
import com.nikhil.twitterclient.model.TweetDataItem;
import com.nikhil.twitterclient.presenter.ClientPresenter;
import com.nikhil.twitterclient.ui.adapters.TweetAdapter;
import com.nikhil.twitterclient.views.TweetView;

import org.parceler.Parcels;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TweetDetailActivity extends AppCompatActivity  {

    @BindView(R.id.usericon)
    ImageView imageView;
    @BindView(R.id.name)
    TextView nameView;
    @BindView(R.id.handle)
    TextView handleView;
    @BindView(R.id.tweet_time)
    TextView timeView;
    @BindView(R.id.tweet_text)
    TextView textTweetView;

    @BindView(R.id.row)
    LinearLayout rowLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
        TweetDataItem tweetDataItem = (TweetDataItem) Parcels.unwrap(getIntent().getParcelableExtra("tweetData"));

        if(tweetDataItem!=null && tweetDataItem.getText()!=null){
            RequestOptions options = new RequestOptions();
            options.centerCrop();

            Glide.with(App.component().application())
                    .load(tweetDataItem.getUserData().getProfileImageUrl())
                    .apply(options)
                    .into(imageView);


            nameView.setText(tweetDataItem.getUserData().getName());
            handleView.setText(String.format("@%s", tweetDataItem.getUserData().getScreenName()));
            timeView.setText(tweetDataItem.getCreatedAt());
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                textTweetView.setText(Html.fromHtml(tweetDataItem.getText(), Html.FROM_HTML_MODE_LEGACY));
            } else {
                textTweetView.setText(Html.fromHtml(tweetDataItem.getText()));
            }
        }

    }
}
