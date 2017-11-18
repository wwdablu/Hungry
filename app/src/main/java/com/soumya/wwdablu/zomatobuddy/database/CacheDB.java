package com.soumya.wwdablu.zomatobuddy.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CacheDB extends SQLiteOpenHelper {

    private static final String DB_NAME = "response_cache";
    private static final int DB_VERSION = 1;

    private static CacheDB instance;

    private CacheDB(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    public static synchronized void init(Context context) {

        if(null == instance) {
            instance = new CacheDB(context);
        }
    }

    public static synchronized CacheDB getInstance() {
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL(
            "CREATE TABLE " + DatabaseContract.ResponseCache.TABLE + " (" +
                DatabaseContract.ResponseCache.CATEGORY_ID + " TEXT PRIMARY KEY, " +
                DatabaseContract.ResponseCache.RESPONSE + " TEXT " +
                ");"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        //
    }

    public synchronized void cache(String id, String response) {

        SQLiteDatabase db = getWritableDatabase();

        Cursor cursor = db.query(DatabaseContract.ResponseCache.TABLE, null,
                DatabaseContract.ResponseCache.CATEGORY_ID + "=?", new String[] {id}, null,
                null, null);

        if(null != cursor && cursor.moveToFirst()) {

            ContentValues contentValues = new ContentValues();
            contentValues.put(DatabaseContract.ResponseCache.RESPONSE, response);

            db.update(DatabaseContract.ResponseCache.TABLE, contentValues, DatabaseContract.ResponseCache.CATEGORY_ID + "=?",
                    new String[] {id});

        } else {

            ContentValues contentValues = new ContentValues();
            contentValues.put(DatabaseContract.ResponseCache.CATEGORY_ID, id);
            contentValues.put(DatabaseContract.ResponseCache.RESPONSE, response);

            db.insert(DatabaseContract.ResponseCache.TABLE, null, contentValues);
        }

        if(null != cursor) {
            cursor.close();
        }

        db.close();
    }

    public synchronized String getFromCache(String id) {

        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.query(DatabaseContract.ResponseCache.TABLE, null,
                DatabaseContract.ResponseCache.CATEGORY_ID + "=?", new String[] {id}, null,
                null, null);

        String responseData = "";
        if(null != cursor && cursor.moveToFirst()) {

            responseData = cursor.getString(cursor.getColumnIndex(DatabaseContract.ResponseCache.RESPONSE));
        }

        if(null != cursor) {
            cursor.close();
        }

        db.close();
        return responseData;
    }

    public synchronized void purgeCache() {

        SQLiteDatabase db = getWritableDatabase();
        db.delete(DatabaseContract.ResponseCache.TABLE, null, null);
        db.close();
    }
}
