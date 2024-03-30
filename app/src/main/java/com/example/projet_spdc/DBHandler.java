package com.example.projet_spdc;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DBHandler extends SQLiteOpenHelper {
    //change version when upgraded
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Favoris.db";

    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + DBContract.favMP.TABLE_NAME + " (" + DBContract.favMP._ID + " INTEGER PRIMARY KEY AUTOINCREMENT" + ")");
        db.execSQL("CREATE TABLE " + DBContract.favGroup.TABLE_NAME + " (" + DBContract.favGroup._ID + " INTEGER PRIMARY KEY AUTOINCREMENT" + ")");
        db.execSQL("CREATE TABLE " + DBContract.favLaw.TABLE_NAME + " (" + DBContract.favLaw._ID + " INTEGER PRIMARY KEY AUTOINCREMENT" + ")");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DBContract.favMP.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + DBContract.favGroup.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + DBContract.favLaw.TABLE_NAME);
        onCreate(db);
    }

    public void insertFavMP(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues row = new ContentValues();
        row.put(DBContract.favMP._ID, id);

        long newRowId = db.insert(DBContract.favMP.TABLE_NAME,null,row);
    }

    public void insertFavGroup(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues row = new ContentValues();
        row.put(DBContract.favGroup._ID, id);

        long newRowId = db.insert(DBContract.favGroup.TABLE_NAME,null,row);
    }

    public void insertFavLaw(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues row = new ContentValues();
        row.put(DBContract.favLaw._ID, id);

        long newRowId = db.insert(DBContract.favLaw.TABLE_NAME,null,row);
    }

    /*public List<Depute> selectAllFavMP() {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(
                DBContract.favMP.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null
        );

        List<Depute> MPs = new ArrayList<>();

        while(cursor.moveToNext()){
            int indexId = cursor.getColumnIndex(DBContract.favMP._ID);

            int id = (int) cursor.getLong((indexId));

            Depute tmp = new Depute(id);
            MPs.add(tmp);
        }
        cursor.close();

        return MPs;
    }

    public List<Groupe> selectAllFavGroup() {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(
                DBContract.favMP.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null
        );

        List<Groupe> Groups = new ArrayList<>();

        while(cursor.moveToNext()){
            int indexId = cursor.getColumnIndex(DBContract.favMP._ID);

            int id = (int) cursor.getLong((indexId));

            Groupe tmp = new Groupe(id);
            Groups.add(tmp);
        }
        cursor.close();

        return Groups;
    }

    /*public List<Law> selectAllFavLaw() {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(
                DBContract.favMP.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null
        );

        List<Law> Laws = new ArrayList<>();

        while(cursor.moveToNext()){
            int indexId = cursor.getColumnIndex(DBContract.favMP._ID);

            int id = (int) cursor.getLong((indexId));

            Law tmp = new Law(id);
            Laws.add(tmp);
        }
        cursor.close();

        return Laws;
    }*/

    public void deleteFavMP(int id){
        SQLiteDatabase db = this.getWritableDatabase();

        String selection = "id = ?";

        String[] selectionArgs = {String.valueOf(id)};

        int count = db.delete(DBContract.favMP.TABLE_NAME, selection, selectionArgs);
    }

    public void deleteFavGroup(int id){
        SQLiteDatabase db = this.getWritableDatabase();

        String selection = "id = ?";

        String[] selectionArgs = {String.valueOf(id)};

        int count = db.delete(DBContract.favGroup.TABLE_NAME, selection, selectionArgs);
    }

    public void deleteFavLaw(int id){
        SQLiteDatabase db = this.getWritableDatabase();

        String selection = "id = ?";

        String[] selectionArgs = {String.valueOf(id)};

        int count = db.delete(DBContract.favLaw.TABLE_NAME, selection, selectionArgs);
    }
}

