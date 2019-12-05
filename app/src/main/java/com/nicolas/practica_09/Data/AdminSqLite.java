package com.nicolas.practica_09.Data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class AdminSqLite extends SQLiteOpenHelper {
    public AdminSqLite(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table personas(id int primary key, nombre text, edad real )");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
    }
}
