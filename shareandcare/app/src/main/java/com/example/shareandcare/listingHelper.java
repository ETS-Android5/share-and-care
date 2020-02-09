package com.example.shareandcare;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class listingHelper extends SQLiteOpenHelper {
    public listingHelper(Context context) {
        super(context, "listing.db", null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table listing(_id integer primary key autoincrement, listingTitle text, listingTag text, listingDescription text, listingDimensionX float, listingDimensionY float, listingDimensionZ float, listingImage text, listingStatus text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("drop table if exists listing");
        onCreate(db);
    }

    public Cursor getAll() {
        return (getReadableDatabase().rawQuery("select _id, listingTitle, listingTag, listingDescription, listingDimensionX, listingDimensionY, listingDimensionZ, listingImage from listing order by listingTag", null));
    }

    public Cursor getAllConfirmed() {
        String arg = "1";
        String[] args = {arg};
        return (getReadableDatabase().rawQuery("select _id, listingTitle, listingTag, listingDescription, listingDimensionX, listingDimensionY, listingDimensionZ, listingImage from listing where listingStatus=?", args));
    }

    public Cursor getById(String id) {
        String[] args = {id};

        return (getReadableDatabase().rawQuery("select _id, listingTitle, listingTag, listingDescription, lisitngDimensionX, listingDimensionY, listingDimensionZ, listingImage from listing where _id=?", args));
    }

    public Cursor getByTitle(String title) {
        String[] args = {title};

        return (getReadableDatabase().rawQuery("select _id, listingTitle, listingTag, listingDescription, listingDimensionX, listingDimensionY, listingDimensionZ, listingImage from listing where listingTitle =?", args));
    }

    public Cursor getByTag(String tag) {
        tag = tag.toLowerCase();
        String[] args = {tag};

        return (getReadableDatabase().rawQuery("select _id, listingTitle, listingTag, listingDescription, listingDimensionX, listingDimensionY, lisitngDimensionZ, listingImage from listing where listingTag =?", args));
    }

    public void updateListingStatus(String title) {
        ContentValues cv = new ContentValues();
        String[] args ={title};

        cv.put("listingStatus", "1");
        getWritableDatabase().update("listing", cv, "listingTitle=?", args);
    }

    public void addListing(String listingTitle, String listingTag, String listingDescription, String listingDimensionX, String listingDimensionY, String listingDimensionZ, String listingImage) {
        ContentValues cv = new ContentValues();

        cv.put("listingTitle", listingTitle);
        cv.put("listingTag", listingTag.toLowerCase());
        cv.put("listingDescription", listingDescription);
        cv.put("listingDimensionX", listingDimensionX);
        cv.put("listingDimensionY", listingDimensionY);
        cv.put("listingDimensionZ", listingDimensionZ);
        cv.put("listingImage", listingImage);

        getWritableDatabase().insert("listing", "listingTitle", cv);
    }

    public void delete(String id) {
        String[] args = {id};
        getWritableDatabase().delete("listing","_id=?", args);
    }

    public String getID(Cursor c) {
        return (c.getString(0));
    }

    public String getListingTitle (Cursor c) {
        return (c.getString(1));
    }

    public String getListingTag (Cursor c) {
        return (c.getString(2));
    }

    public String getListingDesciption (Cursor c) {
        return (c.getString(3));
    }

    public String getListingDimensionX (Cursor c) {
        return (c.getString(4));
    }

    public String getListingDimensionY (Cursor c) {
        return (c.getString(5));
    }

    public String getListingDimensionZ (Cursor c) {
        return (c.getString(6));
    }

    public String getListingImage (Cursor c) {
        return (c.getString(7));
    }

    public void resetDatabase() {
        getWritableDatabase().execSQL("drop table if exists listing");
    }
}
