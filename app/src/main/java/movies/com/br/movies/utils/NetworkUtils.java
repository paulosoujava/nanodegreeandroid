package movies.com.br.movies.utils;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * These utilities will be used to communicate with the network.
 */
public class NetworkUtils {

    public  static boolean checkInternet(Context ctx ){
        ConnectivityManager cm = (ConnectivityManager) ctx.getSystemService( Context.CONNECTIVITY_SERVICE );
        NetworkInfo info = cm.getActiveNetworkInfo();
        return  ( info != null && info.isConnected() );
    }


}