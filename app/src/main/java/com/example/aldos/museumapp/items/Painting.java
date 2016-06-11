package com.example.aldos.museumapp.items;

import android.content.Context;

import com.example.aldos.museumapp.dbClasses.DBHelper;

public class Painting {

    private final int imageId;
    private final String title;
    private final String author;
    private final String date;
    private final String text;
    private final byte[] imgBytes;

    public Painting(int imageId, String title, String author, String date, String text, byte[] imgBytes) {
        this.imageId = imageId;
        this.title = title;
        this.author = author;
        this.date = date;
        this.text = text;
        this.imgBytes = imgBytes;
    }

    public int getImageId() {
        return imageId;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getDate() {
        return date;
    }

    public String getText() {
        return text;
    }

    public byte[] getImg() {
        return imgBytes;
    }

    public static Painting[] getAllPaintings(Context context) {
        DBHelper db = new DBHelper(context);
        int[] ids = db.getIds("gallery");
        int size = ids.length;
        Painting[] paintings = new Painting[size];

        int index = 0;
        for (int id : ids) {
            PictureObject p = db.getPicture(id);
            String title  = p.getName();
            String author = p.getAuthor();
            String date   = p.getDate();
            String text   = p.getText();
            byte[] image  = p.getImg();

            paintings[index] = new Painting(id, title, author, date, text, image);
            index++;
            }
        return paintings;
    }

}
