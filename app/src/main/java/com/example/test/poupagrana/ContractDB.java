package com.example.test.poupagrana;

import android.provider.BaseColumns;

public class ContractDB {
    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.

    public ContractDB() {
    }

    /* Inner class that defines the table contents */
    public static abstract class ItemEntry implements BaseColumns {
        public static final String TABLE_NAME = "item";
        public static final String COLUMN_NAME_ITEM_ID = "item_id";
        public static final String COLUMN_NAME_NAME = "name";
        //public static final String COLUMN_NAME_PRICE = "price";
        //public static final String COLUMN_NAME_SUPERMARKET = "supermarket_id";
        //public static final String COLUMN_NAME_UPDATE_DATE = "update_date";

    }
    public static abstract class ItemInListEntry implements BaseColumns {
        public static final String TABLE_NAME = "item_in_list";
        public static final String COLUMN_NAME_ITEM_ID = "item_id";
        public static final String COLUMN_NAME_LIST_ID = "list_id";
        public static final String COLUMN_NAME_QUANTITY = "quantity";
        public static final String COLUMN_NAME_MAX_QUANTITY = "max_quantity";
    }
    public static abstract class ListEntry implements BaseColumns {
        public static final String TABLE_NAME = "list";
        public static final String COLUMN_NAME_LIST_ID = "list_id";
        public static final String COLUMN_NAME_INFO = "info";
        public static final String COLUMN_NAME_DATE_CREATED = "date_created";
        public static final String COLUMN_NAME_DATE_MODIFIED = "date_modified";
        public static final String COLUMN_NAME_PRICE = "price";
        public static final String COLUMN_NAME_ACHIEVED = "achieved";
    }
    public static abstract class ItemInSupplierItemEntry implements BaseColumns {
        public static final String TABLE_NAME = "item_in_supplier_item";
        public static final String COLUMN_NAME_ITEM_ID = "item_id";
        public static final String COLUMN_NAME_SUPPLIER_ITEM_ID = "supplier_item_id";
    }

    public static abstract class SupplierEntry implements BaseColumns {
        public static final String TABLE_NAME = "supplier";
        public static final String COLUMN_NAME_SUPPLIER_ID = "supplier_id";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_INFO = "info";
        public static final String COLUMN_NAME_ADDRESS = "address";
    }
    public static abstract class SupplierItemEntry implements BaseColumns {
        public static final String TABLE_NAME = "supplier_item";
        public static final String COLUMN_NAME_SUPPLIER_ITEM_ID = "supplier_item_id";
        public static final String COLUMN_NAME_SUPPLIER_ID = "supplier_id";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_INFO = "info";
        public static final String COLUMN_NAME_PRICE = "price";
        public static final String COLUMN_NAME_DATE_MODIFIED = "date_modified";
    }

    private static final String TEXT_TYPE = " TEXT";
    private static final String BOOL_TYPE = " INTEGER";
    private static final String INTEGER_TYPE = " INTEGER";
    private static final String VARCHAR_TYPE = " VARCHAR(150)";
    private static final String DATE_TYPE = " DATE";
    private static final String NOT_NULL = " NOT NULL";
    private static final String PRIMARY_KEY = " PRIMARY KEY";
    private static final String AUTO_INCREMENT = " AUTOINCREMENT";
    private static final String INDEX = " INDEX ";
    private static final String CONSTRAINT = " CONSTRAINT  ";
    private static final String FOREIGN_KEY = " FOREIGN KEY ";
    private static final String REFERENCES = " REFERENCES ";
    private static final String ON_DELETE = " ON DELETE ";
    private static final String ON_UPDATE = " ON UPDATE ";
    private static final String NO_ACTION = " NO ACTION ";
    private static final String COMMA_SEP = ",";

    static final String SQL_CREATE_ITEM_TABLE =
            "CREATE TABLE IF NOT EXISTS " + ItemEntry.TABLE_NAME + " (" +
                    ItemEntry.COLUMN_NAME_ITEM_ID + INTEGER_TYPE + PRIMARY_KEY + AUTO_INCREMENT + NOT_NULL + COMMA_SEP +
                    ItemEntry.COLUMN_NAME_NAME + VARCHAR_TYPE + NOT_NULL + COMMA_SEP +
                    //ItemEntry.COLUMN_NAME_PRICE + INTEGER_TYPE + NOT_NULL + COMMA_SEP +
                    //ItemEntry.COLUMN_NAME_SUPERMARKET + INTEGER_TYPE + NOT_NULL + COMMA_SEP +
                    //ItemEntry.COLUMN_NAME_UPDATE_DATE + DATE_TYPE + NOT_NULL + //COMMA_SEP +
                    /*PRIMARY_KEY + "(" + ItemEntry.COLUMN_NAME_ITEM_ID + ")" + COMMA_SEP +
                    INDEX + ItemEntry.COLUMN_NAME_SUPERMARKET + "x" + "(" + ItemEntry.COLUMN_NAME_SUPERMARKET + " ASC)" + COMMA_SEP +
                    CONSTRAINT + ItemEntry.COLUMN_NAME_SUPERMARKET +
                    FOREIGN_KEY + "(" + ItemEntry.COLUMN_NAME_SUPERMARKET + ")" +
                    REFERENCES + ItemEntry.TABLE_NAME + "." + SupermarketEntry.TABLE_NAME + " (" + SupermarketEntry.COLUMN_NAME_SUPERMARKET_ID + ")" +
                    ON_DELETE + NO_ACTION +
                    ON_UPDATE + NO_ACTION +*/
                    " )";

