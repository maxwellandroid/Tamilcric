package com.maxwell.tamilcric;

import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.maxwell.tamilcric.adapter.PointsTableAdapter;
import com.maxwell.tamilcric.model.PointsTableModel;
import com.maxwell.tamilcric.model.PointsTableNewModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NewPointsTableActivity extends AppCompatActivity {
    private RecyclerView recylerViewPointTables;
    private RecyclerView.Adapter mAdapter;
    List<PointsTableModel> pointsTableModelList;
    List<PointsTableNewModel> pointsTableNewModelList;
    PointsTableModel pointsTableModel;
    PointsTableNewModel pointsTableNewModel;
    RelativeLayout layoutProgress;
    String tournamentId="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_points_table);

        recylerViewPointTables=(RecyclerView)findViewById(R.id.recyclerViewPointsTable);
        layoutProgress=(RelativeLayout)findViewById(R.id.rela_animation) ;
        if(getIntent()!=null){
            tournamentId=getIntent().getStringExtra("TournamentId");
        }

        pointsTableListing();
      //  Toast.makeText(getApplicationContext(),tournamentId,Toast.LENGTH_SHORT).show();
    }
    public void backPressed(View view){

        onBackPressed();
    }

    public void pointsTableListing(){
        RequestQueue queue = Volley.newRequestQueue(NewPointsTableActivity.this);

        StringRequest MyStringRequest = new StringRequest(Request.Method.POST, StringConstants.mainUrl + StringConstants.groupwisePointsUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //This code is executed if the server responds, whether or not the response contains data.
                //The String 'response' contains the server's response.
                Log.d("Response",response);
                try {
                    JSONObject jsonObject=new JSONObject(response.trim());
                 //   if(jsonObject.has("success")){

                       // if(success.matches("true")){

                            if(jsonObject.has("group")){
                                JSONArray tournamentArry=jsonObject.getJSONArray("group");
                                pointsTableNewModelList=new ArrayList<>();
                                for(int i=0;i<tournamentArry.length();i++){
                                    JSONObject tournamentObject=tournamentArry.getJSONObject(i);
                                    pointsTableNewModel=new PointsTableNewModel();
                                    pointsTableNewModel.setGroup(tournamentObject.getString("group_name"));
                                    pointsTableNewModel.setGroupId(tournamentObject.getString("id"));
                                    if(tournamentObject.has("group_team")){
                                        pointsTableModelList=new ArrayList<>();
                                        JSONArray matchesArray=tournamentObject.getJSONArray("group_team");
                                        for (int j=0;j<matchesArray.length();j++){
                                            JSONObject pointsObject=matchesArray.getJSONObject(j);
                                            pointsTableModel=new PointsTableModel();
                                            pointsTableModel.setTeamId(pointsObject.getString("reg_id"));
                                            pointsTableModel.setTeamName(pointsObject.getString("reg_teamname"));
                                            pointsTableModel.setTeamImage(pointsObject.getString("team_logo"));
                                            pointsTableModel.setPlayed(pointsObject.getString("played"));
                                            pointsTableModel.setWon(pointsObject.getString("won"));
                                            pointsTableModel.setLost(pointsObject.getString("lost"));
                                            pointsTableModel.setTied(pointsObject.getString("tied"));
                                            pointsTableModelList.add(pointsTableModel);
                                            pointsTableNewModel.setPointsTableModelList(pointsTableModelList);
                                        }
                                    }
                                    pointsTableNewModelList.add(pointsTableNewModel);
                                }
                            }
                        //}
                        mAdapter=new PointsAdapter(getApplicationContext(),pointsTableNewModelList);
                        LinearLayoutManager horizontalLayoutManager1 = new LinearLayoutManager(getApplicationContext());
                        recylerViewPointTables.setLayoutManager(horizontalLayoutManager1);
                        recylerViewPointTables.setAdapter(mAdapter);

                    //}
                    layoutProgress.setVisibility(View.GONE);
                    //  recylerViewMatches.setVisibility(View.VISIBLE);
                    recylerViewPointTables.setVisibility(View.VISIBLE);

                } catch (JSONException e) {
                    e.printStackTrace();
                    layoutProgress.setVisibility(View.GONE);
                    //  recylerViewMatches.setVisibility(View.VISIBLE);
                    Toast.makeText(getApplicationContext(),"No Data Found",Toast.LENGTH_SHORT).show();
                    recylerViewPointTables.setVisibility(View.VISIBLE);

                }


            }
        }, new Response.ErrorListener() { //Create an error listener to handle errors appropriately.
            @Override
            public void onErrorResponse(VolleyError error) {

                String errormessage=StringConstants.ErrorMessage(error);
                //showAlertDialog(errormessage);
                //This code is executed if there is an error.
                if(errormessage.matches("Connection TimeOut! Please check your internet connection.")){
                    pointsTableListing();
                    layoutProgress.setVisibility(View.VISIBLE);
                    recylerViewPointTables.setVisibility(View.GONE);
                }else {
                    layoutProgress.setVisibility(View.GONE);
                    recylerViewPointTables.setVisibility(View.VISIBLE);
                }
            }
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> MyData = new HashMap<String, String>();
                MyData.put(StringConstants.inputTourn, tournamentId);

                // MyData.put(StringConstants.inputMobile,"");//Add the data you'd like to send to the server.
                return MyData;
            }
        };

// add it to the RequestQueue
        queue.add(MyStringRequest);


    }
    public static class PointsAdapter extends RecyclerView.Adapter<PointsAdapter.ViewHolder>{

        List<PointsTableNewModel> matchScheduleModelList;
        Context context;

        public PointsAdapter(Context mcontext, List<PointsTableNewModel> matchScheduleModelList){
            this.context=mcontext;
            this.matchScheduleModelList =matchScheduleModelList;

        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
            View listItem= layoutInflater.inflate(R.layout.layout_points_table_row_new, viewGroup, false);
            ViewHolder viewHolder = new ViewHolder(listItem);
            return viewHolder;
        }

        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        @Override
        public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
            final PointsTableNewModel pointsTableNewModel= matchScheduleModelList.get(i);
            viewHolder.tv_group_name.setText(pointsTableNewModel.getGroup());
            viewHolder. mAdapter=new PointsTableAdapter(context,pointsTableNewModel.getPointsTableModelList());
            LinearLayoutManager horizontalLayoutManager1 = new LinearLayoutManager(context);
            viewHolder.recyclerViewPointsTable.setLayoutManager(horizontalLayoutManager1);
            viewHolder.recyclerViewPointsTable.setAdapter(viewHolder.mAdapter);
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
            TextView tv_group_name;
            RecyclerView recyclerViewPointsTable;
            private RecyclerView.Adapter mAdapter;

            public ViewHolder(View itemView) {
                super(itemView);

                this.tv_group_name =(TextView)itemView.findViewById(R.id.text_group_name);
                this.recyclerViewPointsTable =(RecyclerView)itemView.findViewById(R.id.recyclerViewPointsTableRows);



            }
        }

    }


}
