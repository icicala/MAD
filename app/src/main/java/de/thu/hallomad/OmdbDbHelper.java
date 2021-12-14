package de.thu.hallomad;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

public class OmdbDbHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Omdb.db";
    public static final String OMDB_TABLE = "movie";
    public static final String OMDB_COL_TITLE = "title";
    public static final String OMDB_COL_IMDBID = "imdbid";
    // SQL: CREATE TABLE movie (_id INTEGER PRIMARY KEY, title TEXT, imdbId TEXT)
    private static final String SQL_CREATE_ENTRIES = "CREATE TABLE " + OMDB_TABLE + " ("
            + BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + OMDB_COL_TITLE + " TEXT,"
            + OMDB_COL_IMDBID + " TEXT)";
    // SQL: DROP TABLE IF EXISTS movie
    private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + OMDB_TABLE;

    public OmdbDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
}
