package com.example.rkjc.news_app_2;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rkjc.news_app_2.NetworkUtils;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;



public class MainActivity extends AppCompatActivity {

    private NewsRecyclerViewAdapter mAdapter;
    private RecyclerView  mRecyclerView;
    private ArrayList<NewsItem> mItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView  = findViewById(R.id.news_recyclerview);

        LinearLayoutManager layoutManager = new LinearLayoutManager(
                this,
                LinearLayoutManager.VERTICAL,
                false);

        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new NewsRecyclerViewAdapter(this,mItems);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void makeNewsSearchQuery() {
        URL newsSearchUrl = NetworkUtils.buildUrl();
        new NewsQueryTask().execute(newsSearchUrl);
    }

    public class NewsQueryTask extends AsyncTask<URL, Void, String> {

        @Override
        protected String doInBackground(URL... params) {
            URL searchUrl = params[0];
            String newsSearchResults = null;
            try {
                newsSearchResults = NetworkUtils.getResponseFromHttpUrl(searchUrl);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return newsSearchResults;
        }

        @Override
        protected void onPostExecute(String newsSearchResults) {
            if (newsSearchResults != null && !newsSearchResults.equals("")) {
                mAdapter.setNews(JsonUtils.parseNews(newsSearchResults));
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemThatWasClickedId = item.getItemId();
        if (itemThatWasClickedId == R.id.get_news) {
            makeNewsSearchQuery();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
