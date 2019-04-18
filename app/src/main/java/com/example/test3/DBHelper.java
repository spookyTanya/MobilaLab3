package com.example.test3;

/*import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.provider.BaseColumns;
import android.database.Cursor;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "labka3.db";
    private static final String TABLE_NAME = "users";
    private static final String USER_ID = "Id";
    private static final String USER_COLUMN_NAME = "username";
    private static final String USER_COLUMN_PASSWORD = "password";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " ("
            + USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + USER_COLUMN_NAME + " TEXT NOT NULL, "
            + USER_COLUMN_PASSWORD + " TEXT NOT NULL);");
//        db.execSQL("CREATE TABLE users ( INTEGER PRIMARY  KEY AUTOINCREMENT, username TEXT NOT NULL, password TEXT NOT NULL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    long addUser(String name, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", name);
        contentValues.put("password", password);

        long res = db.insert(TABLE_NAME, null, contentValues);
        db.close();
        return  res;
    }

    boolean checkUser(String name, String password) {
//        String[] columns = { USER_ID };
        SQLiteDatabase db = getReadableDatabase();
        *//*String selection = USER_COLUMN_NAME + "=?" + " and " + USER_COLUMN_PASSWORD + "=?";
        String[] selectionArgs = { name, password };
        Cursor cursor = db.query(TABLE_NAME, columns, selection, selectionArgs,null,null, null);
        int count = cursor.getCount();
        cursor.close();
        db.close();*//*

        String query = "SELECT " + USER_ID + " FROM " + TABLE_NAME;
        Cursor cursor2 = db.rawQuery(query, null);
        int count2 = cursor2.getCount();
        cursor2.close();
        db.close();

        return count2 > 0;



//        return true;
    }



}*/

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper{
    public static final String DATABASE_NAME ="labka3.db";
    private static final int DATABASE_VERSION = 3;
    public static final String TABLE_NAME ="users";
    public static final String USER_ID ="Id";
    public static final String USER_COLUMN_NAME ="username";
    public static final String USER_COLUMN_PASSWORD ="password";
    public static final String TABLE_TASKS = "tasks";
    public static final String TASK_ID = "Id";
    public static final String TASK_USER_ID = "UserId";
    public static final String TASK_NAME = "name";
    public static final String TASK_TYPE = "type";

    private static final String TAG = DBHelper.class.getSimpleName();


    private static final String tt = "CREATE TABLE " + TABLE_NAME + " ("
            + USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + USER_COLUMN_NAME + " TEXT NOT NULL, "
            + USER_COLUMN_PASSWORD + " TEXT NOT NULL);";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        /*sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_NAME + " ("
                + USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + USER_COLUMN_NAME + " TEXT NOT NULL, "
                + USER_COLUMN_PASSWORD + " TEXT NOT NULL);");*/
        sqLiteDatabase.execSQL("CREATE TABLE tasks ("
                + TASK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + TASK_USER_ID + " INTEGER , "
                + TASK_NAME + " TEXT NOT NULL, "
                + TASK_TYPE + " TEXT NOT NULL);");
        sqLiteDatabase.execSQL(tt);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(" DROP TABLE IF EXISTS " + TABLE_NAME);
        sqLiteDatabase.execSQL(" DROP TABLE IF EXISTS " + TABLE_TASKS);
        onCreate(sqLiteDatabase);
    }

    public long addUser(String user, String password){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username",user);
        contentValues.put("password",password);
        long res = db.insert("users",null,contentValues);
        db.close();
        return  res;
    }

    int checkUser(String name, String password) {
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT 'Id' FROM " + TABLE_NAME + " WHERE " + USER_COLUMN_NAME + " = '" + name + "' AND " + USER_COLUMN_PASSWORD + " = '" + password + "'";
        Cursor cursor2 = db.rawQuery(query, null);
        int count2 = cursor2.getCount();
        int res;
        cursor2.moveToFirst();

        if(count2 > 0) {
            res = cursor2.getInt(0);
        } else {
            res = -1;
        }
        cursor2.close();
        db.close();

        return res;
    }

    public long addTask(String name, String type, int UserId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("type", type);
        contentValues.put("UserId", UserId);
        long res = db.insert("tasks",null,contentValues);
        db.close();
        return  res;
    }

    public String[] getListData(String param, int userId){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM tasks WHERE " + TASK_TYPE + " = '" + param + "' AND " + TASK_USER_ID + " = '" + userId + "'";
        Cursor cursor = db.rawQuery(query, null);
        List<String> names = new ArrayList<String>();

        if(cursor.getCount() != 0) {
            if (cursor.moveToFirst()) {
                do {
                    names.add(cursor.getString(2));
                } while (cursor.moveToNext());
            }
        } else {
            Log.d(TAG, "empty yopta");
            names.add("No tasks here, folks");
        }

        String[] myArray = new String[names.size()];
        names.toArray(myArray);

        cursor.close();
        db.close();

        return myArray;
    }

    public List<TaskModel> getAllData() {
        List<TaskModel> data = new ArrayList<TaskModel>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM tasks WHERE " + TASK_TYPE + " = 'Important Urgent'";
        Cursor cursor = db.rawQuery(query, null);

        try {
            if(cursor.moveToFirst()) {
                do{
                    TaskModel taskmodel = new TaskModel(cursor.getString(1), cursor.getString(2));
                    data.add(taskmodel);
                } while (cursor.moveToNext());
            }
        } finally {
            cursor.close();
        }
        return data;
    }
}
