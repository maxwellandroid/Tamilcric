package com.maxwell.tamilcric;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.maxwell.tamilcric.adapter.TeamAdapter;
import com.maxwell.tamilcric.adapter.TeamsAdapter;
import com.maxwell.tamilcric.model.TeamModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class TeamActivity extends AppCompatActivity {
    private RecyclerView recyclerViewTeam;
    private RecyclerView.Adapter mAdapter;
    List<TeamModel> teamModelList=new ArrayList<>();
    TeamModel teamModel;
    RelativeLayout layoutProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team);

        recyclerViewTeam = (RecyclerView) findViewById(R.id.recyclerTeam);
        layoutProgress=(RelativeLayout)findViewById(R.id.rela_animation);

      teamListing();

    }
    public void backPressed(View view){

        onBackPressed();
    }
    @Override
    public void onBackPressed() {

        Intent i=new Intent(getApplicationContext(),HomePageActivity.class);
        startActivity(i);
        super.onBackPressed();
    }

    public void teamListing(){
        RequestQueue queue = Volley.newRequestQueue(TeamActivity.this);
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
                                        mAdapter=new TeamAdapter(getApplicationContext(),teamModelList);
                                        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
                                        recyclerViewTeam.setLayoutManager(mLayoutManager);
                                        //  recyclerViewTeam.addItemDecoration(new GridSpacingItemDecoration(1, suportClass.dpToPx(0), true));
                                        recyclerViewTeam.setItemAnimator(new DefaultItemAnimator());
                                        recyclerViewTeam.setAdapter(mAdapter);
                                    }
                                }
                            }

                            layoutProgress.setVisibility(View.GONE);
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
                            layoutProgress.setVisibility(View.VISIBLE);
                            recyclerViewTeam.setVisibility(View.GONE);
                        }else {
                            layoutProgress.setVisibility(View.GONE);
                            recyclerViewTeam.setVisibility(View.VISIBLE);
                        }
                        // showAlertDialog(errorMessage);

                    }
                }
        );

// add it to the RequestQueue
        queue.add(getRequest);

    }

}
