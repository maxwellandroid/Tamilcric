package com.maxwell.tamilcric;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.maxwell.tamilcric.adapter.BattingListAdapter;
import com.maxwell.tamilcric.adapter.BowlingListAdapter;
import com.maxwell.tamilcric.adapter.PointsTableAdapter;
import com.maxwell.tamilcric.adapter.ResultAdapter;
import com.maxwell.tamilcric.model.BattingsModel;
import com.maxwell.tamilcric.model.BowlingModel;
import com.maxwell.tamilcric.model.ResultMatchesModels;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MatchSummeryActivity extends AppCompatActivity {

    RecyclerView recyclerViewBatting,recyclerViewBowling;
    TextView tv_batting_empty,tv_bowling_empty;
    BattingsModel battingsModel;
    BowlingModel bowlingModel;
    List<BattingsModel> team1Batting=new ArrayList<>();
    List<BattingsModel> team2Batting=new ArrayList<>();
    List<BowlingModel> team1Bowling=new ArrayList<>();
    List<BowlingModel> team2Bowling=new ArrayList<>();
    TextView tv_match_number,tv_stadium,tv_team1Name,tv_team2Name,tv_team1Runs,tv_team2Runs,tv_team1Wickets,tv_team2Wickets,tv_team1Overs,tv_team2Overs,tv_team1,tv_team2;
    private RecyclerView.Adapter mAdapter,mAdapter1;
    ImageView iv_team1,iv_team2;
    String matchId;
    RelativeLayout layoutProgress;
    LinearLayout layout_home;
    String match_number,team1Name,team1Logo,team2Name,team2Logo,stadium,matchDate,team1Score,team2Score,team1Wickets,team2Wickets,team1Overs,team2Overs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_summery);
