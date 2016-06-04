package com.example.aldos.museumapp.dbClasses;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.example.aldos.museumapp.PictureObject;

public class DBHelper extends DbObject{

    public DBHelper(Context context) {super(context);}

    public PictureObject getPicture(int id) {
        PictureObject picture = null;
        String query = "SELECT * FROM gallery WHERE id = " + id;
        Cursor cursor = this.getDbConnection().rawQuery(query, null);
        if(cursor.moveToFirst()) {
            String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
            String text = cursor.getString(cursor.getColumnIndexOrThrow("text"));
            byte[] img = cursor.getBlob(cursor.getColumnIndexOrThrow("img"));
            picture = new PictureObject(name, text, img);
        }
        cursor.close();
        this.closeDbConnection();
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
}