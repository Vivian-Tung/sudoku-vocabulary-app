package com.example.sudokuvocabulary;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
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

    // Key to table containing all words and translations
    public static final String KEY_WORD_TABLE = "words";
    public static final String KEY_WORD = "word";
    public static final String KEY_TRANSLATION = "translation";
    public static final String KEY_CATEGORY = "category";

    // field numbers (0 = KEY_ROWID, 1=...)
    //public static final int COL_LISTID = 1;
    public static final int COL_WORD = 2;
    public static final int COL_TRANSLATION= 3;


    //public static final String[] ALL_KEYS = new String[] {KEY_ROWID, KEY_LISTID, KEY_WORD, KEY_TRANSLATION};
    public static final String[] ALL_KEYS = new String[] {KEY_ROWID, KEY_WORD, KEY_TRANSLATION};

    // DB info: it's name, and the table .
    public static final String DATABASE_NAME = "Words.db";
    public static final String ANIMAL_TABLE = "animals";
    // Track DB version if a new version of your app changes the format.
    public static final int DATABASE_VERSION = 2;

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

        // Initialize the animal list table if it is empty,
        // TODO: move this logic into the DatabaseHelper onCreate()
        ArrayList<String> tables = getTableNames();
        if (tables.contains("animals") && getAllRows("animals").getCount() == 0) {
            insertRow("dog", "狗");
            insertRow("cat", "猫");
            insertRow("sheep", "羊");
            insertRow("frog", "青蛙");
            insertRow("pig", "猪");
            insertRow("fish", "鱼");
            insertRow("bird", "鸟");
            insertRow("bear", "熊");
            insertRow("wolf", "狼");
        }
        return this;
    }

    // Close the database connection.
    public void close() {
        myDBHelper.close();
    }

    public boolean isOpen() { return myDBHelper.getWritableDatabase().isOpen(); }

    // Creates a new table with the given name
    public void newTable(String tableName) {
        db.execSQL(myDBHelper.createTableEntry(tableName));
    }


    // Add a new set of values to the database.
    //public long insertRow(long listID, String word, String translation) {
    public long insertRow(SQLiteDatabase _db, String word, String translation, String table) {


        // Create row's data:
        ContentValues initialValues = new ContentValues();
        //initialValues.put(KEY_LISTID, listID);
        initialValues.put(KEY_WORD, word);
        initialValues.put(KEY_TRANSLATION, translation);

        // Insert it into the database.
        return _db.insert(table, null, initialValues);
    }

    public long insertRow(String word, String translation, String table) {
        return insertRow(db, word, translation, table);
    }

    public long insertRow(String word, String translation) {
        return insertRow(word, translation, ANIMAL_TABLE);
    }

    // Delete a row from the database, by rowId (primary key)
    public boolean deleteRow(long rowId) {
        String where = KEY_ROWID + "=" + rowId;
        return db.delete(ANIMAL_TABLE, where, null) != 0;
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

    // Retrieves names of tables in Database that aren't the main word table
    // and Android auto-generated tables
    public ArrayList<String> getTableNames() {
        ArrayList<String> tableNames = new ArrayList<>();
        Cursor c = db.rawQuery(
                "SELECT name FROM sqlite_master WHERE type='table' " +
                        "AND name!='android_metadata' " +
                        "AND name != 'sqlite_sequence' " +
                        "AND name != 'words' " +
                        "order by name ", null);
        while (c.moveToNext()) {
            tableNames.add(c.getString(0));
        }
        c.close();
        return tableNames;
    }

    // Return all data in the database.
    public Cursor getAllRows() {
        return getAllRows(ANIMAL_TABLE);
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

    // Returns all available categories in word table as an ArrayList
    public ArrayList<String> getAllCategories() {
        ArrayList<String> categories = new ArrayList<>();
        String[] keyArray = {KEY_CATEGORY};
        Cursor c = db.query(true, KEY_WORD_TABLE, keyArray,
                null, null, null, null, null, null);
        while (c.moveToNext()) {
            categories.add(c.getString(0));
        }
        c.close();
        return categories;
    }

    // Get a specific row (by rowId)
    public Cursor getRow(long rowId) {
        String where = KEY_ROWID + "=" + rowId;
        Cursor c = 	db.query(true, ANIMAL_TABLE, ALL_KEYS,
                where, null, null, null, null, null);
        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }

    // Get all words of a specific category
    public ArrayList<ArrayList<String>> getWordsFromCategory(String category) {
        String where = KEY_CATEGORY + " = ?";
        String[] selectionArgs = { category };
        ArrayList<ArrayList<String>> words = new ArrayList<>();
        ArrayList<String> word = new ArrayList<>();
        ArrayList<String> translation = new ArrayList<>();

        // Query for all words matching the given category
        String[] columnsToQuery = {KEY_WORD, KEY_TRANSLATION};
        Cursor c = db.query(KEY_WORD_TABLE, columnsToQuery,
                where, selectionArgs, null, null, null
        );
        //  Extract all matching words into the ArrayList
        while(c.moveToNext()) {
            word.add(c.getString(0));
            translation.add(c.getString(1));
        }
        c.close();
        words.add(0, word);
        words.add(1, translation);
        return words;
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
        return db.update(ANIMAL_TABLE, newValues, where, null) != 0;
    }



    /////////////////////////////////////////////////////////////////////
    //	Private Helper Classes:
    /////////////////////////////////////////////////////////////////////

    /**
     * Private class which handles database creation and upgrading.
     * Used to handle low-level database access.
     */
    private class DatabaseHelper extends SQLiteOpenHelper
    {
        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase _db) {
            String wordTable = createWordTable(KEY_WORD_TABLE);
            String animalTable = createTableEntry(ANIMAL_TABLE);
            _db.execSQL(wordTable);
            _db.execSQL(animalTable);
            writeCSVData(_db);
        }

        @Override
        public void onUpgrade(SQLiteDatabase _db, int oldVersion, int newVersion) {
            Log.w(TAG, "Upgrading application's database from version " + oldVersion
                    + " to " + newVersion + ", which will destroy all old data!");

            // Get names of all tables
            Cursor c = _db.rawQuery(
                    "SELECT name FROM sqlite_master WHERE type='table' " +
                            "AND name!='android_metadata' " +
                            "AND name != 'sqlite_sequence' " +
                            "order by name ", null);

            // Destroy old database tables:
            while (c.moveToNext()) {
                _db.execSQL("DROP TABLE IF EXISTS " + c.getString(0));
            }
            c.close();
            // Recreate new database:
            onCreate(_db);
        }

        public String createWordTable(String tableName) {
            return "CREATE TABLE IF NOT EXISTS " + tableName
                    + " (" + KEY_ROWID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + KEY_WORD + " TEXT NOT NULL, "
                    + KEY_TRANSLATION + " TEXT NOT NULL, "
                    + KEY_CATEGORY + " TEXT NOT NULL "
                    + ");";
        }

    // Helper method for creating new tables, use for defining new word list tables
        public String createTableEntry(String tableName) {
            return "CREATE TABLE IF NOT EXISTS " + tableName
                            + " (" + KEY_ROWID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                            + KEY_WORD + " TEXT NOT NULL, "
                            + KEY_TRANSLATION + " TEXT NOT NULL "
                            + ");";
        }

        // Private helper for writing data from a csv to the database, from readWordData()
        // Used when initializing database in onCreate()
        private void writeCSVData(SQLiteDatabase _db) {
            InputStream is = context.getResources().openRawResource(R.raw.data);
            //read line by line -> buffered reader
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(is, StandardCharsets.UTF_8)
            );
            //loop to read lines at once
            String line = "";
            try {
                while ((line = reader.readLine()) != null) {
                    //split by comma
                    String[] tokens = line.split(",");

                    //read the data
                    ContentValues contentValues = new ContentValues();
                    contentValues.put(KEY_WORD, tokens[0]);
                    contentValues.put(KEY_TRANSLATION, tokens[1]);
                    contentValues.put(KEY_CATEGORY, tokens[2]);
                    _db.insert(KEY_WORD_TABLE, null, contentValues);
                }
            }  catch (IOException e){
                Log.wtf("DBAdapter", "Error reading data file on line" + line, e);
                e.printStackTrace();
            }
        }
    }
}
