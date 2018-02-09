package movies.com.br.movies.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import movies.com.br.movies.domain.Movie;

/**
 * Created by Paulo on 08/02/2018.
 */

public class MovieRepository {

    private MovieSQLHelper helper;
    final Movie movie;

    public MovieRepository(Context context) {
        this.helper = new MovieSQLHelper(context);
        this.movie = new Movie();
    }

    public long insert(Movie m) {
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(MovieSQLHelper.COLUMN_ID, m.getId());
        cv.put(MovieSQLHelper.COLUMN_TITLE, m.getTitle());
        cv.put(MovieSQLHelper.COLUMN_ORIGINAL_TITLE, m.getOriginal_title());
        cv.put(MovieSQLHelper.COLUMN_POST_PATH, m.getPoster_path());
        cv.put(MovieSQLHelper.COLUMN_IMAGE, m.getBackdrop_path());
        cv.put(MovieSQLHelper.COLUMN_POPULARITY, m.getPopularity());
        cv.put(MovieSQLHelper.COLUMN_ORIGINAL_LANGUAGE, m.getOriginal_language());
        cv.put(MovieSQLHelper.COLUMN_VOTE_AVERAGE, m.getVote_average());
        cv.put(MovieSQLHelper.COLUMN_ADULT, m.getAdult());
        cv.put(MovieSQLHelper.COLUMN_VIDEO, m.getVideo());
        cv.put(MovieSQLHelper.COLUMN_RELEASE_DATE, m.getRelease_date());
        cv.put(MovieSQLHelper.COLUMN_VOTE_AVERAGE, m.getVote_average());
        cv.put(MovieSQLHelper.COLUMN_OVERVIEW, m.getOverview());
        cv.put(MovieSQLHelper.COLUMN_VOTE_COUNT, m.getVote_count());

        long id = db.insert(MovieSQLHelper.TABLE_MOVIE, null, cv);

        Log.d("LOG", "INSERT: " + m.toString());
        if (id != -1) {
            m.setId((int) id);
        }
        db.close();
        return id;
    }

    public int delete(Movie m) {
        SQLiteDatabase db = helper.getWritableDatabase();
        int row = db.delete(MovieSQLHelper.TABLE_MOVIE, MovieSQLHelper.COLUMN_ID + "=?", new String[]{String.valueOf(m.getId())});
        db.close();
        return row;
    }

    public List<Movie> getAllMovie() {
        SQLiteDatabase db = helper.getWritableDatabase();
        String sqlQuery = " SELECT * FROM " + MovieSQLHelper.TABLE_MOVIE;
        List<Movie> movies = new ArrayList<>();
        Cursor cursor = db.rawQuery(sqlQuery, null);
        while (cursor.moveToNext()) {
            Movie m = new Movie();
            m.setVideo(cursor.getString(cursor.getColumnIndex(MovieSQLHelper.COLUMN_VIDEO)));
            m.setPoster_path(cursor.getString(cursor.getColumnIndex(MovieSQLHelper.COLUMN_IMAGE)));
            m.setBackdrop_path(cursor.getString(cursor.getColumnIndex(MovieSQLHelper.COLUMN_IMAGE)));
            m.setOverview(cursor.getString(cursor.getColumnIndex(MovieSQLHelper.COLUMN_OVERVIEW)));
            m.setVote_average(cursor.getDouble(cursor.getColumnIndex(MovieSQLHelper.COLUMN_VOTE_AVERAGE)));
            m.setRelease_date(cursor.getString(cursor.getColumnIndex(MovieSQLHelper.COLUMN_RELEASE_DATE)));
            m.setVote_count(cursor.getInt(cursor.getColumnIndex(MovieSQLHelper.COLUMN_VOTE_COUNT)));
            m.setId(cursor.getInt(cursor.getColumnIndex(MovieSQLHelper.COLUMN_ID)));
            m.setPopularity(cursor.getDouble(cursor.getColumnIndex(MovieSQLHelper.COLUMN_POPULARITY)));
            m.setPoster_path(cursor.getString(cursor.getColumnIndex(MovieSQLHelper.COLUMN_POST_PATH)));
            m.setOriginal_language(cursor.getString(cursor.getColumnIndex(MovieSQLHelper.COLUMN_ORIGINAL_LANGUAGE)));
            m.setOriginal_title(cursor.getString(cursor.getColumnIndex(MovieSQLHelper.COLUMN_ORIGINAL_TITLE)));
            m.setTitle(cursor.getString(cursor.getColumnIndex(MovieSQLHelper.COLUMN_TITLE)));
            m.setUrlBD(cursor.getString(cursor.getColumnIndex(MovieSQLHelper.COLUMN_POST_PATH)));
            movies.add(m);
        }
        cursor.close();
        db.close();
        return movies;
    }

