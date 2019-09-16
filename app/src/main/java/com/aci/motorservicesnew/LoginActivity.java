package com.aci.motorservicesnew;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    public static String URL_LOGIN = "http://mis.digital:7779/genericservice/api/v0/login/";
    private EditText txtUsername, txtPassword;
    private Button btnSignin;
    private ProgressDialog pDialog;
    private FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference mGetReference = mDatabase.getReference();
//    public static String URL_LOGIN = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);


        txtUsername = (EditText) findViewById(R.id.txtUsername);
        txtPassword = (EditText) findViewById(R.id.txtPassword);
        btnSignin = (Button) findViewById(R.id.btnSignin);


        mGetReference.child("url").child("5XNRqCOHUCeSpRMiZ2tU").addListenerForSingleValueEvent(

            new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Url url = dataSnapshot.getValue(Url.class);
                    Log.wtf("TAG",url.LOGIN_URL);
                    URL_LOGIN  = url.LOGIN_URL;
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
        });

//        mGetReference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//                if (dataSnapshot.exists()){
//                    HashMap<String, Object> dataMap = (HashMap<String, Object>) dataSnapshot.getValue();
//
//                    for (String key : dataMap.keySet()){
//
//                        Object data = dataMap.get(key);
//
//                        try{
//                            HashMap<String, Object> urlData = (HashMap<String, Object>) data;
//
////                            User mUser = new User((String) userData.get("name"), (int) (long) userData.get("age"));
////                            addTextToView(mUser.getName() + " - " + Integer.toString(mUser.getAge()));
//                            Log.wtf("LOGIN_URL",String.valueOf(urlData));
//
//                        }catch (ClassCastException cce){
//
//                        // If the object can’t be casted into HashMap, it means that it is of type String.
//
//                            try{
//
////                                String mString = String.valueOf(dataMap.get(key));
////                                addTextToView(mString);
//                                Log.wtf("LOGIN_URL","ERROR");
//
//                            } catch (ClassCastException cce2){
//
//                            }
//                        }
//
//                    }
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });


        btnSignin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                String email = txtUsername.getText().toString().trim();
                String password = txtPassword.getText().toString().trim();

                if (email.equalsIgnoreCase("") && password.equalsIgnoreCase("")) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                    builder.setMessage("আপনার UserId এবং Password সঠিক ভাবে দিন।")
                            .setCancelable(false)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                }
                            });
                    AlertDialog alert = builder.create();
                    alert.show();


                } else {
                    ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                    if(cm.getActiveNetworkInfo() == null){
                        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                        builder.setMessage("আপানর ইন্টারনেট সংযোগ On করুন।")
                                .setCancelable(false)
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                    }
                                });
                        AlertDialog alert = builder.create();
                        alert.show();
                    }else{
                        checkLogin(email, password);
                    }

                }
            }
        });
    }

    private void checkLogin(final String email, final String password) {
        // Tag used to cancel the request
        String tag_string_req = "req_login";

        pDialog.setMessage("Logging in ...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                URL_LOGIN, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    //boolean error = jObj.getBoolean("error");
                    String status = jObj.getString("StatusCode");
                    String msg = jObj.getString("StatusMessage");
                    System.out.println("REEE" + response);

                    if (status.equals("200")) {

                        SharedPreferences sp = getSharedPreferences("MotorService", Context.MODE_PRIVATE);
                        SharedPreferences.Editor ed = sp.edit();
                        ed.putString("UserId", email);
                        //ed.putString("Mobile",password);
                        ed.commit();

                        Intent jorori_intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(jorori_intent);
                        finish();


                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                        builder.setMessage("আপনার UserId অথবা Password ভুল হয়েছে। আবার চেষ্টা করুন।")
                                .setCancelable(false)
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                    }
                                });
                        AlertDialog alert = builder.create();
                        alert.show();
                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                //Log.e(TAG, "Login Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("Username", email);
                params.put("Password", password);

                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

}
