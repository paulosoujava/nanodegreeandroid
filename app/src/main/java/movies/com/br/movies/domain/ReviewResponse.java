package movies.com.br.movies.domain;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Paulo on 07/02/2018.
 */

public class ReviewResponse {

    @SerializedName("results")
    private List<Review> results;


    public List<Review> getResults() {
        return results;
    }

    public void setResults(List<Review> results) {
        this.results = results;
    }


}
