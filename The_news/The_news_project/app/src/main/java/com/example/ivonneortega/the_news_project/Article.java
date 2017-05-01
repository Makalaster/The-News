package com.example.ivonneortega.the_news_project;

/**
 * Created by ivonneortega on 4/29/17.
 */

public class Article {

    private String mImage;
    private String mTitle,mCategory,mDate;
    private boolean mIsSaved;
    //private boolean mIsTopStory;

    public Article(String image, String title, String category, String date) {
        mImage = image;
        mTitle = title;
        mCategory = category;
        mDate = date;
        mIsSaved=false;
    }

    public String getImage() {
        return mImage;
    }

    public void setImage(String image) {
        mImage = image;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getCategory() {
        return mCategory;
    }

    public void setCategory(String category) {
        mCategory = category;
    }

    public String getDate() {
        return mDate;
    }

    public void setDate(String date) {
        mDate = date;
    }

    public boolean isSaved() {
        return mIsSaved;
    }

    public void setSaved(boolean saved) {
        mIsSaved = saved;
    }

//    public boolean isTopStory() {
//        return mIsTopStory;
//    }
//
//    public void setTopStory(boolean topStory) {
//        mIsTopStory = topStory;
//    }
}
