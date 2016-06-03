package com.example.aldos.museumapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class DBHelper extends SQLiteAssetHelper{

    private static final String DATABASE_NAMES = "appDB";
    private static final int DATABASE_VERSION = 1;
    private SQLiteDatabase db;

    public DBHelper(Context context) {
        super(context, DATABASE_NAMES, null, DATABASE_VERSION);
        DBHelper dbHelper = new DBHelper(context);
        this.db = dbHelper.getWritableDatabase();
    }

    public PictureObject getPicture(int id) {
        PictureObject picture = null;
        String query = "SELECT * FROM gallery WHERE id = " + id;
        Cursor cursor = this.db.rawQuery(query, null);
        if(cursor.moveToFirst()) {
            String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
            String text = cursor.getString(cursor.getColumnIndexOrThrow("text"));
            byte[] img = cursor.getBlob(cursor.getColumnIndexOrThrow("img"));
            picture = new PictureObject(name, text, img);
        }
        cursor.close();
        this.db.close();
        return picture;
    }

    public long insertObject(String name, String text, String author, int year, byte[] img){
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("text", text);
        values.put("author", author);
        values.put("year", year);
        values.put("img", img);
        return this.db.insert("dictionary", null, values);
    }
}
