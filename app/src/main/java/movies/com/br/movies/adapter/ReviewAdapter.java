package movies.com.br.movies.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import movies.com.br.movies.R;
import movies.com.br.movies.activiy.ReviewActivity;
import movies.com.br.movies.domain.Review;

/**
 * Created by Paulo on 07/02/2018.
 */

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder> {

    private final List<Review> reviews;
    private Context context;


    public ReviewAdapter(Context context, List<Review> reviews) {
        this.reviews = reviews;
        this.context = context;

    }

    @Override
    public ReviewAdapter.ReviewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_review, parent, false);
        return new ReviewAdapter.ReviewViewHolder(view);


    }

    @Override
    public void onBindViewHolder(final ReviewAdapter.ReviewViewHolder holder, int position) {

        Review review = reviews.get(position);
        Log.d("LOG", "-- " + review.getAuthor());
        holder.tv_author.setText(review.getAuthor());
        holder.tv_title.setText(review.getContent());

    }


    @Override
    public int getItemCount() {
        return this.reviews != null ? this.reviews.size() : 0;
    }


    public class ReviewViewHolder extends RecyclerView.ViewHolder {

        public TextView tv_author;
        public TextView tv_title;

        public ReviewViewHolder(final View itemView) {
            super(itemView);
            //create the view to save in viewholder
            tv_title = itemView.findViewById(R.id.tv_title);
            tv_author = itemView.findViewById(R.id.tv_author);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION) {
                        Intent it = new Intent(context, ReviewActivity.class);
                        it.putExtra("REVIEW", tv_title.getText().toString());
                        it.putExtra("AUTHOR", tv_author.getText().toString());
                        context.startActivity(it);
                    }
                }
            });
        }
    }
}

