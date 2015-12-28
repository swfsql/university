package com.example.test.poupagrana;
import android.util.Log;

import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;

public class FeedReaderDBHelper extends SQLiteOpenHelper {
    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "GoupaGrana.db";

    public FeedReaderDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("DB", "DB onCreate");
        db.execSQL(ContractDB.SQL_CREATE_ITEM_TABLE);
        db.execSQL(ContractDB.SQL_CREATE_ITEM_IN_LIST_TABLE);
        db.execSQL(ContractDB.SQL_CREATE_LIST_TABLE);
        db.execSQL(ContractDB.SQL_CREATE_ITEM_IN_SUPPLIER_ITEM_TABLE);
        db.execSQL(ContractDB.SQL_CREATE_SUPPLIER_TABLE);
        db.execSQL(ContractDB.SQL_CREATE_SUPPLIER_ITEM_TABLE);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d("DB", "DB onUpgrade");
        db.execSQL(ContractDB.SQL_DELETE_ENTRIES);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public void drop(SQLiteDatabase db) {
        Log.d("DB", "DB drop");
        db.execSQL(ContractDB.SQL_DELETE_ENTRIES);
    }
}
