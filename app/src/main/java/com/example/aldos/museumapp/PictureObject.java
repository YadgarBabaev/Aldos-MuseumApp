package com.example.aldos.museumapp;

public class PictureObject {
    private String name;
    private String author;
    private String date;
    private String text;
    private byte[] img;

    public PictureObject(String n, String a, String d, String t, byte[] i) {
        this.name = n;
        this.author = a;
        this.date = d;
        this.text = t;
        this.img = i;
    }

    public String getName() {return name;}

    public String getAuthor() {return author;}

    public String getDate() {return date;}

    public String getText() {return text;}

    public byte[] getImg(){return img;}

}
