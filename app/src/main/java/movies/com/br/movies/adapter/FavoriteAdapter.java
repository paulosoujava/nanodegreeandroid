package movies.com.br.movies.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

import movies.com.br.movies.R;
import movies.com.br.movies.activiy.DetailsActivity;
import movies.com.br.movies.activiy.FavoriteActivity;
import movies.com.br.movies.data.MovieRepository;
import movies.com.br.movies.domain.Movie;

/**
 * Created by Paulo on 31/01/2018.
 */

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.VideoViewHolder> {

    private List<Movie> movies;
    private Context context;
    private MovieRepository movieRepository;
    private AlertDialog.Builder builder;


    public FavoriteAdapter(Context context, List<Movie> movies) {
        this.movieRepository = new MovieRepository(context);
        this.movies = movies;
        this.context = context;
        this.builder = new AlertDialog.Builder(context);

    }

    @Override
    public VideoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_favorite, parent, false);
        return new VideoViewHolder(view);


    }

    @Override
    public void onBindViewHolder(final VideoViewHolder holder, int position) {

        Movie movie = movies.get(position);
        holder.progressBar.setVisibility(View.VISIBLE);
        movie.setUrlBD( movie.getUrlBD() );


        //download photo with picasso
        Picasso.with(context).load(movie.getUrlBD()).fit().into(holder.photoMovie, new Callback() {
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

                        movieRepository.getById( movies.get( pos ));
                        Intent it = new Intent(context, DetailsActivity.class);
                        it.putExtra(Movie.PARCELABLE_KEY, movieRepository.getById(movies.get( pos )));
                        it.putExtra("IMAGE_FROM_BD",movies.get(pos).getUrlBD() );
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
                        final Movie movie = movies.get(pos);
                        builder.setIcon(R.drawable.ic_launcher_background);
                        builder.setTitle(R.string.removeMovie);
                        builder.setMessage(R.string.messegeRemove);
                        builder.setPositiveButton(R.string.yep, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                movieRepository.delete(movie);
                                movies = movieRepository.getAllMovie();
                                notifyDataSetChanged();

                                if( movieRepository.getAllMovie().size() <= 0 ){
                                    ((FavoriteActivity)context).finish();
                                }

                             }
                        });
                        builder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                return;
                            }
                        });
                        AlertDialog dialog = builder.create();
                        dialog.show();

                    }
                    return true;
                }
            });


        }
    }


}
