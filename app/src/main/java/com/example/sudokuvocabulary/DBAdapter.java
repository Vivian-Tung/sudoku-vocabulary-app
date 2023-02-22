package com.example.sudokuvocabulary;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;


public class DBAdapter {

    /////////////////////////////////////////////////////////////////////
    //	Constants & Data
    /////////////////////////////////////////////////////////////////////
    // For logging:
    private static final String TAG = "DBAdapter";

    // DB Fields
    public static final String KEY_ROWID = "_id";
    public static final int COL_ROWID = 0;

    // fields:
    //public static final String KEY_LISTID = "listID";
    public static final String KEY_WORD = "word";
    public static final String KEY_TRANSLATION = "translation";

    // field numbers (0 = KEY_ROWID, 1=...)
    //public static final int COL_LISTID = 1;
    public static final int COL_WORD = 2;
    public static final int COL_TRANSLATION= 3;


    //public static final String[] ALL_KEYS = new String[] {KEY_ROWID, KEY_LISTID, KEY_WORD, KEY_TRANSLATION};
    public static final String[] ALL_KEYS = new String[] {KEY_ROWID, KEY_WORD, KEY_TRANSLATION};

    // DB info: it's name, and the table .
    public static final String DATABASE_NAME = "Words.db";
    public static final String DATABASE_TABLE = "animals";
    // Track DB version if a new version of your app changes the format.
    public static final int DATABASE_VERSION = 1;

    private static final String DATABASE_CREATE_SQL =
            "create table " + DATABASE_TABLE
                    + " (" + KEY_ROWID + " integer primary key autoincrement, "

                    // + KEY_{...} + " {type} not null"
                    //	- Key is the column name you created above.
                    //	- {type} is one of: text, integer, real, blob
                    //		(http://www.sqlite.org/datatype3.html)
                    //  - "not null" means it is a required field (must be given a value).
                    // NOTE: All must be comma separated (end of line!) Last one must have NO comma!!
                    //+ KEY_LISTID + "long not null, "
                    + KEY_WORD + " text not null, "
                    + KEY_TRANSLATION + " text not null "

                    // Rest  of creation:
                    + ");";

    // Context of application
    private final Context context;

    private DatabaseHelper myDBHelper;
    private SQLiteDatabase db;

    /////////////////////////////////////////////////////////////////////
    //	Public methods:
    /////////////////////////////////////////////////////////////////////

    public DBAdapter(Context ctx) {
        this.context = ctx;
        myDBHelper = new DatabaseHelper(context);
    }

    // Open the database connection.
    public DBAdapter open() {
        db = myDBHelper.getWritableDatabase();
        return this;
    }

    // Close the database connection.
    public void close() {
        myDBHelper.close();
    }

    // Add a new set of values to the database.
    //public long insertRow(long listID, String word, String translation) {
    public long insertRow(String word, String translation, String table) {


        // Create row's data:
        ContentValues initialValues = new ContentValues();
        //initialValues.put(KEY_LISTID, listID);
        initialValues.put(KEY_WORD, word);
        initialValues.put(KEY_TRANSLATION, translation);

        // Insert it into the database.
        return db.insert(DATABASE_TABLE, null, initialValues);
    }

    public long insertRow(String word, String translation) {
        return insertRow(word, translation, DATABASE_TABLE);
    }

    // Delete a row from the database, by rowId (primary key)
    public boolean deleteRow(long rowId) {
        String where = KEY_ROWID + "=" + rowId;
        return db.delete(DATABASE_TABLE, where, null) != 0;
    }

    public void deleteAll() {
        Cursor c = getAllRows();
        long rowId = c.getColumnIndexOrThrow(KEY_ROWID);
        if (c.moveToFirst()) {
            do {
                deleteRow(c.getLong((int) rowId));
            } while (c.moveToNext());
        }
        c.close();
    }

    public ArrayList<String> getTableNames() {
        ArrayList<String> tableNames = new ArrayList<>();
        Cursor c = db.rawQuery(
                "SELECT name FROM sqlite_master WHERE type='table' " +
                        "AND name!='android_metadata' " +
                        "AND name != 'sqlite_sequence' order by name ", null);
        while (c.moveToNext()) {
            tableNames.add(c.getString(0));
        }
        c.close();
        return tableNames;
    }

    // Return all data in the database.
    public Cursor getAllRows() {
        return getAllRows(DATABASE_TABLE);
    }

    public Cursor getAllRows(String tableName) {
        String where = null;
        Cursor c = 	db.query(true, tableName, ALL_KEYS,
                where, null, null, null, null, null);
        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }

    // Get a specific row (by rowId)
    public Cursor getRow(long rowId) {
        String where = KEY_ROWID + "=" + rowId;
        Cursor c = 	db.query(true, DATABASE_TABLE, ALL_KEYS,
                where, null, null, null, null, null);
        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }

    // Change an existing row to be equal to new data.
    //public boolean updateRow(long rowId, long listID, String word, String translation) {
    public boolean updateRow(long rowId, String word, String translation) {
        String where = KEY_ROWID + "=" + rowId;


        // Create row's data:
        ContentValues newValues = new ContentValues();
        //newValues.put(KEY_LISTID, listID);
        newValues.put(KEY_WORD, word);
        newValues.put(KEY_TRANSLATION, translation);

        // Insert it into the database.
        return db.update(DATABASE_TABLE, newValues, where, null) != 0;
    }



    /////////////////////////////////////////////////////////////////////
    //	Private Helper Classes:
    /////////////////////////////////////////////////////////////////////

    /**
     * Private class which handles database creation and upgrading.
     * Used to handle low-level database access.
     */
    private static class DatabaseHelper extends SQLiteOpenHelper
    {
        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase _db) {
            _db.execSQL(DATABASE_CREATE_SQL);
        }

        @Override
        public void onUpgrade(SQLiteDatabase _db, int oldVersion, int newVersion) {
            Log.w(TAG, "Upgrading application's database from version " + oldVersion
                    + " to " + newVersion + ", which will destroy all old data!");

            // Destroy old database:
            _db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);

            // Recreate new database:
            onCreate(_db);
        }
    }
}
