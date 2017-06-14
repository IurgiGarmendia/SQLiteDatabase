package com.example.sqlitedatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by innova4b on 13/06/17.
 */

public class DictionaryDatabase extends SQLiteOpenHelper {

    private static final String DATABASE_NAME="dictionary.db";
    private static final String TABLE_DICTIONARY="dictionary";
    private static final String FIELD_WORD="word";
    private static final String FIELD_DEFINITION="definition";
    private static final int DATAVASE_VERSION=1;

    public DictionaryDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATAVASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE "+ TABLE_DICTIONARY + " (_id integer PRIMARY KEY, "+
        FIELD_WORD + " TEXT, "+ FIELD_DEFINITION+ " TEXT);");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public  void saveRecord (String word, String definition){
        long id=findWordId(word);
        if (id>0){
            updateRecord(id, word, definition);
        }else{
            addRecord(word, definition);
        }
    }


    private long addRecord(String word, String definition) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(FIELD_WORD, word);
        values.put(FIELD_DEFINITION, definition);
        return db.insert(TABLE_DICTIONARY, null, values);
    }

    private int updateRecord(long id, String word, String definition) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("_id", id);
        values.put(FIELD_WORD, word);
        values.put(FIELD_DEFINITION, definition);

        return db.update(TABLE_DICTIONARY, values, "_id = ? ", new String[]{String.valueOf(id)});


    }

    private long findWordId(String word) {
        long returnVal = -1;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT _id FROM " + TABLE_DICTIONARY + " WHERE " + FIELD_WORD + " = ?", new String[]{word});
        if (cursor.getCount() == 1){
            cursor.moveToFirst();
            returnVal = cursor.getInt(0);
        }

        return returnVal;
    }

    public String getDefinition(long id){
        String  returnVal="";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor=db.rawQuery("Select definition from "+TABLE_DICTIONARY+" where _id= ?", new String[]{String.valueOf(id)});


        if (cursor.getCount() == 1){
            cursor.moveToFirst();
            returnVal = cursor.getString(0);
        }

        return  returnVal;
    }

    public Cursor getWordList(){
        SQLiteDatabase db = this.getReadableDatabase();
        String query ="Select _id, "+FIELD_WORD+" from "+TABLE_DICTIONARY +" order by "+FIELD_WORD+ " asc";
        return  db.rawQuery(query, null);
    }

    public int deleteRecor(long id) {
        SQLiteDatabase db =getReadableDatabase();
        return db.delete(TABLE_DICTIONARY, "_id= ?", new String[]{String.valueOf(id)});
    }
}
