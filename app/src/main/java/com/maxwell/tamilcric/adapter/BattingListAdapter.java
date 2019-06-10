package com.maxwell.tamilcric.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.maxwell.tamilcric.R;
import com.maxwell.tamilcric.model.BattingsModel;
import com.squareup.picasso.Picasso;

import java.util.List;


public class BattingListAdapter extends RecyclerView.Adapter<BattingListAdapter.ViewHolder>{

    List<BattingsModel> battingsModelList;
    Context context;

    public BattingListAdapter(Context mcontext, List<BattingsModel> battingsModelList){
        this.context=mcontext;
        this.battingsModelList =battingsModelList;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View listItem= layoutInflater.inflate(R.layout.layout_batting_row, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        final BattingsModel teamModel= battingsModelList.get(i);
        viewHolder.tv_name.setText(teamModel.getPlayer_name());
        viewHolder.tv_balls.setText(teamModel.getBalls());
        viewHolder.tv_runs.setText(teamModel.getRuns());
        viewHolder.tv_fours.setText(teamModel.getFours());
        viewHolder.tv_sixes.setText(teamModel.getSixes());
        viewHolder.tv_strike.setText(teamModel.getStraikRate());
    /*
        Glide.with(context)
                .load(teamModel.getTeamImage()) // image url
                // .placeholder(R.drawable.placeholder) // any placeholder to load at start
                //.error(R.drawable.imagenotfound)  // any image in case of error
                // .override(200, 200); // resizing
                // .apply(new RequestOptions().placeholder(R.drawable.loading))
                .into(viewHolder.iv_player);
    */

        if(i %2 == 1)
        {
            viewHolder.linearLayout.setBackgroundColor(Color.WHITE);
        }
        else
        {
            viewHolder.linearLayout.setBackgroundColor(Color.LTGRAY);
        }
    }

    @Override
    public int getItemCount() {
        return battingsModelList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView tv_name, tv_runs, tv_balls, tv_fours, tv_sixes,tv_strike;
        public LinearLayout linearLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            this.linearLayout=(LinearLayout)itemView.findViewById(R.id.layout_contained_view);
            this.tv_name = (TextView) itemView.findViewById(R.id.text_batsman_name);
            this.tv_runs = (TextView) itemView.findViewById(R.id.text_runs);
            this.tv_balls = (TextView) itemView.findViewById(R.id.text_balls);
            this.tv_fours = (TextView) itemView.findViewById(R.id.text_fours);
            this.tv_sixes = (TextView) itemView.findViewById(R.id.text_sixes);
            this.tv_strike = (TextView) itemView.findViewById(R.id.text_strikerates);

        }
    }

}
