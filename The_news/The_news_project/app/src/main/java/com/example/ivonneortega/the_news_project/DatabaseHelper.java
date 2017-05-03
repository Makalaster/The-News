package com.example.ivonneortega.the_news_project;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.ivonneortega.the_news_project.data.Article;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by WilliamAlford on 4/30/17.
 */

public class DatabaseHelper extends SQLiteOpenHelper {


    ////////////////////
    //DB HELPER CONSTRUCTOR
    ////////////////////
    public DatabaseHelper(Context context){
        super(context, DATABASE_NAME,null,DATABASE_VERSION);
    }




    ////////////////////
    //DB VERSION + NAME
    ////////////////////

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "news.db";

    ////////////////////
    //ARTICLE TABLE
    ////////////////////

    public static final String TABLE_ARTICLES = "articles";

    ////////////////////
    //ARTICLE TABLE COLUMNS
    ////////////////////

    public static final String COL_ID = "id";
    public static final String COL_TITLE = "title";
    public static final String COL_BODY = "body";
    public static final String COL_DATE = "date";
    public static final String COL_SOURCE = "source";
    public static final String COL_IS_SAVED = "isSaved";
    public static final String COL_CATEGORY = "category";
    public static final String COL_IMAGE = "image";
    public static final String COL_IS_TOP_STORY = "isTopStory";
    public static final String COL_URL = "url";




    private static final String CREATE_TABLE_ARTICLES = "CREATE TABLE " + TABLE_ARTICLES + " (" +
            COL_ID + " INTEGER PRIMARY KEY, " +
            COL_TITLE + " TEXT, " +
            COL_BODY + " TEXT, " +
            COL_DATE + " TEXT, " +
            COL_SOURCE + " TEXT, " +
            COL_IS_SAVED + " INTEGER, " +
            COL_CATEGORY + " TEXT, " +
            COL_IMAGE + " TEXT, " +
            COL_IS_TOP_STORY + " INTEGER, " +
            COL_URL + " TEXT" + ")";


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_ARTICLES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ARTICLES);

        onCreate(db);


    }

    ////////////////////
    //SINGLETON
    ////////////////////

    private static DatabaseHelper sInstance;

    public static DatabaseHelper getInstance(Context context){
        if(sInstance == null){
            sInstance = new DatabaseHelper(context.getApplicationContext());
        }
        return sInstance;
    }


    ////////////////////
    //GET ARTICLES BY ID
    ////////////////////

    public Article getArticlesById(long id) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(TABLE_ARTICLES,
                null,
                COL_ID + " = ?",
                new String[]{String.valueOf(id)},
                null,
                null,
                null);

        Article articles = null;

        if (cursor.moveToFirst()) {

            articles = new Article(
                    cursor.getLong(cursor.getColumnIndex(COL_ID)),
                    cursor.getString(cursor.getColumnIndex(COL_IMAGE)),
                    cursor.getString(cursor.getColumnIndex(COL_TITLE)),
                    cursor.getString(cursor.getColumnIndex(COL_CATEGORY)),
                    cursor.getString(cursor.getColumnIndex(COL_DATE)),
                    cursor.getString(cursor.getColumnIndex(COL_BODY)),
                    cursor.getString(cursor.getColumnIndex(COL_SOURCE)),
                    cursor.getInt(cursor.getColumnIndex(COL_IS_SAVED)),
                    cursor.getInt(cursor.getColumnIndex(COL_IS_TOP_STORY)),
                    cursor.getString(cursor.getColumnIndex(COL_URL))

                    );
            cursor.close();
            return articles;
        }
        else
        {
            cursor.close();
            return null;
        }

    }

    ////////////////////
    //INSERT ARTICLE
    ////////////////////


    //TODO NEED TO ADD MORE STUFF DEPENDING ON OUR DATABASE
    public long insertArticleIntoDatabase(String image, String title, String category, String date,
                                          String body, String source, int isSaved, int isTopStory,String url) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        //ADD IMAGE TO DATABASE
        values.put(COL_TITLE,title);
        values.put(COL_CATEGORY,category);
        values.put(COL_DATE,date);
        values.put(COL_BODY,body);
        values.put(COL_SOURCE,source);
        values.put(COL_IS_SAVED,isSaved);
        values.put(COL_IMAGE,image);
        values.put(COL_IS_TOP_STORY,isTopStory);
        values.put(COL_URL,url);


        long idToReturn = db.insert(TABLE_ARTICLES,null,values);
        //db.close();
        return  idToReturn;
