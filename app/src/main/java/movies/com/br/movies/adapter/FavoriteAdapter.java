package movies.com.br.movies.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import movies.com.br.movies.R;
import movies.com.br.movies.activiy.DetailsActivity;
import movies.com.br.movies.data.TaskContract;
import movies.com.br.movies.domain.Movie;

/**
 * Created by Paulo on 31/01/2018.
 */

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.VideoViewHolder> {


    private Context context;
    private AlertDialog.Builder builder;
    private Cursor mCursor;
    private ArrayList<Movie> movies;


    public FavoriteAdapter(Context context) {
        this.context = context;
        this.builder = new AlertDialog.Builder(context);
        this.movies = new ArrayList<>();
    }

    @Override
    public VideoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_favorite, parent, false);
        return new VideoViewHolder(view);


    }

    @Override
    public void onBindViewHolder(final VideoViewHolder holder, int position) {


        int idxImg = mCursor.getColumnIndex(TaskContract.TaskEntry.COLUMN_IMAGE);

        int indexId = mCursor.getColumnIndex(TaskContract.TaskEntry.COLUMN_ID);
        int indexTitle = mCursor.getColumnIndex(TaskContract.TaskEntry.COLUMN_TITLE);
        int indexdOrItle = mCursor.getColumnIndex(TaskContract.TaskEntry.COLUMN_ORIGINAL_TITLE);
        int indexPOSTPAT = mCursor.getColumnIndex(TaskContract.TaskEntry.COLUMN_POST_PATH);
        int indexPopu = mCursor.getColumnIndex(TaskContract.TaskEntry.COLUMN_POPULARITY);
        int indexLang = mCursor.getColumnIndex(TaskContract.TaskEntry.COLUMN_ORIGINAL_LANGUAGE);
        int indexVote = mCursor.getColumnIndex(TaskContract.TaskEntry.COLUMN_VOTE_AVERAGE);
        int indexAdult = mCursor.getColumnIndex(TaskContract.TaskEntry.COLUMN_ADULT);
        int indexVideo = mCursor.getColumnIndex(TaskContract.TaskEntry.COLUMN_VIDEO);
        int indexRelease = mCursor.getColumnIndex(TaskContract.TaskEntry.COLUMN_RELEASE_DATE);
        int indexOverview = mCursor.getColumnIndex(TaskContract.TaskEntry.COLUMN_OVERVIEW);
        int indexVoteCount = mCursor.getColumnIndex(TaskContract.TaskEntry.COLUMN_VOTE_COUNT);
        mCursor.moveToPosition(position);
        Movie movie = new Movie();

        movie.setTitle(mCursor.getString(idxImg));

        movie.setId(mCursor.getInt(indexId));
        movie.setAdult(Boolean.parseBoolean(mCursor.getString(indexAdult)));
        movie.setTitle(mCursor.getString(indexTitle));
        movie.setUrlBD(mCursor.getString(indexPOSTPAT));
        movie.setVote_count(mCursor.getInt(indexVoteCount));
        movie.setOverview(mCursor.getString(indexOverview));
        movie.setVote_average(mCursor.getDouble(indexVote));
        movie.setRelease_date(mCursor.getString(indexRelease));
        movie.setVideo(mCursor.getString(indexVideo));
        movie.setPopularity(mCursor.getDouble(indexPopu));
        movie.setBackdrop_path(mCursor.getString(indexPOSTPAT));
        movie.setPoster_path(mCursor.getString(indexPOSTPAT));
        movie.setOriginal_title(mCursor.getString(indexdOrItle));
        movie.setOriginal_language(mCursor.getString(indexLang));

        movies.add(movie);
        holder.progressBar.setVisibility(View.VISIBLE);
        //download photo with picasso
        Picasso.with(context).load(mCursor.getString(indexPOSTPAT)).fit().into(holder.photoMovie, new Callback() {
            @Override
            public void onSuccess() {
                holder.progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onError() {
                holder.progressBar.setVisibility(View.GONE);
            }
        });


    }


    @Override
    public int getItemCount() {
        if (mCursor == null) {
            return 0;
        }
        return mCursor.getCount();
    }


    /**
     * When data changes and a re-query occurs, this function swaps the old Cursor
     * with a newly updated Cursor (Cursor c) that is passed in.
     */
    public Cursor swapCursor(Cursor c) {
        // check if this cursor is the same as the previous cursor (mCursor)
        if (mCursor == c) {
            return null; // bc nothing has changed
        }
        Cursor temp = mCursor;
        this.mCursor = c; // new cursor value assigned

        //check if this is a valid cursor, then update the cursor
        if (c != null) {
            this.notifyDataSetChanged();
        }
        return temp;
    }


    public class VideoViewHolder extends RecyclerView.ViewHolder {

        public ImageView photoMovie;
        ProgressBar progressBar;

        public VideoViewHolder(final View itemView) {
            super(itemView);
            //create the view to save in viewholder
            photoMovie = itemView.findViewById(R.id.iv_photo);
            progressBar = itemView.findViewById(R.id.progress);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION) {
                        Intent it = new Intent(context, DetailsActivity.class);
                        it.putExtra(Movie.PARCELABLE_KEY, movies.get(pos));
                        it.putExtra("IMAGE_FROM_BD", movies.get(pos).getUrlBD());
                        it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(it);

                    }


                }
            });

        }
    }


}
