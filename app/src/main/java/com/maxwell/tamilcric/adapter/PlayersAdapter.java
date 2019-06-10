package com.maxwell.tamilcric.adapter;

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

import com.bumptech.glide.Glide;
import com.maxwell.tamilcric.PlayerDetailsActivity;
import com.maxwell.tamilcric.R;
import com.maxwell.tamilcric.model.PlayerModel;
import com.maxwell.tamilcric.model.TeamModel;
import com.squareup.picasso.Picasso;

import java.util.List;


public class PlayersAdapter extends RecyclerView.Adapter<PlayersAdapter.ViewHolder>{

    List<PlayerModel> peoplesModels;
    Context context;

    public PlayersAdapter(Context mcontext, List<PlayerModel> peoplesModelList){
        this.context=mcontext;
        this.peoplesModels=peoplesModelList;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View listItem= layoutInflater.inflate(R.layout.layout_player_row, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        final PlayerModel teamModel=peoplesModels.get(i);
        viewHolder.tv_name.setText(teamModel.getPlayerName());
        Glide.with(context)
                .load(teamModel.getPlayerImage()) // image url
                .placeholder(R.drawable.tcalogo) // any placeholder to load at start
                .error(R.drawable.tcalogo)  // any image in case of error
                // .override(200, 200); // resizing
                // .apply(new RequestOptions().placeholder(R.drawable.loading))
                .into(viewHolder.imageView);
/*
        Picasso.get()
                .load(teamModel.getTeamLogo())
                .placeholder(R.drawable.tcalogo)
                .error(R.drawable.tcalogo)
                .into(viewHolder.iv_player);
*/

        viewHolder.linearLayout.setTag(i);
        viewHolder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int position=(int) v.getTag();

                Intent i=new Intent(context, PlayerDetailsActivity.class);
                i.putExtra("PlayerId",peoplesModels.get(position).getId());
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
            this.linearLayout=(LinearLayout)itemView.findViewById(R.id.layout_contained_view);
            this.imageView = (ImageView) itemView.findViewById(R.id.image_player_pic);
            this.tv_name = (TextView) itemView.findViewById(R.id.player_name);

        }
    }

}
