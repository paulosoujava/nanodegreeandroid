package movies.com.br.movies.activiy;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import movies.com.br.movies.BuildConfig;
import movies.com.br.movies.R;
import movies.com.br.movies.adapter.MovieAdapter;
import movies.com.br.movies.adapter.VideoAdapter;
import movies.com.br.movies.api.Client;
import movies.com.br.movies.api.Service;
import movies.com.br.movies.domain.Movie;
import movies.com.br.movies.domain.MovieResponse;
import movies.com.br.movies.domain.Video;
import movies.com.br.movies.domain.VideoResponse;
import movies.com.br.movies.utils.Constants;
import movies.com.br.movies.utils.NetworkUtils;
import retrofit2.Call;
import retrofit2.Response;

public class DetailsActivity extends AppCompatActivity {

    private ImageView imageMovie;
    private TextView title;
    private TextView popularity;
    private TextView release_date;
    private TextView overview;
    private TextView vote_average;
    private TextView original_title;
    private TextView original_language;
    private ProgressBar progressBar;
    private String url_image_foto = "";
    private Movie movie;
    private  Integer idMovie;
    private VideoAdapter videoAdapter;
    private RecyclerView recyclerView;
    private List<Video> videos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        imageMovie =  findViewById(R.id.backdrop_path);
        progressBar =  findViewById(R.id.progress);
        title =  findViewById(R.id.title);
        release_date =  findViewById(R.id.release_date);
        overview =  findViewById(R.id.overview);
        vote_average =  findViewById(R.id.vote_average);
        original_title =  findViewById(R.id.original_title);
        original_language =  findViewById(R.id.original_language);
        popularity =  findViewById(R.id.popularity);


        recyclerView =  findViewById(R.id.reciclerViewTrailer);

        recyclerView.setLayoutManager(new GridLayoutManager(this, Constants.TWO_COLUMNS));



        videos = new ArrayList<>();



        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(videoAdapter);
        videoAdapter = new VideoAdapter( this, videos ) ;
        videoAdapter.notifyDataSetChanged();





        Intent it = getIntent();

        Bundle bundle = getIntent().getExtras();
        if( bundle != null ) {
            this.movie = (Movie) bundle.getParcelable(Movie.PARCELABLE_KEY);

            idMovie = movie.getId();

            release_date.setText(movie.getRelease_date());

            original_title.setText(movie.getOriginal_title());

            url_image_foto = movie.getPoster_path();

            overview.setText(movie.getOverview());

            vote_average.setText(String.valueOf(movie.getVote_average()));

            title.setText(movie.getTitle());

            original_language.setText(movie.getOriginal_language());

            popularity.setText(String.valueOf(movie.getPopularity()));

            loadPhotoInImageView(url_image_foto, imageMovie);


            loadJson(idMovie );

        }else{
            Intent intent = new Intent( this, ErrorActivity.class );
            it.putExtra("ERROR", Constants.ERROR_MISSING_DATA );
            startActivity( intent );
        }
    }


    private void loadPhotoInImageView(String url, ImageView imageView) {
        Picasso.with(getApplicationContext()).load(url).fit().into(imageView, new Callback() {
            @Override
            public void onSuccess() {
                progressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onError() {
                progressBar.setVisibility(View.INVISIBLE);
            }
        });
    }

    private void loadJson( Integer idMovie ){
        try{
            if( BuildConfig.MY_API_KEY.isEmpty() ){
                NetworkUtils.myToast(this, getString (R.string.warning_key_api ), Constants.LONG );
                return;
            }

            Service apiService = Client.getClient().create( Service.class );
            Call<VideoResponse> call;
            if( idMovie > 1  )
                call = apiService.getTrailer(BuildConfig.MY_API_KEY);
            else
                call = apiService.getTrailer(BuildConfig.MY_API_KEY);


            call.enqueue(new retrofit2.Callback<VideoResponse>() {
                @Override
                public void onResponse(Call<VideoResponse> call, Response<VideoResponse> response) {
                    if( response.body() == null || response.body().getResults().size() == 0  )
                    {
                        NetworkUtils.myToast( getApplicationContext(), getString(R.string.fail_upload), Constants.LONG );
                    }else{



                        List<Video> videos = response.body().getResults();
                        recyclerView.setAdapter( new VideoAdapter( getApplicationContext(), videos  ) );
                        recyclerView.smoothScrollToPosition( 0 );
                    }


                }

                @Override
                public void onFailure(Call<VideoResponse> call, Throwable t) {
                    Log.d("LOG", "-- "+ t.getCause() + " "+t.getMessage()+" "+ call.toString() );

                    NetworkUtils.myToast( getApplicationContext(),  getString(R.string.warning_fetch_data),  Toast.LENGTH_LONG );
                }
            });
        }catch ( Exception e ){
            NetworkUtils.myToast( getApplicationContext(),  getString(R.string.errror_try_catch),  Toast.LENGTH_LONG );
        }
    }
}
