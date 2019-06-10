package com.maxwell.tamilcric.adapter;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import com.maxwell.tamilcric.R;
import com.maxwell.tamilcric.model.MatchScheduleModel;

import java.util.List;


    public class MatchScheduleAdapter extends RecyclerView.Adapter<MatchScheduleAdapter.ViewHolder>{

    List<MatchScheduleModel> matchScheduleModelList;
    Context context;

    public MatchScheduleAdapter(Context mcontext, List<MatchScheduleModel> matchScheduleModelList){
        this.context=mcontext;
        this.matchScheduleModelList =matchScheduleModelList;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View listItem= layoutInflater.inflate(R.layout.layout_matchschedule_row, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        final MatchScheduleModel matchScheduleModel= matchScheduleModelList.get(i);
       /* viewHolder.tv_player_name.setText(matchScheduleModel.getTeamName());
        viewHolder.tv_slno.setText(String.valueOf(i+1));
        viewHolder.tv_overs.setText(matchScheduleModel.getWon());
        viewHolder.tv_runs.setText(matchScheduleModel.getPlayed());
        viewHolder.tv_wides.setText(matchScheduleModel.getLost());
        viewHolder.tv_noballs.setText(matchScheduleModel.getTied());
        Glide.with(context)
                .load(matchScheduleModel.getTeamImage()) // image url
                // .placeholder(R.drawable.placeholder) // any placeholder to load at start
                //.error(R.drawable.imagenotfound)  // any image in case of error
                // .override(200, 200); // resizing
                // .apply(new RequestOptions().placeholder(R.drawable.loading))
                .into(viewHolder.iv_player);*/
       viewHolder.tv_match_number.setText(matchScheduleModel.getMatchNumber());
       viewHolder.tv_date.setText(matchScheduleModel.getDate());
       viewHolder.tv_time.setText(matchScheduleModel.getTime());
       viewHolder.tv_ground.setText(matchScheduleModel.getGround());
       viewHolder.tv_matchbw.setText(matchScheduleModel.getTeamAName()+" Vs "+matchScheduleModel.getTeamBName());
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
        return matchScheduleModelList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public LinearLayout linearLayout;
        TextView tv_match_number,tv_date,tv_time,tv_ground,tv_matchbw;


        public ViewHolder(View itemView) {
            super(itemView);
            this.linearLayout=(LinearLayout)itemView.findViewById(R.id.layout_contained_view);
            this.tv_match_number =(TextView)itemView.findViewById(R.id.text_match_number);
            this.tv_date =(TextView)itemView.findViewById(R.id.text_date);
            this.tv_time =(TextView)itemView.findViewById(R.id.text_time);
            this.tv_ground =(TextView)itemView.findViewById(R.id.text_ground);
            this.tv_matchbw =(TextView)itemView.findViewById(R.id.text_match_bw);


        }
    }

}
