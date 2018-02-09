package movies.com.br.movies.activiy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import movies.com.br.movies.R;
import movies.com.br.movies.utils.Constants;

public class ErrorActivity extends AppCompatActivity {

    private TextView errorMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_error);
        errorMsg =  findViewById( R.id.error_msg );
        Intent it = getIntent();


        if( it.hasExtra(Constants.ERROR_IDS ) ){
            switch ( it.getStringExtra("ERROR_IDS") ){
                case Constants.ERROR_INTERNET :
                    errorMsg.setText( R.string.error_internet );
                    break;

                case Constants.ERROR_MISSING_DATA :
                    errorMsg.setText( R.string.error_data );
                    break;
            }
        }else{
            errorMsg.setText( R.string.default_waring );
        }

    }



}
