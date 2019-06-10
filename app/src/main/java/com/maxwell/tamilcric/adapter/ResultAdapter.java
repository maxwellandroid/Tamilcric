package com.maxwell.tamilcric.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.maxwell.tamilcric.MatchSummeryActivity;
import com.maxwell.tamilcric.R;
import com.maxwell.tamilcric.model.ResultMatchesModels;
import com.squareup.picasso.Picasso;

import java.util.List;


public class ResultAdapter extends RecyclerView.Adapter<ResultAdapter.ViewHolder>{

    private LayoutInflater layoutInflater;
    Context mcontext;
    List<ResultMatchesModels> resultMatchesModelsList;

    public ResultAdapter(Context mcontext, List<ResultMatchesModels> resultMatchesModelsList){
        this.mcontext = mcontext;
        this.resultMatchesModelsList = resultMatchesModelsList;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View listItem= layoutInflater.inflate(R.layout.layout_result_row, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int position) {
        final ResultMatchesModels teamModel= resultMatchesModelsList.get(position);

        viewHolder.matchNumeber.setText("Match "+ resultMatchesModelsList.get(position).getMatchNumeber());
        viewHolder.tv_winning_description.setText(resultMatchesModelsList.get(position).getDescription());
        viewHolder.teamAName.setText(resultMatchesModelsList.get(position).getTeamAName());
        viewHolder.teamBName.setText(resultMatchesModelsList.get(position).getTeamBName());
        viewHolder.teamAName.setText(resultMatchesModelsList.get(position).getTeamAName());
        if(!resultMatchesModelsList.get(position).getTeamAScore().isEmpty()&&!resultMatchesModelsList.get(position).getTeamAScore().equals("null")&&resultMatchesModelsList.get(position).getTeamAScore()!=null){
            viewHolder.teamAScore.setText(resultMatchesModelsList.get(position).getTeamAScore()+" / "+resultMatchesModelsList.get(position).getTeamAWickets());
        }else {
            viewHolder.teamAScore.setText("0"+" / "+"0");
        }
        if(!resultMatchesModelsList.get(position).getTeamBSore().isEmpty()&&!resultMatchesModelsList.get(position).getTeamBSore().equals("null")&&resultMatchesModelsList.get(position).getTeamBSore()!=null){
            viewHolder.teamBScore.setText(resultMatchesModelsList.get(position).getTeamBSore()+" / "+resultMatchesModelsList.get(position).getTeamBWickets());
        }else {
            viewHolder.teamBScore.setText("0"+" / "+"0");
        }


        viewHolder.teamAOvers.setText("Overs "+resultMatchesModelsList.get(position).getTeamAOvers());
        viewHolder.teamBOvers.setText("Overs "+resultMatchesModelsList.get(position).getTeamBOvers());
        viewHolder.tv_manofthematch.setText(resultMatchesModelsList.get(position).getManoftheMatch());

       /* Glide.with(activity.getApplicationContext()).load(resultMatchesModelsList.get(position).getTeamALogo()).placeholder(R.drawable.tcalogo).into(teamALogo);
        Glide.with(activity.getApplicationContext()).load(resultMatchesModelsList.get(position).getTeamBLogo()).into(teamBLogo);
       */ Picasso.get()
                .load(resultMatchesModelsList.get(position).getTeamALogo())
                .placeholder(R.drawable.tcalogo)
                .error(R.drawable.tcalogo)
                .into(viewHolder.teamALogo);
        Picasso.get()
                .load(resultMatchesModelsList.get(position).getTeamBLogo())
                .placeholder(R.drawable.tcalogo)
                .error(R.drawable.tcalogo)
                .into(viewHolder.teamBLogo);
        viewHolder.ll_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i=new Intent(mcontext, MatchSummeryActivity.class);
                i.putExtra("MatchID",resultMatchesModelsList.get(position).getMatchId());
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mcontext.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return resultMatchesModelsList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView tv_name,tv_follow;
        public LinearLayout linearLayout;
        ImageView teamALogo ;
        ImageView teamBLogo ;
        TextView matchNumeber ;
        TextView tv_winning_description ;
        TextView teamAName ;
        TextView teamBName;
        TextView teamAScore;
        TextView teamBScore ;
        TextView teamAOvers ;
        TextView teamBOvers ;
        TextView tv_manofthematch;
        LinearLayout ll_container;

        public ViewHolder(View itemView) {
            super(itemView);
            this.ll_container=(LinearLayout)itemView.findViewById(R.id.layout_contained_view);
            this.imageView = (ImageView) itemView.findViewById(R.id.image_team_logo);
            this.tv_name = (TextView) itemView.findViewById(R.id.team_name);
            this.teamALogo = (ImageView) itemView.findViewById(R.id.image_teamA_logo);
            this.teamBLogo = (ImageView) itemView.findViewById(R.id.image_teamB_logo);
            this.matchNumeber = (TextView) itemView.findViewById(R.id.text_match_number);
            this.tv_winning_description = (TextView) itemView.findViewById(R.id.text_winning_description);
            this.teamAName = (TextView) itemView.findViewById(R.id.text_teamA_name);
            this.teamBName = (TextView) itemView.findViewById(R.id.text_teamB_name);
            this.teamAScore = (TextView) itemView.findViewById(R.id.text_teamA_score);
            this.teamBScore = (TextView) itemView.findViewById(R.id.text_teamB_score);
            this.teamAOvers = (TextView) itemView.findViewById(R.id.text_teamA_overs);
            this.teamBOvers = (TextView) itemView.findViewById(R.id.text_teamB_overs);
            this.tv_manofthematch=(TextView)itemView.findViewById(R.id.text_manofthematch);

        }
    }

}
