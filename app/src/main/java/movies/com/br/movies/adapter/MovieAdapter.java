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

import movies.com.br.movies.activiy.DetailsActivity;
import movies.com.br.movies.R;
import movies.com.br.movies.domain.Movie;

/**
 * Created by Paulo on 31/01/2018.
 */

public class MovieAdapter extends RecyclerView.Adapter< MovieAdapter.MovieViewHolder > {

    private final List< Movie > movies;
    private Context context;


    public MovieAdapter( Context context, List<Movie> movies ) {
        this.movies = movies;
        this.context = context;

    }

    @Override
    public MovieAdapter.MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_movie, parent,false);
        return new MovieViewHolder(view);


    }

    @Override
    public void onBindViewHolder(final MovieAdapter.MovieViewHolder holder,  int position) {

        Movie movie = movies.get( position );
        holder.titleMovie.setText( movie.getTitle() );
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



    public class MovieViewHolder extends RecyclerView.ViewHolder {

        public ImageView photoMovie;
        public TextView titleMovie;
        ProgressBar progressBar;

        public MovieViewHolder(View itemView) {
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
        }
    }
}
