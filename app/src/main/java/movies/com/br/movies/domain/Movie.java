package movies.com.br.movies.domain;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import movies.com.br.movies.utils.Constants;

/**
 * Created by Paulo on 31/01/2018.
 */

public class Movie implements Parcelable {

    @SerializedName("title")
    private String title;

    @SerializedName("video")
    private String video;

    @SerializedName("backdrop_path")
    private String backdrop_path;

    @SerializedName("overview")
    private String overview;

    @SerializedName("vote_average")
    private Double vote_average;

    @SerializedName("release_date")
    private String release_date;

    @SerializedName("genre_ids")
    private List<Integer> genre_ids = new ArrayList<>();

    @SerializedName("vote_count")
    private Integer vote_count;

    @SerializedName("id")
    private Integer id;

    @SerializedName("popularity")
    private Double popularity;

    @SerializedName("adult")
    private Boolean adult;

    @SerializedName("poster_path")
    private String poster_path;


    @SerializedName("original_language")
    private String original_language;

    @SerializedName("original_title")
    private String original_title;

    public Movie(String title, String video, String backdrop_path, String overview, Double vote_average, String release_date, List<Integer> genre_ids, Integer vote_count, Integer id, Double popularity, Boolean adult, String poster_path, String original_language, String original_title) {
        this.title = title;
        this.video = video;
        this.backdrop_path = backdrop_path;
        this.overview = overview;
        this.vote_average = vote_average;
        this.release_date = release_date;
        this.genre_ids = genre_ids;
        this.vote_count = vote_count;
        this.id = id;
        this.popularity = popularity;
        this.adult = adult;
        this.poster_path = poster_path;
        this.original_language = original_language;
        this.original_title = original_title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public void setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public Double getVote_average() {
        return vote_average;
    }

    public void setVote_average(Double vote_average) {
        this.vote_average = vote_average;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public List<Integer> getGenre_ids() {
        return genre_ids;
    }

    public void setGenre_ids(List<Integer> genre_ids) {
        this.genre_ids = genre_ids;
    }

    public Integer getVote_count() {
        return vote_count;
    }

    public void setVote_count(Integer vote_count) {
        this.vote_count = vote_count;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getPopularity() {
        return popularity;
    }

    public void setPopularity(Double popularity) {
        this.popularity = popularity;
    }

    public Boolean getAdult() {
        return adult;
    }

    public void setAdult(Boolean adult) {
        this.adult = adult;
    }

    public String getPoster_path() {
        return Constants.BASE_IMAGE_URL +Constants.SIZE_IMAGE_185+ poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public String getOriginal_language() {
        return original_language;
    }

    public void setOriginal_language(String original_language) {
        this.original_language = original_language;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }


    public static  final String PARCELABLE_KEY = "movie";

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeString(this.video);
        dest.writeString(this.backdrop_path);
        dest.writeString(this.overview);
        dest.writeValue(this.vote_average);
        dest.writeString(this.release_date);
        dest.writeList(this.genre_ids);
        dest.writeValue(this.vote_count);
        dest.writeValue(this.id);
        dest.writeValue(this.popularity);
        dest.writeValue(this.adult);
        dest.writeString(this.poster_path);
        dest.writeString(this.original_language);
        dest.writeString(this.original_title);
    }

    protected Movie(Parcel in) {
        this.title = in.readString();
        this.video = in.readString();
        this.backdrop_path = in.readString();
        this.overview = in.readString();
        this.vote_average = (Double) in.readValue(Double.class.getClassLoader());
        this.release_date = in.readString();
        this.genre_ids = new ArrayList<Integer>();
        in.readList(this.genre_ids, Integer.class.getClassLoader());
        this.vote_count = (Integer) in.readValue(Integer.class.getClassLoader());
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.popularity = (Double) in.readValue(Double.class.getClassLoader());
        this.adult = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.poster_path = in.readString();
        this.original_language = in.readString();
        this.original_title = in.readString();
    }

    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel source) {
            return new Movie(source);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
}
