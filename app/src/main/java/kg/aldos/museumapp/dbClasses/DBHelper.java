package kg.aldos.museumapp.dbClasses;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;

import kg.aldos.museumapp.items.Exhibition;
import kg.aldos.museumapp.items.News;
import kg.aldos.museumapp.items.Painting;
import kg.aldos.museumapp.items.PictureObject;

public class DBHelper extends DbObject {

    public DBHelper(Context context) {super(context);}

    public int[] getIds(String table) {
        String query = "SELECT id FROM " + table;
        Cursor cursor = this.getDbConnection().rawQuery(query, null);
        ArrayList<Integer> list = new ArrayList<>();
        while (cursor.moveToNext()){
            list.add(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
        }
        cursor.close();
        int[] ids = new int[list.size()];
        for (int i=0; i<ids.length; i++){ids[i] = list.get(i);}
        return ids;
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
    public Painting getPainting(int id) {
        Painting picture = null;
        String query = "SELECT * FROM gallery WHERE id = " + id;
        Cursor cursor = this.getDbConnection().rawQuery(query, null);
        if(cursor.moveToFirst()) {
            String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
            String text = cursor.getString(cursor.getColumnIndexOrThrow("text"));
            String author = cursor.getString(cursor.getColumnIndexOrThrow("author"));
            String date = cursor.getString(cursor.getColumnIndexOrThrow("date"));
            byte[] img = cursor.getBlob(cursor.getColumnIndexOrThrow("img"));
            picture = new Painting(id, name, author, date, text, img);
        }
        cursor.close();
        return picture;
    }

    public long insertNews(String date, String text, byte[] img){
        ContentValues values = new ContentValues();
        values.put("date", date);
        values.put("text", text);
        values.put("cover", img);
        return this.getDbConnection().insert("news", null, values);
    }
    public News getNews(int id) {
        News news = null;
        String query = "SELECT * FROM news WHERE id = " + id;
        Cursor cursor = this.getDbConnection().rawQuery(query, null);
        if(cursor.moveToFirst()) {
            String date = cursor.getString(cursor.getColumnIndexOrThrow("date"));
            String text = cursor.getString(cursor.getColumnIndexOrThrow("text"));
            byte[] img = cursor.getBlob(cursor.getColumnIndexOrThrow("cover"));
            news = new News(id, date, text, img);
        }
        cursor.close();
        return news;
    }

    public long insertExhibition(byte[] img, String title, String text){
        ContentValues values = new ContentValues();
        values.put("title", title);
        values.put("text", text);
        values.put("image", img);
        return this.getDbConnection().insert("exhibition", null, values);
    }
    public Exhibition getExhibition(int id) {
        Exhibition object = null;
        String query = "SELECT * FROM exhibition WHERE id = " + id;
        Cursor cursor = this.getDbConnection().rawQuery(query, null);
        if(cursor.moveToFirst()) {
            String title = cursor.getString(cursor.getColumnIndexOrThrow("title"));
            String text = cursor.getString(cursor.getColumnIndexOrThrow("text"));
            byte[] image = cursor.getBlob(cursor.getColumnIndexOrThrow("image"));
            object = new Exhibition(id, title, text, image);
        }
        cursor.close();
        return object;
    }
}