    public Movie getById(Movie m) {

        SQLiteDatabase db = helper.getWritableDatabase();
        Log.d("LOG", " ID ---  ::" + m.getId() );

        Cursor cursor = db.query(MovieSQLHelper.TABLE_MOVIE, new String[]{
                MovieSQLHelper.COLUMN_VIDEO,
                MovieSQLHelper.COLUMN_IMAGE,
                MovieSQLHelper.COLUMN_IMAGE,
                MovieSQLHelper.COLUMN_OVERVIEW,
                MovieSQLHelper.COLUMN_RELEASE_DATE,
                MovieSQLHelper.COLUMN_VOTE_AVERAGE,
                MovieSQLHelper.COLUMN_VOTE_COUNT,
                MovieSQLHelper.COLUMN_ID,
                MovieSQLHelper.COLUMN_POPULARITY,
                MovieSQLHelper.COLUMN_POST_PATH,
                MovieSQLHelper.COLUMN_ORIGINAL_LANGUAGE,
                MovieSQLHelper.COLUMN_ORIGINAL_TITLE,
                MovieSQLHelper.COLUMN_TITLE,
                MovieSQLHelper.COLUMN_POST_PATH },"_id=?", new String[]{String.valueOf(m.getId())}, null, null, null
        );

        Log.d("LOG", " QUERY ::" + cursor.getCount());

        if ( cursor.getCount() > 0) {
            cursor.moveToFirst();
            movie.setVideo(cursor.getString(cursor.getColumnIndex(MovieSQLHelper.COLUMN_VIDEO)));
            movie.setPoster_path(cursor.getString(cursor.getColumnIndex(MovieSQLHelper.COLUMN_IMAGE)));
            movie.setBackdrop_path(cursor.getString(cursor.getColumnIndex(MovieSQLHelper.COLUMN_IMAGE)));
            movie.setOverview(cursor.getString(cursor.getColumnIndex(MovieSQLHelper.COLUMN_OVERVIEW)));
            movie.setVote_average(cursor.getDouble(cursor.getColumnIndex(MovieSQLHelper.COLUMN_VOTE_AVERAGE)));
            movie.setRelease_date(cursor.getString(cursor.getColumnIndex(MovieSQLHelper.COLUMN_RELEASE_DATE)));
            movie.setVote_count(cursor.getInt(cursor.getColumnIndex(MovieSQLHelper.COLUMN_VOTE_COUNT)));
            movie.setId(cursor.getInt(cursor.getColumnIndex(MovieSQLHelper.COLUMN_ID)));
            movie.setPopularity(cursor.getDouble(cursor.getColumnIndex(MovieSQLHelper.COLUMN_POPULARITY)));
            movie.setPoster_path(cursor.getString(cursor.getColumnIndex(MovieSQLHelper.COLUMN_POST_PATH)));
            movie.setOriginal_language(cursor.getString(cursor.getColumnIndex(MovieSQLHelper.COLUMN_ORIGINAL_LANGUAGE)));
            movie.setOriginal_title(cursor.getString(cursor.getColumnIndex(MovieSQLHelper.COLUMN_ORIGINAL_TITLE)));
            movie.setTitle(cursor.getString(cursor.getColumnIndex(MovieSQLHelper.COLUMN_TITLE)));
            movie.setUrlBD(cursor.getString(cursor.getColumnIndex(MovieSQLHelper.COLUMN_POST_PATH)));


        }else{
            return null;
        }
        cursor.close();
        db.close();
        return movie;
    }

}
