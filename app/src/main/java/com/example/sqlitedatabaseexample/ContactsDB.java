package com.example.sqlitedatabaseexample;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class ContactsDB {

    public static final String KEY_ROWID = "_id";
    public static final String KEY_NAME = "person_name";
    public static final String KEY_NUMBER ="_number";

    private final String DATABASE_NAME = "ContactsDB";
    private final String DATABASE_TABLE = "ContactsTable";
    private final int DATABESE_VERSION = 1;

    private DBHelper ourHelper;
    private final Context ourContext;
    private SQLiteDatabase ourDatabase;

    public ContactsDB(Context context) {
        this.ourContext = context;
    }

    private class DBHelper extends SQLiteOpenHelper {

        public DBHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABESE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {

            /*
            CREATE TABLE ContactsTable (_id INTEGER PRIMARY KEY AUTOINCREMENT,
                        person_name TEXT NOT NULL, _number TEXT NOT NULL);
             */

            String sqlCode = "CREATE TABLE " + DATABASE_TABLE + " (" + KEY_ROWID +
                    " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    KEY_NAME + " TEXT NOT NULL, " +
                    KEY_NUMBER + " TEXT NOT NULL);";

            db.execSQL(sqlCode);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

            db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
            onCreate(db);
        }

    }

    public ContactsDB open() throws SQLException {
        ourHelper = new DBHelper(ourContext);
        ourDatabase = ourHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        ourHelper.close();
    }

    public long createEntry(String name, String number) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_NAME, name);
        contentValues.put(KEY_NUMBER, number);

        return ourDatabase.insert(DATABASE_TABLE, null, contentValues);
    }

    public String readData() {
        String [] columns = new String[] {KEY_ROWID, KEY_NAME, KEY_NUMBER};

        Cursor cursor = ourDatabase.query(DATABASE_TABLE,columns, null,null,null,null,null);

        String result = "";
        int iRowID = cursor.getColumnIndex(KEY_ROWID);
        int iName = cursor.getColumnIndex(KEY_NAME);
        int iNumber = cursor.getColumnIndex(KEY_NUMBER);

        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            result = result + cursor.getString(iRowID) + ": " + cursor.getString(iName) + " " + cursor.getString(iNumber) + "\n";

        }
        cursor.close();
        return result;
    }

    public long deleteEntry(String rowID) {
        return ourDatabase.delete(DATABASE_TABLE, KEY_ROWID + "=?", new String[]{rowID});
    }

    public long updateEntry(String rowID, String name, String number) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_NAME, name);
        contentValues.put(KEY_NUMBER, number);

        return ourDatabase.update(DATABASE_TABLE, contentValues, KEY_ROWID + "=?", new String[]{rowID});
    }
}
