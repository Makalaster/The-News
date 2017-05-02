package com.example.ivonneortega.the_news_project.DetailView;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ivonneortega.the_news_project.Article;
import com.example.ivonneortega.the_news_project.DatabaseHelper;
import com.example.ivonneortega.the_news_project.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DetailViewFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DetailViewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetailViewFragment extends Fragment {

    private long mId;
    private ImageView mImage;
    private TextView mTitle, mDate, mContent;

    private OnFragmentInteractionListener mListener;

    public DetailViewFragment() {
        // Required empty public constructor
    }

    public static DetailViewFragment newInstance(Article article) {
        DetailViewFragment fragment = new DetailViewFragment();
        Bundle args = new Bundle();
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
        View view =  inflater.inflate(R.layout.fragment_detail_view, container, false);
        creatingViews(view);
        return view;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction();
    }

    public void creatingViews(View view)
    {
        mImage = (ImageView) view.findViewById(R.id.detail_image);
        mTitle = (TextView) view.findViewById(R.id.detail_title);
        mDate = (TextView) view.findViewById(R.id.detail_date);
        mContent = (TextView) view.findViewById(R.id.detail_content);
    }

}

