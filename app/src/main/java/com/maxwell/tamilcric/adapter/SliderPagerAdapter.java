package com.maxwell.tamilcric.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.maxwell.tamilcric.R;
import com.maxwell.tamilcric.model.LiveMatchModel;
import com.squareup.picasso.Picasso;


import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class SliderPagerAdapter extends PagerAdapter {
    private LayoutInflater layoutInflater;
    Activity activity;
    List<LiveMatchModel> liveMatchModelList;

    public SliderPagerAdapter(Activity activity, List<LiveMatchModel> liveMatchModelList) {
        this.activity = activity;
        this.liveMatchModelList = liveMatchModelList;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = layoutInflater.inflate(R.layout.layout_live_update_row, container, false);
        ImageView teamALogo = (ImageView) view.findViewById(R.id.image_teamA_logo);
        ImageView teamBLogo = (ImageView) view.findViewById(R.id.image_teamB_logo);
        TextView matchNumeber = (TextView) view.findViewById(R.id.text_match_number);
        TextView tv_location = (TextView) view.findViewById(R.id.text_location);
        TextView teamAName = (TextView) view.findViewById(R.id.text_teamA_name);
        TextView teamBName = (TextView) view.findViewById(R.id.text_teamB_name);
        TextView teamAScore = (TextView) view.findViewById(R.id.text_teamA_score);
        TextView teamBScore = (TextView) view.findViewById(R.id.text_teamB_score);
        TextView teamAOvers = (TextView) view.findViewById(R.id.text_teamA_overs);
        TextView teamBOvers = (TextView) view.findViewById(R.id.text_teamB_overs);


            matchNumeber.setText("Match "+ liveMatchModelList.get(position).getMatchNumeber());
            tv_location.setText(liveMatchModelList.get(position).getStadium());
            teamAName.setText(liveMatchModelList.get(position).getTeamAName());
            teamBName.setText(liveMatchModelList.get(position).getTeamBName());
            teamAName.setText(liveMatchModelList.get(position).getTeamAName());
            teamAScore.setText(liveMatchModelList.get(position).getTeamAScore()+" / "+liveMatchModelList.get(position).getTeamAWickets());
            teamBScore.setText(liveMatchModelList.get(position).getTeamBSore()+" / "+liveMatchModelList.get(position).getTeamBWickets());
            teamAOvers.setText(liveMatchModelList.get(position).getTeamAOvers());
            teamBOvers.setText(liveMatchModelList.get(position).getTeamBOvers());

       /* Glide.with(activity.getApplicationContext()).load(resultMatchesModelsList.get(position).getTeamALogo()).placeholder(R.drawable.tcalogo).into(teamALogo);
        Glide.with(activity.getApplicationContext()).load(resultMatchesModelsList.get(position).getTeamBLogo()).into(teamBLogo);
       */ Picasso.get()
                .load(liveMatchModelList.get(position).getTeamALogo())
                .placeholder(R.drawable.tcalogo)
                .error(R.drawable.tcalogo)
                .into(teamALogo);
        Picasso.get()
                .load(liveMatchModelList.get(position).getTeamBLogo())
                .placeholder(R.drawable.tcalogo)
                .error(R.drawable.tcalogo)
                .into(teamBLogo);

        container.addView(view);
        return view;
    }

    @Override
    public int getCount() {
        return liveMatchModelList.size();
    }


    @Override
    public boolean isViewFromObject(View view, Object obj) {
        return view == obj;
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        View view = (View) object;
        container.removeView(view);
    }


    public boolean loadImageFromURL(String fileUrl,
                                    ImageView iv) {
        try {

            URL myFileUrl = new URL(fileUrl);
            HttpURLConnection conn =
                    (HttpURLConnection) myFileUrl.openConnection();
            conn.setDoInput(true);
            conn.connect();

            InputStream is = conn.getInputStream();
            iv.setImageBitmap(BitmapFactory.decodeStream(is));

            return true;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}