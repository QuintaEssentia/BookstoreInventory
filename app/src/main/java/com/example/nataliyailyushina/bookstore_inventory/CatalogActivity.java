package com.example.nataliyailyushina.bookstore_inventory;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
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

public class CatalogActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private BookDbHelper mDbHelper;

    private static final int BOOK_LOADER = 0;

    BookCursorAdapter mCursorAdapter;

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
        // Find the ListView which will be populated with the pet data
        ListView bookListView = findViewById(R.id.list);

        // Find and set empty view on the ListView, so that it only shows when the list has 0 items.
        View emptyView = findViewById(R.id.empty_view);
        bookListView.setEmptyView(emptyView);

        mCursorAdapter = new BookCursorAdapter(this,null);
        bookListView.setAdapter(mCursorAdapter);
        getLoaderManager().initLoader(BOOK_LOADER,null,this);
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

    @Override
    public Loader onCreateLoader(int i, Bundle bundle) {
       String[] projection = {
               BookEntry._ID,
               BookEntry.COLUMN_BOOK_NAME,
               BookEntry.COLUMN_BOOK_PRICE,
               BookEntry.COLUMN_BOOK_QUANTITY
       };

       return new CursorLoader(this,
               BookEntry.CONTENT_URI,
               projection,
               null,
               null,
               null);
    }

      @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mCursorAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader loader) {
        mCursorAdapter.swapCursor(null);
    }
}

