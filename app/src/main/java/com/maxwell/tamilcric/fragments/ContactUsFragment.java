package com.maxwell.tamilcric.fragments;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.maxwell.tamilcric.HomePageActivity;
import com.maxwell.tamilcric.R;
import com.maxwell.tamilcric.StringConstants;
import com.maxwell.tamilcric.TeamActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ContactUsFragment extends Fragment {

    View view;

    EditText et_first_name,et_last_name,et_email,et_message,et_subject;
    Button button_submit;

    String s_first_name,s_last_name,s_email,s_message,s_subject;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view=inflater.inflate(R.layout.layout_contactus,container,false);
        et_first_name=(EditText)view.findViewById(R.id.edittext_first_name);
        et_last_name=(EditText)view.findViewById(R.id.edittext_last_name);
        et_email=(EditText)view.findViewById(R.id.edittext_email);
        et_message=(EditText)view.findViewById(R.id.edittext_message);
        et_subject=(EditText)view.findViewById(R.id.edittext_subject);
        button_submit=(Button)view.findViewById(R.id.button_submit);

        button_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                s_first_name=et_first_name.getText().toString().trim();
                s_last_name=et_last_name.getText().toString().trim();
                s_email=et_email.getText().toString().trim();
                s_subject=et_subject.getText().toString().trim();
                s_message=et_message.getText().toString().trim();

                if(!s_first_name.isEmpty()){
                    if(!s_last_name.isEmpty()){
                        if(!s_email.isEmpty()){

                            if(isValidMail(s_email)){

                                if(!s_subject.isEmpty()){

                                    if(!s_message.isEmpty()){
                                       // Toast.makeText(getContext(),"Added successfully",Toast.LENGTH_SHORT).show();

                                        new SendContactFormOperation().execute();
                                    }else {
                                        et_message.setFocusable(true);
                                        showAlertDialog("Please enter message");

                                    }
                                }else {
                                    et_subject.setFocusable(true);
                                    showAlertDialog("Please enter subject");

                                }

                            }else {
                                et_email.setFocusable(true);
                                showAlertDialog("Please enter valid email ID");

                            }
                        }else {
                            showAlertDialog("Please enter email ID");
                            et_email.setFocusable(true);
                        }

                    }else {
                        et_last_name.setFocusable(true);
                        showAlertDialog("Please enter last name");

                    }

                }else {
                    et_first_name.setFocusable(true);
                    showAlertDialog("Please enter first name");

                }

            }
        });



        return view;
    }
    private boolean isValidMail(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
    public void showAlertDialog(String message){
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
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
    private class SendContactFormOperation extends AsyncTask<String, Void,String> {


        String result="";
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        @Override
        protected String doInBackground(String... strings) {
            StringRequest MyStringRequest = new StringRequest(Request.Method.POST, StringConstants.mainUrl + StringConstants.contactFormUrl, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    //This code is executed if the server responds, whether or not the response contains data.
                    //The String 'response' contains the server's response.
                    Log.d("Response",response);

                    try {
                        JSONObject jsonObject=new JSONObject(response.trim());

                            result=jsonObject.getString("response");
                            if(result.matches("success")){
                                Toast.makeText(getContext(),"Form submitted successfully",Toast.LENGTH_SHORT).show();

                                Intent i=new Intent(getContext(), HomePageActivity.class);
                                startActivity(i);

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
/*
                    try {
                        JSONObject jsonObject=new JSONObject(response.trim());
                        if(jsonObject.has("Data")){

                            JSONArray jsonArray=jsonObject.getJSONArray("Data");
                            for (int i=0;i<jsonArray.length();i++){
                                JSONObject dataObject=jsonArray.getJSONObject(i);
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
*/
                }
            }, new Response.ErrorListener() { //Create an error listener to handle errors appropriately.
                @Override
                public void onErrorResponse(VolleyError error) {
                    //This code is executed if there is an error.
                }
            }) {
                protected Map<String, String> getParams() {
                    Map<String, String> MyData = new HashMap<String, String>();
                    MyData.put(StringConstants.inputFirstName, s_first_name);
                    MyData.put(StringConstants.inputLsatName,s_last_name);
                    MyData.put(StringConstants.inputEmail, s_email);
                    MyData.put(StringConstants.inputSubject,s_subject);
                    MyData.put(StringConstants.inputMessage, s_message);
                    return MyData;
                }
            };

            requestQueue.add(MyStringRequest);
            return result;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

        }
    }

}
