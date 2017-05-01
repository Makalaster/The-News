package com.example.ivonneortega.the_news_project;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import javax.net.ssl.SSLContext;

/**
 * Created by WilliamAlford on 4/30/17.
 */

public class DBAssetHelper extends SQLiteAssetHelper {

    private static final String DATABASE_NAME = "news.db";
    private static final int DATABASE_VERSION = 1;

    public DBAssetHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

}
