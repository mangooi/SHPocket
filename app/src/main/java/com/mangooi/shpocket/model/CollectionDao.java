package com.mangooi.shpocket.model;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.mangooi.shpocket.data.Constant;
import com.mangooi.shpocket.util.DBHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/10/31.
 */

public class CollectionDao{

    private static final String TAG = "CollectionDao";
    private SQLiteDatabase db;

    private DBHelper dbHelper;


    public CollectionDao(DBHelper dbHelper){
        this.dbHelper=dbHelper;
        db=dbHelper.getWritableDatabase();
    }

    public boolean saveCollection(CollectionMode mode){
        ContentValues values=new ContentValues();
        Log.i(TAG,mode.toString());
        values.put("pic",mode.getPicUrl());
        values.put("brief",mode.getBrief());
        values.put("title",mode.getTitle());
        values.put("time",mode.getTime());
        values.put("url",mode.getUrl());
        return db.insert("collection",null,values)>0;
    }

    public boolean deleteCollection(String title){
        return db.delete("collection","title=?",new String[]{title})>0;
    }

    //create table collection(_id integer primary key autoincrement, pic text, title text , brief text, time text ,url text )
    //private String picUrl;private String brief;private String title;private String time;private String url;
    public List<CollectionMode> findCollection(){
        List<CollectionMode> list=new ArrayList<>();
        Cursor cursor=queryTheCursor();
        while (cursor.moveToNext()){
            CollectionMode mode=new CollectionMode();
            mode.setPicUrl(cursor.getString(cursor.getColumnIndex(Constant.COLLECTION_COLUMN_PIC)));
            mode.setTitle(cursor.getString(cursor.getColumnIndex(Constant.COLLECTION_COLUMN_TITLE)));
            mode.setBrief(cursor.getString(cursor.getColumnIndex(Constant.COLLECTION_COLUMN_BRIEF)));
            mode.setTime(cursor.getString(cursor.getColumnIndex(Constant.COLLECTION_COLUMN_TIME)));
            mode.setUrl(cursor.getString(cursor.getColumnIndex(Constant.COLLECTION_COLUMN_URL)));
            list.add(mode);
        }
        cursor.close();
        return list;
    }

    private Cursor queryTheCursor(){
        return db.rawQuery("SELECT * FROM collection", null);
    }






}
