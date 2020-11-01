package com.example.appp3;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.appp3.model.User;

public class UserSQLiteHelper extends SQLiteOpenHelper {
    private Context context;
    private static final String DATABASE_NAME = "usersVault.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "users";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_PASSWORD = "user_password";
    private static final String COLUMN_NOFTIMES = "user_noftimes";

    public UserSQLiteHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME +
                " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_PASSWORD + " TEXT, " +
                COLUMN_NOFTIMES + " INTEGER);";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    void addUser(String password, int noftimes) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_PASSWORD, password);
        cv.put(COLUMN_NOFTIMES, noftimes);
        long result = db.insert(TABLE_NAME, null, cv);
        if (result == -1) {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Enjoy! ;)", Toast.LENGTH_SHORT).show();
        }
    }

    Cursor readUser()
    {
        String query = "SELECT " + COLUMN_ID +", " + COLUMN_PASSWORD + ", " + COLUMN_NOFTIMES + " FROM " + TABLE_NAME + " WHERE " +
                COLUMN_ID + "=1";
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = null;
        if(db!=null)
        {
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

}
