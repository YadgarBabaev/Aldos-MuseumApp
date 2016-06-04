package com.example.aldos.museumapp.dbClasses;

import android.content.Context;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class DictionaryDatabase extends SQLiteAssetHelper{
    private static final String DATABASE_NAMES = "appDB";
    private static final int DATABASE_VERSION = 1;

    public DictionaryDatabase(Context context) {
        super(context, DATABASE_NAMES, null, DATABASE_VERSION);
    }

}
