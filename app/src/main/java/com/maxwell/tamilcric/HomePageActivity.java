package com.maxwell.tamilcric;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ExpandableListView;

import com.maxwell.tamilcric.adapter.ExpandableListAdapter;
import com.maxwell.tamilcric.fragments.AboutUsFragment;
import com.maxwell.tamilcric.fragments.ContactUsFragment;
import com.maxwell.tamilcric.fragments.HomeFragment;
import com.maxwell.tamilcric.model.MenuModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HomePageActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    ExpandableListAdapter expandableListAdapter;
    ExpandableListView expandableListView;
    List<MenuModel> headerList = new ArrayList<>();
    HashMap<MenuModel, List<MenuModel>> childList = new HashMap<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        expandableListView = findViewById(R.id.expandableListView);
        prepareMenuData();
        populateExpandableList();


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
      //  toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.black));
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        HomeFragment homeFragment=new HomeFragment();
        insertFragment(homeFragment);
    }
    private void prepareMenuData() {

        MenuModel menuModel = new MenuModel("Home", true, false, "0",R.drawable.home); //Menu of Android Tutorial. No sub menus
        headerList.add(menuModel);

        if (!menuModel.hasChildren) {
            childList.put(menuModel, null);
        }
        menuModel = new MenuModel("Tournaments", true, false, "1",R.drawable.tournament); //Menu of Android Tutorial. No sub menus
        headerList.add(menuModel);

        if (!menuModel.hasChildren) {
            childList.put(menuModel, null);
        }
        menuModel = new MenuModel("Match Schedule", true, false, "2",R.drawable.match_schedule); //Menu of Android Tutorial. No sub menus
        headerList.add(menuModel);

        if (!menuModel.hasChildren) {
            childList.put(menuModel, null);
        }
        menuModel = new MenuModel("Team", true, false, "3",R.drawable.team); //Menu of Android Tutorial. No sub menus
        headerList.add(menuModel);

        if (!menuModel.hasChildren) {
            childList.put(menuModel, null);
        }
        menuModel = new MenuModel("Players", true, false, "4",R.drawable.bats_man); //Menu of Android Tutorial. No sub menus
        headerList.add(menuModel);

        if (!menuModel.hasChildren) {
            childList.put(menuModel, null);
        }

        menuModel = new MenuModel("Bowling", true, true, "",R.drawable.bowling); //Menu of Java Tutorials
        headerList.add(menuModel);
        List<MenuModel> childModelsList = new ArrayList<>();
        MenuModel childModel = new MenuModel("Purple Cap / Most Wickets", false, false, "5",0);
        childModelsList.add(childModel);

        childModel = new MenuModel("Best Bowling Figures", false, false, "6",0);
        childModelsList.add(childModel);

        childModel = new MenuModel("Best Averages", false, false, "7",0);
        childModelsList.add(childModel);

        childModel = new MenuModel("Best Economy Rates", false, false, "8",0);
        childModelsList.add(childModel);

        childModel = new MenuModel("Best Strike Rates (innings)", false, false, "9",0);
        childModelsList.add(childModel);

        childModel = new MenuModel("Best Strike Rates (tournament)", false, false, "10",0);
        childModelsList.add(childModel);

        childModel = new MenuModel("Most Runs Conceded (innings)", false, false, "11",0);
        childModelsList.add(childModel);

        childModel = new MenuModel("Most Hat-Tricks", false, false, "12",0);
        childModelsList.add(childModel);

        childModel = new MenuModel("Most Dot Balls Bowled (tournament)", false, false, "13",0);
        childModelsList.add(childModel);

        childModel = new MenuModel("Most Number of Maiden Overs Bowled", false, false, "14",0);
        childModelsList.add(childModel);

        childModel = new MenuModel("Most Four Wickets in an Innings", false, false, "15",0);
        childModelsList.add(childModel);


        if (menuModel.hasChildren) {
            Log.d("API123","here");
            childList.put(menuModel, childModelsList);
        }

        childModelsList = new ArrayList<>();
        menuModel = new MenuModel("Batting", true, true, "",R.drawable.cricket_player_with_bat); //Menu of Python Tutorials
        headerList.add(menuModel);
        childModel = new MenuModel("Orange Cap / Most Runs", false, false, "16",0);
        childModelsList.add(childModel);

        childModel = new MenuModel("Most Sixes", false, false, "17",0);
        childModelsList.add(childModel);

        childModel = new MenuModel("Most Sixes (innings)", false, false, "18",0);
        childModelsList.add(childModel);

        childModel = new MenuModel("Highest Individual Score", false, false, "19",0);
        childModelsList.add(childModel);

        childModel = new MenuModel("Highest Strike Rates", false, false, "20",0);
        childModelsList.add(childModel);

        childModel = new MenuModel("Highest Strike Rates (innings)", false, false, "21",0);
        childModelsList.add(childModel);

        childModel = new MenuModel("Highest Averages", false, false, "22",0);
        childModelsList.add(childModel);

        childModel = new MenuModel("Most Fifties", false, false, "23",0);
        childModelsList.add(childModel);

        childModel = new MenuModel("Most Centuries", false, false, "24",0);
        childModelsList.add(childModel);

        childModel = new MenuModel("Most Fours", false, false, "25",0);
        childModelsList.add(childModel);

        childModel = new MenuModel("Fastest Fifties (innings)", false, false, "26",0);
        childModelsList.add(childModel);

        if (menuModel.hasChildren) {
            childList.put(menuModel, childModelsList);
        }

        menuModel = new MenuModel("Points", true, false, "27",R.drawable.numbered_list); //Menu of Android Tutorial. No sub menus
        headerList.add(menuModel);

        if (!menuModel.hasChildren) {
            childList.put(menuModel, null);
        }
        menuModel = new MenuModel("Results", true, false, "28",R.drawable.result); //Menu of Android Tutorial. No sub menus
        headerList.add(menuModel);

        if (!menuModel.hasChildren) {
            childList.put(menuModel, null);
        }
        menuModel = new MenuModel("About", true, false, "29",R.drawable.info); //Menu of Android Tutorial. No sub menus
        headerList.add(menuModel);

        if (!menuModel.hasChildren) {
            childList.put(menuModel, null);
        }
        menuModel = new MenuModel("Contact", true, false, "30",R.drawable.contactus); //Menu of Android Tutorial. No sub menus
        headerList.add(menuModel);

        if (!menuModel.hasChildren) {
            childList.put(menuModel, null);
        }

    }

    private void populateExpandableList() {

        expandableListAdapter = new ExpandableListAdapter(this, headerList, childList);
        expandableListView.setAdapter(expandableListAdapter);

        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {

                v.setSelected(true);

                if (headerList.get(groupPosition).isGroup) {
                    if (!headerList.get(groupPosition).hasChildren) {
                        MenuModel model = headerList.get(groupPosition);
                        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                        if(model.position.equals("0")){
                            HomeFragment homeFragment=new HomeFragment();
                            insertFragment(homeFragment);
                            drawer.closeDrawer(GravityCompat.START);
                        }
                        if(model.position.equals("1")){
                            Intent i=new Intent(getApplicationContext(),TournamentActivity.class);
                            startActivity(i);


                        }
                        if(model.position.equals("2")){
                            Intent i=new Intent(getApplicationContext(),MatchScheduleActivity.class);
                            startActivity(i);


                        }
                        if(model.position.equals("3")){
                            Intent i=new Intent(getApplicationContext(),TeamActivity.class);
                            startActivity(i);

                        }
                        if(model.position.equals("4")){
                            Intent i=new Intent(getApplicationContext(),PlayersActivity.class);
                            i.putExtra("TeamID","");
                            startActivity(i);
                        }
                        if(model.position.equals("27")){
                            Intent i=new Intent(getApplicationContext(), PointsTableNewActivity.class);
                            startActivity(i);

                        }
                        if(model.position.equals("28")){
                            Intent i=new Intent(getApplicationContext(),ResultActivity.class);
                            startActivity(i);

                        }
                        if(model.position.equals("29")){
                            AboutUsFragment aboutUsFragment=new AboutUsFragment();
                            insertFragment(aboutUsFragment);
                            drawer.closeDrawer(GravityCompat.START);
                        }
                        if(model.position.equals("30")){
                            ContactUsFragment contactUsFragment=new ContactUsFragment();
                            insertFragment(contactUsFragment);
                            drawer.closeDrawer(GravityCompat.START);
                        }

                    }
                }

                return false;
            }
        });

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

                if (childList.get(headerList.get(groupPosition)) != null) {
                    MenuModel model = childList.get(headerList.get(groupPosition)).get(childPosition);
                    if(model.position.equals("16")){
                        Intent i=new Intent(getApplicationContext(), BattingModulesActivity.class);
                        i.putExtra("StatName","Orange Cap / Most Runs");
                        startActivity(i);
                    }
                    if(model.position.equals("17")){
                        Intent i=new Intent(getApplicationContext(), BattingModulesActivity.class);
                        i.putExtra("StatName","Most Sixes");
                        startActivity(i);
                    }
                    if(model.position.equals("25")){
                        Intent i=new Intent(getApplicationContext(), BattingModulesActivity.class);
                        i.putExtra("StatName","Most Fours");
                        startActivity(i);
                    }
                }

                return false;
            }
        });
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home_page, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            HomeFragment homeFragment=new HomeFragment();
            insertFragment(homeFragment);
            // Handle the camera action
        } else if (id == R.id.nav_tournament) {
            Intent i=new Intent(getApplicationContext(),TournamentActivity.class);
            startActivity(i);

        }else if (id == R.id.nav_tournament_result) {
            Intent i=new Intent(getApplicationContext(),ResultNewActivity.class);
            startActivity(i);

        } else if (id == R.id.nav_match_schedule) {
            Intent i=new Intent(getApplicationContext(),MatchScheduleNewActivity.class);
            startActivity(i);

        }else if (id == R.id.nav_team) {
            Intent i=new Intent(getApplicationContext(),TeamActivity.class);
            startActivity(i);

        }else if (id == R.id.nav_bowling) {
            Intent i=new Intent(getApplicationContext(),BattingModulesActivity.class);
            i.putExtra("StatType","Bowling");
            startActivity(i);

        }else if (id == R.id.nav_batting) {
            Intent i=new Intent(getApplicationContext(),BattingModulesActivity.class);
                i.putExtra("StatType","Batting");
            startActivity(i);

        } else if (id == R.id.nav_player) {
            Intent i=new Intent(getApplicationContext(),PlayersActivity.class);
            i.putExtra("TeamID","");
            startActivity(i);

        } else if (id == R.id.nav_points) {
            Intent i=new Intent(getApplicationContext(), PointsTableNewActivity.class);
            startActivity(i);

        } else if (id == R.id.nav_about) {

            AboutUsFragment aboutUsFragment=new AboutUsFragment();
            insertFragment(aboutUsFragment);

        } else if (id == R.id.nav_contact) {

            ContactUsFragment contactUsFragment=new ContactUsFragment();
            insertFragment(contactUsFragment);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void insertFragment(Fragment fragment) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.frame, fragment);
        transaction.addToBackStack("mainstack");
        transaction.commit();
    }

}
