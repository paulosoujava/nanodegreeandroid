package movies.com.br.movies.activiy;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import movies.com.br.movies.BuildConfig;
import movies.com.br.movies.R;
import movies.com.br.movies.adapter.MovieAdapter;
import movies.com.br.movies.api.Client;
import movies.com.br.movies.api.Service;
import movies.com.br.movies.domain.Movie;
import movies.com.br.movies.domain.MovieResponse;
import movies.com.br.movies.utils.Constants;
import movies.com.br.movies.utils.NetworkUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity{

    private MovieAdapter movieAdapter;
    private RecyclerView recyclerView;
    private List<Movie> movies;
    private String querysearch = "";
    private ProgressDialog progressDialog;
    private Toolbar toolbar;
    private Button bakcButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bakcButton = findViewById(R.id.backFloat);
        bakcButton.setVisibility( View.INVISIBLE );
        toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle( R.string.title_popular );

        recyclerView =  findViewById(R.id.RecyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, Constants.TWO_COLUMNS));


        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getString(R.string.wait_please));
        progressDialog.setCancelable(false);
        progressDialog.show();

        movies = new ArrayList<>();

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            recyclerView.setLayoutManager(new GridLayoutManager(this, Constants.TWO_COLUMNS));
        } else {
            recyclerView.setLayoutManager(new GridLayoutManager(this, Constants.FOUR_COLUMNS));
        }

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(movieAdapter);
        movieAdapter = new MovieAdapter( this, movies ) ;
        movieAdapter.notifyDataSetChanged();


        loadJson(Constants.POPULAR_MOVIES );

    }

    @Override
    protected void onResume() {
        super.onResume();
        if( !NetworkUtils.checkInternet( this )){
            startErrorIntent( Constants.ERROR_INTERNET );
        }
    }

    private void loadJson(Integer id ){
        try{
            if( BuildConfig.MY_API_KEY.isEmpty() ){
                NetworkUtils.myToast(this, getString (R.string.warning_key_api ), Constants.LONG );
                progressDialog.dismiss();
                return;
            }

            Service apiService = Client.getClient().create( Service.class );
            Call<MovieResponse> call;

            switch ( id )
            {
                case 1 : call = apiService.getTopRated( BuildConfig.MY_API_KEY ); break;
                case 2 : call = apiService.getSearch(  querysearch );  break;
                case 3 : call = apiService.getupComingMovie( BuildConfig.MY_API_KEY ); break;
                default: call =
                        apiService.getPopularMovie( BuildConfig.MY_API_KEY ); break;
            }
            call.enqueue(new Callback<MovieResponse>() {
                @Override
                public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                    if( response.body() == null || response.body().getResults().size() == 0  )
                    {
                        loadJson( Constants.POPULAR_MOVIES );
                        Toast.makeText( MainActivity.this,  R.string.opss,  Toast.LENGTH_LONG ).show();
                    }else{

                        if( !querysearch.isEmpty() ){
                            Toast.makeText( MainActivity.this,  getString(R.string.found)+ " [ "+ response.body().getResults().size()+" ] "+ getString(R.string.result) ,  Toast.LENGTH_LONG ).show();
                             querysearch = "";
                        }

                        List<Movie> movies = response.body().getResults();
                        recyclerView.setAdapter( new MovieAdapter( getApplicationContext(), movies  ) );
                        recyclerView.smoothScrollToPosition( 0 );
                    }

                    progressDialog.dismiss();
                }

                @Override
                public void onFailure(Call<MovieResponse> call, Throwable t) {
                    Toast.makeText( MainActivity.this,  R.string.warning_fetch_data,  Toast.LENGTH_LONG ).show();
                }
            });
        }catch ( Exception e ){
            Toast.makeText( MainActivity.this,  R.string.errror_try_catch,  Toast.LENGTH_LONG ).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        SearchView mSearchView = (SearchView) menu.findItem(R.id.ic_search).getActionView();
        mSearchView.setQueryHint(getString(R.string.search_here));
        mSearchView.setBackgroundColor(Color.WHITE);

        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(final String query) {
                querysearch = query;
                loadJson( Constants.SEARCH_MOVIES );
                bakcButton.setVisibility( View.VISIBLE );
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.ic_coming:
                toolbar.setTitle(R.string.title_upcoming );
                bakcButton.setVisibility( View.VISIBLE );
                loadJson( Constants.UPCOMING_MOVIES );
                return true;

            case R.id.ic_top_rated:
                toolbar.setTitle(R.string.title_top_rated );
                bakcButton.setVisibility( View.VISIBLE );
                loadJson( Constants.TOPRATED_MOVIES );
                return true;
        }
        return super.onOptionsItemSelected(item);
    }



    private void startErrorIntent( String codError ){
        Intent it = new Intent( this, ErrorActivity.class );
        it.putExtra(Constants.ERROR_IDS, codError );
        startActivityForResult( it,  Constants.CLOSE_ALLL_ACTIVITY );
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {


        if( requestCode == Constants.CLOSE_ALLL_ACTIVITY ){
            if( !NetworkUtils.checkInternet( this ) ){
                finish();
            }
        }
    }
}
