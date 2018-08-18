package com.example.ndambuki.shoppingtracker.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.ListView;


import com.example.ndambuki.shoppingtracker.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 4;
    private static final String DATABASE_NAME = "contactsManager";
    //private SQLiteDatabase db;

    String SQL_CREATE_SCANNED_ITEM_TABLE = "CREATE TABLE " + ColumnsContract.ScannedItemEntry.TABLE_SCANNED_ITEMS + " ("
            + ColumnsContract.ScannedItemEntry.COLUMN_ITEM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + ColumnsContract.ScannedItemEntry.COLUMN_SCANNED_ITEMS_PRICE + " TEXT NOT NULL " + ")";
    String SQL_DROP_SCANNED_ITEM_TABLE = "DROP TABLE IF EXISTS "+ColumnsContract.ScannedItemEntry.TABLE_SCANNED_ITEMS;

    public DatabaseHelper (Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_SCANNED_ITEM_TABLE);
        Log.e("onCreate: ", "TABLE CREATED");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DROP_SCANNED_ITEM_TABLE);
        onCreate(db);

    }

    public long insertScannedItem(String amount){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ColumnsContract.ScannedItemEntry.COLUMN_SCANNED_ITEMS_PRICE, amount);
        long result = db.insert(ColumnsContract.ScannedItemEntry.TABLE_SCANNED_ITEMS,null,values);
        Log.e("saved_item", amount );
        db.close();
        return result;

    }

    public ArrayList<HashMap<String,String>> getAllAmounts(){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<HashMap<String,String>> amountsArray = new ArrayList<>();
        String query = "SELECT * FROM "+ ColumnsContract.ScannedItemEntry.TABLE_SCANNED_ITEMS;
        Cursor cursor = db.rawQuery(query,null);
        while (cursor.moveToNext()){
            HashMap<String,String> results = new HashMap<>();
            results.put("id",cursor.getString(cursor.getColumnIndex(ColumnsContract.ScannedItemEntry.COLUMN_ITEM_ID)));
            results.put("price",cursor.getString(cursor.getColumnIndex(ColumnsContract.ScannedItemEntry.COLUMN_SCANNED_ITEMS_PRICE)));
            amountsArray.add(results);
        }
        db.close();
        return amountsArray;
    }

}
