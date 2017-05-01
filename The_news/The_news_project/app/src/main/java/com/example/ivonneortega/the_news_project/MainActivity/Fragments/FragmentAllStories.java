package com.example.ivonneortega.the_news_project.MainActivity.Fragments;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ivonneortega.the_news_project.Category;
import com.example.ivonneortega.the_news_project.Article;
import com.example.ivonneortega.the_news_project.RecyclerViewAdapters.CategoriesRecyclerAdapter;
import com.example.ivonneortega.the_news_project.R;

import java.util.ArrayList;
import java.util.List;


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


        //Setting Recycler View
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView_all_stories);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);

        //Creating a list to test recycler view
        List<Article> categoryIndividualItems = new ArrayList<>();
        categoryIndividualItems.add(new Article(1,"image","This is the text for the article. Testing Text. What happens if I add more?","Business","today","this is the body","source",0));
        categoryIndividualItems.add(new Article(1,"image","This is the text for the article. Testing Text. What happens if I add more?","Business","today","this is the body","source",0));
        categoryIndividualItems.add(new Article(1,"image","This is the text for the article. Testing Text. What happens if I add more?","Business","today","this is the body","source",0));
        categoryIndividualItems.add(new Article(1,"image","This is the text for the article. Testing Text. What happens if I add more?","Business","today","this is the body","source",0));
        categoryIndividualItems.add(new Article(1,"image","This is the text for the article. Testing Text. What happens if I add more?","Business","today","this is the body","source",0));


        //Secondary Test
        List<Category> allStories = new ArrayList<>();
        allStories.add(new Category("Category 1",categoryIndividualItems));
        allStories.add(new Category("Category 2",categoryIndividualItems));
        allStories.add(new Category("Category 3",categoryIndividualItems));

        //Setting Adapter With lists
        CategoriesRecyclerAdapter adapter = new CategoriesRecyclerAdapter(allStories);
        recyclerView.setAdapter(adapter);

        return view;

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
