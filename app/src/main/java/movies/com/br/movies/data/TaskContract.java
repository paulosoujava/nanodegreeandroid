package movies.com.br.movies.data;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Paulo on 10/02/2018.
 */

public class TaskContract {

    public static final String AUTHORITY = "movies.com.br.movies";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);
    public static final String PATH_TASKS = "movie";


    public static final class TaskEntry implements BaseColumns {


        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_TASKS).build();

        public static String TABLE_MOVIE = "movie";
        public static String COLUMN_ID = "_id";
        public static String COLUMN_TITLE = "title";
        public static String COLUMN_IMAGE = "image";

        public static String COLUMN_VIDEO = "backdrop_path";
        public static String COLUMN_OVERVIEW = "overview";
        public static String COLUMN_VOTE_AVERAGE = "vote_average";
        public static String COLUMN_RELEASE_DATE = "release_date";
        public static String COLUMN_VOTE_COUNT = "vote_count";
        public static String COLUMN_POPULARITY = "popularity";
        public static String COLUMN_ADULT = "adult";
        public static String COLUMN_POST_PATH = "poster_path";
        public static String COLUMN_ORIGINAL_LANGUAGE = "original_language";
        public static String COLUMN_ORIGINAL_TITLE = "original_title";


    }


}
