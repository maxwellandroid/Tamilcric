package com.maxwell.tamilcric;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
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
import com.maxwell.tamilcric.adapter.PointsTableAdapter;
import com.maxwell.tamilcric.adapter.ResultAdapter;
import com.maxwell.tamilcric.adapter.ResultsAdapter;
import com.maxwell.tamilcric.model.PointsTableModel;
import com.maxwell.tamilcric.model.PointsTableNewModel;
import com.maxwell.tamilcric.model.ResultMatchesModels;
import com.maxwell.tamilcric.model.ResultNewModel;
import com.maxwell.tamilcric.viewpageranimation.CubeOutRotationTransformation;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class ResultActivity extends AppCompatActivity {
    private ViewPager vp_slider;
    int page_position = 0;
    List<ResultMatchesModels> resultMatchesModelsList=new ArrayList<>();
    ResultMatchesModels resultMatchesModels;
    ResultsAdapter sliderPagerAdapter;
    RecyclerView recyclerViewResult;
    private RecyclerView.Adapter mAdapter;
    RelativeLayout layoutProgress;
    List<ResultNewModel> resultNewModelList=new ArrayList<>();
    ResultNewModel resultNewModel;
    String tournamentId="",tournamentName="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        initializeViews();
    }


    public void  initializeViews(){
        vp_slider = (ViewPager) findViewById(R.id.vp_slider);
        recyclerViewResult=(RecyclerView)findViewById(R.id.recyclerResult);
        layoutProgress=(RelativeLayout)findViewById(R.id.rela_animation);
        if(getIntent()!=null){
            tournamentId=getIntent().getStringExtra("TournamentId");
            tournamentName=getIntent().getStringExtra("TournamentName");
        }

        final CubeOutRotationTransformation CubeOutRotationTransformation = new CubeOutRotationTransformation();
        final Handler handler = new Handler();

        final Runnable update = new Runnable() {
            public void run() {
                if (page_position == resultMatchesModelsList.size()) {
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
        new Timer().schedule(new TimerTask() {

            @Override
            public void run() {
                handler.post(update);
            }
        }, 100, 5000);

        matchResultListing();
    }
    public void backPressed(View view){

        onBackPressed();
    }
  /*  @Override
    public void onBackPressed() {

        Intent i=new Intent(getApplicationContext(),HomePageActivity.class);
        startActivity(i);
        super.onBackPressed();
    }*/


    public void matchResultListing(){
        RequestQueue queue = Volley.newRequestQueue(ResultActivity.this);
        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, StringConstants.mainUrl + StringConstants.resultNewUrl+StringConstants.inputTournamentId+tournamentId, null,
                new Response.Listener<JSONObject>()
                {

                    @Override
                    public void onResponse(JSONObject response) {
                        // display response
                        Log.d("Response", response.toString());

                        try {


                            if(response.has("group")){
                                JSONArray tournamentArry=response.getJSONArray("group");
                                resultNewModelList=new ArrayList<>();
                                for(int i=0;i<tournamentArry.length();i++){
                                    JSONObject tournamentObject=tournamentArry.getJSONObject(i);
                                    resultNewModel=new ResultNewModel();
                                    resultNewModel.setGroup(tournamentObject.getString("group_name"));
                                    resultNewModel.setGroupId(tournamentObject.getString("id"));
                                    if(tournamentObject.has("group_team")){
                                        resultMatchesModelsList=new ArrayList<>();
                                        JSONArray matchesArray=tournamentObject.getJSONArray("group_team");
                                        for (int j=0;j<matchesArray.length();j++){
                                            JSONObject object=matchesArray.getJSONObject(j);
                                            resultMatchesModels=new ResultMatchesModels();
                                            resultMatchesModels.setMatchId(object.getString("match_id"));
                                            resultMatchesModels.setMatchNumeber(object.getString("match_number"));
                                            resultMatchesModels.setTeamAName(object.getString("team1_name"));
                                            resultMatchesModels.setTeamALogo(object.getString("team1_image"));
                                            resultMatchesModels.setTeamAScore(object.getString("team1_score"));
                                            resultMatchesModels.setTeamAWickets(object.getString("team1_wicket"));
                                            resultMatchesModels.setTeamBName(object.getString("team2_name"));
                                            resultMatchesModels.setTeamBLogo(object.getString("team2_image"));
                                            resultMatchesModels.setTeamBSore(object.getString("team2_score"));
                                            resultMatchesModels.setTeamBWickets(object.getString("team2_wicket"));
                                            resultMatchesModelsList.add(resultMatchesModels);
                                            resultNewModel.setResultMatchesModelsList(resultMatchesModelsList);

                                        }
                                    }
                                    resultNewModelList.add(resultNewModel);
                                }

                                mAdapter=new ResultAdapter(getApplicationContext(),resultNewModelList);
                                LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(getApplicationContext());
                                recyclerViewResult.setLayoutManager(horizontalLayoutManager);
                                recyclerViewResult.setAdapter(mAdapter);

                                layoutProgress.setVisibility(View.GONE);
                                recyclerViewResult.setVisibility(View.VISIBLE);


                            } else {
                                        layoutProgress.setVisibility(View.GONE);
                                        recyclerViewResult.setVisibility(View.VISIBLE);

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
                            matchResultListing();
                            layoutProgress.setVisibility(View.VISIBLE);
                            recyclerViewResult.setVisibility(View.GONE);

                        }else {
                            layoutProgress.setVisibility(View.GONE);
                            recyclerViewResult.setVisibility(View.VISIBLE);

                        }
                    }
                }
        );

// add it to the RequestQueue
        queue.add(getRequest);

    }

    public static class ResultAdapter extends RecyclerView.Adapter<ResultAdapter.ViewHolder>{

        private LayoutInflater layoutInflater;
        Context mcontext;
        List<ResultNewModel> resultMatchesModelsList;

        public ResultAdapter(Context mcontext, List<ResultNewModel> resultMatchesModelsList){
            this.mcontext = mcontext;
            this.resultMatchesModelsList = resultMatchesModelsList;

        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
            LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
            View listItem= layoutInflater.inflate(R.layout.layout_result_new_row, viewGroup, false);
            ViewHolder viewHolder = new ViewHolder(listItem);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int position) {
            final ResultNewModel teamModel= resultMatchesModelsList.get(position);

            viewHolder.tv_group_name.setText(teamModel.getGroup());
            viewHolder. mAdapter=new ResultAdapter1(mcontext,teamModel.getResultMatchesModelsList());
            LinearLayoutManager horizontalLayoutManager1 = new LinearLayoutManager(mcontext);
            viewHolder.recyclerViewPointsTable.setLayoutManager(horizontalLayoutManager1);
            viewHolder.recyclerViewPointsTable.setAdapter(viewHolder.mAdapter);
        }

        @Override
        public int getItemCount() {
            return resultMatchesModelsList.size();
        }

        public static class ViewHolder extends RecyclerView.ViewHolder {
            public LinearLayout linearLayout;
            TextView tv_group_name;
            RecyclerView recyclerViewPointsTable;
            private RecyclerView.Adapter mAdapter;

            public ViewHolder(View itemView) {
                super(itemView);
                this.tv_group_name =(TextView)itemView.findViewById(R.id.text_group_name);
                this.recyclerViewPointsTable =(RecyclerView)itemView.findViewById(R.id.recyclerViewResultRow);

            }
        }

    }
    public static class ResultAdapter1 extends RecyclerView.Adapter<ResultAdapter1.ViewHolder>{

        private LayoutInflater layoutInflater;
        Context mcontext;
        List<ResultMatchesModels> resultMatchesModelsList;

        public ResultAdapter1(Context mcontext, List<ResultMatchesModels> resultMatchesModelsList){
            this.mcontext = mcontext;
            this.resultMatchesModelsList = resultMatchesModelsList;

        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
            LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
            View listItem= layoutInflater.inflate(R.layout.layout_result_row, viewGroup, false);
            ViewHolder viewHolder = new ViewHolder(listItem);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int position) {
            final ResultMatchesModels teamModel= resultMatchesModelsList.get(position);

            viewHolder.matchNumeber.setText("Match "+ resultMatchesModelsList.get(position).getMatchNumeber());
            viewHolder.tv_winning_description.setText(resultMatchesModelsList.get(position).getDescription());
            viewHolder.teamAName.setText(resultMatchesModelsList.get(position).getTeamAName());
            viewHolder.teamBName.setText(resultMatchesModelsList.get(position).getTeamBName());
            viewHolder.teamAName.setText(resultMatchesModelsList.get(position).getTeamAName());
            if(!resultMatchesModelsList.get(position).getTeamAScore().isEmpty()&&!resultMatchesModelsList.get(position).getTeamAScore().equals("null")&&resultMatchesModelsList.get(position).getTeamAScore()!=null){
                viewHolder.teamAScore.setText(resultMatchesModelsList.get(position).getTeamAScore()+" / "+resultMatchesModelsList.get(position).getTeamAWickets());
            }else {
                viewHolder.teamAScore.setText("0"+" / "+"0");
            }
            if(!resultMatchesModelsList.get(position).getTeamBSore().isEmpty()&&!resultMatchesModelsList.get(position).getTeamBSore().equals("null")&&resultMatchesModelsList.get(position).getTeamBSore()!=null){
                viewHolder.teamBScore.setText(resultMatchesModelsList.get(position).getTeamBSore()+" / "+resultMatchesModelsList.get(position).getTeamBWickets());
            }else {
                viewHolder.teamBScore.setText("0"+" / "+"0");
            }


            viewHolder.teamAOvers.setText("Overs "+resultMatchesModelsList.get(position).getTeamAOvers());
            viewHolder.teamBOvers.setText("Overs "+resultMatchesModelsList.get(position).getTeamBOvers());
            viewHolder.tv_manofthematch.setText(resultMatchesModelsList.get(position).getManoftheMatch());

       /* Glide.with(activity.getApplicationContext()).load(resultMatchesModelsList.get(position).getTeamALogo()).placeholder(R.drawable.tcalogo).into(teamALogo);
        Glide.with(activity.getApplicationContext()).load(resultMatchesModelsList.get(position).getTeamBLogo()).into(teamBLogo);
       */ Picasso.get()
                    .load(resultMatchesModelsList.get(position).getTeamALogo())
                    .placeholder(R.drawable.tcalogo)
                    .error(R.drawable.tcalogo)
                    .into(viewHolder.teamALogo);
            Picasso.get()
                    .load(resultMatchesModelsList.get(position).getTeamBLogo())
                    .placeholder(R.drawable.tcalogo)
                    .error(R.drawable.tcalogo)
                    .into(viewHolder.teamBLogo);
            viewHolder.ll_container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent i=new Intent(mcontext, MatchSummeryActivity.class);
                    i.putExtra("MatchID",resultMatchesModelsList.get(position).getMatchId());
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mcontext.startActivity(i);
                }
            });

        }

        @Override
        public int getItemCount() {
            return resultMatchesModelsList.size();
        }

        public static class ViewHolder extends RecyclerView.ViewHolder {
            public ImageView imageView;
            public TextView tv_name,tv_follow;
            public LinearLayout linearLayout;
            ImageView teamALogo ;
            ImageView teamBLogo ;
            TextView matchNumeber ;
            TextView tv_winning_description ;
            TextView teamAName ;
            TextView teamBName;
            TextView teamAScore;
            TextView teamBScore ;
            TextView teamAOvers ;
            TextView teamBOvers ;
            TextView tv_manofthematch;
            LinearLayout ll_container;

            public ViewHolder(View itemView) {
                super(itemView);
                this.ll_container=(LinearLayout)itemView.findViewById(R.id.layout_contained_view);
                this.imageView = (ImageView) itemView.findViewById(R.id.image_team_logo);
                this.tv_name = (TextView) itemView.findViewById(R.id.team_name);
                this.teamALogo = (ImageView) itemView.findViewById(R.id.image_teamA_logo);
                this.teamBLogo = (ImageView) itemView.findViewById(R.id.image_teamB_logo);
                this.matchNumeber = (TextView) itemView.findViewById(R.id.text_match_number);
                this.tv_winning_description = (TextView) itemView.findViewById(R.id.text_winning_description);
                this.teamAName = (TextView) itemView.findViewById(R.id.text_teamA_name);
                this.teamBName = (TextView) itemView.findViewById(R.id.text_teamB_name);
                this.teamAScore = (TextView) itemView.findViewById(R.id.text_teamA_score);
                this.teamBScore = (TextView) itemView.findViewById(R.id.text_teamB_score);
                this.teamAOvers = (TextView) itemView.findViewById(R.id.text_teamA_overs);
                this.teamBOvers = (TextView) itemView.findViewById(R.id.text_teamB_overs);
                this.tv_manofthematch=(TextView)itemView.findViewById(R.id.text_manofthematch);

            }
        }

    }

}