initializeViews();

    }
    public void backPressed(View view){
        onBackPressed();
    }

    public void initializeViews(){
        tv_match_number=(TextView)findViewById(R.id.text_match_number);
        tv_stadium=(TextView)findViewById(R.id.text_stadium);
        tv_team1Name=(TextView)findViewById(R.id.text_teamA_name);
        tv_team2Name=(TextView)findViewById(R.id.text_teamB_name);
        tv_team1Runs=(TextView)findViewById(R.id.text_teamA_score);
        tv_team2Runs=(TextView)findViewById(R.id.text_teamB_score);
        iv_team1=(ImageView)findViewById(R.id.image_teamA_logo) ;
        iv_team2=(ImageView)findViewById(R.id.image_teamB_logo) ;

        tv_team1Overs=(TextView)findViewById(R.id.text_teamA_overs);
        tv_team2Overs=(TextView)findViewById(R.id.text_teamB_overs);
        tv_team1=(TextView)findViewById(R.id.text_team1);
        tv_team2=(TextView)findViewById(R.id.text_team2);
        recyclerViewBatting=(RecyclerView)findViewById(R.id.recyclerBatting);
        recyclerViewBowling=(RecyclerView)findViewById(R.id.recyclerBowling);
        tv_batting_empty=(TextView)findViewById(R.id.text_batting_no_data);
        tv_bowling_empty=(TextView)findViewById(R.id.text_bowling_no_data);
        layoutProgress=(RelativeLayout)findViewById(R.id.rela_animation);
        layout_home=(LinearLayout)findViewById(R.id.layout_home) ;
        if(getIntent()!=null){
            matchId=getIntent().getStringExtra("MatchID");
        }

        matchSummeryOperation();

        tv_team2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_team2.setBackgroundResource(R.drawable.rounded_cornered_edittext_color);
                tv_team2.setTextColor(getResources().getColor(R.color.white));
               tv_team1.setBackgroundResource(R.drawable.rounded_cornered_edittext);
               tv_team1.setTextColor(getResources().getColor(R.color.black));
                if(team2Batting.size()>0){
                    tv_batting_empty.setVisibility(View.GONE);
                    recyclerViewBatting.setVisibility(View.VISIBLE);
                    mAdapter=new BattingListAdapter(getApplicationContext(),team2Batting);
                    LinearLayoutManager horizontalLayoutManager1 = new LinearLayoutManager(getApplicationContext());
                    recyclerViewBatting.setLayoutManager(horizontalLayoutManager1);
                    recyclerViewBatting.setAdapter(mAdapter);
                }else {
                    tv_batting_empty.setVisibility(View.VISIBLE);
                    recyclerViewBatting.setVisibility(View.GONE);
                }

                if(team1Bowling.size()>0){
                    tv_bowling_empty.setVisibility(View.GONE);
                    recyclerViewBowling.setVisibility(View.VISIBLE);
                    mAdapter1=new BowlingListAdapter(getApplicationContext(),team1Bowling);
                    LinearLayoutManager horizontalLayoutManager1 = new LinearLayoutManager(getApplicationContext());
                    recyclerViewBowling.setLayoutManager(horizontalLayoutManager1);
                    recyclerViewBowling.setAdapter(mAdapter1);

                }else {
                    tv_bowling_empty.setVisibility(View.VISIBLE);
                    recyclerViewBowling.setVisibility(View.GONE);
                }

            }
        });
        tv_team1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_team1.setBackgroundResource(R.drawable.rounded_cornered_edittext_color);
                tv_team1.setTextColor(getResources().getColor(R.color.white));
               tv_team2.setBackgroundResource(R.drawable.rounded_cornered_edittext);
               tv_team2.setTextColor(getResources().getColor(R.color.black));
                if(team1Batting.size()>0){
                    tv_batting_empty.setVisibility(View.GONE);
                    recyclerViewBatting.setVisibility(View.VISIBLE);
                    mAdapter=new BattingListAdapter(getApplicationContext(),team1Batting);
                    LinearLayoutManager horizontalLayoutManager1 = new LinearLayoutManager(getApplicationContext());
                    recyclerViewBatting.setLayoutManager(horizontalLayoutManager1);
                    recyclerViewBatting.setAdapter(mAdapter);
                }else {
                    tv_batting_empty.setVisibility(View.VISIBLE);
                    recyclerViewBatting.setVisibility(View.GONE);
                }
                if(team2Bowling.size()>0){
                    tv_bowling_empty.setVisibility(View.GONE);
                    recyclerViewBowling.setVisibility(View.VISIBLE);
                    mAdapter1=new BowlingListAdapter(getApplicationContext(),team2Bowling);
                    LinearLayoutManager horizontalLayoutManager1 = new LinearLayoutManager(getApplicationContext());
                    recyclerViewBowling.setLayoutManager(horizontalLayoutManager1);
                    recyclerViewBowling.setAdapter(mAdapter1);

                }else {
                    tv_bowling_empty.setVisibility(View.VISIBLE);
                    recyclerViewBowling.setVisibility(View.GONE);
                }

            }
        });


    }

    public void matchSummeryOperation(){
        RequestQueue queue = Volley.newRequestQueue(MatchSummeryActivity.this);
        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, StringConstants.mainUrl + StringConstants.matchSummeryUrl+StringConstants.inputMatchId+matchId, null,
                new Response.Listener<JSONObject>()
                {

                    @Override
                    public void onResponse(JSONObject response) {
                        // display response
                        Log.d("Response", response.toString());

                        try {

                            if(response.has("status")){
                                JSONObject succussObject=response.getJSONObject("status");
                                String statusCode=succussObject.getString("StatusCode");
                                String statusMessage=succussObject.getString("StatusMessage");
                                if(statusCode.matches("200")&&statusMessage.matches("Success")){
                                    if(response.has("match_schedule")){
                                        JSONArray matchArray=response.getJSONArray("match_schedule");
                                        for(int i=0;i<matchArray.length();i++){
                                            JSONObject matchObject=matchArray.getJSONObject(i);
                                            match_number=matchObject.getString("match_number");
                                            team1Name=matchObject.getString("team1_name");
                                            team1Logo=matchObject.getString("team1_logo");
                                            team2Name=matchObject.getString("team2_name");
                                            team2Logo=matchObject.getString("team2_logo");
                                            stadium=matchObject.getString("stadium_name");
                                            matchDate=matchObject.getString("match_schedule_date");
                                        }



                                       //  str =matchDate;
// parse the String "29/07/2013" to a java.util.Date object

                                        try {
                                            Date date = new SimpleDateFormat("yyyy-MM-dd").parse(matchDate);
                                            String formattedDate = new SimpleDateFormat("dd-MM-yyyy").format(date);
                                            tv_match_number.setText("Match : "+match_number+" ( "+formattedDate+" )");
                                        } catch (ParseException e) {
                                            e.printStackTrace();
                                            tv_match_number.setText("Match Number : "+match_number+" ( "+matchDate+" )");
                                        }

                                        tv_team1Name.setText(team1Name);
                                        tv_team2Name.setText(team2Name);
                                        tv_team1.setText(team1Name);
                                        tv_team2.setText(team2Name);
                                        tv_stadium.setText(stadium);

                                        Glide.with(getApplicationContext()).load(team1Logo).placeholder(R.drawable.tcalogo).into(iv_team1);
                                        Glide.with(getApplicationContext()).load(team2Logo).placeholder(R.drawable.tcalogo).into(iv_team2);
                                    }
                                    if(response.has("team1_score")){
                                        JSONArray team1ScoreArray=response.getJSONArray("team1_score");
                                        for(int i=0;i<team1ScoreArray.length();i++){
                                            JSONObject team1ScoreObject=team1ScoreArray.getJSONObject(i);
                                            team1Score=team1ScoreObject.getString("total_runs");
                                            team1Wickets=team1ScoreObject.getString("wicket");
                                            team1Overs=team1ScoreObject.getString("total_over");

                                        }
                                        tv_team1Runs.setText(team1Score+" / "+team1Wickets);
                                        tv_team1Overs.setText("Overs : "+team1Overs);
                                    }
                                    if(response.has("team2_score")){
                                        JSONArray team1ScoreArray=response.getJSONArray("team2_score");
                                        for(int i=0;i<team1ScoreArray.length();i++){
                                            JSONObject team1ScoreObject=team1ScoreArray.getJSONObject(i);
                                            team2Score=team1ScoreObject.getString("total_runs");
                                            team2Wickets=team1ScoreObject.getString("wicket");
                                            team2Overs=team1ScoreObject.getString("total_over");

                                        }
                                        tv_team2Runs.setText(team2Score+" / "+team2Wickets);
                                        tv_team2Overs.setText("Overs : "+team2Overs);
                                    }
                                    if(response.has("team1_batting")){

                                        JSONArray team1BattingArray=response.getJSONArray("team1_batting");
                                        team1Batting=new ArrayList<>();
                                        for (int i=0;i<team1BattingArray.length();i++){
                                            JSONObject team1BattingObject=team1BattingArray.getJSONObject(i);
                                            battingsModel=new BattingsModel();
                                            battingsModel.setPlayer_name(team1BattingObject.getString("batsman_name"));
                                            battingsModel.setBalls(team1BattingObject.getString("balls"));
                                            battingsModel.setRuns(team1BattingObject.getString("runs"));
                                            battingsModel.setFours(team1BattingObject.getString("four"));
                                            battingsModel.setSixes(team1BattingObject.getString("six"));
                                            battingsModel.setStraikRate(team1BattingObject.getString("strike"));
                                            team1Batting.add(battingsModel);
                                        }
                                        if(team1Batting.size()>0){
                                            recyclerViewBatting.setVisibility(View.VISIBLE);
                                            tv_batting_empty.setVisibility(View.GONE);
                                            mAdapter=new BattingListAdapter(getApplicationContext(),team1Batting);
                                            LinearLayoutManager horizontalLayoutManager1 = new LinearLayoutManager(getApplicationContext());
                                            recyclerViewBatting.setLayoutManager(horizontalLayoutManager1);
                                            recyclerViewBatting.setAdapter(mAdapter);
                                        }else {
                                            recyclerViewBatting.setVisibility(View.GONE);
                                            tv_batting_empty.setVisibility(View.VISIBLE);
                                        }


                                    }

                                    if(response.has("team1_bowling")){

                                        JSONArray team1BowlingArray=response.getJSONArray("team1_bowling");
                                        team1Bowling=new ArrayList<>();
                                        for (int i=0;i<team1BowlingArray.length();i++){
                                            JSONObject team1BowlingObject=team1BowlingArray.getJSONObject(i);
                                            bowlingModel=new BowlingModel();
                                            bowlingModel.setPlayer_name(team1BowlingObject.getString("bowler_name"));
                                            bowlingModel.setOvers(team1BowlingObject.getString("balls"));
                                            bowlingModel.setRuns(team1BowlingObject.getString("runs"));
                                            bowlingModel.setWides(team1BowlingObject.getString("wide"));
                                            bowlingModel.setNoBallas(team1BowlingObject.getString("noball"));
                                            team1Bowling.add(bowlingModel);
                                        }


                                    }
                                    if(response.has("team2_batting")){

                                        JSONArray team1BattingArray=response.getJSONArray("team2_batting");
                                        team2Batting=new ArrayList<>();
                                        for (int i=0;i<team1BattingArray.length();i++){
                                            JSONObject team1BattingObject=team1BattingArray.getJSONObject(i);
                                            battingsModel=new BattingsModel();
                                            battingsModel.setPlayer_name(team1BattingObject.getString("batsman_name"));
                                            battingsModel.setBalls(team1BattingObject.getString("balls"));
                                            battingsModel.setRuns(team1BattingObject.getString("runs"));
                                            battingsModel.setFours(team1BattingObject.getString("four"));
                                            battingsModel.setSixes(team1BattingObject.getString("six"));
                                            battingsModel.setStraikRate(team1BattingObject.getString("strike"));
                                            team2Batting.add(battingsModel);
                                        }
                               /* mAdapter=new BattingListAdapter(getApplicationContext(),team2Batting);
                                LinearLayoutManager horizontalLayoutManager1 = new LinearLayoutManager(getApplicationContext());
                                recyclerViewBatting.setLayoutManager(horizontalLayoutManager1);
                                recyclerViewBatting.setAdapter(mAdapter);
*/
                                    }

                                    if(response.has("team2_bowling")){

                                        JSONArray team1BowlingArray=response.getJSONArray("team2_bowling");
                                        team2Bowling=new ArrayList<>();
                                        for (int i=0;i<team1BowlingArray.length();i++){
                                            JSONObject team1BowlingObject=team1BowlingArray.getJSONObject(i);
                                            bowlingModel=new BowlingModel();
                                            bowlingModel.setPlayer_name(team1BowlingObject.getString("bowler_name"));
                                            bowlingModel.setOvers(team1BowlingObject.getString("balls"));
                                            bowlingModel.setRuns(team1BowlingObject.getString("runs"));
                                            bowlingModel.setWides(team1BowlingObject.getString("wide"));
                                            bowlingModel.setNoBallas(team1BowlingObject.getString("noball"));
                                            team2Bowling.add(bowlingModel);
                                        }
                                        if(team2Bowling.size()>0){
                                            recyclerViewBowling.setVisibility(View.VISIBLE);
                                            tv_bowling_empty.setVisibility(View.GONE);
                                            mAdapter1=new BowlingListAdapter(getApplicationContext(),team2Bowling);
                                            LinearLayoutManager horizontalLayoutManager1 = new LinearLayoutManager(getApplicationContext());
                                            recyclerViewBowling.setLayoutManager(horizontalLayoutManager1);
                                            recyclerViewBowling.setAdapter(mAdapter1);
                                        }else {
                                            recyclerViewBowling.setVisibility(View.GONE);
                                            tv_bowling_empty.setVisibility(View.VISIBLE);
                                        }

                                /*mAdapter1=new BowlingListAdapter(getApplicationContext(),team2Bowling);
                                LinearLayoutManager horizontalLayoutManager1 = new LinearLayoutManager(getApplicationContext());
                                recyclerViewBowling.setLayoutManager(horizontalLayoutManager1);
                                recyclerViewBowling.setAdapter(mAdapter1);*/

                                    }

                                }
                            }

                            layoutProgress.setVisibility(View.GONE);
                            layout_home.setVisibility(View.VISIBLE);


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
                        if(errorMessage.matches("Connection TimeOut! Please check your internet connection.")) {
                            matchSummeryOperation();
                            layoutProgress.setVisibility(View.VISIBLE);
                            layout_home.setVisibility(View.GONE);

                        }else {
                            layoutProgress.setVisibility(View.GONE);
                            layout_home.setVisibility(View.VISIBLE);

                        }

                        // showAlertDialog(errorMessage);

                    }
                }
        );
        queue.add(getRequest);
    }

}
