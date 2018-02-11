package movies.com.br.movies.adapter;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

import movies.com.br.movies.R;
import movies.com.br.movies.activiy.DetailsActivity;
import movies.com.br.movies.data.TaskContract;
import movies.com.br.movies.domain.Movie;
import movies.com.br.movies.utils.Constants;
import movies.com.br.movies.utils.NetworkUtils;

/**
 * Created by Paulo on 31/01/2018.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.VideoViewHolder> {

    private final List<Movie> movies;
    private Context context;


    public MovieAdapter(Context context, List<Movie> movies) {
        this.movies = movies;
        this.context = context;

    }

    @Override
    public VideoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false);
        return new VideoViewHolder(view);


    }

    @Override
    public void onBindViewHolder(final VideoViewHolder holder, int position) {

        Movie movie = movies.get(position);
//        holder.titleMovie.setText( movie.getTitle() );
        holder.progressBar.setVisibility(View.VISIBLE);

        //download photo with picasso
        Picasso.with(context).load(movie.getPoster_path()).fit().into(holder.photoMovie, new Callback() {
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
        return this.movies != null ? this.movies.size() : 0;
    }


    public class VideoViewHolder extends RecyclerView.ViewHolder {

        public ImageView photoMovie;
        public TextView titleMovie;
        public ProgressBar progressBar;
        public ContentValues contentValues = new ContentValues();
        public Uri uri;


        public VideoViewHolder(View itemView) {
            super(itemView);
            //create the view to save in viewholder
            titleMovie = itemView.findViewById(R.id.tv_title);
            photoMovie = itemView.findViewById(R.id.iv_photo);
            progressBar = itemView.findViewById(R.id.progress);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION) {
                        Intent it = new Intent(context, DetailsActivity.class);
                        it.putExtra(Movie.PARCELABLE_KEY, movies.get(pos));
                        it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(it);
                    }
                }

            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION) {

                        contentValues.put(TaskContract.TaskEntry.COLUMN_ID, movies.get(pos).getId());
                        contentValues.put(TaskContract.TaskEntry.COLUMN_ID, movies.get(pos).getId());
                        contentValues.put(TaskContract.TaskEntry.COLUMN_TITLE, movies.get(pos).getTitle());
                        contentValues.put(TaskContract.TaskEntry.COLUMN_ORIGINAL_TITLE, movies.get(pos).getOriginal_title());
                        contentValues.put(TaskContract.TaskEntry.COLUMN_POST_PATH, movies.get(pos).getPoster_path());
                        contentValues.put(TaskContract.TaskEntry.COLUMN_IMAGE, movies.get(pos).getBackdrop_path());
                        contentValues.put(TaskContract.TaskEntry.COLUMN_POPULARITY, movies.get(pos).getPopularity());
                        contentValues.put(TaskContract.TaskEntry.COLUMN_ORIGINAL_LANGUAGE, movies.get(pos).getOriginal_language());
                        contentValues.put(TaskContract.TaskEntry.COLUMN_VOTE_AVERAGE, movies.get(pos).getVote_average());
                        contentValues.put(TaskContract.TaskEntry.COLUMN_ADULT, movies.get(pos).getAdult());
                        contentValues.put(TaskContract.TaskEntry.COLUMN_VIDEO, movies.get(pos).getVideo());
                        contentValues.put(TaskContract.TaskEntry.COLUMN_RELEASE_DATE, movies.get(pos).getRelease_date());
                        contentValues.put(TaskContract.TaskEntry.COLUMN_VOTE_AVERAGE, movies.get(pos).getVote_average());
                        contentValues.put(TaskContract.TaskEntry.COLUMN_OVERVIEW, movies.get(pos).getOverview());
                        contentValues.put(TaskContract.TaskEntry.COLUMN_VOTE_COUNT, movies.get(pos).getVote_count());

                        uri = context.getContentResolver().insert(TaskContract.TaskEntry.CONTENT_URI, contentValues);

                        if (uri != null) {
                            NetworkUtils.myToast(context, context.getString(R.string.yepInserMovie), Constants.SHORT);
                        } else {
                            NetworkUtils.myToast(context, context.getString(R.string.alreadyMovie), Constants.SHORT);
                        }
                    }
                    return true;
                }
            });


        }


    }
}
