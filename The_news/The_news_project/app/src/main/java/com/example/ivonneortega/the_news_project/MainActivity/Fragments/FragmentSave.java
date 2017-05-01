package com.example.ivonneortega.the_news_project.MainActivity.Fragments;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ivonneortega.the_news_project.Article;
import com.example.ivonneortega.the_news_project.RecyclerViewAdapters.ArticlesVerticalRecyclerAdapter;
import com.example.ivonneortega.the_news_project.R;

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

    private OnFragmentInteractionListener mListener;

    public FragmentSave() {
        // Required empty public constructor
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
        View view = inflater.inflate(R.layout.fragment_fragment_save, container, false);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.save_recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);

        //Creating a list to test recycler view
        List<Article> categoryIndividualItems = new ArrayList<>();
        categoryIndividualItems.add(new Article("image","This is the text for the article. Testing Text. What happens if I add more?","Business","today"));
        categoryIndividualItems.add(new Article("image","Text2","Business","today"));
        categoryIndividualItems.add(new Article("image","Text3","Business","today"));
        categoryIndividualItems.add(new Article("image","Text4","Business","today"));
        categoryIndividualItems.add(new Article("image","Text5","Business","today"));

        ArticlesVerticalRecyclerAdapter adapter = new ArticlesVerticalRecyclerAdapter(categoryIndividualItems,true);
        recyclerView.setAdapter(adapter);

        return view;
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