    static final String SQL_CREATE_ITEM_IN_LIST_TABLE =
            "CREATE TABLE IF NOT EXISTS " + ItemInListEntry.TABLE_NAME + " (" +
                    ItemEntry.COLUMN_NAME_ITEM_ID + INTEGER_TYPE + NOT_NULL + COMMA_SEP +
                    ListEntry.COLUMN_NAME_LIST_ID + INTEGER_TYPE + NOT_NULL + COMMA_SEP +
                    ItemInListEntry.COLUMN_NAME_QUANTITY + INTEGER_TYPE + NOT_NULL + COMMA_SEP +
                    ItemInListEntry.COLUMN_NAME_MAX_QUANTITY + INTEGER_TYPE + NOT_NULL + COMMA_SEP +
                    " )";
    static final String SQL_CREATE_LIST_TABLE =
            "CREATE TABLE IF NOT EXISTS " + ListEntry.TABLE_NAME + " (" +
                    ListEntry.COLUMN_NAME_LIST_ID + INTEGER_TYPE + PRIMARY_KEY + AUTO_INCREMENT + NOT_NULL + COMMA_SEP +
                    ListEntry.COLUMN_NAME_INFO + VARCHAR_TYPE + COMMA_SEP +
                    ListEntry.COLUMN_NAME_DATE_CREATED + DATE_TYPE + NOT_NULL + COMMA_SEP +
                    ListEntry.COLUMN_NAME_DATE_MODIFIED + DATE_TYPE + NOT_NULL + COMMA_SEP +
                    ListEntry.COLUMN_NAME_PRICE + INTEGER_TYPE + COMMA_SEP +
                    ListEntry.COLUMN_NAME_ACHIEVED + BOOL_TYPE + COMMA_SEP +
                    " )";
    static final String SQL_CREATE_ITEM_IN_SUPPLIER_ITEM_TABLE =
            "CREATE TABLE IF NOT EXISTS " + ItemInSupplierItemEntry.TABLE_NAME + " (" +
                    ItemEntry.COLUMN_NAME_ITEM_ID + INTEGER_TYPE + NOT_NULL + COMMA_SEP +
                    ItemInSupplierItemEntry.COLUMN_NAME_SUPPLIER_ITEM_ID + INTEGER_TYPE + NOT_NULL + COMMA_SEP +
                    " )";
    static final String SQL_CREATE_SUPPLIER_TABLE =
            "CREATE TABLE IF NOT EXISTS " + SupplierEntry.TABLE_NAME + " (" +
                    SupplierEntry.COLUMN_NAME_SUPPLIER_ID + INTEGER_TYPE + PRIMARY_KEY + AUTO_INCREMENT + NOT_NULL + COMMA_SEP +
                    SupplierEntry.COLUMN_NAME_NAME + VARCHAR_TYPE + NOT_NULL + COMMA_SEP +
                    SupplierEntry.COLUMN_NAME_INFO + VARCHAR_TYPE + NOT_NULL + COMMA_SEP +
                    SupplierEntry.COLUMN_NAME_ADDRESS + VARCHAR_TYPE + NOT_NULL + COMMA_SEP +
                    PRIMARY_KEY + "(" + SupplierEntry.COLUMN_NAME_SUPPLIER_ID + ")" + COMMA_SEP +
                    " )";
    static final String SQL_CREATE_SUPPLIER_ITEM_TABLE =
            "CREATE TABLE IF NOT EXISTS " + SupplierItemEntry.TABLE_NAME + " (" +
                    SupplierItemEntry.COLUMN_NAME_SUPPLIER_ITEM_ID + INTEGER_TYPE + PRIMARY_KEY + AUTO_INCREMENT + NOT_NULL + COMMA_SEP +
                    SupplierEntry.COLUMN_NAME_SUPPLIER_ID + INTEGER_TYPE + NOT_NULL + COMMA_SEP +
                    SupplierItemEntry.COLUMN_NAME_NAME + VARCHAR_TYPE + COMMA_SEP +
                    SupplierItemEntry.COLUMN_NAME_INFO + VARCHAR_TYPE + COMMA_SEP +
                    SupplierItemEntry.COLUMN_NAME_PRICE + INTEGER_TYPE + COMMA_SEP +
                    SupplierItemEntry.COLUMN_NAME_DATE_MODIFIED + DATE_TYPE + NOT_NULL + COMMA_SEP +
                    " )";

    static final String SQL_DELETE_ENTRIES =
                    "DROP TABLE IF EXISTS " + ItemEntry.TABLE_NAME + ";" +
                            "DROP TABLE IF EXISTS " + ItemInListEntry.TABLE_NAME + ";" +
                            "DROP TABLE IF EXISTS " + ListEntry.TABLE_NAME + ";" +
                            "DROP TABLE IF EXISTS " + ItemInSupplierItemEntry.TABLE_NAME + ";" +
                            "DROP TABLE IF EXISTS " + SupplierEntry.TABLE_NAME + ";" +
                            "DROP TABLE IF EXISTS " + SupplierItemEntry.TABLE_NAME;
}
