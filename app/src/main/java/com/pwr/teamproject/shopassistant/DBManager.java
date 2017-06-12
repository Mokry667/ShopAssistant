package com.pwr.teamproject.shopassistant;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by mokry on 11-Jun-17.
 */

public class DBManager extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "shoppingListDB";
    // Contacts table name
    private static final String TABLE_DBPRODUCT = "DBProduct";

    private int id;
    private String name;
    private double price;
    private String storeName;
    private String storeAddress;
    private double lat;
    private double lng;

    // DBProduct Table Columns names
    private static final String KEY_STOREPRODUCT_ID = "storeProductID";
    private static final String KEY_NAME = "name";
    private static final String KEY_PRICE = "price";
    private static final String KEY_STORE_NAME = "storeName";
    private static final String KEY_STORE_ADDRESS = "storeAddress";
    private static final String KEY_LAT = "lat";
    private static final String KEY_LNG = "lng";


    public DBManager(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        String CREATE_DB_PRODUCT_TABLE = "CREATE TABLE " + TABLE_DBPRODUCT + "(" + KEY_STOREPRODUCT_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
                + KEY_PRICE + " REAL," + KEY_STORE_NAME + " TEXT," + KEY_STORE_ADDRESS + " TEXT," + KEY_LAT + " REAL," + KEY_LNG + " REAL" + ")";
        db.execSQL(CREATE_DB_PRODUCT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DBPRODUCT);
    // Creating tables again
        onCreate(db);
    }

    public void addDBProduct(StoreProduct storeProduct){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_STOREPRODUCT_ID, storeProduct.getId());
        values.put(KEY_NAME, storeProduct.getProduct().getName());
        values.put(KEY_PRICE, storeProduct.getPrice());
        values.put(KEY_STORE_NAME, storeProduct.getStore().getName());
        values.put(KEY_STORE_ADDRESS, storeProduct.getStore().getAddress());
        values.put(KEY_LAT, storeProduct.getStore().getLat());
        values.put(KEY_LNG, storeProduct.getStore().getLng());

        db.insert(TABLE_DBPRODUCT, null, values);
        db.close();

    }

    public ArrayList<DBProduct> getAllProducts() {
        ArrayList<DBProduct> productList = new ArrayList<DBProduct>();

        String selectQuery = "SELECT * FROM " + TABLE_DBPRODUCT;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                DBProduct dbProduct = new DBProduct();
                dbProduct.setStoreProductID(Integer.parseInt(cursor.getString(0)));
                dbProduct.setName(cursor.getString(1));
                dbProduct.setPrice(cursor.getDouble(2));
                dbProduct.setStoreName(cursor.getString(3));
                dbProduct.setStoreAddress(cursor.getString(4));
                dbProduct.setLat(cursor.getDouble(5));
                dbProduct.setLng(cursor.getDouble(6));

                productList.add(dbProduct);

            } while (cursor.moveToNext());
        }

        return productList;
    }

    public void deleteDBProduct(DBProduct dbProduct) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_DBPRODUCT, KEY_STOREPRODUCT_ID + " = ?",
        new String[] { String.valueOf(dbProduct.getStoreProductID()) });
        db.close();
    }


}
