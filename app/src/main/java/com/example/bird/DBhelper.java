package com.example.bird;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class DBhelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "DBTAMZ_GAME_01.db";
    private static final String TABLE_NAME = "scoreTable";
    //private static final String COL1 = "ID";
    //private static final String COL2 = "SCORE";

    public static final String ITEM_COLUMN_NAME = "name";
    public static final String ITEM_COLUMN_SCORE = "score";

    public DBhelper(Context context){
        super(context, DATABASE_NAME, null, 1);
    }

   /* @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = " CREATE TABLE " + TABLE_NAME + "(ID INTEGER PRIMARY KEY AUTOINCREMENT, "+ COL2 + "INTEGER)";
        db.execSQL(createTable);
    }
*/
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE items " + "(id INTEGER PRIMARY KEY, name TEXT, score INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS items");
        onCreate(db);
    }


    public boolean addData(int score, String name)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("score", score);
        long insertedId = db.insert("items", null, contentValues);
        if (insertedId == -1) return false;
        return true;
    }

    public ArrayList<String> getItemList()
    {
        ArrayList<String> arrayList = new ArrayList<String>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from items", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            
            String name = res.getString(res.getColumnIndex(ITEM_COLUMN_NAME));
            String score = res.getString(res.getColumnIndex(ITEM_COLUMN_SCORE));
            //int id = res.getInt(0);
            arrayList.add("name: " + name + " --- score: " + score);
            res.moveToNext();
        }

        return arrayList;
    }
    public int removeAll()    {

        SQLiteDatabase db = this.getReadableDatabase();
        int nRecordDeleted = db.delete("items", null, null);
        return nRecordDeleted;
    }


    /*
    public boolean addData(int item){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL2, item);
        Log.d(TAG, "addData: Adding " + item );
        long result = db.insert(TABLE_NAME, null, contentValues);
         if (result == 1){
             return false;
         }else{return true;}

    }

     */
}
