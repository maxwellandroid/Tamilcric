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

import com.bumptech.glide.Glide;
import com.maxwell.tamilcric.R;
import com.maxwell.tamilcric.model.PointsTableModel;
import com.maxwell.tamilcric.model.TeamModel;
import com.squareup.picasso.Picasso;

import java.util.List;


public class PointsTableAdapter extends RecyclerView.Adapter<PointsTableAdapter.ViewHolder>{

    List<PointsTableModel> peoplesModels;
    Context context;

    public PointsTableAdapter(Context mcontext, List<PointsTableModel> peoplesModelList){
        this.context=mcontext;
        this.peoplesModels=peoplesModelList;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View listItem= layoutInflater.inflate(R.layout.layout_points_table_row, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        final PointsTableModel teamModel=peoplesModels.get(i);
        viewHolder.tv_name.setText(teamModel.getTeamName());
        viewHolder.tv_slno.setText(String.valueOf(i+1));
        viewHolder.tv_won.setText(teamModel.getWon());
        viewHolder.tv_pld.setText(teamModel.getPlayed());
        viewHolder.tv_lost.setText(teamModel.getLost());
        viewHolder.tv_tied.setText(teamModel.getTied());
/*
        Glide.with(context)
                .load(teamModel.getTeamImage()) // image url
                // .placeholder(R.drawable.placeholder) // any placeholder to load at start
                //.error(R.drawable.imagenotfound)  // any image in case of error
                // .override(200, 200); // resizing
                // .apply(new RequestOptions().placeholder(R.drawable.loading))
                .into(viewHolder.iv_player);
*/
        Picasso.get()
                .load(teamModel.getTeamImage())
                .placeholder(R.drawable.tcalogo)
                .error(R.drawable.tcalogo)
                .into(viewHolder.imageView);

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
        return peoplesModels.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView tv_slno,tv_name,tv_pld,tv_won,tv_lost,tv_tied;
        public LinearLayout linearLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            this.linearLayout=(LinearLayout)itemView.findViewById(R.id.layout_contained_view);
            this.imageView = (ImageView) itemView.findViewById(R.id.image_team_logo);
            this.tv_name = (TextView) itemView.findViewById(R.id.text_team_name);
            this.tv_slno = (TextView) itemView.findViewById(R.id.text_slno);
            this.tv_pld = (TextView) itemView.findViewById(R.id.text_played);
            this.tv_won = (TextView) itemView.findViewById(R.id.text_won);
            this.tv_lost = (TextView) itemView.findViewById(R.id.text_lost);
            this.tv_tied = (TextView) itemView.findViewById(R.id.text_tied);

        }
    }

}
