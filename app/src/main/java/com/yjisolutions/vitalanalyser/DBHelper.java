package com.yjisolutions.vitalanalyser;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context) {
        super(context, "Theme_data.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create Table Colordata(name TEXT PRIMARY KEY, colorCard TEXT, colorBG TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop Table if exists Colordata");
    }

    public boolean addTheme(String name, String colorCard,String colorBG) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("colorCard", colorCard);
        contentValues.put("colorBG", colorBG);
        long result = db.insert("Colordata", null, contentValues);
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public boolean updateTheme(String name, String colorCard,String colorBG) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("colorCard", colorCard);
        contentValues.put("colorBG", colorBG);
        Cursor cursor = db.rawQuery("Select * from Colordata where name = ?", new String[]{name});
        if (cursor.getCount() > 0) {
            long result = db.update("Colordata", contentValues, "name=?", new String[]{name});
            if (result == -1) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    public boolean deleteTheme(String name) {
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery("Select * from Colordata where name = ?", new String[]{name});
        if (cursor.getCount() > 0) {
            long result = db.delete("Colordata","name=?", new String[]{name});
            if (result == -1) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }
    public Cursor getTheme(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select  * from Colordata",null);
        return cursor;


    }

}
