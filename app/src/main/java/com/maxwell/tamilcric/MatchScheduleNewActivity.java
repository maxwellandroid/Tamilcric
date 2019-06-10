package com.maxwell.tamilcric;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.maxwell.tamilcric.adapter.MatchScheduleNewAdapter;
import com.maxwell.tamilcric.adapter.TournamentsAdapter;
import com.maxwell.tamilcric.model.TournamentsModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MatchScheduleNewActivity extends AppCompatActivity {

    TournamentsModel tournamentsModel;
    List<TournamentsModel> tournamentsModelList=new ArrayList<>();
    private RecyclerView.Adapter mAdapter,mAdapter2;
    private RecyclerView recyclerViewTournaments;
    RelativeLayout layoutProgress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_schedule_new);
        recyclerViewTournaments=(RecyclerView)findViewById(R.id.recyclerTounaments);
        layoutProgress=(RelativeLayout)findViewById(R.id.rela_animation) ;
        // new TournamentListingOperation().execute();
        tournamentListing();

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
    public void tournamentListing(){
        RequestQueue queue = Volley.newRequestQueue(MatchScheduleNewActivity.this);
        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, StringConstants.mainUrl + StringConstants.tournamentUrl+StringConstants.inputAppKey, null,
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

                                    if(response.has("tournament_service")){
                                        JSONArray teamArray=response.getJSONArray("tournament_service");
                                        for(int i=0;i<teamArray.length();i++){
                                            tournamentsModel=new TournamentsModel();
                                            JSONObject object=teamArray.getJSONObject(i);
                                            tournamentsModel.setDate(object.getString("tourn_date"));
                                            tournamentsModel.setName(object.getString("tourn_name"));
                                            tournamentsModel.setId(object.getString("tourn_id"));
                                            // tournamentsModel.setAgegroup(object.getString("tourn_age"));

                                            tournamentsModelList.add(tournamentsModel);
                                        }
                                        mAdapter=new MatchScheduleNewAdapter(getApplicationContext(),tournamentsModelList);
                                        LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(getApplicationContext());
                                        recyclerViewTournaments.setLayoutManager(horizontalLayoutManager);
                                        recyclerViewTournaments.setAdapter(mAdapter);
                                    }
                                }
                            }

                            layoutProgress.setVisibility(View.GONE);
                            recyclerViewTournaments.setVisibility(View.VISIBLE);

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
                            layoutProgress.setVisibility(View.VISIBLE);
                            recyclerViewTournaments.setVisibility(View.GONE);
                        }else {
                            layoutProgress.setVisibility(View.GONE);
                            recyclerViewTournaments.setVisibility(View.VISIBLE);
                        }
                        //eshowAlertDialog(errorMessage);

                    }
                }
        );

// add it to the RequestQueue
        queue.add(getRequest);

    }

}
