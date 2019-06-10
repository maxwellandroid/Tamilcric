package com.maxwell.tamilcric;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
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
import com.maxwell.tamilcric.adapter.PlayersAllAdapter;
import com.maxwell.tamilcric.model.PlayerModel;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PlayerDetailsActivity extends AppCompatActivity {

    ImageView iv_player_image,iv_team_logo;
    TextView tv_name,tv_role,tv_batting,tv_bowling,tv_dob,tv_debut,tv_team;

    String playerId="";
    PlayerModel playerModel;

    RelativeLayout layoutProgress;
    LinearLayout layout_home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_details);

        iv_player_image=(ImageView)findViewById(R.id.image_player_pic);
        iv_team_logo=(ImageView)findViewById(R.id.image_team_logo);

        tv_name=(TextView)findViewById(R.id.text_name);
        tv_role=(TextView)findViewById(R.id.text_role);
        tv_batting=(TextView)findViewById(R.id.text_batting_style);
        tv_bowling=(TextView)findViewById(R.id.text_bowling_style);
        tv_dob=(TextView)findViewById(R.id.text_dob);
        tv_debut=(TextView)findViewById(R.id.text_debut);
        tv_team=(TextView)findViewById(R.id.text_team);

        layoutProgress=(RelativeLayout)findViewById(R.id.rela_animation);
        layout_home=(LinearLayout)findViewById(R.id.layout_home) ;

        playerId=getIntent().getStringExtra("PlayerId");

        playerDetailsOperation();
     //   new PlayersDetailOperation().execute();
    }
    public void backPressed(View view){

        onBackPressed();
    }

    public void playerDetailsOperation(){
        RequestQueue queue = Volley.newRequestQueue(PlayerDetailsActivity.this);
        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, StringConstants.mainUrl + StringConstants.playerDetailUrl+StringConstants.inputAppKey+StringConstants.inputPlayerId+playerId, null,
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

                                    if(response.has("playerdetail_service")){
                                        JSONArray teamArray=response.getJSONArray("playerdetail_service");

                                        for(int i=0;i<teamArray.length();i++){

                                            JSONObject playerDetailObject=teamArray.getJSONObject(i);
                                            playerModel=new PlayerModel();
                                            playerModel.setPlayerName(playerDetailObject.getString("player_name"));
                                            playerModel.setPlayerImage(playerDetailObject.getString("player_image"));
                                            playerModel.setTeamLogo(playerDetailObject.getString("team_logo"));
                                            playerModel.setTeamName(playerDetailObject.getString("player_team_name"));
                                            playerModel.setRole(playerDetailObject.getString("player_role"));
                                            playerModel.setBattingStyle(playerDetailObject.getString("batting_style"));
                                            playerModel.setBowlingStyle(playerDetailObject.getString("bowling_style"));
                                            playerModel.setPlayerDob(playerDetailObject.getString("player_dob"));
                                            playerModel.setDebut(playerDetailObject.getString("player_debut"));
                                        }

/*
                                            Glide.with(getApplicationContext())
                                                    .load(playerModel.getTeamLogo()) // image url
                                                    // .placeholder(R.drawable.placeholder) // any placeholder to load at start
                                                    //.error(R.drawable.imagenotfound)  // any image in case of error
                                                    // .override(200, 200); // resizing
                                                    // .apply(new RequestOptions().placeholder(R.drawable.loading))
                                                    .into(iv_team_logo);
*/
                                           /* Glide.with(getApplicationContext())
                                                    .load(playerModel.getPlayerImage()) // image url
                                                   //.placeholder(R.drawable.tcalogo) // any placeholder to load at start
                                                   .error(R.drawable.tcalogo)  // any image in case of error
                                                    // .override(200, 200); // resizing
                                                    // .apply(new RequestOptions().placeholder(R.drawable.loading))
                                                    .into(iv_player_image);*/

                                        Glide.with(getApplicationContext())
                                                .load(playerModel.getTeamLogo()) // image url
                                                // .placeholder(R.drawable.placeholder) // any placeholder to load at start
                                                //.error(R.drawable.imagenotfound)  // any image in case of error
                                                // .override(200, 200); // resizing
                                                // .apply(new RequestOptions().placeholder(R.drawable.loading))
                                                .into(iv_team_logo);

                                        Picasso.get()
                                                .load(playerModel.getPlayerImage())
                                                .placeholder(R.drawable.tcalogo)
                                                .error(R.drawable.tcalogo)
                                                .into(iv_player_image);


                                        tv_name.setText(playerModel.getPlayerName());
                                        tv_role.setText(playerModel.getRole());
                                        tv_batting.setText(playerModel.getBattingStyle());
                                        tv_bowling.setText(playerModel.getBowlingStyle());
                                        tv_debut.setText(playerModel.getDebut());
                                        tv_team.setText(playerModel.getTeamName());
                                        String str =playerModel.getPlayerDob();
// parse the String "29/07/2013" to a java.util.Date object

                                        try {
                                            Date date = new SimpleDateFormat("yyyy-MM-dd").parse(str);
                                            String formattedDate = new SimpleDateFormat("dd-MM-yyyy").format(date);
                                            tv_dob.setText(formattedDate);
                                        } catch (ParseException e) {
                                            e.printStackTrace();
                                            tv_dob.setText(playerModel.getPlayerDob());
                                        }
                                    }
                                }
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        layoutProgress.setVisibility(View.GONE);
                        layout_home.setVisibility(View.VISIBLE);


                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Log.d("Error.Response", error.getLocalizedMessage());
                        String errorMessage=StringConstants.ErrorMessage(error);
                        if(errorMessage.matches("Connection TimeOut! Please check your internet connection.")){
                            playerDetailsOperation();
                            layoutProgress.setVisibility(View.VISIBLE);
                            layout_home.setVisibility(View.GONE);
                        }else {

                            layoutProgress.setVisibility(View.GONE);
                            layout_home.setVisibility(View.VISIBLE);
                        }
                        //showAlertDialog(errorMessage);

                    }
                }
        );

// add it to the RequestQueue
        queue.add(getRequest);

    }
}
