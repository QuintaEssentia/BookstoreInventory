package com.example.nataliyailyushina.bookstore_inventory.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.nataliyailyushina.bookstore_inventory.data.BookContract.BookEntry;

public class BookDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "bookstore.db";
    private static final int DATABASE_VERSION = 1;

    public BookDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
        @Override
        public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_BOOKS_TABLE = "CREATE TABLE " + BookEntry.TABLE_NAME + "("
                + BookEntry._ID + "INTEGER PRIMARY KEY AUTOINCREMENT, "
                + BookEntry.COLUMN_BOOK_NAME + "TEXT NOT NULL, "
                + BookEntry.COLUMN_BOOK_PRICE + "TEXT NOT NULL, "
                + BookEntry.COLUMN_BOOK_QUANTITY + "INTEGER NOT NULL, "
                + BookEntry.COLUMN_SUPPLIER_NAME + "TEXT, "
                + BookEntry.COLUMN_SUPPLIER_PHONE + "INTEGER);";
        db.execSQL(SQL_CREATE_BOOKS_TABLE);
    }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}

    

