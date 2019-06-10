package com.maxwell.tamilcric.fragments;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.maxwell.tamilcric.MatchScheduleActivity;
import com.maxwell.tamilcric.MatchScheduleNewActivity;
import com.maxwell.tamilcric.PlayersActivity;
import com.maxwell.tamilcric.PointsTableActivity;
import com.maxwell.tamilcric.R;
import com.maxwell.tamilcric.StringConstants;
import com.maxwell.tamilcric.TeamActivity;
import com.maxwell.tamilcric.TournamentActivity;
import com.maxwell.tamilcric.adapter.PlayersAdapter;
import com.maxwell.tamilcric.adapter.PointsTableAdapter;
import com.maxwell.tamilcric.adapter.SliderPagerAdapter;
import com.maxwell.tamilcric.adapter.TeamsAdapter;
import com.maxwell.tamilcric.model.LiveMatchModel;
import com.maxwell.tamilcric.model.PlayerModel;
import com.maxwell.tamilcric.model.PointsTableModel;
import com.maxwell.tamilcric.model.TeamModel;
import com.maxwell.tamilcric.viewpageranimation.CubeOutRotationTransformation;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class HomeFragment extends Fragment {

    View view;
    private RecyclerView recyclerViewTeam,recylerViewPlayers;
    private RecyclerView.Adapter mAdapter,mAdapter2;
    List<TeamModel> teamModelList=new ArrayList<>();
    List<PlayerModel> playerModelList=new ArrayList<>();
    List<LiveMatchModel> liveMatchModelList=new ArrayList<>();

    TeamModel teamModel,teamModel1;
    PlayerModel playerModel;
    LiveMatchModel liveMatchModel;

    TextView tv_team,tv_players,tv_points,tv_tournaments,tv_match_schedule;
    PointsTableModel pointsTableModel;
    List<PointsTableModel> pointsTableModelList=new ArrayList<>();
    private RecyclerView recyclerViewPoints;
    private RecyclerView.Adapter pointsAdapter;
    ProgressBar progressBar;
    RelativeLayout layout_progress,layout_progress1,layout_progress2;

    private ViewPager vp_slider;
    private LinearLayout ll_dots;

    SliderPagerAdapter sliderPagerAdapter;
    int page_position = 0;
    RelativeLayout layout_slider;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.content_home_page,container,false);

        recyclerViewTeam = (RecyclerView)view. findViewById(R.id.recyclerTeam);
        recylerViewPlayers = (RecyclerView)view. findViewById(R.id.recyclerPlayers);
        recyclerViewPoints=(RecyclerView)view.findViewById(R.id.recyclerPoints);
        tv_team=(TextView)view.findViewById(R.id.text_team);
        tv_players=(TextView)view.findViewById(R.id.text_players);
        tv_points=(TextView)view.findViewById(R.id.text_points_table);
        tv_tournaments=(TextView)view.findViewById(R.id.text_tournaments);
        tv_match_schedule=(TextView)view.findViewById(R.id.text_match_schedule);
        progressBar=(ProgressBar)view.findViewById(R.id.progress);
        layout_progress=(RelativeLayout)view.findViewById(R.id.rela_animation) ;
        layout_progress1=(RelativeLayout)view.findViewById(R.id.rela_animation1) ;

        layout_progress2=(RelativeLayout)view.findViewById(R.id.rela_animation2) ;
        layout_slider=(RelativeLayout)view.findViewById(R.id.layout_slider) ;
        vp_slider = (ViewPager) view.findViewById(R.id.vp_slider);
        ll_dots = (LinearLayout) view.findViewById(R.id.ll_dots);
        tv_team.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent i=new Intent(getActivity(), TeamActivity.class);
                startActivity(i);
            }
        });
        tv_players.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i=new Intent(getActivity(), PlayersActivity.class);
                i.putExtra("TeamID","");
                i.putExtra("TeamName","");
                startActivity(i);
            }
        });
        tv_points.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i=new Intent(getActivity(), PointsTableActivity.class);
                startActivity(i);
            }
        });
        tv_tournaments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i=new Intent(getActivity(), TournamentActivity.class);
                startActivity(i);
            }
        });
        tv_match_schedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i=new Intent(getActivity(), MatchScheduleNewActivity.class);
                startActivity(i);
            }
        });


        final CubeOutRotationTransformation CubeOutRotationTransformation = new CubeOutRotationTransformation();
        final Handler handler = new Handler();

        final Runnable update = new Runnable() {
            public void run() {
                if (page_position == liveMatchModelList.size()) {
                    page_position = 0;
                } else {
                    page_position = page_position + 1;
                }
                if (vp_slider != null) {
                    vp_slider.setCurrentItem(page_position, true);
                    vp_slider.setPageTransformer(true, CubeOutRotationTransformation);
                }
                //vp_slider.setAnimation();
            }
        };
