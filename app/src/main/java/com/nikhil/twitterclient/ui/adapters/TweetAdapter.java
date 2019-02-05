package com.nikhil.twitterclient.ui.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.nikhil.twitterclient.App;
import com.nikhil.twitterclient.R;
import com.nikhil.twitterclient.model.TweetDataItem;
import com.nikhil.twitterclient.ui.activities.TweetDetailActivity;

import org.parceler.Parcels;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TweetAdapter extends RecyclerView.Adapter<TweetAdapter.ViewHolder> {

    private final Context mContext;
    private List<TweetDataItem> tweetDataItems;

    public void setItems(List<TweetDataItem> tweetDataItems) {
        this.tweetDataItems = tweetDataItems;
        notifyDataSetChanged();
    }

    public TweetAdapter(Context context) {
        mContext = context;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.itemcards, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        TweetDataItem tweetDataItem = tweetDataItems.get(i);
        Glide.with(App.component().application())
                .load(tweetDataItem.getUserData().getProfileImageUrl())
                .into(viewHolder.imageView);

        viewHolder.nameView.setText(tweetDataItem.getUserData().getName());
        viewHolder.handleView.setText(String.format("@%s", tweetDataItem.getUserData().getScreenName()));
        viewHolder.timeView.setText(tweetDataItem.getCreatedAt());
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            viewHolder.textTweetView.setText(Html.fromHtml(tweetDataItem.getText(), Html.FROM_HTML_MODE_LEGACY));
        } else {
            viewHolder.textTweetView.setText(Html.fromHtml(tweetDataItem.getText()));
        }

        viewHolder.rowLayout.setOnClickListener(v -> {
            Intent intent = new Intent(mContext,TweetDetailActivity.class);
            intent.putExtra("tweetData", Parcels.wrap(tweetDataItem));
            mContext.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return tweetDataItems != null ? tweetDataItems.size() : 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

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

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
