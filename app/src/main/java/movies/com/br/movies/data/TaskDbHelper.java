package movies.com.br.movies.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import movies.com.br.movies.data.TaskContract.TaskEntry;

/**
 * Created by Paulo on 10/02/2018.
 */

public class TaskDbHelper extends SQLiteOpenHelper {


    private static final String DATABASE_NAME = "dbHotel";
    private static final int VERSION = 1;


    TaskDbHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {


        final String CREATE_TABLE = ("CREATE TABLE  " + TaskEntry.TABLE_MOVIE + "(" +
                TaskEntry.COLUMN_ID + " INTEGER PRIMARY KEY NOT NULL, " +
                TaskEntry.COLUMN_TITLE + " TEXT NOT NULL, " +
                TaskEntry.COLUMN_VIDEO + " TEXT , " +
                TaskEntry.COLUMN_VOTE_AVERAGE + " TEXT , " +
                TaskEntry.COLUMN_VOTE_COUNT + " TEXT , " +
                TaskEntry.COLUMN_RELEASE_DATE + " TEXT , " +
                TaskEntry.COLUMN_ADULT + " TEXT , " +
                TaskEntry.COLUMN_IMAGE + " TEXT , " +
                TaskEntry.COLUMN_ORIGINAL_LANGUAGE + " TEXT , " +
                TaskEntry.COLUMN_OVERVIEW + " TEXT , " +
                TaskEntry.COLUMN_ORIGINAL_TITLE + " TEXT , " +
                TaskEntry.COLUMN_POPULARITY + " TEXT , " +
                TaskEntry.COLUMN_POST_PATH + " TEXT  )");

        db.execSQL(CREATE_TABLE);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TaskEntry.TABLE_MOVIE);
        onCreate(db);
    }
}