package com.example.ivonneortega.the_news_project.mainActivity.fragments;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by ivonneortega on 4/29/17.
 */

public class FragmentAdapterMainActivity extends FragmentPagerAdapter {

    public FragmentAdapterMainActivity(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position)
        {
            case 0:
                return FragmentAllStories.newInstance();
            case 1:
                return FragmentTopStories.newInstance();
            case 2:
                return FragmentSave.newInstance();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position)
        {
            case 0:
                return "All Stories";
            case 1:
                return "Top Stories";
            case 2:
                return "Save";
            default:
                return null;
        }
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }
}
