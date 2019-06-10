package com.maxwell.tamilcric;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.maxwell.tamilcric.adapter.BattingStatsAdapter;
import com.maxwell.tamilcric.model.BattingStatsModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class BattingModulesActivity extends AppCompatActivity {

    ImageView iv_player,iv_team_logo;
    TextView tv_tag,tv_name,tv_team_name,tv_runs,tv_score_tag;
    String s_player_name,s_team_name,s_runs,s_stat,s_player_image,s_team_image;
    String s_stat_name="";
    RelativeLayout layoutProgress;
    List<BattingStatsModel> battingStatsModelList;
    BattingStatsModel battingStatsModel;
    private RecyclerView recyclerViewBattingModule;
    private RecyclerView.Adapter mAdapter;
    TextView tv_screen_name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_batting_modules);

        initializeViews();
    }
    public void backPressed(View view){

        Intent i=new Intent(getApplicationContext(),HomePageActivity.class);
        startActivity(i);
    }

    public void initializeViews(){

        iv_player=(ImageView)findViewById(R.id.image_player);
        iv_team_logo=(ImageView)findViewById(R.id.image_team_logo);
        tv_tag=(TextView)findViewById(R.id.text_tag);
        tv_name=(TextView)findViewById(R.id.text_player_name);
        tv_team_name=(TextView)findViewById(R.id.text_team_name);
        tv_runs=(TextView)findViewById(R.id.text_score);
        tv_score_tag=(TextView)findViewById(R.id.text_score_tag);
        layoutProgress=(RelativeLayout)findViewById(R.id.rela_animation);
        tv_screen_name=(TextView)findViewById(R.id.text_screen_name);

        recyclerViewBattingModule=(RecyclerView)findViewById(R.id.recyclerBattingStats);
        if(getIntent()!=null){
            s_stat_name=getIntent().getStringExtra("StatType");
            tv_screen_name.setText(s_stat_name+" Stats");
        }
     //   tv_tag.setText(s_stat_name);

        if(s_stat_name.matches("Batting"))
            battingStatsOperation();
        else if(s_stat_name.matches("Bowling"))
            bowlingStatsOperation();
    }
    public void battingStatsOperation(){
        RequestQueue queue = Volley.newRequestQueue(BattingModulesActivity.this);
        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, StringConstants.mainUrl + StringConstants.battingStrategyUrl, null,
                new Response.Listener<JSONObject>()
                {

                    @Override
                    public void onResponse(JSONObject response) {
                        // display response
                        Log.d("Response", response.toString());

                        try {

                                    if(response.has("batting")){
                                        JSONObject battingObject=response.getJSONObject("batting");
                                        battingStatsModelList=new ArrayList<>();
                                        Iterator<String> iter = battingObject.keys();
                                        while (iter.hasNext()) {
                                            String key = iter.next();
                                            try {
                                                JSONObject bestScoreObject=battingObject.getJSONObject(key);
                                                battingStatsModel=new BattingStatsModel();
                                                battingStatsModel.setPlayerImage(bestScoreObject.getString("player_image"));
                                                battingStatsModel.setPlayerName(bestScoreObject.getString("player_name"));
                                                battingStatsModel.setScore(bestScoreObject.getString("score"));
                                                battingStatsModel.setTeamName(bestScoreObject.getString("team_name"));
                                                battingStatsModel.setTeamImage(bestScoreObject.getString("team_image"));
                                               // battingStatsModel.setPlayerId(bestScoreObject.getString("player_id"));
                                                battingStatsModel.setStat(key);

                                                battingStatsModelList.add(battingStatsModel);

                                            } catch (JSONException e) {
                                                // Something went wrong!
                                            }

                                            mAdapter=new BattingStatsAdapter(BattingModulesActivity.this,battingStatsModelList);
                                            LinearLayoutManager horizontalLayoutManager1 = new LinearLayoutManager(getApplicationContext());
                                            recyclerViewBattingModule.setLayoutManager(horizontalLayoutManager1);
                                            recyclerViewBattingModule.setAdapter(mAdapter);
                                        }
/*
                                        if(battingObject.has("best_score")){
                                            JSONObject bestScoreObject=battingObject.getJSONObject("best_score");
                                            s_player_name=bestScoreObject.getString("player_name");
                                            s_team_name=bestScoreObject.getString("team_name");
                                            s_runs=bestScoreObject.getString("score");
                                            s_player_image=bestScoreObject.getString("player_image");
                                            s_team_image=bestScoreObject.getString("team_image");
                                            s_stat="runs";

                                            if(s_stat_name.matches("Orange Cap / Most Runs")){


                                            tv_player_name.setText(s_player_name);
                                            tv_runs.setText(s_runs);
                                            tv_score_tag.setText(s_stat);
                                            tv_team_name.setText(s_team_name);
                                            Glide.with(getApplicationContext())
                                                    .load(s_player_image) // image url
                                                    // .placeholder(R.drawable.placeholder) // any placeholder to load at start
                                                    //.error(R.drawable.imagenotfound)  // any image in case of error
                                                    // .override(200, 200); // resizing
                                                    // .apply(new RequestOptions().placeholder(R.drawable.loading))
                                                    .into(iv_player);
                                            Glide.with(getApplicationContext())
                                                    .load(s_team_image) // image url
                                                    // .placeholder(R.drawable.placeholder) // any placeholder to load at start
                                                    //.error(R.drawable.imagenotfound)  // any image in case of error
                                                    // .override(200, 200); // resizing
                                                    // .apply(new RequestOptions().placeholder(R.drawable.loading))
                                                    .into(iv_team_logo);
                                            }
                                        }
*/
/*
                                        if(battingObject.has("max_four")){
                                            JSONObject maxFoursObject=battingObject.getJSONObject("max_four");
                                            s_player_name=maxFoursObject.getString("player_name");
                                            s_team_name=maxFoursObject.getString("team_name");
                                            s_runs=maxFoursObject.getString("score");
                                            s_player_image=maxFoursObject.getString("player_image");
                                            s_team_image=maxFoursObject.getString("team_image");
                                            s_stat="Fours";

                                            if(s_stat_name.matches("Most Fours")){


                                            tv_player_name.setText(s_player_name);
                                            tv_runs.setText(s_runs);
                                            tv_score_tag.setText(s_stat);
                                            tv_team_name.setText(s_team_name);
                                            Glide.with(getApplicationContext())
                                                    .load(s_player_image) // image url
                                                    // .placeholder(R.drawable.placeholder) // any placeholder to load at start
                                                    //.error(R.drawable.imagenotfound)  // any image in case of error
                                                    // .override(200, 200); // resizing
                                                    // .apply(new RequestOptions().placeholder(R.drawable.loading))
                                                    .into(iv_player);
                                            Glide.with(getApplicationContext())
                                                    .load(s_team_image) // image url
                                                    // .placeholder(R.drawable.placeholder) // any placeholder to load at start
                                                    //.error(R.drawable.imagenotfound)  // any image in case of error
                                                    // .override(200, 200); // resizing
                                                    // .apply(new RequestOptions().placeholder(R.drawable.loading))
                                                    .into(iv_team_logo);
                                            }
                                        }
*/
/*
                                        if(battingObject.has("max_six")){
                                            JSONObject bestScoreObject=battingObject.getJSONObject("max_six");
                                            s_player_name=bestScoreObject.getString("player_name");
                                            s_team_name=bestScoreObject.getString("team_name");
                                            s_runs=bestScoreObject.getString("score");
                                            s_player_image=bestScoreObject.getString("player_image");
                                            s_team_image=bestScoreObject.getString("team_image");
                                            s_stat="sixes";

                                            if(s_stat_name.matches("Most Sixes")){


                                            tv_player_name.setText(s_player_name);
                                            tv_runs.setText(s_runs);
                                            tv_score_tag.setText(s_stat);
                                            tv_team_name.setText(s_team_name);
                                            Glide.with(getApplicationContext())
                                                    .load(s_player_image) // image url
                                                    // .placeholder(R.drawable.placeholder) // any placeholder to load at start
                                                    //.error(R.drawable.imagenotfound)  // any image in case of error
                                                    // .override(200, 200); // resizing
                                                    // .apply(new RequestOptions().placeholder(R.drawable.loading))
                                                    .into(iv_player);
                                            Glide.with(getApplicationContext())
                                                    .load(s_team_image) // image url
                                                    // .placeholder(R.drawable.placeholder) // any placeholder to load at start
                                                    //.error(R.drawable.imagenotfound)  // any image in case of error
                                                    // .override(200, 200); // resizing
                                                    // .apply(new RequestOptions().placeholder(R.drawable.loading))
                                                    .into(iv_team_logo);
                                            }
                                        }
*/
// parse the String "29/07/2013" to a java.util.Date object

                                    }

                            layoutProgress.setVisibility(View.GONE);

                        } catch (JSONException e) {
                            e.printStackTrace();
                            layoutProgress.setVisibility(View.GONE);
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
                            battingStatsOperation();
                        }else {
                            layoutProgress.setVisibility(View.GONE);
                        }
                        //showAlertDialog(errorMessage);

                    }
                }
        );

