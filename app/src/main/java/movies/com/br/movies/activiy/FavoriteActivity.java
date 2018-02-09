package movies.com.br.movies.activiy;

import android.app.ProgressDialog;
import android.content.res.Configuration;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import movies.com.br.movies.R;
import movies.com.br.movies.adapter.FavoriteAdapter;
import movies.com.br.movies.adapter.MovieAdapter;
import movies.com.br.movies.data.MovieRepository;
import movies.com.br.movies.domain.Movie;
import movies.com.br.movies.utils.Constants;
import movies.com.br.movies.utils.NetworkUtils;

public class FavoriteActivity extends AppCompatActivity {

    private FavoriteAdapter movieAdapter;
    private RecyclerView recyclerView;
    private List<Movie> movies;
    private MovieRepository movieRepository;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        recyclerView = findViewById(R.id.RecyclerViewFavorite);
        recyclerView.setLayoutManager(new GridLayoutManager(this, Constants.TWO_COLUMNS));

        CollapsingToolbarLayout collaspeLayout;
        collaspeLayout = findViewById(R.id.collaspeLayout);
        collaspeLayout.setTitle(getString(R.string.myFavorite));

        //from BD
        movieRepository = new MovieRepository(this);
        movies = movieRepository.getAllMovie();


            movieAdapter = new FavoriteAdapter(this, movies);


            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                recyclerView.setLayoutManager(new GridLayoutManager(this, Constants.TWO_COLUMNS));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(this, Constants.THREE_COLUMNS));
            }

            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(movieAdapter);
            movieAdapter.notifyDataSetChanged();

    }



}
