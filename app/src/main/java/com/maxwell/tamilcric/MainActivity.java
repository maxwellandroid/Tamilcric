package com.maxwell.tamilcric;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.maxwell.tamilcric.adapter.TeamsAdapter;
import com.maxwell.tamilcric.model.TeamModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerViewTeam,recylerViewPlayers;
    private RecyclerView.Adapter mAdapter,mAdapter2;
    private RecyclerView.LayoutManager layoutManager,layoutManager2;

    List<TeamModel> teamModelList=new ArrayList<>();
    List<TeamModel> playerModelList=new ArrayList<>();

    TeamModel teamModel,teamModel1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerViewTeam = (RecyclerView) findViewById(R.id.recyclerTeam);
        recylerViewPlayers = (RecyclerView) findViewById(R.id.recyclerPlayers);

        for(int i=0;i<4;i++){
            teamModel=new TeamModel();

            if(i==0){
                teamModel.setTeamName("Chennai Super Kings");
            } if(i==1){
                teamModel.setTeamName("Mumbai Indians");
            } if(i==2){
                teamModel.setTeamName("Kolkata Knight Righders");
            } if(i==3){
                teamModel.setTeamName("Kings XI Punjab");
            }
            teamModelList.add(teamModel);

        }
        for(int i=0;i<4;i++){
            teamModel1=new TeamModel();

            if(i==0){
                teamModel1.setTeamName("M.S Dhoni");
            } if(i==1){
                teamModel1.setTeamName("Rohit Sharma");
            } if(i==2){
                teamModel1.setTeamName("Dinesh Karthik");
            } if(i==3){
                teamModel1.setTeamName("Aswin");
            }
            playerModelList.add(teamModel1);

        }

        mAdapter=new TeamsAdapter(getApplicationContext(),teamModelList);
        LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewTeam.setLayoutManager(horizontalLayoutManager);
        recyclerViewTeam.setAdapter(mAdapter);
        mAdapter2=new TeamsAdapter(getApplicationContext(),playerModelList);
        LinearLayoutManager horizontalLayoutManager1 = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        recylerViewPlayers.setLayoutManager(horizontalLayoutManager1);
        recylerViewPlayers.setAdapter(mAdapter2);
    }
}
