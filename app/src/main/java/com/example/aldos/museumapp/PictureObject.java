package com.example.aldos.museumapp;

public class PictureObject {
    private String name;
    private String text;
    private byte[] img;

    public PictureObject(String n, String t, byte[] i) {
        this.name = n;
        this.text = t;
        this.img = i;
    }

    public String getName() {return name;}

    public void setName(String name) {this.name = name;}

    public String getText() {return text;}

    public void setText(String text) {this.text = text;}

    public byte[] getImg(){return img;}

    public void setImg(byte[] img){this.img = img;}
}
