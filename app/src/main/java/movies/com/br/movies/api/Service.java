package movies.com.br.movies.api;


import movies.com.br.movies.BuildConfig;
import movies.com.br.movies.domain.Movie;
import movies.com.br.movies.domain.MovieResponse;
import movies.com.br.movies.domain.ReviewResponse;
import movies.com.br.movies.domain.VideoResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Paulo on 03/02/2018.
 */

public interface Service {

    @GET("movie/popular")
    Call<MovieResponse> getPopularMovie(@Query("api_key") String apiKey );

    @GET("movie/upcoming")
    Call<MovieResponse> getupComingMovie(@Query("api_key") String apiKey );

    @GET("movie/top_rated")
    Call<MovieResponse> getTopRated(@Query("api_key") String apiKey );

    @GET("https://api.themoviedb.org/3/search/movie?api_key="+ BuildConfig.MY_API_KEY+"&query=")
    Call<MovieResponse> getSearch( @Query("query") String q  );

    @GET("https://api.themoviedb.org/3/movie/{id}/videos?api_key="+ BuildConfig.MY_API_KEY )
    Call<VideoResponse> getTrailer( @Path("id") String  id, @Query("api_key") String apiKey );

    @GET("https://api.themoviedb.org/3/movie/{id}/reviews?api_key="+ BuildConfig.MY_API_KEY )
    Call<ReviewResponse> getReview(@Path("id") String  id, @Query("api_key") String apiKey  );

    @GET("https://api.themoviedb.org/3/movie/{id}?api_key="+ BuildConfig.MY_API_KEY )
    Call<Movie> getFavoriteMovie(@Path("id") String  id, @Query("api_key") String apiKey  );

}
