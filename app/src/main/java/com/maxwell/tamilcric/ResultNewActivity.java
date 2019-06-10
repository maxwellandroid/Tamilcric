package com.maxwell.tamilcric;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.maxwell.tamilcric.model.TournamentsModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ResultNewActivity extends AppCompatActivity {
    TournamentsModel tournamentsModel;
    List<TournamentsModel> tournamentsModelList=new ArrayList<>();
    private RecyclerView.Adapter mAdapter,mAdapter2;
    private RecyclerView recyclerViewTournaments;
    RelativeLayout layoutProgress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_new);
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
        RequestQueue queue = Volley.newRequestQueue(ResultNewActivity.this);
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
                                        mAdapter=new ResultTournamentsAdapter(getApplicationContext(),tournamentsModelList);
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
    public static class ResultTournamentsAdapter extends RecyclerView.Adapter<ResultTournamentsAdapter.ViewHolder>{

        List<TournamentsModel> peoplesModels;
        Context context;

        public ResultTournamentsAdapter(Context mcontext, List<TournamentsModel> peoplesModelList){
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

            viewHolder.linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent i=new Intent(context, ResultActivity.class);
                    i.putExtra("TournamentId",teamModel.getId());
                    i.putExtra("TournamentName",teamModel.getName());
                    context.startActivity(i);
                }
            });
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

}
