package com.example.ivonneortega.the_news_project;

import java.util.List;

/**
 * Created by ivonneortega on 4/29/17.
 */

public class AllStories {

    private String mCategoryName;
    private List<CategoryIndividualItem> mList;

    public AllStories(String categoryName, List<CategoryIndividualItem> list) {
        mCategoryName = categoryName;
        mList = list;
    }

    public String getCategoryName() {
        return mCategoryName;
    }

    public void setCategoryName(String categoryName) {
        mCategoryName = categoryName;
    }

    public List<CategoryIndividualItem> getList() {
        return mList;
    }

    public void setList(List<CategoryIndividualItem> list) {
        mList = list;
    }
}
