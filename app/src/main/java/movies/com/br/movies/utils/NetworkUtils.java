package movies.com.br.movies.utils;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

/**
 * These utilities will be used to communicate with the network.
 */
public class NetworkUtils {

    public  static boolean checkInternet(Context ctx ){
        ConnectivityManager cm = (ConnectivityManager) ctx.getSystemService( Context.CONNECTIVITY_SERVICE );
        NetworkInfo info = cm.getActiveNetworkInfo();
        return  ( info != null && info.isConnected() );
    }


    public  static void myToast(Context ctx, String msg, Integer duration ){
        Toast.makeText(ctx, msg, duration).show();
    }
}