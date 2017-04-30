package com.example.ivonneortega.the_news_project;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by WilliamAlford on 4/30/17.
 */

public class DatabaseHelper extends SQLiteOpenHelper {


    //Constructor
    public DatabaseHelper(Context context){
        super(context, DATABASE_NAME,null,DATABASE_VERSION);
    }



    //Database Version and Name

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "news.db";

    //POSITION TABLE

    public static final String TABLE_ARTICLES = "articles";

    //ARTICLE TABLE ROWS

    public static final String COL_ID = "id";
    public static final String COL_TITLE = "title";
    public static final String COL_BODY = "body";
    public static final String COL_DATE = "date";
    public static final String COL_SOURCE = "source";
    public static final String COL_IS_SAVED = "saved";
    public static final String COL_CATEGORY = "category";


    private static final String CREATE_TABLE_ARTICLES = "CREATE_TABLE " + TABLE_ARTICLES + "(" +
            COL_ID + " INTEGER NOT NULLPRIMARY KEY " +
            COL_TITLE + " TEXT " +
            COL_BODY + " TEXT " +
            COL_DATE + " TEXT " +
            COL_SOURCE + " TEXT " +
            COL_IS_SAVED + " INTEGER " +
            COL_CATEGORY + " TEXT " +

            " ) ";


    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ARTICLES);

        onCreate(db);


    }

    private static DatabaseHelper sInstance;

    public static DatabaseHelper getInstance(Context context){
        if(sInstance == null){
            sInstance = new DatabaseHelper(context.getApplicationContext());
        }
        return sInstance;
    }



}
