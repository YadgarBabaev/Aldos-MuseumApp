package com.example.aldos.museumapp.dbClasses;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.example.aldos.museumapp.PictureObject;

import java.util.ArrayList;

public class DBHelper extends DbObject{

    public DBHelper(Context context) {super(context);}

    public int[] getIds() {
        String query = "SELECT id FROM gallery";
        Cursor cursor = this.getDbConnection().rawQuery(query, null);
        ArrayList<Integer> list = new ArrayList<>();
        while (cursor.moveToNext()){
            list.add(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
        }
        cursor.close();
        int[] ids = new int[list.size()];
        for (int i=0; i < ids.length; i++){
            ids[i] = list.get(i);
        }
        return ids;
    }

    public PictureObject getPicture(int id) {
        PictureObject picture = null;
        String query = "SELECT * FROM gallery WHERE id = " + id;
        Cursor cursor = this.getDbConnection().rawQuery(query, null);
        if(cursor.moveToFirst()) {
            String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
            String text = cursor.getString(cursor.getColumnIndexOrThrow("text"));
            String author = cursor.getString(cursor.getColumnIndexOrThrow("author"));
            String date = cursor.getString(cursor.getColumnIndexOrThrow("date"));
            byte[] img = cursor.getBlob(cursor.getColumnIndexOrThrow("img"));
            picture = new PictureObject(name, author, date, text, img);
        }
        cursor.close();
        return picture;
    }

    public long insertPicture(String name, String text, String author, String date, byte[] img){
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("text", text);
        values.put("author", author);
        values.put("date", date);
        values.put("img", img);
        return this.getDbConnection().insert("gallery", null, values);
    }

    public long insertNews(String date, String text, byte[] img){
        ContentValues values = new ContentValues();
        values.put("date", date);
        values.put("text", text);
        values.put("cover", img);
        return this.getDbConnection().insert("news", null, values);
    }
}