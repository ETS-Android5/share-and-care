package com.example.furnituredonation;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class FurnitureHelper  extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "furniturelist.db";
    private static final int SCHEMA_VERSION = 2;

    public FurnitureHelper(Context context){
        super(context, DATABASE_NAME, null, SCHEMA_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL("CREATE TABLE furnitures_table (_id INTEGER PRIMARY KEY AUTOINCREMENT, furnitureDetails TEXT, furnitureMeasurement TEXT,furnitureType TEXT, furnitureImage TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){}

    public Cursor getAll(){
        return (getReadableDatabase().rawQuery("SELECT _id, furnitureDetails, furnitureMeasurement,furnitureType,furnitureImage FROM furnitures_table ORDER BY furnitureDetails",null));
    }

    public Cursor getById(String id){
        String[] args = {id};

        return (getReadableDatabase().rawQuery("SELECT _id, furnitureDetails, furnitureMeasurement,furnitureType,furnitureImage FROM furnitures_table WHERE _ID=?" , args ));
    }

    public void insert (String furnitureDetails, String furnitureMeasurement, String furnitureType, String furnitureImage){
            ContentValues cv = new ContentValues();

        cv.put("furnitureDetails", furnitureDetails);
        cv.put("furnitureMeasurement", furnitureMeasurement);
        cv.put("furnitureType", furnitureType);
        cv.put("furnitureImage", furnitureImage);


        getWritableDatabase().insert("furnitures_table","furnitureDetails",cv);
    }

    public void update (String id, String furnitureDetails, String furnitureMeasurement, String furnitureType ){
        ContentValues cv = new ContentValues();
        String[] args = {id};
        cv.put("furnitureDetails", furnitureDetails);
        cv.put("furnitureMeasurement", furnitureMeasurement);
        cv.put("furnitureType", furnitureType);


        getWritableDatabase().update("furnitures_table",cv,"_ID=?",args);
    }

    public void delete (String id){
        String[] args= {id};
        getWritableDatabase().delete("furnitures_table","_ID=?",args);
    }

    public String getID (Cursor c) {return (c.getString(0));}
    public String getfurnitureDetails (Cursor c){
        return (c.getString(1));
    }
    public String getfurnitureMeasurement (Cursor c){
        return (c.getString(2));
    }
    public String getfurnitureType (Cursor c){
        return (c.getString(3));
    }
    public String getfurnitureImage (Cursor c){ return (c.getString(4)); }



}
