package com.example.nataliyailyushina.bookstore_inventory;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.example.nataliyailyushina.bookstore_inventory.data.BookDbHelper;
import com.example.nataliyailyushina.bookstore_inventory.data.BookContract.BookEntry;

public class EditActivity extends AppCompatActivity {

    private EditText mEditName;
    private EditText mEditPrice;
    private EditText mEditQuantity;
    private EditText mEditSupName;
    private EditText mEditSupPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        mEditName = findViewById(R.id.edit_book_name);
        mEditPrice = findViewById(R.id.edit_book_price);
        mEditQuantity = findViewById(R.id.edit_book_quantity);
        mEditSupName = findViewById(R.id.edit_supplier_name);
        mEditSupPhone = findViewById(R.id.edit_supplier_phone);
    }

    private void insertBook() {
        String nameString = mEditName.getText().toString().trim();
        String priceString = mEditPrice.getText().toString().trim();
        String quantityString = mEditQuantity.getText().toString().trim();
        int quantity = Integer.parseInt(quantityString);
        String supNameString = mEditSupName.getText().toString().trim();
        String supPhoneString = mEditSupPhone.getText().toString().trim();

        BookDbHelper mDbHelper = new BookDbHelper(this);
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(BookEntry.COLUMN_BOOK_NAME, nameString);
        values.put(BookEntry.COLUMN_BOOK_PRICE, priceString);
        values.put(BookEntry.COLUMN_BOOK_QUANTITY, quantity);
        values.put(BookEntry.COLUMN_SUPPLIER_NAME, supNameString);
        values.put(BookEntry.COLUMN_SUPPLIER_PHONE, supPhoneString);
        Uri newUri = getContentResolver().insert(BookEntry.CONTENT_URI, values);

        if (newUri == null) {
            // If the row ID is -1, then there was an error with insertion.
            Toast.makeText(this, "Error with saving book", Toast.LENGTH_SHORT).show();
        } else {
            // Otherwise, the insertion was successful and we can display a toast with the row ID.
            Toast.makeText(this, "Book saved ", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_editor.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_edit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Save" menu option
            case R.id.action_save:
                insertBook();
                finish();
                // Do nothing for now
                return true;
            // Respond to a click on the "Delete" menu option
            case R.id.action_delete:
                // Do nothing for now
                return true;
            // Respond to a click on the "Up" arrow button in the app bar
            case android.R.id.home:
                // Navigate back to parent activity (CatalogActivity)
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

