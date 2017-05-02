package com.example.ivonneortega.the_news_project.MainActivity;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.ivonneortega.the_news_project.DatabaseHelper;
import com.example.ivonneortega.the_news_project.data.Doc;
import com.example.ivonneortega.the_news_project.data.Headline;
import com.example.ivonneortega.the_news_project.data.NYTApiData;
import com.example.ivonneortega.the_news_project.data.NYTSearchQuery;
import com.example.ivonneortega.the_news_project.data.SearchResponse;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ArticleRefreshService extends JobService {
    private Call<SearchResponse> mCall;
    private List<String> mNewsWireList = new ArrayList<>();
    private List<String> mTopStoriesList = new ArrayList<>();
    private static final String TAG = "ArticleRefreshService";
    public static final String JSON = ".json";

    @Override
    public void onCreate() {
        super.onCreate();

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

    @Override
    public boolean onStartJob(JobParameters params) {
        Log.d(TAG, "onStartJob: REFRESH JOB STARTED");

        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null) {

            for (String topic : mNewsWireList) {
                queryNewsWire(topic);
            }

            for (String topic : mTopStoriesList) {
                queryTopStories(topic);
            }

            jobFinished(params, true);
        }

        return true;
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
                            JSONArray items = response.getJSONArray("results");

                            for(int i=0;i<items.length();i++)
                            {
                                JSONObject aux = items.getJSONObject(i);
                                String url = aux.getString("url");
                                Log.d(TAG, "onResponse: newsWire url - " + url);
                                //searchArticlesRetrofit(url, 0);
                                searchArticles(url, 0);
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

    private void queryTopStories(String query) {
        RequestQueue queue = Volley.newRequestQueue(this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                NYTApiData.URL_TOP_STORY + query + JSON+"?api-key=" + NYTApiData.API_KEY, null,
                new com.android.volley.Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try
                        {
                            JSONArray items = response.getJSONArray("results");
                            for(int i=0;i<items.length();i++)
                            {
                                JSONObject aux = items.getJSONObject(i);
                                String url = aux.getString("url");
                                //searchArticlesRetrofit(url, 1);
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

    public void searchArticles(String url, final int fromTopStories) {
        RequestQueue queue = Volley.newRequestQueue(this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                NYTApiData.URL_SEARCH + "?api-key=" + NYTApiData.API_KEY + "&fq=web_url(\"" + url + "\")", null,
                new com.android.volley.Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Gson gson = new Gson();
                        SearchResponse searchResponse = gson.fromJson(response.toString(), SearchResponse.class);
                        addArticleToDatabase(searchResponse, fromTopStories);
                    }
                }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        queue.add(jsonObjectRequest);
    }

    public void addArticleToDatabase(SearchResponse searchResponse, int fromTopStories) {
        DatabaseHelper db = DatabaseHelper.getInstance(this);

        com.example.ivonneortega.the_news_project.data.Response response = searchResponse.getResponse();
        Doc doc = response.getDocs().get(0);
        String url = doc.getWebUrl();
        Headline headline = doc.getHeadline();
        String mainHeadline = headline.getMain();
        String date = doc.getPubDate();
        String category = doc.getSectionName();
        String body = doc.getLeadParagraph();
        String source = "New York Times";
        int isSaved = 0;

        Log.d(TAG, "addArticleToDatabase: " + url);
        db.insertArticleIntoDatabase(null, mainHeadline, category, date, body, source, isSaved, fromTopStories, url);
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        mCall.cancel();

        Log.d(TAG, "onStopJob: JOB STOPPED");
        return false;
    }
}
