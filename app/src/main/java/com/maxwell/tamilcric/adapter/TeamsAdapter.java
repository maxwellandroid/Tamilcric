package com.maxwell.tamilcric.adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.maxwell.tamilcric.PlayersActivity;
import com.maxwell.tamilcric.R;
import com.maxwell.tamilcric.model.TeamModel;
import com.squareup.picasso.Picasso;

import java.util.List;


public class TeamsAdapter extends RecyclerView.Adapter<TeamsAdapter.ViewHolder>{

    List<TeamModel> peoplesModels;
    Context context;

    public TeamsAdapter(Context mcontext, List<TeamModel> peoplesModelList){
        this.context=mcontext;
        this.peoplesModels=peoplesModelList;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View listItem= layoutInflater.inflate(R.layout.layout_team_row, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        final TeamModel teamModel=peoplesModels.get(i);
        viewHolder.tv_name.setText(teamModel.getTeamName());
      /*  Glide.with(context)
                .load(teamModel.getTeamLogo()) // image url
                // .placeholder(R.drawable.placeholder) // any placeholder to load at start
              .error(R.drawable.tcalogo)  // any image in case of error
                // .override(200, 200); // resizing
                // .apply(new RequestOptions().placeholder(R.drawable.loading))
                .into(viewHolder.iv_player);*/
        Picasso.get()
                .load(teamModel.getTeamLogo())
                .placeholder(R.drawable.tcalogo)
                .error(R.drawable.tcalogo)
                .into(viewHolder.imageView);
        viewHolder.linearLayout.setTag(i);
        viewHolder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i=new Intent(context, PlayersActivity.class);
                i.putExtra("TeamID",teamModel.getId());
                i.putExtra("TeamName",teamModel.getTeamName());
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return peoplesModels.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView tv_name,tv_follow;
        public LinearLayout linearLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            this.imageView = (ImageView) itemView.findViewById(R.id.image_team_logo);
            this.tv_name = (TextView) itemView.findViewById(R.id.team_name);
            this.linearLayout=(LinearLayout)itemView.findViewById(R.id.layout_contained_view);

        }
    }

}
