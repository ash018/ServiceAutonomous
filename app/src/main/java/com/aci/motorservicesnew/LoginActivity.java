package com.aci.motorservicesnew;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    public static String URL_LOGIN = "http://192.168.100.61:7779/genericservice/api/v0/login/";
    private EditText txtUsername, txtPassword;
    private Button btnSignin;
    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);


        txtUsername = (EditText) findViewById(R.id.txtUsername);
        txtPassword = (EditText) findViewById(R.id.txtPassword);
        btnSignin = (Button) findViewById(R.id.btnSignin);


        btnSignin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String email = txtUsername.getText().toString().trim();
                String password = txtPassword.getText().toString().trim();

                if (email.equalsIgnoreCase("") && password.equalsIgnoreCase("")) {
                    String message = "Please Give All Input ";
                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                } else {
                    checkLogin(email, password);
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

                        //startService(new Intent(getBaseContext(), RunAfterBootService.class));
                       /* Intent in = new Intent(getBaseContext(), MainActivity.class);
                        in.setAction("ACTION_BOOT_COMPLETED");
                        sendBroadcast(in)*/;
                        Intent jorori_intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(jorori_intent);
                        finish();


                    } else {
                        String statusCode = jObj.getString("message");
                        Toast.makeText(getApplicationContext(), "ERROR IN LOGIN", Toast.LENGTH_LONG).show();
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
