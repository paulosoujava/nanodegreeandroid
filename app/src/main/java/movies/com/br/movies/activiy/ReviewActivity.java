package movies.com.br.movies.activiy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import movies.com.br.movies.R;

public class ReviewActivity extends AppCompatActivity {

    private TextView review;
    private TextView author;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        review = findViewById(R.id.review);
        author = findViewById(R.id.author);

        Intent it = getIntent();

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            author.setText( it.getStringExtra("AUTHOR") );
            review.setText(  it.getStringExtra("REVIEW") );
        }
    }
}
