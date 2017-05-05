package com.example.ivonneortega.the_news_project.loadingPage;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.ivonneortega.the_news_project.R;
import com.example.ivonneortega.the_news_project.data.Article;
import com.example.ivonneortega.the_news_project.data.NYTApiData;
import com.example.ivonneortega.the_news_project.database.DatabaseHelper;
import com.example.ivonneortega.the_news_project.mainActivity.MainActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Response;

import static com.example.ivonneortega.the_news_project.data.NYTApiData.JSON;

public class LoadingActivity extends AppCompatActivity {
    private static final String TAG = "LoadingActivity";

    private List<String> mNewsWireList = new ArrayList<>();
    private List<String> mTopStoriesList = new ArrayList<>();
    private DatabaseHelper mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        mDb = DatabaseHelper.getInstance(this);

        setupTopicLists();

        load();
    }

    private void setupTopicLists() {
        if (mNewsWireList.isEmpty()) {
            mNewsWireList.add("World");
            mNewsWireList.add("u.s.");
            mNewsWireList.add("Business+Day");
            mNewsWireList.add("technology");
            mNewsWireList.add("science");
            mNewsWireList.add("Sports");
            mNewsWireList.add("Movies");
            mNewsWireList.add("fashion+&+style");
            mNewsWireList.add("Food");
            mNewsWireList.add("Health");
        }

        if (mTopStoriesList.isEmpty()) {
            mTopStoriesList.add("world");
            mTopStoriesList.add("politics");
            mTopStoriesList.add("business");
            mTopStoriesList.add("technology");
            mTopStoriesList.add("science");
            mTopStoriesList.add("sports");
            mTopStoriesList.add("movies");
            mTopStoriesList.add("fashion");
            mTopStoriesList.add("food");
            mTopStoriesList.add("health");
        }
    }

    private void load() {
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null) {
            new AsyncTask<Void, Void, Void>() {
                @Override
                protected Void doInBackground(Void... params) {
                    for (String topic : mTopStoriesList) {
                        queryTopStories(topic);
                    }

                    for (String topic : mNewsWireList) {
                        queryNewsWire(topic);
                    }

                    return null;
                }

                @Override
                protected void onPostExecute(Void aVoid) {
                    super.onPostExecute(aVoid);

                    startActivity(new Intent(LoadingActivity.this, MainActivity.class));
                }
            }.execute();
        } else {
            Toast.makeText(this, "No connectivity", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(LoadingActivity.this, MainActivity.class));
        }
    }

    private void queryTopStories(String query) {
        OkHttpClient topClient = new OkHttpClient();

        String url = NYTApiData.URL_TOP_STORY + query + JSON + "?api-key=" + NYTApiData.API_KEY;

        okhttp3.Request request = new okhttp3.Request.Builder()
                .url(url)
                .build();

        Response response;
        JSONArray articles = null;
        try {
            response = topClient.newCall(request).execute();
            String reply = response.body().string();
            JSONObject jsonReply = new JSONObject(reply);
            if (jsonReply.has("results")) {
                articles = jsonReply.getJSONArray("results");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (articles != null) {
            for (int i = 0; i < articles.length(); i++) {
                try {
                    addArticleToDatabase(articles.getJSONObject(i), Article.TRUE);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void queryNewsWire(String query) {
        OkHttpClient wireClient = new OkHttpClient();

        String url = NYTApiData.URL_NEWS_WIRE + query + JSON + "?api-key=" + NYTApiData.API_KEY;

        okhttp3.Request request = new okhttp3.Request.Builder()
                .url(url)
                .build();

        Response response;
        JSONArray articles = null;
        try {
            response = wireClient.newCall(request).execute();
            String reply = response.body().string();
            JSONObject jsonReply = new JSONObject(reply);
            articles = jsonReply.getJSONArray("results");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (articles != null) {
            for (int i = 0; i < articles.length(); i++) {
                try {
                    addArticleToDatabase(articles.getJSONObject(i), Article.FALSE);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void addArticleToDatabase(JSONObject object, final int fromTopStories) {
        String url = null;
        String title = null;
        String date = null;
        String category = null;
        String image = null;
        boolean hasImage = false;
        try {
            url = object.getString("url");
            title = object.getString("title");
            date = object.getString("published_date");
            category = object.getString("section");
            if (!object.getString("multimedia").equals("")) {
                JSONArray multimedia = object.getJSONArray("multimedia");
                for (int i = 0; i < multimedia.length(); i++) {
                    JSONObject pic = multimedia.getJSONObject(i);
                    if (pic.getString("format").equals("Normal") && pic.getString("type").equals("image")) {
                        image = pic.getString("url");
                        hasImage = true;
                    }
                }
            }
        } catch (JSONException e) {
            hasImage = false;
            e.printStackTrace();
        }
        String source = "New York Times";
        int isSaved = Article.FALSE;

        if (mDb.getArticleByUrl(url) == null && hasImage) {
            mDb.checkSizeAndRemoveOldest();
            Log.d(TAG, "doInBackground: " + title);
            mDb.insertArticleIntoDatabase(image, title, category, date.substring(0, date.indexOf('T')), null, source, isSaved, fromTopStories, url);
        }
    }
}