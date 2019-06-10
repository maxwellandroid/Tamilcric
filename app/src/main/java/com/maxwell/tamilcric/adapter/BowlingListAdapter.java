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
import com.maxwell.tamilcric.model.BowlingModel;

import java.util.List;


public class BowlingListAdapter extends RecyclerView.Adapter<BowlingListAdapter.ViewHolder>{

    List<BowlingModel> bowlingModelList;
    Context context;

    public BowlingListAdapter(Context mcontext, List<BowlingModel> bowlingModelList){
        this.context=mcontext;
        this.bowlingModelList =bowlingModelList;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View listItem= layoutInflater.inflate(R.layout.layout_bowling_row, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        final BowlingModel teamModel= bowlingModelList.get(i);
        viewHolder.tv_name.setText(teamModel.getPlayer_name());
        viewHolder.tv_overs.setText(teamModel.getOvers());
        viewHolder.tv_runs.setText(teamModel.getRuns());
        viewHolder.tv_wides.setText(teamModel.getWides());
        viewHolder.tv_noballs.setText(teamModel.getNoBallas());
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
        return bowlingModelList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView tv_name, tv_runs, tv_overs, tv_wides, tv_noballs;
        public LinearLayout linearLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            this.linearLayout=(LinearLayout)itemView.findViewById(R.id.layout_contained_view);
            this.tv_name = (TextView) itemView.findViewById(R.id.text_name);
            this.tv_runs = (TextView) itemView.findViewById(R.id.text_runs);
            this.tv_overs = (TextView) itemView.findViewById(R.id.text_overs);
            this.tv_wides = (TextView) itemView.findViewById(R.id.text_wides);
            this.tv_noballs = (TextView) itemView.findViewById(R.id.text_noballs);

        }
    }

}
