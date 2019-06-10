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
import com.maxwell.tamilcric.model.TournamentsModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class TournamentsAdapter extends RecyclerView.Adapter<TournamentsAdapter.ViewHolder>{

    List<TournamentsModel> peoplesModels;
    Context context;

    public TournamentsAdapter(Context mcontext, List<TournamentsModel> peoplesModelList){
        this.context=mcontext;
        this.peoplesModels=peoplesModelList;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View listItem= layoutInflater.inflate(R.layout.layout_tournaments, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        final TournamentsModel teamModel=peoplesModels.get(i);
        viewHolder.tv_name.setText(teamModel.getName());
       // viewHolder.tv_date.setText(teamModel.getDate());
        String str =teamModel.getDate();
// parse the String "29/07/2013" to a java.util.Date object

        try {
            Date date = new SimpleDateFormat("yyyy-MM-dd").parse(str);
            String formattedDate = new SimpleDateFormat("dd-MM-yyyy").format(date);
            viewHolder.tv_date.setText(formattedDate);
        } catch (ParseException e) {


            e.printStackTrace();
            viewHolder.tv_date.setText(teamModel.getDate());
        }
       // viewHolder.tv_age.setText(teamModel.getAgegroup());

       /* if(i %2 == 1)
        {
            viewHolder.linearLayout.setBackgroundColor(Color.WHITE);
        }
        else
        {
            viewHolder.linearLayout.setBackgroundColor(Color.LTGRAY);
        }*/
    }

    @Override
    public int getItemCount() {
        return peoplesModels.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView tv_date,tv_name, tv_age;
        public LinearLayout linearLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            this.linearLayout=(LinearLayout)itemView.findViewById(R.id.layout_contained_view);
            this.tv_name = (TextView) itemView.findViewById(R.id.text_name);
            this.tv_date = (TextView) itemView.findViewById(R.id.text_date);
            this.tv_age = (TextView) itemView.findViewById(R.id.text_age);

        }
    }

}
