package com.example.ivonneortega.the_news_project;

import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


/**
 * Created by ivonneortega on 5/1/17.
 */

public class NewsWire {

//    public static final String URL_NEWS_WIRE = "https://api.nytimes.com/svc/news/v3/content/nyt/";
//    public static final String URL_TOP_STORY =  "https://api.nytimes.com/svc/topstories/v2/home.json";
//    public static final String JSON = ".json";
//    public static final String API_KEY = "6001aaf165c547ae92eed08002677f7f";
//
//    List<String> newsWireList = new ArrayList<>();
//    List<String> topStoriesList = new ArrayList<>();
//
//    public void settingLists() {
//
//        newsWireList.add("World");
//        newsWireList.add("u.s.");
//        newsWireList.add("Business Day");
//        newsWireList.add("technology");
//        newsWireList.add("science");
//        newsWireList.add("Sports");
//        newsWireList.add("Movies");
//        newsWireList.add("fashion & style");
//        newsWireList.add("Food");
//        newsWireList.add("Health");
//
//        topStoriesList.add("World");
//        topStoriesList.add("Politics");
//        topStoriesList.add("Business");
//        topStoriesList.add("Technology");
//        topStoriesList.add("Science");
//        topStoriesList.add("Sports");
//        topStoriesList.add("Movies");
//        topStoriesList.add("Fashion");
//        topStoriesList.add("Food");
//        topStoriesList.add("Health");
//    }
//
//
//
//    private List<String> volleySearchNewsWire(String query)
//    {
//        RequestQueue queue = Volley.newRequestQueue(this);
//        final List<String> list = new ArrayList<>();
//
//        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, URL_NEWS_WIRE + query +JSON+"?"+"api-key"+API_KEY, null,
//                new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        try
//                        {
//                            JSONObject root = response;
//                            JSONArray items = root.getJSONArray("results");
//                            List<Article> articleList = new ArrayList<>();
//                            for(int i=0;i<items.length();i++)
//                            {
//                                JSONObject aux = items.getJSONObject(i);
//                                String url = aux.getString("url");
//                                list.add(url);
//                            }
//
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
//                Log.d(TAG, "onErrorResponse: "+error);
//            }
//        });
//        queue.add(jsonObjectRequest);
//        return list;
//    }
//
//    private List<String> volleySearchTopStories(String query)
//    {
//        RequestQueue queue = Volley.newRequestQueue(this);
//        final List<String> list = new ArrayList<>();
//
//        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, URL_TOP_STORY + query +JSON+"?"+"api-key"+API_KEY, null,
//                new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        try
//                        {
//                            JSONObject root = response;
//                            JSONArray items = root.getJSONArray("results");
//                            List<Article> articleList = new ArrayList<>();
//                            for(int i=0;i<items.length();i++)
//                            {
//                                JSONObject aux = items.getJSONObject(i);
//                                String url = aux.getString("url");
//                                list.add(url);
//                            }
//
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
//                Log.d(TAG, "onErrorResponse: "+error);
//            }
//        });
//        queue.add(jsonObjectRequest);
//        return list;
//    }


}
