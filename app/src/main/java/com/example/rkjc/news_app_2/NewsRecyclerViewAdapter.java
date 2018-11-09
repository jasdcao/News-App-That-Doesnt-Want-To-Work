package com.example.rkjc.news_app_2;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;


public class NewsRecyclerViewAdapter extends RecyclerView.Adapter<NewsRecyclerViewAdapter.NewsViewHolder> {
    Context mContext;
    ArrayList<NewsItem> mNewsItem; //articles

    public NewsRecyclerViewAdapter(Context mContext, ArrayList<NewsItem> mNewsItem) {
        this.mContext = mContext;
        this.mNewsItem = mNewsItem;
    }

    public void setNews(ArrayList<NewsItem> items){
        mNewsItem = items;
        notifyDataSetChanged();
    }

    @Override
    public NewsRecyclerViewAdapter.NewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        int newsItemId = R.layout.news_item;
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(newsItemId, parent, false);
        return new NewsViewHolder(view, context);
    }

    @Override
    public void onBindViewHolder(NewsRecyclerViewAdapter.NewsViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return mNewsItem.size();
    }

    public class NewsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public final TextView author, title, url, description, publishedAt;
        Context mContext;

        public NewsViewHolder(View itemView, Context context) {
            super(itemView);

            author = (TextView) itemView.findViewById(R.id.author);
            title = (TextView) itemView.findViewById(R.id.title);
            url = (TextView) itemView.findViewById(R.id.url);
            description = (TextView) itemView.findViewById(R.id.description);
            publishedAt = (TextView) itemView.findViewById(R.id.publishedAt);


            this.mContext = context;
        }

        void bind(final int listIndex) {
            author.setText("Author: " + mNewsItem.get(listIndex).getAuthor());
            title.setText("Title: " + mNewsItem.get(listIndex).getTitle());
            description.setText("Description: " + mNewsItem.get(listIndex).getDescription());
            url.setText("URL: " + mNewsItem.get(listIndex).getUrl());
      //    urlToImage.setText("urlToImage: " + mNewsItem.get(listIndex).getUrlToImage());
            publishedAt.setText("Published Date: " + mNewsItem.get(listIndex).getPublishedAt());



            itemView.setOnClickListener(this);
        }

        public void onClick(View view) {
            String urlString = mNewsItem.get(getAdapterPosition()).getUrl();
            Intent intent = new Intent(mContext, NewsItem.class);
            intent.putExtra("urlString", urlString);
            mContext.startActivity(intent);
        }
    }

}
