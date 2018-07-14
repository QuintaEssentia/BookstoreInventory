package com.example.nataliyailyushina.bookstore_inventory.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

/**
 * {@link ContentProvider} for Pets app.
 */
public class BookProvider extends ContentProvider {

    /** Tag for the log messages */
    public static final String LOG_TAG = BookProvider.class.getSimpleName();
    /**Database helper object*/
    private BookDbHelper mDbHelper;

    private static final int BOOKS = 100;
    private static final int BOOK_ID = 101;

    private static final UriMatcher sUsiMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        sUsiMatcher.addURI(BookContract.CONTENT_AUTHORITY, BookContract.PATH_BOOKS, BOOKS);
        sUsiMatcher.addURI(BookContract.CONTENT_AUTHORITY, BookContract.PATH_BOOKS + "/#", BOOK_ID);
    }
    /**
     * Initialize the provider and the database helper object.
     */
    @Override
    public boolean onCreate() {
        mDbHelper = new BookDbHelper(getContext());
        return true;
    }

    /**
     * Perform the query for the given URI. Use the given projection, selection, selection arguments, and sort order.
     */
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
                        String sortOrder) {
        SQLiteDatabase database = mDbHelper.getReadableDatabase();
        Cursor cursor;

        int match = sUsiMatcher.match(uri);
        switch (match){
            case BOOKS:
                cursor = database.query(BookContract.BookEntry.TABLE_NAME, projection,selection,selectionArgs, null, null, sortOrder);
                break;

            case BOOK_ID:
                selection = BookContract.BookEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                cursor = database.query(BookContract.BookEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;

                default:
                    throw new IllegalArgumentException("Cannot query unknown URI " + uri);
        }
        return cursor;
    }

    /**
     * Insert new data into the provider with the given ContentValues.
     */
    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        final int match = sUsiMatcher.match(uri);
        switch (match){
            case BOOKS:
                return insertBook(uri, contentValues);
            default:
                throw new IllegalArgumentException("Insertion is not supported for" + uri);
        }
        //return null;
    }

    private Uri insertBook(Uri uri, ContentValues values){

        String name = values.getAsString(BookContract.BookEntry.COLUMN_BOOK_NAME);
        if (name == null){
            throw new IllegalArgumentException("Book requires a name");
        }
        Integer price = values.getAsInteger(BookContract.BookEntry.COLUMN_BOOK_PRICE);
        if (price != null && price < 0){
            throw new IllegalArgumentException("Book requires a price");
        }
        SQLiteDatabase database = mDbHelper.getWritableDatabase();
    long id = database.insert(BookContract.BookEntry.TABLE_NAME, null, values);
    if (id == -1){
        Log.e(LOG_TAG, "Failed to insert row for" + uri);
        return null;
    }
        return ContentUris.withAppendedId(uri,id);
    }
    /**
     * Updates the data at the given selection and selection arguments, with the new ContentValues.
     */
    @Override
    public int update(Uri uri, ContentValues contentValues, String selection, String[] selectionArgs) {
        return 0;
    }

    /**
     * Delete the data at the given selection and selection arguments.
     */
    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    /**
     * Returns the MIME type of data for the content URI.
     */
    @Override
    public String getType(Uri uri) {
        return null;
    }
}
