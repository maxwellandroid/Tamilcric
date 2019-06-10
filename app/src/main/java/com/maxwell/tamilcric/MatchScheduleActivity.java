package com.maxwell.tamilcric;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.maxwell.tamilcric.model.MatchScheduleModel;
import com.maxwell.tamilcric.model.MatchScheduleNewModel;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MatchScheduleActivity extends AppCompatActivity {

     RecyclerView recylerViewMatches,recyclerViewKnockOutMatches,recyclerViewQuaterFinalMatches,recyclerViewSemiFinalMatches,recyclerViewThirdPlaceMatches,recyclerViewFinalMatches;
    private RecyclerView.Adapter mAdapter,mAdapterKnockOut,mAdapterQuaterFinal,mAdapterSemiFinal,mAdapterThirdPlace,mAdapterFinal;
    List<MatchScheduleModel> matchScheduleModelList=new ArrayList<>();
    List<MatchScheduleModel> matchScheduleModelKnockOutList=new ArrayList<>();
    List<MatchScheduleModel> matchScheduleModelQuaterFinalList=new ArrayList<>();
    List<MatchScheduleModel> matchScheduleModelSemiFinalList=new ArrayList<>();
    List<MatchScheduleModel> matchScheduleModelThirdPlaceList=new ArrayList<>();
    List<MatchScheduleModel> matchScheduleModelFinalList=new ArrayList<>();
    MatchScheduleModel matchScheduleModel;
    RelativeLayout layoutProgress;
    TextView tv_tournamentName;
    TableLayout tableLayout;
    LinearLayout linearLayout;
    List<MatchScheduleNewModel> matchScheduleNewModelList=new ArrayList<>();
    MatchScheduleNewModel matchScheduleNewModel;
    String tournamentId="",tournamentName="";
    TextView tv_knockout_no_match,tv_quater_no_matches,tv_semi_no_matches,tv_third_no_matches,tv_final_no_matches;
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_schedule);

        recylerViewMatches=(RecyclerView)findViewById(R.id.recyclerViewMatchSchedule);
        recyclerViewKnockOutMatches=(RecyclerView)findViewById(R.id.recyclerKnockoutSchedule);
        recyclerViewQuaterFinalMatches=(RecyclerView)findViewById(R.id.recyclerQuaterFinalSchedule);
        recyclerViewSemiFinalMatches=(RecyclerView)findViewById(R.id.recyclerSemiFinalSchedule);
        recyclerViewThirdPlaceMatches=(RecyclerView)findViewById(R.id.recyclerThirdPlaceSchedule);
        recyclerViewFinalMatches=(RecyclerView)findViewById(R.id.recyclerFinalMatchSchedule);
        layoutProgress=(RelativeLayout)findViewById(R.id.rela_animation);
        linearLayout=(LinearLayout)findViewById(R.id.linear_home) ;
        tv_tournamentName=(TextView)findViewById(R.id.text_tournament_name);
        tableLayout=(TableLayout)findViewById(R.id.table_match_schedule);

        tv_knockout_no_match=(TextView)findViewById(R.id.text_knock_out_schedule);
        tv_quater_no_matches=(TextView)findViewById(R.id.text_quater_final_schedule);
        tv_semi_no_matches=(TextView)findViewById(R.id.text_semi_final_schedule);
        tv_third_no_matches=(TextView)findViewById(R.id.text_third_place_schedule);
        tv_final_no_matches=(TextView)findViewById(R.id.text_final_match_schedule);

        matchScheduleModelList=new ArrayList<>();
        matchScheduleNewModelList=new ArrayList<>();

        if(getIntent()!=null){
            tournamentId=getIntent().getStringExtra("TournamentId");
            tournamentName=getIntent().getStringExtra("TournamentName");
        }

        tv_tournamentName.setText("Group Match Schedule - "+ tournamentName);



   matchSchedule();

    }
    public void backPressed(View view){

        onBackPressed();
    }



    public void matchSchedule(){
        RequestQueue queue = Volley.newRequestQueue(MatchScheduleActivity.this);
        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, StringConstants.mainUrl + StringConstants.matchScheduleNewUrl+StringConstants.inputTournamentId+tournamentId, null,
                new Response.Listener<JSONObject>()
                {

                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                    @Override
                    public void onResponse(JSONObject response) {
                        // display response
                        Log.d("Response", response.toString());

                        try {

                            /*if(response.has("success")){
                                String success=response.getString("success");
                                if(success.matches("true")){*/

                                    if(response.has("group")){
                                        JSONArray tournamentArry=response.getJSONArray("group");
                                        for(int i=0;i<tournamentArry.length();i++){
                                            JSONObject tournamentObject=tournamentArry.getJSONObject(i);
                                            matchScheduleNewModel=new MatchScheduleNewModel();
                                            matchScheduleNewModel.setTournamentName(tournamentObject.getString("group_name"));
                                            if(tournamentObject.has("group_team")){
                                                matchScheduleModelList=new ArrayList<>();
                                                JSONArray matchesArray=tournamentObject.getJSONArray("group_team");
                                                for (int j=0;j<matchesArray.length();j++){
                                                    JSONObject matchesObject=matchesArray.getJSONObject(j);
                                                    matchScheduleModel=new MatchScheduleModel();
                                                   matchScheduleModel.setMatchNumber(matchesObject.getString("match_number"));
                                                    matchScheduleModel.setDate(matchesObject.getString("schedule_date"));
                                                    matchScheduleModel.setTime(matchesObject.getString("schedule_time"));
                                                    matchScheduleModel.setGround(matchesObject.getString("ground"));
                                                    matchScheduleModel.setGroup(matchesObject.getString("group_name"));
                                                    matchScheduleModel.setStadium(matchesObject.getString("stadium_name"));
                                                    matchScheduleModel.setTeamAName(matchesObject.getString("team1_name"));
                                                    matchScheduleModel.setTeamBName(matchesObject.getString("team2_name"));
                                                    matchScheduleModel.setTeamALogo(matchesObject.getString("team1_image"));
                                                    matchScheduleModel.setTeamBLogo(matchesObject.getString("team2_image"));
                                                    matchScheduleModelList.add(matchScheduleModel);
                                                    matchScheduleNewModel.setMatchScheduleModelList(matchScheduleModelList);
                                                }
                                            }
                                            matchScheduleNewModelList.add(matchScheduleNewModel);
                                        }


                                        mAdapter=new MatchScheduleAdapter(getApplicationContext(),matchScheduleNewModelList);
                                        LinearLayoutManager horizontalLayoutManager1 = new LinearLayoutManager(getApplicationContext());
                                        recylerViewMatches.setLayoutManager(horizontalLayoutManager1);
                                        recylerViewMatches.setAdapter(mAdapter);

                                    }
                                    if(response.has("matchschedule")){

                                        JSONObject matchschedule=response.getJSONObject("matchschedule");

                                           // matchScheduleNewModel.setTournamentName(tournamentObject.getString("group_name"));
                                            if(matchschedule.has("knock out Schedule")){
                                                matchScheduleModelKnockOutList=new ArrayList<>();
                                                JSONArray matchesArray=matchschedule.getJSONArray("knock out Schedule");
                                                for (int j=0;j<matchesArray.length();j++){
                                                    JSONObject matchesObject=matchesArray.getJSONObject(j);
                                                    matchScheduleModel=new MatchScheduleModel();
                                                    matchScheduleModel.setMatchNumber(matchesObject.getString("match_number"));
                                                    matchScheduleModel.setDate(matchesObject.getString("schedule_date"));
                                                    matchScheduleModel.setTime(matchesObject.getString("schedule_time"));
                                                    matchScheduleModel.setGround(matchesObject.getString("ground"));
                                                   // matchScheduleModel.setGroup(matchesObject.getString("group_name"));
                                                    matchScheduleModel.setStadium(matchesObject.getString("stadium_name"));
                                                    matchScheduleModel.setTeamAName(matchesObject.getString("team1_name"));
                                                    matchScheduleModel.setTeamBName(matchesObject.getString("team2_name"));
                                                    matchScheduleModel.setTeamALogo(matchesObject.getString("team1_image"));
                                                    matchScheduleModel.setTeamBLogo(matchesObject.getString("team2_image"));

                                                   matchScheduleModelKnockOutList.add(matchScheduleModel);
                                                }

                                                if(matchScheduleModelKnockOutList.size()>0){
                                                    tv_knockout_no_match.setVisibility(View.GONE);
                                                    recyclerViewKnockOutMatches.setVisibility(View.VISIBLE);
                                                    mAdapterKnockOut=new MatchScheduleAdapter1(getApplicationContext(),matchScheduleModelKnockOutList);
                                                    LinearLayoutManager horizontalLayoutManager1 = new LinearLayoutManager(getApplicationContext());
                                                    recyclerViewKnockOutMatches.setLayoutManager(horizontalLayoutManager1);
                                                    recyclerViewKnockOutMatches.setAdapter(mAdapterKnockOut);
                                                }else {
                                                    recyclerViewKnockOutMatches.setVisibility(View.GONE);
                                                    tv_knockout_no_match.setVisibility(View.VISIBLE);
                                                }


                                            }
                                            if(matchschedule.has("Quarter Final Schedule")){
                                                matchScheduleModelQuaterFinalList=new ArrayList<>();
                                                JSONArray matchesArray=matchschedule.getJSONArray("Quarter Final Schedule");
                                                for (int j=0;j<matchesArray.length();j++){
                                                    JSONObject matchesObject=matchesArray.getJSONObject(j);
                                                    matchScheduleModel=new MatchScheduleModel();
                                                   matchScheduleModel.setMatchNumber(matchesObject.getString("match_number"));
                                                    matchScheduleModel.setDate(matchesObject.getString("schedule_date"));
                                                    matchScheduleModel.setTime(matchesObject.getString("schedule_time"));
                                                 matchScheduleModel.setGround("-");
                                                   // matchScheduleModel.setGroup(matchesObject.getString("group_name"));
                                                    matchScheduleModel.setStadium(matchesObject.getString("stadium_name"));
                                                    matchScheduleModel.setTeamAName(matchesObject.getString("team1_name"));
                                                    matchScheduleModel.setTeamBName(matchesObject.getString("team2_name"));
                                                    matchScheduleModel.setTeamALogo(matchesObject.getString("team1_image"));
                                                    matchScheduleModel.setTeamBLogo(matchesObject.getString("team2_image"));

                                                   matchScheduleModelQuaterFinalList.add(matchScheduleModel);
                                                }

                                                if(matchScheduleModelQuaterFinalList.size()>0){
                                                    tv_quater_no_matches.setVisibility(View.GONE);
                                                    recyclerViewQuaterFinalMatches.setVisibility(View.VISIBLE);
                                                    mAdapterQuaterFinal=new MatchScheduleAdapter1(getApplicationContext(),matchScheduleModelQuaterFinalList);
                                                    LinearLayoutManager horizontalLayoutManager1 = new LinearLayoutManager(getApplicationContext());
                                                    recyclerViewQuaterFinalMatches.setLayoutManager(horizontalLayoutManager1);
                                                    recyclerViewQuaterFinalMatches.setAdapter(mAdapterQuaterFinal);
                                                }else {
                                                    recyclerViewQuaterFinalMatches.setVisibility(View.GONE);
                                                    tv_quater_no_matches.setVisibility(View.VISIBLE);
                                                }


                                            }
                                            if(matchschedule.has("Semi Final Schedule")){
                                                matchScheduleModelSemiFinalList=new ArrayList<>();
                                                JSONArray matchesArray=matchschedule.getJSONArray("Semi Final Schedule");
                                                for (int j=0;j<matchesArray.length();j++){
                                                    JSONObject matchesObject=matchesArray.getJSONObject(j);
                                                    matchScheduleModel=new MatchScheduleModel();
                                                    matchScheduleModel.setMatchNumber(matchesObject.getString("match_number"));
                                                    matchScheduleModel.setDate(matchesObject.getString("schedule_date"));
                                                    matchScheduleModel.setTime(matchesObject.getString("schedule_time"));
                                                    matchScheduleModel.setGround("-");
                                                   // matchScheduleModel.setGroup(matchesObject.getString("group_name"));
                                                    matchScheduleModel.setStadium(matchesObject.getString("stadium_name"));
                                                    matchScheduleModel.setTeamAName(matchesObject.getString("team1_name"));
                                                    matchScheduleModel.setTeamBName(matchesObject.getString("team2_name"));
                                                    matchScheduleModel.setTeamALogo(matchesObject.getString("team1_image"));
                                                    matchScheduleModel.setTeamBLogo(matchesObject.getString("team2_image"));

                                                   matchScheduleModelSemiFinalList.add(matchScheduleModel);
                                                }

                                                if(matchScheduleModelSemiFinalList.size()>0){
                                                    tv_semi_no_matches.setVisibility(View.GONE);
                                                    recyclerViewSemiFinalMatches.setVisibility(View.VISIBLE);
                                                    mAdapterSemiFinal=new MatchScheduleAdapter1(getApplicationContext(),matchScheduleModelSemiFinalList);
                                                    LinearLayoutManager horizontalLayoutManager1 = new LinearLayoutManager(getApplicationContext());
                                                    recyclerViewSemiFinalMatches.setLayoutManager(horizontalLayoutManager1);
                                                    recyclerViewSemiFinalMatches.setAdapter(mAdapterSemiFinal);
                                                }else {
                                                    recyclerViewSemiFinalMatches.setVisibility(View.GONE);
                                                    tv_semi_no_matches.setVisibility(View.VISIBLE);
                                                }


                                            }
                                            if(matchschedule.has("Third Place Schedule")){
                                                matchScheduleModelThirdPlaceList=new ArrayList<>();
                                                JSONArray matchesArray=matchschedule.getJSONArray("Third Place Schedule");
                                                for (int j=0;j<matchesArray.length();j++){
                                                    JSONObject matchesObject=matchesArray.getJSONObject(j);
                                                    matchScheduleModel=new MatchScheduleModel();
                                                   matchScheduleModel.setMatchNumber(matchesObject.getString("match_number"));
                                                    matchScheduleModel.setDate(matchesObject.getString("schedule_date"));
                                                    matchScheduleModel.setTime(matchesObject.getString("schedule_time"));
                                                   matchScheduleModel.setGround("-");
                                                   // matchScheduleModel.setGroup(matchesObject.getString("group_name"));
                                                    matchScheduleModel.setStadium(matchesObject.getString("stadium_name"));
                                                    matchScheduleModel.setTeamAName(matchesObject.getString("team1_name"));
                                                    matchScheduleModel.setTeamBName(matchesObject.getString("team2_name"));
                                                    matchScheduleModel.setTeamALogo(matchesObject.getString("team1_image"));
                                                    matchScheduleModel.setTeamBLogo(matchesObject.getString("team2_image"));

                                                   matchScheduleModelThirdPlaceList.add(matchScheduleModel);
                                                }

                                                if(matchScheduleModelThirdPlaceList.size()>0){
                                                    tv_third_no_matches.setVisibility(View.GONE);
                                                    recyclerViewThirdPlaceMatches.setVisibility(View.VISIBLE);
                                                    mAdapterThirdPlace=new MatchScheduleAdapter1(getApplicationContext(),matchScheduleModelThirdPlaceList);
                                                    LinearLayoutManager horizontalLayoutManager1 = new LinearLayoutManager(getApplicationContext());
                                                    recyclerViewThirdPlaceMatches.setLayoutManager(horizontalLayoutManager1);
                                                    recyclerViewThirdPlaceMatches.setAdapter(mAdapterThirdPlace);
                                                }else {
                                                    recyclerViewThirdPlaceMatches.setVisibility(View.GONE);
                                                    tv_third_no_matches.setVisibility(View.VISIBLE);
                                                }

                                            }
                                            if(matchschedule.has("Final match Schedule")){
                                                matchScheduleModelFinalList=new ArrayList<>();
                                                JSONArray matchesArray=matchschedule.getJSONArray("Final match Schedule");
                                                for (int j=0;j<matchesArray.length();j++){
                                                    JSONObject matchesObject=matchesArray.getJSONObject(j);
                                                    matchScheduleModel=new MatchScheduleModel();
                                                    matchScheduleModel.setMatchNumber(matchesObject.getString("match_number"));
                                                    matchScheduleModel.setDate(matchesObject.getString("schedule_date"));
                                                    matchScheduleModel.setTime(matchesObject.getString("schedule_time"));
                                                  matchScheduleModel.setGround("-");
                                                   // matchScheduleModel.setGroup(matchesObject.getString("group_name"));
                                                    matchScheduleModel.setStadium(matchesObject.getString("stadium_name"));
                                                    matchScheduleModel.setTeamAName(matchesObject.getString("team1_name"));
                                                    matchScheduleModel.setTeamBName(matchesObject.getString("team2_name"));
                                                    matchScheduleModel.setTeamALogo(matchesObject.getString("team1_image"));
                                                    matchScheduleModel.setTeamBLogo(matchesObject.getString("team2_image"));

                                                    matchScheduleModelFinalList.add(matchScheduleModel);
                                                }

                                                if(matchScheduleModelFinalList.size()>0){
                                                    tv_final_no_matches.setVisibility(View.GONE);
                                                    recyclerViewFinalMatches.setVisibility(View.VISIBLE);
                                                    mAdapterFinal=new MatchScheduleAdapter1(getApplicationContext(),matchScheduleModelFinalList);
                                                    LinearLayoutManager horizontalLayoutManager1 = new LinearLayoutManager(getApplicationContext());
                                                    recyclerViewFinalMatches.setLayoutManager(horizontalLayoutManager1);
                                                    recyclerViewFinalMatches.setAdapter(mAdapterFinal);
                                                }else {
                                                    recyclerViewFinalMatches.setVisibility(View.GONE);
                                                    tv_final_no_matches.setVisibility(View.VISIBLE);
                                                }

                                            }





                                    }
                                //}
                           // }

                            layoutProgress.setVisibility(View.GONE);
                            //  recylerViewMatches.setVisibility(View.VISIBLE);
                            linearLayout.setVisibility(View.VISIBLE);


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
                            matchSchedule();
                            layoutProgress.setVisibility(View.VISIBLE);
                            linearLayout.setVisibility(View.GONE);
                        }else {
                            layoutProgress.setVisibility(View.GONE);
                            linearLayout.setVisibility(View.VISIBLE);
                        }
                    }
                }
        );
