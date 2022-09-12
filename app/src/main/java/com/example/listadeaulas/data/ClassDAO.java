package com.example.listadeaulas.data;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class ClassDAO {
    private static ClassDAO instance;

    private SQLiteDatabase db;

    private ClassDAO(Context context){
        DBHelper dbHelper = DBHelper.getInstance(context);
        db = dbHelper.getWritableDatabase();
    }

    public static ClassDAO getInstance(Context context){
        if (instance == null){
            instance = new ClassDAO(context.getApplicationContext());
        }
        return instance;
    }

    public List<Class> list(){

        String[] columns = {
                ClassesContract.Columns._ID,
                ClassesContract.Columns.TITLE,
                ClassesContract.Columns.MATTER,
                ClassesContract.Columns.LINK
        };

        List<Class> classes = new ArrayList<>();

        try(Cursor c = db.query(ClassesContract.TABLE_NAME,
                columns, null, null, null, null, ClassesContract.Columns.TITLE)) {
            if (c.moveToFirst()){
                do {
                    Class cl = ClassDAO.fromCursor(c);
                    classes.add(cl);
                } while (c.moveToNext());
            }
            return classes;
        }
    }

    private static Class fromCursor(Cursor c){
        @SuppressLint("Range") int id = c.getInt(c.getColumnIndex(ClassesContract.Columns._ID));
        @SuppressLint("Range") String title = c.getString(c.getColumnIndex(ClassesContract.Columns.TITLE));
        @SuppressLint("Range") String matter = c.getString(c.getColumnIndex(ClassesContract.Columns.MATTER));
        @SuppressLint("Range") String link = c.getString(c.getColumnIndex(ClassesContract.Columns.LINK));
        return new Class(id, title, matter, link);
    }

    public void save(Class c){
        ContentValues values = new ContentValues();
        values.put(ClassesContract.Columns.TITLE, c.getTitle());
        values.put(ClassesContract.Columns.MATTER, c.getMatter());
        values.put(ClassesContract.Columns.LINK, c.getUrl());
        long id = db.insert(ClassesContract.TABLE_NAME, null, values);
        c.setId((int) id);
    }

    public void update(Class c){
        ContentValues values = new ContentValues();
        values.put(ClassesContract.Columns.TITLE, c.getTitle());
        values.put(ClassesContract.Columns.MATTER, c.getMatter());
        values.put(ClassesContract.Columns.LINK, c.getUrl());
        db.update(ClassesContract.TABLE_NAME, values,
                ClassesContract.Columns._ID + " = ?", new String[]{String.valueOf(c.getId()) });
    }

    public void delete(Class c){
        db.delete(ClassesContract.TABLE_NAME, ClassesContract.Columns._ID + " = ?", new String[]{String.valueOf(c.getId()) });
    }

}
