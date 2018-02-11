package movies.com.br.movies.domain;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Paulo on 07/02/2018.
 */

public class VideoResponse  {

    @SerializedName("results")
    private List<Video> results;


    public List<Video> getResults() {
        return results;
    }

    public void setResults(List<Video> results) {
        this.results = results;
    }




}
