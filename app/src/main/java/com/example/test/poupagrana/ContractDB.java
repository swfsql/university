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
        public static final String COLUMN_NAME_PRICE = "price";
        public static final String COLUMN_NAME_SUPERMARKET = "supermarket_id";
        public static final String COLUMN_NAME_UPDATE_DATE = "update_date";

    }

    public static abstract class SupermarketEntry implements BaseColumns {
        public static final String TABLE_NAME = "supermarket";
        public static final String COLUMN_NAME_SUPERMARKET_ID = "supermarket_id";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_ADDRESS = "address";
    }

    private static final String TEXT_TYPE = " TEXT";
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
                    ItemEntry.COLUMN_NAME_PRICE + INTEGER_TYPE + NOT_NULL + COMMA_SEP +
                    ItemEntry.COLUMN_NAME_SUPERMARKET + INTEGER_TYPE + NOT_NULL + COMMA_SEP +
                    ItemEntry.COLUMN_NAME_UPDATE_DATE + DATE_TYPE + NOT_NULL + //COMMA_SEP +
                    /*PRIMARY_KEY + "(" + ItemEntry.COLUMN_NAME_ITEM_ID + ")" + COMMA_SEP +
                    INDEX + ItemEntry.COLUMN_NAME_SUPERMARKET + "x" + "(" + ItemEntry.COLUMN_NAME_SUPERMARKET + " ASC)" + COMMA_SEP +
                    CONSTRAINT + ItemEntry.COLUMN_NAME_SUPERMARKET +
                    FOREIGN_KEY + "(" + ItemEntry.COLUMN_NAME_SUPERMARKET + ")" +
                    REFERENCES + ItemEntry.TABLE_NAME + "." + SupermarketEntry.TABLE_NAME + " (" + SupermarketEntry.COLUMN_NAME_SUPERMARKET_ID + ")" +
                    ON_DELETE + NO_ACTION +
                    ON_UPDATE + NO_ACTION +*/
                    " )";

    static final String SQL_CREATE_SUPERMARKET_TABLE =
            "CREATE TABLE IF NOT EXISTS " + SupermarketEntry.TABLE_NAME + " (" +
                    SupermarketEntry.COLUMN_NAME_SUPERMARKET_ID + INTEGER_TYPE + PRIMARY_KEY + AUTO_INCREMENT + NOT_NULL + COMMA_SEP +
                    SupermarketEntry.COLUMN_NAME_NAME + VARCHAR_TYPE + NOT_NULL + COMMA_SEP +
                    SupermarketEntry.COLUMN_NAME_ADDRESS + VARCHAR_TYPE + NOT_NULL + COMMA_SEP +
                    PRIMARY_KEY + "(" + SupermarketEntry.COLUMN_NAME_SUPERMARKET_ID + ")" + COMMA_SEP +
                    " )";

    static final String SQL_DELETE_ENTRIES =
                    "DROP TABLE IF EXISTS " + ItemEntry.TABLE_NAME + ";" +
                    "DROP TABLE IF EXISTS " + SupermarketEntry.TABLE_NAME;

}
