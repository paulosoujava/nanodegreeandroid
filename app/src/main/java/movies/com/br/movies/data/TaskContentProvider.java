package movies.com.br.movies.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import movies.com.br.movies.utils.Constants;
import movies.com.br.movies.utils.NetworkUtils;

/**
 * Created by Paulo on 10/02/2018.
 */

public class TaskContentProvider extends ContentProvider {


    public static final int MOVIE = 100;
    public static final int MOVIE_WITH_ID = 101;

    private static final UriMatcher sUrMATCHER = buildUriMatcher();

    public static UriMatcher buildUriMatcher() {
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        //directory
        uriMatcher.addURI(TaskContract.AUTHORITY, TaskContract.PATH_TASKS, MOVIE);
        //single item
        uriMatcher.addURI(TaskContract.AUTHORITY, TaskContract.PATH_TASKS + "/#", MOVIE_WITH_ID);

        return uriMatcher;
    }


    private TaskDbHelper mTaskDbHelper;


    @Override
    public boolean onCreate() {

        Context context = getContext();
        mTaskDbHelper = new TaskDbHelper(context);
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        final SQLiteDatabase db = mTaskDbHelper.getReadableDatabase();
        int match = sUrMATCHER.match(uri);

        Cursor retCursor = null;
        switch (match) {
            case MOVIE:
                retCursor = db.query(TaskContract.TaskEntry.TABLE_MOVIE,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);

                break;

            case MOVIE_WITH_ID:
                // Get the id from the URI
                String id = uri.getPathSegments().get(1);


                String mSelection = "_id=?";
                String[] mSelectionArgs = new String[]{id};


                retCursor = db.query(TaskContract.TaskEntry.TABLE_MOVIE,
                        projection,
                        mSelection,
                        mSelectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            default:
                NetworkUtils.myToast(getContext(), "Unknow Uri..." + uri, Constants.SHORT);
        }

        retCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return retCursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {

        final SQLiteDatabase db = mTaskDbHelper.getWritableDatabase();
        int match = sUrMATCHER.match(uri);

        Uri returnUri = null;

        switch (match) {
            case MOVIE:
                long id = db.insert(TaskContract.TaskEntry.TABLE_MOVIE, null, values);
                if (id > 0) {
                    returnUri = ContentUris.withAppendedId(TaskContract.TaskEntry.CONTENT_URI, id);
                }
                break;

            default:
                NetworkUtils.myToast(getContext(), "Unknow Uri..." + uri, Constants.SHORT);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {

        final SQLiteDatabase db = mTaskDbHelper.getWritableDatabase();
        int match = sUrMATCHER.match(uri);
        int tasksDeleted; // starts as 0

        switch (match) {
            case MOVIE_WITH_ID:
                String id = uri.getPathSegments().get(1);
                tasksDeleted = db.delete(TaskContract.TaskEntry.TABLE_MOVIE, "_id=?", new String[]{id});
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        if (tasksDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        // Return the number of tasks deleted
        return tasksDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}
