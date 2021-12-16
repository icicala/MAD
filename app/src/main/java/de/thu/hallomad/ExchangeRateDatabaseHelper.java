package de.thu.hallomad;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import androidx.annotation.Nullable;


public class ExchangeRateDatabaseHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "XR_ECB.db";
    public static final String XR_ECB_TABLE = "exchange_rate";
    public static final String XR_ECB_COL_CURRENCY = "currency";
    public static final String XR_EXB_COL_RATE = "rate";

    private static final String SQL_CREATE_ENTRIES = "CREATE TABLE " + XR_ECB_TABLE + " ("
            + BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + XR_ECB_COL_CURRENCY + " TEXT NOT NULL, "
            + XR_EXB_COL_RATE + " REAL NOT NULL)";
    private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + XR_ECB_TABLE;

    public ExchangeRateDatabaseHelper(@Nullable Context context) {
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