//        db.update(TABLE_ARTICLES,
//                values,
//                COL_ID + " = ?",
//                new String[]{String.valueOf(id)}
//        );




    }



    ////////////////////
    //DELETE FROM DB BY ID
    ////////////////////

    public void deleteIndividualArticlesFromDatabase(long id){
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_ARTICLES, COL_ID+" = ?", new String[]{String.valueOf(id)});
    }



    ////////////////////
    //SAVE ARTICLES BY ID
    ////////////////////

    public void saveArticle(long id){
        SQLiteDatabase db = getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(COL_IS_SAVED,Article.TRUE);
            db.update(TABLE_ARTICLES,
                    values,
                    COL_ID + " = ?",
                    new String[]{String.valueOf(id)}
            );

    }



    ////////////////////
    //UN SAVED ARTICLES BY ID
    ////////////////////

    public void unSaveArticle(long id){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_IS_SAVED,Article.FALSE);
        db.update(TABLE_ARTICLES,
                values,
                COL_ID + " = ?",
                new String[]{String.valueOf(id)}
        );

    }



    ////////////////////
    //DELETE ALL SAVED ARTICLES
    ////////////////////

    public void deleteAllSavedArticles(){
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.query(TABLE_ARTICLES,null,null,null,null,null,null);

        if(cursor.moveToFirst())
        {
            while(!cursor.isAfterLast()) {
                deleteIndividualArticlesFromDatabase(cursor.getLong(cursor.getColumnIndex(COL_ID)));
                cursor.moveToNext();
            }
        }

        cursor.close();
        //db.close();
    }




    ////////////////////
    //GET ARTICLES BY TITLE (SEARCH)
    ////////////////////

    public List<Article> searchArticles(String query){
        SQLiteDatabase db = this.getReadableDatabase();


        Cursor cursor = db.query(TABLE_ARTICLES, // a. table
                null, // b. column names
                COL_TITLE +" LIKE ? OR " + COL_CATEGORY + " LIKE ?", // c. selections
                new String[]{"%" + query +"%", "%" + query +"%"}, // d. selections args
                null, // e. group by
                null, // f. having
                null, // g. order by
                null); // h. limit

        List<Article> articles = new ArrayList<>();

        if(cursor.moveToFirst()){
            while (!cursor.isAfterLast()){
                articles.add( new Article(
                        cursor.getLong(cursor.getColumnIndex(COL_ID)),
                        cursor.getString(cursor.getColumnIndex(COL_IMAGE)),
                        cursor.getString(cursor.getColumnIndex(COL_TITLE)),
                        cursor.getString(cursor.getColumnIndex(COL_CATEGORY)),
                        cursor.getString(cursor.getColumnIndex(COL_DATE)),
                        cursor.getString(cursor.getColumnIndex(COL_BODY)),
                        cursor.getString(cursor.getColumnIndex(COL_SOURCE)),
                        cursor.getInt(cursor.getColumnIndex(COL_IS_SAVED)),
                        cursor.getInt(cursor.getColumnIndex(COL_IS_TOP_STORY)),
                        cursor.getString(cursor.getColumnIndex(COL_URL)))

                );

                cursor.moveToNext();
            }
        }
        cursor.close();
        return articles;
    }


    public List<Article> getTopStoryArticles()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_ARTICLES, // a. table
                null, // b. column names
                COL_IS_TOP_STORY + " = ?", // c. selections
                new String[]{String.valueOf(Article.TRUE)}, // d. selections args
                null, // e. group by
                null, // f. having
                COL_ID + " DESC", // g. order by
                null); // h. limit

        List<Article> articles = new ArrayList<>();

        if(cursor.moveToFirst()){
            while (!cursor.isAfterLast()){
                articles.add( new Article(
                        cursor.getLong(cursor.getColumnIndex(COL_ID)),
                        cursor.getString(cursor.getColumnIndex(COL_IMAGE)),
                        cursor.getString(cursor.getColumnIndex(COL_TITLE)),
                        cursor.getString(cursor.getColumnIndex(COL_CATEGORY)),
                        cursor.getString(cursor.getColumnIndex(COL_DATE)),
                        cursor.getString(cursor.getColumnIndex(COL_BODY)),
                        cursor.getString(cursor.getColumnIndex(COL_SOURCE)),
                        cursor.getInt(cursor.getColumnIndex(COL_IS_SAVED)),
                        cursor.getInt(cursor.getColumnIndex(COL_IS_TOP_STORY)),
                        cursor.getString(cursor.getColumnIndex(COL_URL)))

                );

                cursor.moveToNext();
            }
        }
        cursor.close();
        return articles;
    }

    public List<Article> getSavedArticles()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_ARTICLES, // a. table
                null, // b. column names
                COL_IS_SAVED + " = ?", // c. selections
                new String[]{String.valueOf(Article.TRUE)}, // d. selections args
                null, // e. group by
                null, // f. having
                null, // g. order by
                null); // h. limit

        List<Article> articles = new ArrayList<>();

        if(cursor.moveToFirst()){
            while (!cursor.isAfterLast()){
                articles.add( new Article(
                        cursor.getLong(cursor.getColumnIndex(COL_ID)),
                        cursor.getString(cursor.getColumnIndex(COL_IMAGE)),
                        cursor.getString(cursor.getColumnIndex(COL_TITLE)),
                        cursor.getString(cursor.getColumnIndex(COL_CATEGORY)),
                        cursor.getString(cursor.getColumnIndex(COL_DATE)),
                        cursor.getString(cursor.getColumnIndex(COL_BODY)),
                        cursor.getString(cursor.getColumnIndex(COL_SOURCE)),
                        cursor.getInt(cursor.getColumnIndex(COL_IS_SAVED)),
                        cursor.getInt(cursor.getColumnIndex(COL_IS_TOP_STORY)),
                        cursor.getString(cursor.getColumnIndex(COL_URL)))

                );

                cursor.moveToNext();
            }
        }
        cursor.close();
        return articles;
    }


    public List<Article> getArticlesByCategory(String query){
        SQLiteDatabase db = this.getReadableDatabase();


        Cursor cursor = db.query(TABLE_ARTICLES, // a. table
                null, // b. column names
                COL_CATEGORY + " LIKE ?", // c. selections
                new String[]{"%" + query + "%"}, // d. selections args
                null, // e. group by
                null, // f. having
                null, // g. order by
                null); // h. limit

        List<Article> articles = new ArrayList<>();

        if(cursor.moveToFirst()){
            while (!cursor.isAfterLast()){
                articles.add( new Article(
                        cursor.getLong(cursor.getColumnIndex(COL_ID)),
                        cursor.getString(cursor.getColumnIndex(COL_IMAGE)),
                        cursor.getString(cursor.getColumnIndex(COL_TITLE)),
                        cursor.getString(cursor.getColumnIndex(COL_CATEGORY)),
                        cursor.getString(cursor.getColumnIndex(COL_DATE)),
                        cursor.getString(cursor.getColumnIndex(COL_BODY)),
                        cursor.getString(cursor.getColumnIndex(COL_SOURCE)),
                        cursor.getInt(cursor.getColumnIndex(COL_IS_SAVED)),
                        cursor.getInt(cursor.getColumnIndex(COL_IS_TOP_STORY)),
                        cursor.getString(cursor.getColumnIndex(COL_URL)))

                );

                cursor.moveToNext();
            }
        }
        cursor.close();
        return articles;
    }

    public Article getArticleByUrl(String url) {
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.query(TABLE_ARTICLES,
                null,
                COL_URL + " = ?",
                new String[]{url},
                null, null, null);

        Article article = null;

        if (cursor.moveToFirst()) {
            article = new Article(cursor.getLong(cursor.getColumnIndex(COL_ID)),
                    cursor.getString(cursor.getColumnIndex(COL_IMAGE)),
                    cursor.getString(cursor.getColumnIndex(COL_TITLE)),
                    cursor.getString(cursor.getColumnIndex(COL_CATEGORY)),
                    cursor.getString(cursor.getColumnIndex(COL_DATE)),
                    cursor.getString(cursor.getColumnIndex(COL_BODY)),
                    cursor.getString(cursor.getColumnIndex(COL_SOURCE)),
                    cursor.getInt(cursor.getColumnIndex(COL_IS_SAVED)),
                    cursor.getInt(cursor.getColumnIndex(COL_IS_TOP_STORY)),
                    cursor.getString(cursor.getColumnIndex(COL_URL)));
        }

        cursor.close();

        return article;
    }

    public void checkSizeAndRemoveOldest() {
        SQLiteDatabase db = getWritableDatabase();

        Cursor cursor = db.query(TABLE_ARTICLES, null, null, null, null, null, COL_DATE + " ASC");
        boolean oldestRemoved = false;

        if (cursor.moveToFirst() && cursor.getCount() > 300) {
            while (!oldestRemoved && !cursor.isAfterLast()) {
                if (cursor.getInt(cursor.getColumnIndex(COL_IS_SAVED)) == Article.FALSE) {
                    db.delete(TABLE_ARTICLES,
                            COL_ID + " = ?",
                            new String[]{String.valueOf(cursor.getInt(cursor.getColumnIndex(COL_ID)))});

                    oldestRemoved = true;
                } else {
                    cursor.moveToNext();
                }
            }
        }

        cursor.close();
    }
}
