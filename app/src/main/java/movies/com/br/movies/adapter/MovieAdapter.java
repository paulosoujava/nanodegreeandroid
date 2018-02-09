package movies.com.br.movies.adapter;

import android.content.Context;
import android.content.Intent;
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
import movies.com.br.movies.data.MovieRepository;
import movies.com.br.movies.domain.Movie;
import movies.com.br.movies.utils.Constants;
import movies.com.br.movies.utils.NetworkUtils;

/**
 * Created by Paulo on 31/01/2018.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.VideoViewHolder> {

    private final List< Movie > movies;
    private Context context;
    private MovieRepository movieRepository;


    public MovieAdapter( Context context, List<Movie> movies ) {
       this.movieRepository = new MovieRepository(context);
        this.movies = movies;
        this.context = context;

    }

    @Override
    public VideoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_movie, parent,false);
        return new VideoViewHolder(view);


    }

    @Override
    public void onBindViewHolder(final VideoViewHolder holder, int position) {

        Movie movie = movies.get( position );
//        holder.titleMovie.setText( movie.getTitle() );
        holder.progressBar.setVisibility( View.VISIBLE );

        //download photo with picasso
        Picasso.with( context ).load( movie.getPoster_path() ).fit().into(holder.photoMovie, new Callback() {
            @Override
            public void onSuccess() {
                holder.progressBar.setVisibility( View.GONE );
            }

            @Override
            public void onError() {
                holder.progressBar.setVisibility( View.GONE );
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
        ProgressBar progressBar;

        public VideoViewHolder(View itemView) {
            super(itemView);
            //create the view to save in viewholder
            titleMovie =  itemView.findViewById( R.id.tv_title );
            photoMovie =  itemView.findViewById( R.id.iv_photo );
            progressBar =  itemView.findViewById( R.id.progress );

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if( pos != RecyclerView.NO_POSITION ){
                        Intent it = new Intent( context, DetailsActivity.class );
                        it.putExtra(Movie.PARCELABLE_KEY, movies.get(pos));
                        it.addFlags( Intent.FLAG_ACTIVITY_NEW_TASK );
                        context.startActivity( it );
                    }
                }

            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int pos = getAdapterPosition();
                    if( pos != RecyclerView.NO_POSITION ) {
                        if( canInsertThis(movies.get(pos) ) ){
                            NetworkUtils.myToast(context, context.getString(R.string.yepInserMovie), Constants.SHORT );
                            movieRepository.insert( movies.get(pos) );
                        }else{

                            NetworkUtils.myToast(context, context.getString(R.string.alreadyMovie), Constants.SHORT );

                        }
                    }
                    return true;
                }
            });


        }

        private  boolean canInsertThis( Movie m ){

            if( movieRepository.getById( m ) == null )
                return  true;
            else
                return  false;
        }
    }
}
