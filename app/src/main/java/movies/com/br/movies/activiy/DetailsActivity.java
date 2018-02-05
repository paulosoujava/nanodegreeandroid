package movies.com.br.movies.activiy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import movies.com.br.movies.R;
import movies.com.br.movies.domain.Movie;
import movies.com.br.movies.utils.Constants;

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


        progressBar.setVisibility(View.VISIBLE);


        Intent it = getIntent();

        Bundle bundle = getIntent().getExtras();
        if( bundle != null ) {
            this.movie = (Movie) bundle.getParcelable(Movie.PARCELABLE_KEY);


            release_date.setText(movie.getRelease_date());

            original_title.setText(movie.getOriginal_title());

            url_image_foto = movie.getPoster_path();

            overview.setText(movie.getOverview());

            vote_average.setText(String.valueOf(movie.getVote_average()));

            title.setText(movie.getTitle());

            original_language.setText(movie.getOriginal_language());

            popularity.setText(String.valueOf(movie.getPopularity()));

            loadPhotoInImageView(url_image_foto, imageMovie);

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


}
