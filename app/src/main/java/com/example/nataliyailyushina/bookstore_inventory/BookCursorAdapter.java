package com.example.nataliyailyushina.bookstore_inventory;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nataliyailyushina.bookstore_inventory.data.BookContract;

public class BookCursorAdapter extends CursorAdapter {
    Context currentContext;
    public BookCursorAdapter(Context context, Cursor c){
        super(context, c, 0);
        currentContext = context;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent){
        return LayoutInflater.from(context).inflate(R.layout.list_item,parent,false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor){
        TextView nameTextView = view.findViewById(R.id.name);
        TextView priceTextView = view.findViewById(R.id.price);
        TextView quantityTextView = view.findViewById(R.id.quantity);

        int nameColumnIndex = cursor.getColumnIndex(BookContract.BookEntry.COLUMN_BOOK_NAME);
        int priceColumnIndex = cursor.getColumnIndex(BookContract.BookEntry.COLUMN_BOOK_PRICE);
        int quantityColumnIndex = cursor.getColumnIndex(BookContract.BookEntry.COLUMN_BOOK_QUANTITY);

        String bookName = cursor.getString(nameColumnIndex);
        String bookPrice = cursor.getString(priceColumnIndex);
        String bookQuantity = cursor.getString(quantityColumnIndex);

        nameTextView.setText(bookName);
        priceTextView.setText(bookPrice);
        quantityTextView.setText(bookQuantity);

    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View view = super.getView(position, convertView, parent);
        final TextView currentItemQuantityTV = view.findViewById(R.id.quantity);
        final TextView currentIdTextView = view.findViewById(R.id.product_id_tv);

        Button btn = view.findViewById(R.id.sale);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strProductId = currentIdTextView.getText().toString();
                long _id = Long.parseLong(strProductId);
                Uri currentItemUri = ContentUris.withAppendedId(BookContract.BookEntry.CONTENT_URI, _id);
                String strCurrentItemQuantity = currentItemQuantityTV.getText().toString();
                int currentItemQuantity = Integer.parseInt(strCurrentItemQuantity);
                currentItemQuantity--;
                ContentValues newQuantityValue = new ContentValues();
                newQuantityValue.put(BookContract.BookEntry.COLUMN_BOOK_QUANTITY, currentItemQuantity);
                int rowUpdated = currentContext.getContentResolver().update(currentItemUri, newQuantityValue, null, null);
                if (rowUpdated != 0) {
                    currentItemQuantityTV.setText(String.valueOf(currentItemQuantity));
                    Toast.makeText(currentContext, R.string.item_sold, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(currentContext, R.string.item_not_sold, Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }
}



