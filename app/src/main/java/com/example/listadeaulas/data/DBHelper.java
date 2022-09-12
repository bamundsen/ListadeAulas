package com.example.listadeaulas.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "classesdb";
    public static final int DB_VERSION = 1;

    private static final String SQL_DROP = "DROP TABLE IF EXISTS " + ClassesContract.TABLE_NAME;
    private static final String SQL_CREATE = String.format(
            "CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "%s TEXT NOT NULL, %s TEXT NOT NULL,  %s TEXT NOT NULL)", ClassesContract.TABLE_NAME,
            ClassesContract.Columns._ID,
            ClassesContract.Columns.TITLE,
            ClassesContract.Columns.MATTER,
            ClassesContract.Columns.LINK);

    public static DBHelper getInstance(Context context) {
        if (instance == null) {
            instance = new DBHelper(context, DB_NAME, null, DB_VERSION);
        }
        return instance;
    }

    private static DBHelper instance;

    public DBHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_DROP);
        db.execSQL(SQL_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
    }
}
