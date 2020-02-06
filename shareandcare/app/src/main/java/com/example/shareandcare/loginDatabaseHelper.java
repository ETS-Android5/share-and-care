package com.example.shareandcare;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class loginDatabaseHelper extends SQLiteOpenHelper {
    public loginDatabaseHelper(Context context){
        super(context,"Login.db",null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL("Create table user(username text primary key, password text, email text, number text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("drop table if exists user");
    }

    public boolean addAccount (String username, String password, String email, String number){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues =new ContentValues();
        contentValues.put("email",email);
        contentValues.put("password",password);
        contentValues.put("username",username);
        contentValues.put("number",number);
        long ins = db.insert("user",null,contentValues);
        if(ins==-1) return false;
        else return true;

    }
    // check if username exists
    public Boolean checkUsername(String username){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from user where username=?",new String[] {username});
        if (cursor.getCount()>0) return false;
        else return true;
    }
    // checking for username and password
    public Boolean checkAccount(String username, String password){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from user where username=? and password=?",new String[]{username,password});
        if (cursor.getCount()>0)return true;
        else return false;
    }
}
