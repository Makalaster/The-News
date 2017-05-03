package com.example.ivonneortega.the_news_project.MainActivity.Fragments;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ivonneortega.the_news_project.MainActivity.ArticleRefreshService;
import com.example.ivonneortega.the_news_project.data.Category;
import com.example.ivonneortega.the_news_project.data.Article;
import com.example.ivonneortega.the_news_project.DatabaseHelper;
import com.example.ivonneortega.the_news_project.RecyclerViewAdapters.CategoriesRecyclerAdapter;
import com.example.ivonneortega.the_news_project.R;
import com.example.ivonneortega.the_news_project.data.NYTApiData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentAllStories.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentAllStories#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentAllStories extends Fragment {
    // TODO: Rename parameter arguments, choose names that match

    private OnFragmentInteractionListener mListener;
    private SwipeRefreshLayout mAllRefresh;
    private AsyncTask<String, Void, Boolean> mTask;
    private static final String TAG = "FragmentAllStories";
    private CategoriesRecyclerAdapter mAdapter;
    private List<String> mSources;

    public FragmentAllStories() {
        // Required empty public constructor
    }

    public static FragmentAllStories newInstance() {
        FragmentAllStories fragment = new FragmentAllStories();
        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_fragment_all_stories,container,false);
        setSources();

        //Setting Recycler View
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView_all_stories);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);

//        //Creating a list to test recycler view
        List<Article> categoryIndividualItems = new ArrayList<>();
//        categoryIndividualItems.add(new Article(1,"image","This is the text for the article. Testing Text. What happens if I add more?","Business","today","this is the body","source",0,0,"url"));
//        categoryIndividualItems.add(new Article(1,"image","This is the text for the article. Testing Text. What happens if I add more?","Business","today","this is the body","source",0,0,"url"));
//        categoryIndividualItems.add(new Article(1,"image","This is the text for the article. Testing Text. What happens if I add more?","Business","today","this is the body","source",0,0,"url"));
//        categoryIndividualItems.add(new Article(1,"image","This is the text for the article. Testing Text. What happens if I add more?","Business","today","this is the body","source",0,0,"url"));
//        categoryIndividualItems.add(new Article(1,"image","This is the text for the article. Testing Text. What happens if I add more?","Business","today","this is the body","source",0,0,"url"));
//
//
//        //Secondary Test
        List<Category> allStories = new ArrayList<>();
//        allStories.add(new Category("Category 1",categoryIndividualItems));
//        allStories.add(new Category("Category 2",categoryIndividualItems));
//        allStories.add(new Category("Category 3",categoryIndividualItems));

        DatabaseHelper db = DatabaseHelper.getInstance(view.getContext());
        categoryIndividualItems = db.getArticlesByCategory("Business");
        allStories.add(new Category("Business",categoryIndividualItems));
        categoryIndividualItems = db.getArticlesByCategory("Tech");
        allStories.add(new Category("Tech",categoryIndividualItems));
        categoryIndividualItems = db.getArticlesByCategory("World");
        allStories.add(new Category("World",categoryIndividualItems));
        categoryIndividualItems = db.getArticlesByCategory("Health");
        allStories.add(new Category("Health", categoryIndividualItems));
        categoryIndividualItems = db.getArticlesByCategory("u.s.");
        allStories.add(new Category("National", categoryIndividualItems));
        categoryIndividualItems = db.getArticlesByCategory("science");
        allStories.add(new Category("Science", categoryIndividualItems));
        categoryIndividualItems = db.getArticlesByCategory("sports");
        allStories.add(new Category("Sports", categoryIndividualItems));
        categoryIndividualItems = db.getArticlesByCategory("movies");
        allStories.add(new Category("Movies", categoryIndividualItems));
        categoryIndividualItems = db.getArticlesByCategory("fashion");
        allStories.add(new Category("Fashion", categoryIndividualItems));
        categoryIndividualItems = db.getArticlesByCategory("food");
        allStories.add(new Category("Food", categoryIndividualItems));

        //Setting Adapter With lists
        mAdapter = new CategoriesRecyclerAdapter(allStories);
        recyclerView.setAdapter(mAdapter);

        return view;
    }

    public void setSources()
    {
        mSources = new ArrayList<>();
        mSources.add("associated-press");
        mSources.add("bbc-news");
        mSources.add("business-insider");
        mSources.add("buzzfeed");
        mSources.add("cnn");
        mSources.add("espn");
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mAllRefresh = (SwipeRefreshLayout) view.findViewById(R.id.all_swipe_refresh);
        mAllRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshAllStories();
            }
        });
    }

    private void refreshAllStories() {
        mTask = new AsyncTask<String, Void, Boolean>() {
            @Override
            protected Boolean doInBackground(String... params) {
                boolean updated = false;
                long sum = 0;

                for (String topic : params) {
                    JSONArray articles = getArticles(topic);
                    sum += addArticlesToDatabase(articles);
                }

                if (sum > 0) {
                    updated = true;
                }

                return updated;
            }

            @Override
            protected void onPostExecute(Boolean dbChanged) {
                super.onPostExecute(dbChanged);
                mAllRefresh.setRefreshing(false);
                if (dbChanged) {
                    mAdapter.notifyDataSetChanged();
                }
            }
        };
        mTask.execute("World", "u.s.", "Business Day", "technology", "science", "Sports", "Movies", "fashion+&+style", "Food", "Health");
    }

    private JSONArray getArticles(String topic) {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(NYTApiData.URL_NEWS_WIRE + topic + ArticleRefreshService.JSON + "?api-key=" + NYTApiData.API_KEY)
                .build();

        JSONArray articles = null;

        try {
            Response response = client.newCall(request).execute();
            JSONObject jsonReply = new JSONObject(response.body().string());
            articles = jsonReply.getJSONArray("results");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return articles;
    }

    private long addArticlesToDatabase(JSONArray articles) {
        DatabaseHelper db = DatabaseHelper.getInstance(getContext());
        long added = 0;

        for (int i = 0; i < articles.length(); i++) {
            String url = null;
            String title = null;
            String date = null;
            String category = null;
            String image = null;
            boolean hasImage = false;
            try {
                JSONObject article = articles.getJSONObject(i);
                url = article.getString("url");
                title = article.getString("title");
                date = article.getString("published_date");
                category = article.getString("section");
                if (!article.getString("multimedia").equals("")) {
                    JSONArray multimedia = article.getJSONArray("multimedia");
                    for (int j = 0; j < multimedia.length(); j++) {
                        JSONObject pic = multimedia.getJSONObject(j);
                        if (pic.getString("format").equals("Normal") && pic.getString("type").equals("image")) {
                            image = pic.getString("url");
                            hasImage = true;
                        }
                    }
                }
                String source = "New York Times";
                int isSaved = Article.FALSE;

                if (db.getArticleByUrl(url) == null && hasImage) {
                    db.checkSizeAndRemoveOldest();
                    Log.d(TAG, "doInBackground: " + title);
                    added += db.insertArticleIntoDatabase(image, title, category, date.substring(0, date.indexOf('T')), null, source, isSaved, Article.FALSE, url);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return added;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
//    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
