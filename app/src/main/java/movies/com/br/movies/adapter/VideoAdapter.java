package movies.com.br.movies.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

import movies.com.br.movies.R;
import movies.com.br.movies.activiy.DetailsActivity;
import movies.com.br.movies.domain.Movie;
import movies.com.br.movies.domain.Video;
import movies.com.br.movies.utils.Constants;

/**
 * Created by Paulo on 07/02/2018.
 */

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoViewHolder> {

    private final List< Video > videos;
    private Context context;


    public VideoAdapter( Context context, List<Video> videos ) {
        this.videos = videos;
        this.context = context;

    }

    @Override
    public VideoAdapter.VideoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_trailer, parent,false);
        return new VideoAdapter.VideoViewHolder(view);


    }

    @Override
    public void onBindViewHolder(final VideoAdapter.VideoViewHolder holder, int position) {

        Video video = videos.get( position );

       // holder.titleMovie.setText( video.getName() );

        }


    @Override
    public int getItemCount() {
        return this.videos != null ? this.videos.size() : 0;
    }



    public class VideoViewHolder extends RecyclerView.ViewHolder {

        public ImageView photoMovie;
        //public TextView titleMovie;

        public VideoViewHolder(final View itemView) {
            super(itemView);
            //create the view to save in viewholder
           // titleMovie =  itemView.findViewById( R.id.tv_title );


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if( pos != RecyclerView.NO_POSITION ){
                        context.startActivity( new Intent( Intent.ACTION_VIEW, Uri.parse(  Constants.URL_YOUTUBE+ videos.get( pos).getKey()) ) );
                    }
                }
            });
        }
    }
}

