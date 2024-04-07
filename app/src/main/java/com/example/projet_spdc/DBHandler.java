package com.example.projet_spdc;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DBContract.favMP.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + DBContract.favGroup.TABLE_NAME);
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

        Log.d("Ajout ID : ", id + "");

        long newRowId = db.insert(DBContract.favGroup.TABLE_NAME,null,row);

        Log.d("Ajout group : ", newRowId + "");
    }

    public List<Depute> selectAllFavMP() {
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

            Depute tmp = Depute.getListDepute().stream().filter(d -> d.getId() == id).findAny().orElse(null);

            if(tmp != null)
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

        Groupe.listeGroupe.stream().forEach(g -> Log.d("GrId : ", g.getId() + ""));

        while(cursor.moveToNext()){
            int indexId = cursor.getColumnIndex(DBContract.favMP._ID);

            int id = (int) cursor.getLong((indexId));

            Log.d("registerIDGr : ", id + "");

            Groupe tmp = Groupe.listeGroupe.stream().filter(g -> g.getId() == id).findAny().orElse(null);

            if(tmp != null)
                Groups.add(tmp);
        }
        cursor.close();

        return Groups;
    }

    public void deleteFavMP(int id){
        SQLiteDatabase db = this.getWritableDatabase();

        int count = db.delete(DBContract.favMP.TABLE_NAME, DBContract.favMP._ID + " = " + id, null);
    }

    public void deleteFavGroup(int id){
        SQLiteDatabase db = this.getWritableDatabase();

        int count = db.delete(DBContract.favGroup.TABLE_NAME, DBContract.favGroup._ID + " = " + id, null);
    }
}

