package com.maxwell.tamilcric.adapter;

import android.content.Context;
import android.content.Intent;
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
import com.maxwell.tamilcric.PlayerDetailsActivity;
import com.maxwell.tamilcric.R;
import com.maxwell.tamilcric.model.BattingStatsModel;

import java.security.SecureRandom;
import java.util.List;


public class BattingStatsAdapter extends RecyclerView.Adapter<BattingStatsAdapter.ViewHolder>{

    List<BattingStatsModel> battingStatsModelList;
    Context context;

    public BattingStatsAdapter(Context mcontext, List<BattingStatsModel> battingStatsModelList){
        this.context=mcontext;
        this.battingStatsModelList =battingStatsModelList;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View listItem= layoutInflater.inflate(R.layout.layout_batting_stats_row, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        final BattingStatsModel battingStatsModel= battingStatsModelList.get(i);


        viewHolder.tv_player_name.setText(battingStatsModel.getPlayerName());
        viewHolder.tv_team_name.setText(battingStatsModel.getTeamName());

        if(battingStatsModel.getScore().contains(",")) {
            String[] values = battingStatsModel.getScore().split(",");
            viewHolder.tv_score.setText(values[0]);
            viewHolder.tv_score_tag.setText(values[1]);

        }else {
            viewHolder.tv_score.setText(battingStatsModel.getScore());
            viewHolder.tv_score_tag.setText("");
        }
        viewHolder.tv_tag.setText(battingStatsModel.getStat());
      //
        //  viewHolder.tv_tag.setBackgroundColor(getRandomColor());

        Glide.with(context)
                .load(battingStatsModel.getPlayerImage()) // image url
                .placeholder(R.drawable.tcalogo) // any placeholder to load at start
                .error(R.drawable.tcalogo)  // any image in case of error
                // .override(200, 200); // resizing
                // .apply(new RequestOptions().placeholder(R.drawable.loading))
                .into(viewHolder.iv_player);
        Glide.with(context)
                .load(battingStatsModel.getTeamImage()) // image url
                .placeholder(R.drawable.tcalogo) // any placeholder to load at start
                .error(R.drawable.tcalogo)  // any image in case of error
                // .override(200, 200); // resizing
                // .apply(new RequestOptions().placeholder(R.drawable.loading))
                .into(viewHolder.iv_team);

        viewHolder.linearLayout.setTag(i);
        if(!battingStatsModel.getPlayerName().equals("-")&&battingStatsModel.getPlayerName().length()>=3){

            viewHolder.layout_player_detail.setVisibility(View.VISIBLE);
            viewHolder.tv_no_player_details.setVisibility(View.GONE);
        }else {
            viewHolder.tv_no_player_details.setVisibility(View.VISIBLE);
            viewHolder.layout_player_detail.setVisibility(View.GONE);
        }
/*
        viewHolder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int position=(int) v.getTag();

                Intent i=new Intent(context, PlayerDetailsActivity.class);
                i.putExtra("PlayerId", battingStatsModel.getPlayerId());
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
            }
        });
*/

    }
    private int getRandomColor() {
        SecureRandom rgen = new SecureRandom();
        return Color.HSVToColor(170, new float[]{
                rgen.nextInt(359), 1, 1
        });
    }
    @Override
    public int getItemCount() {
        return battingStatsModelList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView iv_player,iv_team;
        public TextView tv_player_name,tv_tag,tv_team_name,tv_score,tv_score_tag;
        public LinearLayout linearLayout,layout_player_detail;
        TextView tv_no_player_details;


        public ViewHolder(View itemView) {
            super(itemView);
            this.linearLayout=(LinearLayout)itemView.findViewById(R.id.layout_contained_view);
            this.layout_player_detail=(LinearLayout)itemView.findViewById(R.id.layout_player_info);
            this.iv_player = (ImageView) itemView.findViewById(R.id.image_player);
            this.iv_team = (ImageView) itemView.findViewById(R.id.image_team_logo);
            this.tv_player_name = (TextView) itemView.findViewById(R.id.text_player_name);
            this.tv_tag = (TextView) itemView.findViewById(R.id.text_tag);
            this.tv_team_name = (TextView) itemView.findViewById(R.id.text_team_name);
            this.tv_score = (TextView) itemView.findViewById(R.id.text_score);
            this.tv_score_tag = (TextView) itemView.findViewById(R.id.text_score_tag);
            this.tv_no_player_details = (TextView) itemView.findViewById(R.id.text_no_details);


        }
    }

}
