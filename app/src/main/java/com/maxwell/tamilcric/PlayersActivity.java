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
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.maxwell.tamilcric.adapter.PlayersAdapter;
import com.maxwell.tamilcric.adapter.PlayersAllAdapter;
import com.maxwell.tamilcric.adapter.TeamAdapter;
import com.maxwell.tamilcric.model.PlayerModel;
import com.maxwell.tamilcric.model.TeamModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PlayersActivity extends AppCompatActivity {
    private RecyclerView recyclerViewTeam;
    private RecyclerView.Adapter mAdapter;
    List<PlayerModel> teamModelList=new ArrayList<>();
    PlayerModel teamModel;
    RelativeLayout layoutProgress;

    String teamID="";
    TextView tv_no_players;
    TextView tv_team_name;
    String teamName="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_players);
        recyclerViewTeam = (RecyclerView) findViewById(R.id.recyclerPlayers);
        layoutProgress=(RelativeLayout)findViewById(R.id.rela_animation) ;
        tv_no_players=(TextView)findViewById(R.id.text_no_players);
        tv_team_name=(TextView)findViewById(R.id.text_team_name);

        if(getIntent()!=null){
            teamID=getIntent().getStringExtra("TeamID");
            teamName=getIntent().getStringExtra("TeamName");
        }
        if(!teamID.isEmpty()){
            tv_team_name.setVisibility(View.VISIBLE);
            tv_team_name.setText(teamName);
            playersListingById(StringConstants.mainUrl+StringConstants.teamPlayerUrl+StringConstants.inputAppKey+StringConstants.inputTeamId+teamID);
        }else {
            playersListing(StringConstants.mainUrl + StringConstants.playerUrl+StringConstants.inputAppKey);
        }
      //  new PlayersListingOperation().execute();
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


    public void playersListing(String url){
        RequestQueue queue = Volley.newRequestQueue(PlayersActivity.this);
        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url, null,
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
                                            teamModel=new PlayerModel();
                                            JSONObject object=teamArray.getJSONObject(i);
                                            teamModel.setId(object.getString("player_id"));
                                            teamModel.setPlayerName(object.getString("player_name"));
                                            teamModel.setPlayerImage(object.getString("player_image"));
                                            teamModel.setTeamLogo(object.getString("team_image"));
                                            teamModel.setTeamName(object.getString("team_name"));

                                            teamModelList.add(teamModel);
                                        }
                                        mAdapter=new PlayersAllAdapter(PlayersActivity.this,teamModelList);
                                        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
                                        recyclerViewTeam.setLayoutManager(mLayoutManager);
                                        //  recyclerViewTeam.addItemDecoration(new GridSpacingItemDecoration(1, suportClass.dpToPx(0), true));
                                        recyclerViewTeam.setItemAnimator(new DefaultItemAnimator());
                                        recyclerViewTeam.setAdapter(mAdapter);
                                    }
                                    layoutProgress.setVisibility(View.GONE);
                                    recyclerViewTeam.setVisibility(View.VISIBLE);
                                    tv_no_players.setVisibility(View.GONE);

                                }
                                else {
                                    layoutProgress.setVisibility(View.GONE);
                                    recyclerViewTeam.setVisibility(View.GONE);
                                    tv_no_players.setVisibility(View.VISIBLE);
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
                            playersListing(StringConstants.mainUrl + StringConstants.playerUrl+StringConstants.inputAppKey);

                            //showAlertDialog(errorMessage);
                            layoutProgress.setVisibility(View.VISIBLE);
                            recyclerViewTeam.setVisibility(View.GONE);
                        }else {

                            layoutProgress.setVisibility(View.GONE);
                            recyclerViewTeam.setVisibility(View.VISIBLE);
                        }




                    }
                }
        );

