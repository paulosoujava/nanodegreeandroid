package movies.com.br.movies.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Paulo on 08/02/2018.
 */

public class MovieSQLHelper extends SQLiteOpenHelper {


    private static final String BD_MOVIE = "dbHotel";
    private static final int BD_VERSION = 1;

    public  static  String TABLE_MOVIE = "movie";
    public  static  String COLUMN_ID = "_id";
    public  static  String COLUMN_TITLE = "title";
    public  static  String COLUMN_IMAGE = "image";

    public  static  String COLUMN_VIDEO = "backdrop_path";
    public  static  String COLUMN_OVERVIEW = "overview";
    public  static  String COLUMN_VOTE_AVERAGE = "vote_average";
    public  static  String COLUMN_RELEASE_DATE = "release_date";
    public  static  String COLUMN_VOTE_COUNT = "vote_count";
    public  static  String COLUMN_POPULARITY = "popularity";
    public  static  String COLUMN_ADULT = "adult";
    public  static  String COLUMN_POST_PATH = "poster_path";
    public  static  String COLUMN_ORIGINAL_LANGUAGE = "original_language";
    public  static  String COLUMN_ORIGINAL_TITLE = "original_title";






    public MovieSQLHelper(Context context) {
        super( context, BD_MOVIE, null, BD_VERSION );
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL( "CREATE TABLE  "+ TABLE_MOVIE + "(" +
                COLUMN_ID+" INTEGER PRIMARY KEY NOT NULL, " +
                COLUMN_TITLE+" TEXT NOT NULL, " +
                COLUMN_VIDEO+" TEXT , " +
                COLUMN_VOTE_AVERAGE+" TEXT , " +
                COLUMN_VOTE_COUNT+" TEXT , " +
                COLUMN_RELEASE_DATE+" TEXT , " +
                COLUMN_ADULT+" TEXT , " +
                COLUMN_IMAGE+" TEXT , " +
                COLUMN_ORIGINAL_LANGUAGE+" TEXT , " +
                COLUMN_OVERVIEW+" TEXT , " +
                COLUMN_ORIGINAL_TITLE+" TEXT , " +
                COLUMN_POPULARITY+" TEXT , " +
                COLUMN_POST_PATH+" TEXT  )" );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //the next version
    }
}
