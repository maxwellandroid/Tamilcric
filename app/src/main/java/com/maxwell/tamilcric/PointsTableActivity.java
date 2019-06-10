package com.maxwell.tamilcric;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
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
import com.bumptech.glide.Glide;
import com.maxwell.tamilcric.adapter.PointsTableAdapter;
import com.maxwell.tamilcric.adapter.TeamsAdapter;
import com.maxwell.tamilcric.model.PlayerModel;
import com.maxwell.tamilcric.model.PointsTableModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PointsTableActivity extends AppCompatActivity {

    PointsTableModel pointsTableModel;
    List<PointsTableModel> pointsTableModelList=new ArrayList<>();
    private RecyclerView recyclerViewPoints;
    private RecyclerView.Adapter mAdapter;
    RelativeLayout layoutProgress;
    TextView tv_no_items;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_points_table);

        recyclerViewPoints=(RecyclerView)findViewById(R.id.recyclerPoints);
        layoutProgress=(RelativeLayout)findViewById(R.id.rela_animation) ;
        tv_no_items=(TextView)findViewById(R.id.text_no_data);
      //  new PointsTableOperation().execute();
pointsTableListing();

    }
    public void backPressed(View view){

        onBackPressed();
    }

    public void pointsTableListing(){
        RequestQueue queue = Volley.newRequestQueue(PointsTableActivity.this);
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
                                        mAdapter=new PointsTableAdapter(getApplicationContext(),pointsTableModelList);
                                        LinearLayoutManager horizontalLayoutManager1 = new LinearLayoutManager(getApplicationContext());
                                        recyclerViewPoints.setLayoutManager(horizontalLayoutManager1);
                                        recyclerViewPoints.setAdapter(mAdapter);


                                        layoutProgress.setVisibility(View.GONE);
                                        recyclerViewPoints.setVisibility(View.VISIBLE);
                                    }else {
                                        layoutProgress.setVisibility(View.GONE);
                                        recyclerViewPoints.setVisibility(View.GONE);
                                        tv_no_items.setVisibility(View.VISIBLE);
                                    }
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
                            pointsTableListing();
                            layoutProgress.setVisibility(View.VISIBLE);
                            recyclerViewPoints.setVisibility(View.GONE);
                        }
                        else{
                            tv_no_items.setVisibility(View.VISIBLE);
                            //showAlertDialog(errorMessage);
                            layoutProgress.setVisibility(View.GONE);
                            recyclerViewPoints.setVisibility(View.GONE);

                        }

                    }
                }
        );

// add it to the RequestQueue
        queue.add(getRequest);

    }
}
