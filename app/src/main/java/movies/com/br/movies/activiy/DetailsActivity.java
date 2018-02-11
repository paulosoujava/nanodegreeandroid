package movies.com.br.movies.activiy;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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
import movies.com.br.movies.adapter.ReviewAdapter;
import movies.com.br.movies.adapter.VideoAdapter;
import movies.com.br.movies.api.Client;
import movies.com.br.movies.api.Service;
import movies.com.br.movies.data.TaskContract;
import movies.com.br.movies.domain.Movie;
import movies.com.br.movies.domain.Review;
import movies.com.br.movies.domain.ReviewResponse;
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
    private TextView favoriteMovie;
    private ProgressBar progressBar;
    private String url_image_foto = "";
    private Movie movie;
    private Integer idMovie;
    private VideoAdapter videoAdapter;
    private ReviewAdapter reviewAdapter;
    private RecyclerView recyclerViewTrailer;
    private RecyclerView recyclerViewReviews;
    private List<Video> videos;
    private List<Review> reviews;
    private AlertDialog.Builder builder;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);

        imageMovie = findViewById(R.id.backdrop_path);
        progressBar = findViewById(R.id.progress);
        title = findViewById(R.id.title);
        release_date = findViewById(R.id.release_date);
        overview = findViewById(R.id.overview);
        vote_average = findViewById(R.id.vote_average);
        original_title = findViewById(R.id.original_title);
        original_language = findViewById(R.id.original_language);
        popularity = findViewById(R.id.popularity);
        favoriteMovie = findViewById(R.id.favoriteMovie);


        builder = new AlertDialog.Builder(this);
        videos = new ArrayList<>();
        reviews = new ArrayList<>();


        recyclerViewTrailer = findViewById(R.id.reciclerViewTrailer);
        recyclerViewReviews = findViewById(R.id.reciclerViewReview);


        recyclerViewTrailer.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerViewReviews.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        recyclerViewTrailer.setItemAnimator(new DefaultItemAnimator());
        recyclerViewReviews.setItemAnimator(new DefaultItemAnimator());


        recyclerViewTrailer.setAdapter(videoAdapter);
        recyclerViewReviews.setAdapter(reviewAdapter);
        videoAdapter = new VideoAdapter(this, videos);
        reviewAdapter = new ReviewAdapter(this, reviews);

        videoAdapter.notifyDataSetChanged();
        reviewAdapter.notifyDataSetChanged();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        Intent it = getIntent();

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {


            this.movie = (Movie) bundle.getParcelable(Movie.PARCELABLE_KEY);

            CollapsingToolbarLayout collaspeLayout;
            collaspeLayout = findViewById(R.id.collaspeLayout);
            collaspeLayout.setTitle(movie.getTitle());

            idMovie = movie.getId();

            release_date.setText(movie.getRelease_date());

            original_title.setText(movie.getOriginal_title());

            if (it.hasExtra("IMAGE_FROM_BD")) {
                url_image_foto = bundle.getString("IMAGE_FROM_BD");
                favoriteMovie.setVisibility(View.VISIBLE);
            } else
                url_image_foto = movie.getPoster_path();


            overview.setText(movie.getOverview());

            vote_average.setText(String.valueOf(movie.getVote_average()));

            title.setText(movie.getTitle());

            original_language.setText(movie.getOriginal_language());

            popularity.setText(String.valueOf(movie.getPopularity()));

            loadPhotoInImageView(url_image_foto, imageMovie);


            loadVideo();
            loadReview();

        } else {


            Intent intent = new Intent(this, ErrorActivity.class);
            it.putExtra("ERROR", Constants.ERROR_MISSING_DATA);
            startActivity(intent);
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

    private void loadVideo() {
        try {
            if (BuildConfig.MY_API_KEY.isEmpty()) {
                NetworkUtils.myToast(this, getString(R.string.warning_key_api), Constants.LONG);
                return;
            }

            Service apiService = Client.getClient().create(Service.class);
            Call<VideoResponse> call;

            call = apiService.getTrailer(String.valueOf(idMovie), BuildConfig.MY_API_KEY);
            call.enqueue(new retrofit2.Callback<VideoResponse>() {
                @Override
                public void onResponse(Call<VideoResponse> call, Response<VideoResponse> response) {
                    if (response.body() == null || response.body().getResults().size() == 0) {
                        NetworkUtils.myToast(getApplicationContext(), getString(R.string.fail_upload), Constants.LONG);
                    } else {
                        List<Video> videos = response.body().getResults();
                        Log.d("LOG", videos.toString());
                        recyclerViewTrailer.setAdapter(new VideoAdapter(getApplicationContext(), videos));
                        recyclerViewTrailer.smoothScrollToPosition(0);
                    }

                }

                @Override
                public void onFailure(Call<VideoResponse> call, Throwable t) {
                    NetworkUtils.myToast(getApplicationContext(), getString(R.string.warning_fetch_data), Toast.LENGTH_LONG);
                }
            });
        } catch (Exception e) {
            NetworkUtils.myToast(getApplicationContext(), getString(R.string.errror_try_catch), Toast.LENGTH_LONG);
        }
    }

    private void loadReview() {
        try {
            if (BuildConfig.MY_API_KEY.isEmpty()) {
                NetworkUtils.myToast(this, getString(R.string.warning_key_api), Constants.LONG);
                return;
            }

            Service apiService = Client.getClient().create(Service.class);
            Call<ReviewResponse> call;

            call = apiService.getReview(String.valueOf(idMovie), BuildConfig.MY_API_KEY);


            call.enqueue(new retrofit2.Callback<ReviewResponse>() {
                @Override
                public void onResponse(Call<ReviewResponse> call, Response<ReviewResponse> response) {
                    if (response.body() == null || response.body().getResults().size() == 0) {
                        NetworkUtils.myToast(getApplicationContext(), getString(R.string.fail_upload), Constants.LONG);
                    } else {
                        List<Review> videos = response.body().getResults();
                        recyclerViewReviews.setAdapter(new ReviewAdapter(getApplicationContext(), videos));
                        recyclerViewReviews.smoothScrollToPosition(0);
                    }

                }

                @Override
                public void onFailure(Call<ReviewResponse> call, Throwable t) {
                    NetworkUtils.myToast(getApplicationContext(), getString(R.string.warning_fetch_data), Toast.LENGTH_LONG);
                }
            });
        } catch (Exception e) {
            NetworkUtils.myToast(getApplicationContext(), getString(R.string.errror_try_catch), Toast.LENGTH_LONG);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.ic_favorite:

                ContentValues contentValues = new ContentValues();
                Uri uri;

                contentValues.put(TaskContract.TaskEntry.COLUMN_ID, movie.getId());
                contentValues.put(TaskContract.TaskEntry.COLUMN_TITLE, movie.getTitle());
                contentValues.put(TaskContract.TaskEntry.COLUMN_ORIGINAL_TITLE, movie.getOriginal_title());
                contentValues.put(TaskContract.TaskEntry.COLUMN_POST_PATH, movie.getPoster_path());
                contentValues.put(TaskContract.TaskEntry.COLUMN_IMAGE, movie.getBackdrop_path());
                contentValues.put(TaskContract.TaskEntry.COLUMN_POPULARITY, movie.getPopularity());
                contentValues.put(TaskContract.TaskEntry.COLUMN_ORIGINAL_LANGUAGE, movie.getOriginal_language());
                contentValues.put(TaskContract.TaskEntry.COLUMN_VOTE_AVERAGE, movie.getVote_average());
                contentValues.put(TaskContract.TaskEntry.COLUMN_ADULT, movie.getAdult());
                contentValues.put(TaskContract.TaskEntry.COLUMN_VIDEO, movie.getVideo());
                contentValues.put(TaskContract.TaskEntry.COLUMN_RELEASE_DATE, movie.getRelease_date());
                contentValues.put(TaskContract.TaskEntry.COLUMN_VOTE_AVERAGE, movie.getVote_average());
                contentValues.put(TaskContract.TaskEntry.COLUMN_OVERVIEW, movie.getOverview());
                contentValues.put(TaskContract.TaskEntry.COLUMN_VOTE_COUNT, movie.getVote_count());

                uri = getContentResolver().insert(TaskContract.TaskEntry.CONTENT_URI, contentValues);

                if (uri != null) {
                    NetworkUtils.myToast(this, getString(R.string.yepInserMovie), Constants.SHORT);
                } else {
                    builder.setIcon(R.drawable.ic_launcher_background);
                    builder.setTitle(R.string.removeMovie);
                    builder.setMessage(R.string.messegeRemove);
                    builder.setPositiveButton(R.string.yep, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String stringId = String.valueOf(movie.getId());
                            Uri uri = TaskContract.TaskEntry.CONTENT_URI;
                            uri = uri.buildUpon().appendPath(stringId).build();
                            getContentResolver().delete(uri, null, null);
                            NetworkUtils.myToast(getApplicationContext(), getString(R.string.removedMovie), Constants.SHORT);
                            favoriteMovie.setVisibility(View.INVISIBLE);


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

            case R.id.ic_share:

                Intent share = new Intent(Intent.ACTION_SEND);
                share.setType("text/plain");
                share.putExtra(Intent.EXTRA_SUBJECT, "Share ");
                share.putExtra(Intent.EXTRA_TEXT, " My app THE MOVIE FASE 2");
                startActivity(share);

                return true;

            case android.R.id.home:
                finish();
                return true;

        }
        return super.onOptionsItemSelected(item);
    }


}
