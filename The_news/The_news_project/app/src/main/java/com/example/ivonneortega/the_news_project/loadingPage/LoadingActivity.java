package com.example.ivonneortega.the_news_project.loadingPage;

import android.content.Context;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class LoadingActivity extends AppCompatActivity {
    public static final String JSON = ".json";
    private static final String TAG = "LoadingActivity";

    private List<String> mNewsWireList = new ArrayList<>();
    private List<String> mTopStoriesList = new ArrayList<>();
    private DatabaseHelper mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        mDb = DatabaseHelper.getInstance(this);

        setupLists();

        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null) {
            for (String topic : mTopStoriesList) {
                AsyncTask<String, Void, Void> topTask = new AsyncTask<String, Void, Void>() {
                    @Override
                    protected Void doInBackground(String... params) {
                        queryTopStories(params[0]);
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        return null;
                    }
                };
                topTask.execute(topic);
            }

            for (String topic : mNewsWireList) {
                queryNewsWire(topic);
            }
        }
    }

    private void setupLists() {
        if (mNewsWireList.isEmpty()) {
            mNewsWireList.add("World");
            mNewsWireList.add("u.s.");
            mNewsWireList.add("Business Day");
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

    private void queryTopStories(String query) {
        RequestQueue queue = Volley.newRequestQueue(this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                NYTApiData.URL_TOP_STORY + query + JSON+"?api-key=" + NYTApiData.API_KEY, null,
                new com.android.volley.Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try
                        {
                            JSONArray results = response.getJSONArray("results");
                            for(int i = 0; i < results.length(); i++)
                            {
                                JSONObject article = results.getJSONObject(i);
                                addArticleToDatabase(article, Article.TRUE);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onErrorResponse: " + error);
            }
        });

        queue.add(jsonObjectRequest);
    }

    private void queryNewsWire(String query) {
        RequestQueue queue = Volley.newRequestQueue(this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                NYTApiData.URL_NEWS_WIRE + query + JSON + "?api-key=" + NYTApiData.API_KEY, null,
                new com.android.volley.Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try
                        {
                            JSONArray results = response.getJSONArray("results");

                            for(int i = 0; i < results.length(); i++)
                            {
                                JSONObject article = results.getJSONObject(i);
                                addArticleToDatabase(article, Article.FALSE);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onErrorResponse: "+error);
            }
        });
        queue.add(jsonObjectRequest);
    }

    public void addArticleToDatabase(JSONObject object, final int fromTopStories) {
        AsyncTask<JSONObject, Void, Void> dbTask = new AsyncTask<JSONObject, Void, Void>() {
            @Override
            protected Void doInBackground(JSONObject... params) {
                JSONObject object = params[0];

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

                return null;
            }
        };
        dbTask.execute(object);
    }
}
