package com.example.ivonneortega.the_news_project.MainActivity.Fragments;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ivonneortega.the_news_project.data.Article;
import com.example.ivonneortega.the_news_project.DatabaseHelper;
import com.example.ivonneortega.the_news_project.RecyclerViewAdapters.ArticlesVerticalRecyclerAdapter;
import com.example.ivonneortega.the_news_project.R;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentTopStories.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentTopStories#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentTopStories extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private SwipeRefreshLayout mTopRefresh;

    private OnFragmentInteractionListener mListener;

    public FragmentTopStories() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static FragmentTopStories newInstance() {
        FragmentTopStories fragment = new FragmentTopStories();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_fragment_top_stories, container, false);
        //Setting Recycler View
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.top_stories_recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);

        //Creating a list to test recycler view
        List<Article> categoryIndividualItems = new ArrayList<>();
//        categoryIndividualItems.add(new Article(1,"image","This is the text for the article. Testing Text. What happens if I add more?","Business","today","this is the body","source",0,0,"url"));
//        categoryIndividualItems.add(new Article(1,"image","This is the text for the article. Testing Text. What happens if I add more?","Business","today","this is the body","source",0,0,"url"));
//        categoryIndividualItems.add(new Article(1,"image","This is the text for the article. Testing Text. What happens if I add more?","Business","today","this is the body","source",0,0,"url"));
//        categoryIndividualItems.add(new Article(1,"image","This is the text for the article. Testing Text. What happens if I add more?","Business","today","this is the body","source",0,0,"url"));
//        categoryIndividualItems.add(new Article(1,"image","This is the text for the article. Testing Text. What happens if I add more?","Business","today","this is the body","source",0,0,"url"));
        categoryIndividualItems = DatabaseHelper.getInstance(view.getContext()).getTopStoryArticles();

        ArticlesVerticalRecyclerAdapter adapter = new ArticlesVerticalRecyclerAdapter(categoryIndividualItems,false);
        recyclerView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mTopRefresh = (SwipeRefreshLayout) view.findViewById(R.id.top_swipe_refresh);
        mTopRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshTopStories();
            }
        });
    }

    private void refreshTopStories() {

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
