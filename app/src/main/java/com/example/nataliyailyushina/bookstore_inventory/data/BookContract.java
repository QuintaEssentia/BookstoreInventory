package com.example.nataliyailyushina.bookstore_inventory.data;

import android.provider.BaseColumns;

public class BookContract {
    private BookContract() {}

    //Product Name, Price, Quantity, Supplier Name, and Supplier Phone Number
    public static abstract class BookEntry implements BaseColumns {
        public static final String TABLE_NAME = "books";
        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_BOOK_NAME = "name";
        public static final String COLUMN_BOOK_PRICE = "price";
        public static final String COLUMN_BOOK_QUANTITY= "quantity";
        public static final String COLUMN_SUPPLIER_NAME = "supplier_name";
        public static final String COLUMN_SUPPLIER_PHONE = "supplier_phone";


    }
}