/*
        new Timer().schedule(new TimerTask() {

            @Override
            public void run() {
                handler.post(update);
            }
        }, 100, 5000);
*/

        livematchListing();
        teamListing();
        playerListing();
       // pointsTable();
        return view;
    }


    public void livematchListing(){
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        JsonObjectRequest getRequest = new JsonObjectRequest(Request. Method.GET, StringConstants.mainUrl + StringConstants.liveMatchUrl, null,
                new Response.Listener<JSONObject>()
                {

                    @Override
                    public void onResponse(JSONObject response) {
                        // display response
                        Log.d("Response", response.toString());

                        try {

                            if(response.has("status")){
                                JSONObject statusObjects=response.getJSONObject("status");
                                String responsecode=statusObjects.getString("StatusCode");
                                String message=statusObjects.getString("StatusMessage");

                                if(responsecode.matches("200")&&message.matches("Success")){

                                    if(response.has("live_match")){
                                        JSONArray teamArray=response.getJSONArray("live_match");
                                        for(int i=0;i<teamArray.length();i++){
                                            liveMatchModel=new LiveMatchModel();
                                            JSONObject object=teamArray.getJSONObject(i);
                                            liveMatchModel.setMatchNumeber(object.getString("match_number"));
                                            liveMatchModel.setStadium(object.getString("stadium_name"));
                                            liveMatchModel.setTeamAName(object.getString("team1_name"));
                                            liveMatchModel.setTeamALogo(object.getString("team1_logo"));
                                            liveMatchModel.setTeamAScore(object.getString("team1_score"));
                                            liveMatchModel.setTeamAWickets(object.getString("team1_wicket"));
                                            liveMatchModel.setTeamBName(object.getString("team2_name"));
                                            liveMatchModel.setTeamBLogo(object.getString("team2_logo"));
                                            liveMatchModel.setTeamBSore(object.getString("team2_score"));
                                            liveMatchModel.setTeamBWickets(object.getString("team2_wicket"));

                                            liveMatchModelList.add(liveMatchModel);
                                        }
                                        if(liveMatchModelList.size()>0){
                                            layout_slider.setVisibility(View.VISIBLE);
                                            sliderPagerAdapter = new SliderPagerAdapter(getActivity(), liveMatchModelList);
                                            vp_slider.setAdapter(sliderPagerAdapter);
                                            /*mAdapter=new TeamsAdapter(getContext(),teamModelList);
                                            LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
                                            recyclerViewTeam.setLayoutManager(horizontalLayoutManager);
                                            recyclerViewTeam.setAdapter(mAdapter);*/
                                            vp_slider.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                                                @Override
                                                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                                                }

                                                @Override
                                                public void onPageSelected(int position) {
                                                    //addBottomDots(position);
                                                }

                                                @Override
                                                public void onPageScrollStateChanged(int state) {

                                                }
                                            });

                                        }


                                    }
                                }
                            }


                            //layout_progress1.setVisibility(View.GONE);
                            // recyclerViewTeam.setVisibility(View.VISIBLE);



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Log.d("Error.Response", error.getLocalizedMessage());
                        String errorMessage=StringConstants.ErrorMessage(error);
                        if(errorMessage.matches("Connection TimeOut! Please check your internet connection.")){
                            livematchListing();
                            layout_progress1.setVisibility(View.VISIBLE);
                            recyclerViewTeam.setVisibility(View.GONE);
                        }else {
                            layout_progress1.setVisibility(View.GONE);
                            recyclerViewTeam.setVisibility(View.VISIBLE);
                        }
                        // showAlertDialog(errorMessage);

                    }
                }
        );

