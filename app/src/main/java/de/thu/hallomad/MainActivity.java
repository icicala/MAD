package de.thu.hallomad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    OmdbDbHelper omdbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        omdbHelper = new OmdbDbHelper(this);

        {

            SQLiteDatabase db = omdbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(OmdbDbHelper.OMDB_COL_TITLE, "The Crown");
            values.put(OmdbDbHelper.OMDB_COL_IMDBID, "tt7489237");

            long longRowID = db.insert(OmdbDbHelper.OMDB_TABLE, null, values);
            Log.i("Inserted", longRowID + " times");
        }

        {
            SQLiteDatabase db = omdbHelper.getReadableDatabase();

            String[] projection = {BaseColumns._ID, OmdbDbHelper.OMDB_COL_TITLE, OmdbDbHelper.OMDB_COL_IMDBID};
            String selection = OmdbDbHelper.OMDB_COL_TITLE + " = ?";
            String[] selectionArg = {"The Crown"};

            Cursor cursor = db.query(OmdbDbHelper.OMDB_TABLE, projection, selection, selectionArg, null, null, null);

            while (cursor.moveToNext()) {
                String imdbId = cursor.getString(cursor.getColumnIndexOrThrow(OmdbDbHelper.OMDB_COL_IMDBID));
                Log.d("DataBasetest", imdbId);
            }

        }


    }
}