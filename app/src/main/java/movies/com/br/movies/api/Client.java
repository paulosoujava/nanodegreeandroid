package movies.com.br.movies.api;

import movies.com.br.movies.utils.Constants;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Paulo on 03/02/2018.
 */

public class Client {

    public  static Retrofit retrofit = null;

    public  static Retrofit getClient(){
        if( retrofit == null ){
            retrofit = new Retrofit.Builder().baseUrl(Constants.BASE_URL ).addConverterFactory(GsonConverterFactory.create()).build();
        }
        return  retrofit;
    }
}
