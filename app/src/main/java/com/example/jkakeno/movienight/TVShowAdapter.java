package com.example.jkakeno.movienight;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class TVShowAdapter extends RecyclerView.Adapter<TVShowAdapter.TVShowViewHolder> {
    private TVShow[] mTVShows;
    private Context mContext;


    public TVShowAdapter(Context context, TVShow[] tvshows){
        mContext = context;
        mTVShows = tvshows;
    }

    @Override
    public TVShowViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tvshow_list_item,parent,false);
        TVShowViewHolder viewHolder= new TVShowViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(TVShowViewHolder holder, int position) {
        holder.bindTVShow(mTVShows[position]);
    }

    @Override
    public int getItemCount() {
        return mTVShows.length;
    }



    public class TVShowViewHolder extends RecyclerView.ViewHolder {
        public TextView mName;
        public TextView mFirstReleaseDate;


        public TVShowViewHolder(final View itemView) {
            super(itemView);
            itemView.setClickable(true);
            itemView.setFocusableInTouchMode(true);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(itemView.getContext(),"Position: " + Integer.toString(getAdapterPosition()), Toast.LENGTH_LONG).show();
                }
            });
            mName = (TextView) itemView.findViewById(R.id.nameLabel);
            mFirstReleaseDate = (TextView) itemView.findViewById(R.id.firstAirDateLabel);
        }


        public void bindTVShow (TVShow tvShow) {
            mName.setText(tvShow.getName());
            mFirstReleaseDate.setText(tvShow.getFirstReleaseDate());
        }
    }
}