// add it to the RequestQueue
        queue.add(getRequest);

    }
    public void playersListingById(String url){
        RequestQueue queue = Volley.newRequestQueue(PlayersActivity.this);
        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url, null,
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

                                    if(response.has("teamplayer_service")){
                                        JSONArray teamArray=response.getJSONArray("teamplayer_service");
                                        for(int i=0;i<teamArray.length();i++){
                                            teamModel=new PlayerModel();
                                            JSONObject object=teamArray.getJSONObject(i);
                                            teamModel.setId(object.getString("player_id"));
                                            teamModel.setPlayerName(object.getString("player_name"));
                                            teamModel.setPlayerImage(object.getString("player_image"));
                                            teamModel.setTeamLogo(object.getString("team_logo"));
                                            teamModel.setTeamName(object.getString("team_name"));
                                            teamModelList.add(teamModel);
                                        }
                                        mAdapter=new PlayersAllAdapter(PlayersActivity.this,teamModelList);
                                        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
                                        recyclerViewTeam.setLayoutManager(mLayoutManager);
                                        //  recyclerViewTeam.addItemDecoration(new GridSpacingItemDecoration(1, suportClass.dpToPx(0), true));
                                        recyclerViewTeam.setItemAnimator(new DefaultItemAnimator());
                                        recyclerViewTeam.setAdapter(mAdapter);
                                    }
                                    layoutProgress.setVisibility(View.GONE);
                                    recyclerViewTeam.setVisibility(View.VISIBLE);
                                    tv_no_players.setVisibility(View.GONE);

                                }
                                else {
                                    layoutProgress.setVisibility(View.GONE);
                                    recyclerViewTeam.setVisibility(View.GONE);
                                    tv_no_players.setVisibility(View.VISIBLE);
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
                            playersListingById(StringConstants.mainUrl+StringConstants.teamPlayerUrl+StringConstants.inputAppKey+StringConstants.inputTeamId+teamID);
                            //showAlertDialog(errorMessage);
                            layoutProgress.setVisibility(View.VISIBLE);
                            recyclerViewTeam.setVisibility(View.GONE);
                        }else {
                            //showAlertDialog(errorMessage);

                            layoutProgress.setVisibility(View.GONE);
                            recyclerViewTeam.setVisibility(View.VISIBLE);
                        }

                   //     tv_no_players.setVisibility(View.VISIBLE);
                   //     tv_no_players.setText(errorMessage);


                    }
                }
        );

// add it to the RequestQueue
        queue.add(getRequest);

    }
    private class PlayersListingOperation extends AsyncTask<String,Void,String> {

        ProgressDialog pDialog=new ProgressDialog(PlayersActivity.this);
        String result="";
        RequestQueue queue = Volley.newRequestQueue(PlayersActivity.this);



        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog.setMessage("Loading..");
            pDialog.setTitle("");
            pDialog.show();

        }

        @Override
        protected String doInBackground(String... strings) {
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
                                                teamModel=new PlayerModel();
                                                JSONObject object=teamArray.getJSONObject(i);
                                                teamModel.setId(object.getString("player_id"));
                                                teamModel.setTeamName(object.getString("player_name"));
                                                teamModel.setTeamLogo(object.getString("player_image"));

                                                teamModelList.add(teamModel);
                                            }
                                            mAdapter=new PlayersAllAdapter(PlayersActivity.this,teamModelList);
                                            RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
                                            recyclerViewTeam.setLayoutManager(mLayoutManager);
                                            //  recyclerViewTeam.addItemDecoration(new GridSpacingItemDecoration(1, suportClass.dpToPx(0), true));
                                            recyclerViewTeam.setItemAnimator(new DefaultItemAnimator());
                                            recyclerViewTeam.setAdapter(mAdapter);
                                        }
                                    }
                                }
                                if(pDialog.isShowing()){
                                    pDialog.dismiss();
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
                            //showAlertDialog(errorMessage);
                            layoutProgress.setVisibility(View.GONE);
                            recyclerViewTeam.setVisibility(View.VISIBLE);

                            if(pDialog.isShowing()){
                                pDialog.dismiss();
                            }
                        }
                    }
            );

// add it to the RequestQueue
            queue.add(getRequest);
            return result;

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if(pDialog.isShowing()){
                pDialog.dismiss();
            }
        }
    }
    public void showAlertDialog(String message){
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(PlayersActivity.this);
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

}