// add it to the RequestQueue
        queue.add(getRequest);

    }

    public void teamListing(){
        RequestQueue queue = Volley.newRequestQueue(getActivity());

        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, StringConstants.mainUrl + StringConstants.teamUrl+StringConstants.inputAppKey, null,
                new Response.Listener<JSONObject>()
                {

                    @Override
                    public void onResponse(JSONObject response) {
                        // display response
                        Log.d("Response", response.toString());

                        try {

                            if(response.has("status")){
                                JSONObject statusObjects=response.getJSONObject("status");
                                String responsecode=statusObjects.getString("StatusCode");
                                String message=statusObjects.getString("StatusMessage");

                                if(responsecode.matches("200")&&message.matches("Success")){

                                    if(response.has("teammembers")){
                                        JSONArray teamArray=response.getJSONArray("teammembers");
                                        for(int i=0;i<teamArray.length();i++){
                                            teamModel=new TeamModel();
                                            JSONObject object=teamArray.getJSONObject(i);
                                            teamModel.setId(object.getString("team_id"));
                                            teamModel.setTeamName(object.getString("team_name"));
                                            teamModel.setTeamLogo(object.getString("team_image"));

                                            teamModelList.add(teamModel);
                                        }
                                        mAdapter=new TeamsAdapter(getContext(),teamModelList);
                                        LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
                                        recyclerViewTeam.setLayoutManager(horizontalLayoutManager);
                                        recyclerViewTeam.setAdapter(mAdapter);
                                    }
                                }
                            }


                            layout_progress1.setVisibility(View.GONE);
                            recyclerViewTeam.setVisibility(View.VISIBLE);



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Log.d("Error.Response", error.getLocalizedMessage());
                        String errorMessage=StringConstants.ErrorMessage(error);
                        if(errorMessage.matches("Connection TimeOut! Please check your internet connection.")){
                            teamListing();
                            layout_progress1.setVisibility(View.VISIBLE);
                            recyclerViewTeam.setVisibility(View.GONE);
                        }else {
                            layout_progress1.setVisibility(View.GONE);
                            recyclerViewTeam.setVisibility(View.VISIBLE);
                        }
                        // showAlertDialog(errorMessage);

                    }
                }
        );

// add it to the RequestQueue
        queue.add(getRequest);

    }

    public void playerListing(){
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, StringConstants.mainUrl + StringConstants.playerUrl+StringConstants.inputAppKey, null,
                new Response.Listener<JSONObject>()
                {

                    @Override
                    public void onResponse(JSONObject response) {
                        // display response
                        Log.d("Response", response.toString());

                        try {

                            if(response.has("status")){
                                JSONObject statusObjects=response.getJSONObject("status");
                                String responsecode=statusObjects.getString("StatusCode");
                                String message=statusObjects.getString("StatusMessage");

                                if(responsecode.matches("200")&&message.matches("Success")){

                                    if(response.has("player_service")){
                                        JSONArray teamArray=response.getJSONArray("player_service");
                                        for(int i=0;i<teamArray.length();i++){
                                            playerModel=new PlayerModel();
                                            JSONObject object=teamArray.getJSONObject(i);
                                            playerModel.setId(object.getString("player_id"));
                                            playerModel.setPlayerName(object.getString("player_name"));
                                            playerModel.setPlayerImage(object.getString("player_image"));

                                            playerModelList.add(playerModel);
                                        }
                                        mAdapter2=new PlayersAdapter(getContext(),playerModelList);
                                        LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
                                        recylerViewPlayers.setLayoutManager(horizontalLayoutManager);
                                        recylerViewPlayers.setAdapter(mAdapter2);
                                    }
                                    recylerViewPlayers.setVisibility(View.VISIBLE);
                                    layout_progress.setVisibility(View.GONE);
                                }
                            }



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Log.d("Error.Response", error.getLocalizedMessage());
                        String errorMessage=StringConstants.ErrorMessage(error);
                        if(errorMessage.matches("Connection TimeOut! Please check your internet connection.")){
                            playerListing();
                            recylerViewPlayers.setVisibility(View.VISIBLE);
                            layout_progress.setVisibility(View.VISIBLE);
                        }else {

                            recylerViewPlayers.setVisibility(View.VISIBLE);
                            layout_progress.setVisibility(View.GONE);
                        }
                    }
                }
        );

// add it to the RequestQueue
        queue.add(getRequest);

    }
    public void showAlertDialog(String message){
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setMessage(message);
        alertDialogBuilder.setPositiveButton("ok",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        arg0.cancel();
                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    public void pointsTable(){
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, StringConstants.mainUrl + StringConstants.pointsTableapi, null,
                new Response.Listener<JSONObject>()
                {

                    @Override
                    public void onResponse(JSONObject response) {
                        // display response
                        Log.d("Response", response.toString());

                        try {
                            if(response.has("status")){
                                JSONObject statusObjects=response.getJSONObject("status");
                                String responsecode=statusObjects.getString("StatusCode");
                                String message=statusObjects.getString("StatusMessage");
                                if(responsecode.matches("200")&&message.matches("Success")){
                                    if(response.has("team")){
                                        JSONArray teamArray=response.getJSONArray("team");
                                        for(int i=0;i<teamArray.length();i++){
                                            JSONObject pointsObject=teamArray.getJSONObject(i);
                                            pointsTableModel=new PointsTableModel();
                                            pointsTableModel.setTeamId(pointsObject.getString("team_id"));
                                            pointsTableModel.setTeamName(pointsObject.getString("team_name"));
                                            pointsTableModel.setTeamImage(pointsObject.getString("team_logo"));
                                            pointsTableModel.setPlayed(pointsObject.getString("played"));
                                            pointsTableModel.setWon(pointsObject.getString("won"));
                                            pointsTableModel.setLost(pointsObject.getString("lost"));
                                            pointsTableModel.setTied(pointsObject.getString("tied"));
                                            pointsTableModelList.add(pointsTableModel);
                                        }
                                        pointsAdapter=new PointsTableAdapter(getContext(),pointsTableModelList);
                                        LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(getContext());
                                        recyclerViewPoints.setLayoutManager(horizontalLayoutManager);
                                        recyclerViewPoints.setAdapter(pointsAdapter);
                                    }
                                }
                            }
                            layout_progress2.setVisibility(View.GONE);
                            recyclerViewPoints.setVisibility(View.VISIBLE);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Log.d("Error.Response", error.getLocalizedMessage());
                        String errorMessage=StringConstants.ErrorMessage(error);
                        if(errorMessage.matches("Connection TimeOut! Please check your internet connection.")){
                           pointsTable();
                            layout_progress2.setVisibility(View.VISIBLE);
                            recyclerViewTeam.setVisibility(View.GONE);
                        }else {

                            layout_progress2.setVisibility(View.GONE);
                            recyclerViewPoints.setVisibility(View.VISIBLE);
                        }
                    }
                }
        );

// add it to the RequestQueue
        queue.add(getRequest);

    }
}