// add it to the RequestQueue
        queue.add(getRequest);


    }
    public static class MatchScheduleAdapter extends RecyclerView.Adapter<MatchScheduleAdapter.ViewHolder>{

        List<MatchScheduleNewModel> matchScheduleModelList;
        Context context;

        public MatchScheduleAdapter(Context mcontext, List<MatchScheduleNewModel> matchScheduleModelList){
            this.context=mcontext;
            this.matchScheduleModelList =matchScheduleModelList;

        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
            View listItem= layoutInflater.inflate(R.layout.layout_match_schedule_new, viewGroup, false);
            ViewHolder viewHolder = new ViewHolder(listItem);
            return viewHolder;
        }

        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        @Override
        public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
            final MatchScheduleNewModel matchScheduleModel= matchScheduleModelList.get(i);

            if(matchScheduleModel.getMatchScheduleModelList().size()>0){
                viewHolder.tv_tournament_name.setText(matchScheduleModel.getTournamentName());
                viewHolder. mAdapter=new MatchScheduleAdapter1(context,matchScheduleModel.getMatchScheduleModelList());
                LinearLayoutManager horizontalLayoutManager1 = new LinearLayoutManager(context);
                viewHolder.recyclerViewMatches.setLayoutManager(horizontalLayoutManager1);
                viewHolder.recyclerViewMatches.setAdapter(viewHolder.mAdapter);
            }

       /* viewHolder.tv_player_name.setText(matchScheduleModel.getTeamName());
        viewHolder.tv_slno.setText(String.valueOf(i+1));
        viewHolder.tv_overs.setText(matchScheduleModel.getWon());
        viewHolder.tv_runs.setText(matchScheduleModel.getPlayed());
        viewHolder.tv_wides.setText(matchScheduleModel.getLost());
        viewHolder.tv_noballs.setText(matchScheduleModel.getTied());
        Glide.with(context)
                .load(matchScheduleModel.getTeamImage()) // image url
                // .placeholder(R.drawable.placeholder) // any placeholder to load at start
                //.error(R.drawable.imagenotfound)  // any image in case of error
                // .override(200, 200); // resizing
                // .apply(new RequestOptions().placeholder(R.drawable.loading))
                .into(viewHolder.iv_player);*/

        }

        @Override
        public int getItemCount() {
            return matchScheduleModelList.size();
        }

        public static class ViewHolder extends RecyclerView.ViewHolder {

            public LinearLayout linearLayout;
            TextView tv_tournament_name;
            RecyclerView recyclerViewMatches;
            private RecyclerView.Adapter mAdapter;

            public ViewHolder(View itemView) {
                super(itemView);

                this.tv_tournament_name =(TextView)itemView.findViewById(R.id.text_tournament_name);
                this.recyclerViewMatches=(RecyclerView)itemView.findViewById(R.id.recyclerViewTournamentRows);



            }
        }

    }
    public static class MatchScheduleAdapter1 extends RecyclerView.Adapter<MatchScheduleAdapter1.ViewHolder>{

        List<MatchScheduleModel> matchScheduleModelList;
        Context context;

        public MatchScheduleAdapter1(Context mcontext, List<MatchScheduleModel> matchScheduleModelList){
            this.context=mcontext;
            this.matchScheduleModelList =matchScheduleModelList;

        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
            View listItem= layoutInflater.inflate(R.layout.layout_matchschedule_tournament_row, viewGroup, false);
            ViewHolder viewHolder = new ViewHolder(listItem);
            return viewHolder;
        }

        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        @Override
        public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
            final MatchScheduleModel matchScheduleModel= matchScheduleModelList.get(i);
       /* viewHolder.tv_player_name.setText(matchScheduleModel.getTeamName());
        viewHolder.tv_slno.setText(String.valueOf(i+1));
        viewHolder.tv_overs.setText(matchScheduleModel.getWon());
        viewHolder.tv_runs.setText(matchScheduleModel.getPlayed());
        viewHolder.tv_wides.setText(matchScheduleModel.getLost());
        viewHolder.tv_noballs.setText(matchScheduleModel.getTied());
        Glide.with(context)
                .load(matchScheduleModel.getTeamImage()) // image url
                // .placeholder(R.drawable.placeholder) // any placeholder to load at start
                //.error(R.drawable.imagenotfound)  // any image in case of error
                // .override(200, 200); // resizing
                // .apply(new RequestOptions().placeholder(R.drawable.loading))
                .into(viewHolder.iv_player);*/
            viewHolder.tv_match_number.setText("Match "+matchScheduleModel.getMatchNumber());
            /*try {
                Date date = new SimpleDateFormat("yyyy-MM-dd").parse(matchScheduleModel.getDate());
                String formattedDate = new SimpleDateFormat("dd-MM-yyyy").format(date);
                viewHolder.tv_date.setText(formattedDate+" / "+matchScheduleModel.getTime());
            } catch (ParseException e) {
                e.printStackTrace();

            }*/

            viewHolder.tv_date.setText(matchScheduleModel.getDate()+" / "+matchScheduleModel.getTime());
            //viewHolder.tv_time.setText(matchScheduleModel.getTime());
            viewHolder.tv_ground.setText("Ground : "+matchScheduleModel.getGround());
            viewHolder.tv_matchbw.setText(matchScheduleModel.getTeamAName()+"\n Vs \n"+matchScheduleModel.getTeamBName());
           viewHolder.tv_stadium.setText(matchScheduleModel.getStadium());
            Glide.with(context)

                    .load(matchScheduleModel.getTeamALogo()) // image url
                    // .placeholder(R.drawable.placeholder) // any placeholder to load at start
                    //.error(R.drawable.imagenotfound)  // any image in case of error
                    // .override(200, 200); // resizing
                    // .apply(new RequestOptions().placeholder(R.drawable.loading))
                    .into(viewHolder.iv_team1);
            Glide.with(context)
                    .load(matchScheduleModel.getTeamBLogo()) // image url
                    // .placeholder(R.drawable.placeholder) // any placeholder to load at start
                    //.error(R.drawable.imagenotfound)  // any image in case of error
                    // .override(200, 200); // resizing
                    // .apply(new RequestOptions().placeholder(R.drawable.loading))
                    .into(viewHolder.iv_team2);
        }

        @Override
        public int getItemCount() {
            return matchScheduleModelList.size();
        }

        public static class ViewHolder extends RecyclerView.ViewHolder {

            public LinearLayout linearLayout;
            TextView tv_match_number,tv_date,tv_time,tv_ground,tv_matchbw,tv_stadium;
            ImageView iv_team1,iv_team2;


            public ViewHolder(View itemView) {
                super(itemView);
                this.tv_stadium=(TextView)itemView.findViewById(R.id.text_stadium) ;
                this.tv_match_number =(TextView)itemView.findViewById(R.id.text_match_number);
                this.tv_date =(TextView)itemView.findViewById(R.id.text_match_date);
                this.tv_ground =(TextView)itemView.findViewById(R.id.text_match_group);
                this.tv_matchbw =(TextView)itemView.findViewById(R.id.text_teams);
                this.iv_team1=(ImageView)itemView.findViewById(R.id.image_teamA_logo);
                this.iv_team2=(ImageView)itemView.findViewById(R.id.image_teamB_logo);



            }
        }

    }

}
