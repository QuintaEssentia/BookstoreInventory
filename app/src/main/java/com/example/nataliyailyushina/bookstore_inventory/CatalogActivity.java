package com.example.nataliyailyushina.bookstore_inventory;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.FloatingActionButton;
import android.view.Menu;
import android.view.MenuItem;
import android.database.Cursor;
import android.widget.ListView;
import android.widget.TextView;
import android.view.View;

import com.example.nataliyailyushina.bookstore_inventory.data.BookContract.BookEntry;
import com.example.nataliyailyushina.bookstore_inventory.data.BookDbHelper;

import java.util.List;

public class CatalogActivity extends AppCompatActivity {

    private BookDbHelper mDbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog);

        // Setup FAB to open EditorActivity
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CatalogActivity.this, EditActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        displayDatabaseInfo();
    }

    private void insertbook() {
        ContentValues values = new ContentValues();
        values.put(BookEntry.COLUMN_BOOK_NAME, "The Lord of the Rings");
        values.put(BookEntry.COLUMN_BOOK_PRICE, "30$");
        values.put(BookEntry.COLUMN_BOOK_QUANTITY, 3);
        values.put(BookEntry.COLUMN_SUPPLIER_NAME, "Waterstones");
        values.put(BookEntry.COLUMN_SUPPLIER_PHONE, "89099219581");

        Uri newUri = getContentResolver().insert(BookEntry.CONTENT_URI, values);

    }

    private void displayDatabaseInfo() {

        String[] projection = {
                BookEntry._ID,
                BookEntry.COLUMN_BOOK_NAME,
                BookEntry.COLUMN_BOOK_PRICE,
                BookEntry.COLUMN_BOOK_QUANTITY,
                BookEntry.COLUMN_SUPPLIER_NAME,
                BookEntry.COLUMN_SUPPLIER_PHONE
        };


        Cursor cursor = getContentResolver().query(BookEntry.CONTENT_URI,
                projection,
                null,
                null,
                null);
        // Perform this raw SQL query "SELECT * FROM pets"
        // to get a Cursor that contains all rows from the pets table.
        ListView bookListView = findViewById(R.id.list);
        BookCursorAdapter adapter = new BookCursorAdapter(this, cursor);
        bookListView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_catalog.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_catalog, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Insert dummy data" menu option
            case R.id.action_insert_dummy_data:
                insertbook();
                return true;
            // Respond to a click on the "Delete all entries" menu option
            case R.id.action_delete_all_entries:
                // Do nothing for now
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

