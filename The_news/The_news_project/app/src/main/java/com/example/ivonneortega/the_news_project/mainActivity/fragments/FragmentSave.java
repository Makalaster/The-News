package com.example.ivonneortega.the_news_project.mainActivity.fragments;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ivonneortega.the_news_project.R;
import com.example.ivonneortega.the_news_project.recyclerViewAdapters.ArticlesVerticalRecyclerAdapter;
import com.example.ivonneortega.the_news_project.swipe.SimpleItemTouchHelperCallback;
import com.example.ivonneortega.the_news_project.data.Article;
import com.example.ivonneortega.the_news_project.database.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentSave.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentSave#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentSave extends Fragment {

    private ArticlesVerticalRecyclerAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private DatabaseHelper db;

    private OnFragmentInteractionListener mListener;

    public FragmentSave() {
        // Required empty public constructor
    }

    @Override
    public void onResume() {
        super.onResume();
        //Creating a list to test recycler view
        List<Article> categoryIndividualItems = new ArrayList<>();
        mAdapter = new ArticlesVerticalRecyclerAdapter(categoryIndividualItems,true);
        mRecyclerView.setAdapter(mAdapter);

        db = DatabaseHelper.getInstance(mRecyclerView.getContext());

        ItemTouchHelper.Callback callback =
                new SimpleItemTouchHelperCallback(mAdapter);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(mRecyclerView);

        getSaveArticles();
    }

    private void getSaveArticles()
    {
        AsyncTask<Void,Void,List<Article>> asyncTask = new AsyncTask<Void, Void, List<Article>>() {
            @Override
            protected List<Article> doInBackground(Void... params) {
                List<Article> list = db.getSavedArticles();
                return list;
            }

            @Override
            protected void onPostExecute(List<Article> list) {
                super.onPostExecute(list);
                mAdapter.swapData(list);
            }
        }.execute();
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment FragmentSave.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentSave newInstance() {
        FragmentSave fragment = new FragmentSave();
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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fragment_save, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.save_recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext(),LinearLayoutManager.VERTICAL,false);
        mRecyclerView.setLayoutManager(linearLayoutManager);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }
//
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
