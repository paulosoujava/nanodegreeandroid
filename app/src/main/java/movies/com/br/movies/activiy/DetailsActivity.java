package movies.com.br.movies.activiy;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Handler;
import android.support.annotation.UiThread;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import movies.com.br.movies.R;
import movies.com.br.movies.utils.Constants;

public class DetailsActivity extends AppCompatActivity {

    private ImageView imageMovie;
    private TextView title,
            popularity,
            release_date,
            overview,
            vote_average,
            original_title,
            original_language;
    private ProgressBar progressBar;
    private String url_image_foto = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        imageMovie = (ImageView) findViewById(R.id.backdrop_path);
        progressBar = (ProgressBar) findViewById(R.id.progress);
        title = (TextView) findViewById(R.id.title);
        release_date = (TextView) findViewById(R.id.release_date);
        overview = (TextView) findViewById(R.id.overview);
        vote_average = (TextView) findViewById(R.id.vote_average);
        original_title = (TextView) findViewById(R.id.original_title);
        original_language = (TextView) findViewById(R.id.original_language);
        popularity = (TextView) findViewById(R.id.popularity);


        progressBar.setVisibility(View.VISIBLE);


        Intent it = getIntent();

        if (it.hasExtra("RELEASE_DATA"))
            release_date.setText(it.getStringExtra("RELEASE_DATA"));
        if (it.hasExtra("ORIGINAL_TITLE"))
            original_title.setText(it.getStringExtra("ORIGINAL_TITLE"));
        if (it.hasExtra("POSTER_PATH"))
            url_image_foto = it.getStringExtra("POSTER_PATH");
        if (it.hasExtra("OVERVIEW"))
            overview.setText(it.getStringExtra("OVERVIEW"));
        if (it.hasExtra("VOTE_AVERAGE"))
            vote_average.setText(it.getStringExtra("VOTE_AVERAGE"));
        if (it.hasExtra("TITLE"))
            title.setText(it.getStringExtra("TITLE"));
        if (it.hasExtra("ORIGINAL_LANGUAGE"))
            original_language.setText(it.getStringExtra("ORIGINAL_LANGUAGE"));
        if (it.hasExtra("POPULARITY"))
            popularity.setText(it.getStringExtra("POPULARITY"));

        myPicasso(url_image_foto, imageMovie);


    }


    private void myPicasso(String url, ImageView imageView) {
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