// add it to the RequestQueue
        queue.add(getRequest);

    }
    public void bowlingStatsOperation(){
        RequestQueue queue = Volley.newRequestQueue(BattingModulesActivity.this);
        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, StringConstants.mainUrl + StringConstants.bowlingStrategyUrl, null,
                new Response.Listener<JSONObject>()
                {

                    @Override
                    public void onResponse(JSONObject response) {
                        // display response
                        Log.d("Response", response.toString());

                        try {

                                    if(response.has("bowling")){
                                        JSONObject battingObject=response.getJSONObject("bowling");
                                        battingStatsModelList=new ArrayList<>();
                                        Iterator<String> iter = battingObject.keys();
                                        while (iter.hasNext()) {
                                            String key = iter.next();
                                            try {
                                                JSONObject bestScoreObject=battingObject.getJSONObject(key);
                                                battingStatsModel=new BattingStatsModel();
                                                battingStatsModel.setPlayerImage(bestScoreObject.getString("player_image"));
                                                battingStatsModel.setPlayerName(bestScoreObject.getString("player_name"));
                                                battingStatsModel.setScore(bestScoreObject.getString("wickets"));
                                                battingStatsModel.setTeamName(bestScoreObject.getString("team_name"));
                                                battingStatsModel.setTeamImage(bestScoreObject.getString("team_image"));
                                          //      battingStatsModel.setPlayerId(bestScoreObject.getString("player_id"));
                                                battingStatsModel.setStat(key);

                                                battingStatsModelList.add(battingStatsModel);

                                            } catch (JSONException e) {
                                                // Something went wrong!
                                            }

                                            mAdapter=new BattingStatsAdapter(BattingModulesActivity.this,battingStatsModelList);
                                            LinearLayoutManager horizontalLayoutManager1 = new LinearLayoutManager(getApplicationContext());
                                            recyclerViewBattingModule.setLayoutManager(horizontalLayoutManager1);
                                            recyclerViewBattingModule.setAdapter(mAdapter);
                                        }
/*
                                        if(battingObject.has("best_score")){
                                            JSONObject bestScoreObject=battingObject.getJSONObject("best_score");
                                            s_player_name=bestScoreObject.getString("player_name");
                                            s_team_name=bestScoreObject.getString("team_name");
                                            s_runs=bestScoreObject.getString("score");
                                            s_player_image=bestScoreObject.getString("player_image");
                                            s_team_image=bestScoreObject.getString("team_image");
                                            s_stat="runs";

                                            if(s_stat_name.matches("Orange Cap / Most Runs")){


                                            tv_player_name.setText(s_player_name);
                                            tv_runs.setText(s_runs);
                                            tv_score_tag.setText(s_stat);
                                            tv_team_name.setText(s_team_name);
                                            Glide.with(getApplicationContext())
                                                    .load(s_player_image) // image url
                                                    // .placeholder(R.drawable.placeholder) // any placeholder to load at start
                                                    //.error(R.drawable.imagenotfound)  // any image in case of error
                                                    // .override(200, 200); // resizing
                                                    // .apply(new RequestOptions().placeholder(R.drawable.loading))
                                                    .into(iv_player);
                                            Glide.with(getApplicationContext())
                                                    .load(s_team_image) // image url
                                                    // .placeholder(R.drawable.placeholder) // any placeholder to load at start
                                                    //.error(R.drawable.imagenotfound)  // any image in case of error
                                                    // .override(200, 200); // resizing
                                                    // .apply(new RequestOptions().placeholder(R.drawable.loading))
                                                    .into(iv_team_logo);
                                            }
                                        }
*/
/*
                                        if(battingObject.has("max_four")){
                                            JSONObject maxFoursObject=battingObject.getJSONObject("max_four");
                                            s_player_name=maxFoursObject.getString("player_name");
                                            s_team_name=maxFoursObject.getString("team_name");
                                            s_runs=maxFoursObject.getString("score");
                                            s_player_image=maxFoursObject.getString("player_image");
                                            s_team_image=maxFoursObject.getString("team_image");
                                            s_stat="Fours";

                                            if(s_stat_name.matches("Most Fours")){


                                            tv_player_name.setText(s_player_name);
                                            tv_runs.setText(s_runs);
                                            tv_score_tag.setText(s_stat);
                                            tv_team_name.setText(s_team_name);
                                            Glide.with(getApplicationContext())
                                                    .load(s_player_image) // image url
                                                    // .placeholder(R.drawable.placeholder) // any placeholder to load at start
                                                    //.error(R.drawable.imagenotfound)  // any image in case of error
                                                    // .override(200, 200); // resizing
                                                    // .apply(new RequestOptions().placeholder(R.drawable.loading))
                                                    .into(iv_player);
                                            Glide.with(getApplicationContext())
                                                    .load(s_team_image) // image url
                                                    // .placeholder(R.drawable.placeholder) // any placeholder to load at start
                                                    //.error(R.drawable.imagenotfound)  // any image in case of error
                                                    // .override(200, 200); // resizing
                                                    // .apply(new RequestOptions().placeholder(R.drawable.loading))
                                                    .into(iv_team_logo);
                                            }
                                        }
*/
/*
                                        if(battingObject.has("max_six")){
                                            JSONObject bestScoreObject=battingObject.getJSONObject("max_six");
                                            s_player_name=bestScoreObject.getString("player_name");
                                            s_team_name=bestScoreObject.getString("team_name");
                                            s_runs=bestScoreObject.getString("score");
                                            s_player_image=bestScoreObject.getString("player_image");
                                            s_team_image=bestScoreObject.getString("team_image");
                                            s_stat="sixes";

                                            if(s_stat_name.matches("Most Sixes")){


                                            tv_player_name.setText(s_player_name);
                                            tv_runs.setText(s_runs);
                                            tv_score_tag.setText(s_stat);
                                            tv_team_name.setText(s_team_name);
                                            Glide.with(getApplicationContext())
                                                    .load(s_player_image) // image url
                                                    // .placeholder(R.drawable.placeholder) // any placeholder to load at start
                                                    //.error(R.drawable.imagenotfound)  // any image in case of error
                                                    // .override(200, 200); // resizing
                                                    // .apply(new RequestOptions().placeholder(R.drawable.loading))
                                                    .into(iv_player);
                                            Glide.with(getApplicationContext())
                                                    .load(s_team_image) // image url
                                                    // .placeholder(R.drawable.placeholder) // any placeholder to load at start
                                                    //.error(R.drawable.imagenotfound)  // any image in case of error
                                                    // .override(200, 200); // resizing
                                                    // .apply(new RequestOptions().placeholder(R.drawable.loading))
                                                    .into(iv_team_logo);
                                            }
                                        }
*/
// parse the String "29/07/2013" to a java.util.Date object

                                    }

                            layoutProgress.setVisibility(View.GONE);

                        } catch (JSONException e) {
                            e.printStackTrace();
                            layoutProgress.setVisibility(View.GONE);
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
                            battingStatsOperation();
                        }else {
                            layoutProgress.setVisibility(View.GONE);
                        }
                        //showAlertDialog(errorMessage);

                    }
                }
        );

// add it to the RequestQueue
        queue.add(getRequest);

    }

}
