package com.maxwell.tamilcric;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;

public class StringConstants {

    public static String mainUrl="https://tamilcric.ch/api/";
    public static String teamUrl="team.php?team";
    public static String inputAppKey="&AppKey=StFaInfo";
    public static String tournamentUrl="torunament.php?tournament";
    public static String playerUrl="player.php?player";
    public static String playerDetailUrl="player_details.php?playerdetail";
    public static String pointsTableapi="pointtable.php";
    public static String liveMatchUrl="livematch.php";
    public static String matchScheduleUrl="matchschedule.php";
    public static String matchScheduleNewUrl="matchschedule_new.php?";
    public static String contactFormUrl="contact_us.php";
    public static String resultUrl="result_view.php";
    public static String resultNewUrl="result_view_new.php?";
    public static String teamPlayerUrl="teamplayer.php?teamplayer";
    public static String matchSummeryUrl="match_summary.php?";
    public static String groupwisePointsUrl="groupwise_point.php";
    public static String battingStrategyUrl="batting_strategy.php";
    public static String bowlingStrategyUrl="bowling_strategy.php";

    public static String inputPlayerId="&player_id=";
    public static String inputFirstName="first_name";
    public static String inputLsatName="last_name";
    public static String inputEmail="email";
    public static String inputSubject="subject";
    public static String inputMessage="message";
    public static String inputTeamId="&team_id=";
    public static String inputMatchId="match_id=";
    public static String inputTourn="tourn";
    public static String inputTournamentId="tournament_id=";


    public static String ErrorMessage(VolleyError volleyError) {
        String message = null;
        if (volleyError instanceof NetworkError) {
            message = "Cannot connect to Internet...Please check your connection!";
// Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
        } else if (volleyError instanceof ServerError) {
            message = "The server could not be found. Please try again after some time!!";
// Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
        } else if (volleyError instanceof AuthFailureError) {
            message = "Cannot connect to Internet...Please check your connection!";
// Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
        } else if (volleyError instanceof ParseError) {
            message = "Parsing error! Please try again after some time!!";
// Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
        } else if (volleyError instanceof NoConnectionError) {
            message = "Cannot connect to Internet...Please check your connection!";
//Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
        } else if (volleyError instanceof TimeoutError) {
            message = "Connection TimeOut! Please check your internet connection.";
//Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
        }

        //   Toast.makeText(context, message, Toast.LENGTH_SHORT).show();

        return message;
    }

}
