package com.example.jkakeno.movienight;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder>{
    private Movie[] mMovies;
    private Context mContext;

    public MovieAdapter(Context context, Movie[] movies){
        mContext = context;
        mMovies = movies;
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_list_item,parent,false);
        MovieViewHolder viewHolder = new MovieViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        holder.bindMovie(mMovies[position]);
    }

    @Override
    public int getItemCount() {
        return mMovies.length;
    }



    public class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView mTitle;
        public TextView mReleaseDate;

        public MovieViewHolder(View itemView) {
            super(itemView);
            mTitle = (TextView) itemView.findViewById(R.id.titleLabel);
            mReleaseDate = (TextView) itemView.findViewById(R.id.releaseDateLabel);
        }
        public void bindMovie(Movie movie) {
            mTitle.setText(movie.getTitle());
            mReleaseDate.setText(movie.getReleaseDate());
        }
        @Override
        public void onClick(View view) {
//Start a ShowOverViewDialogFragment when clicked on a holder

        }
    }
}
