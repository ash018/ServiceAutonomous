package com.aci.motorservicesnew;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ServiceRatio extends AppCompatActivity {
    public static String URL_SERVICE_RATIO = "http://mis.digital:7779/genericservice/api/v0/login/";
    private String userId;
    private ProgressDialog pDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_ratio);

        Intent selProIntent = getIntent();
        userId = selProIntent.getStringExtra("UserId");
        getServiceRatio(userId);
    }

    private void getServiceRatio(final String userId) {
        String tag_string_req = "req_login";

        pDialog.setMessage("Logging in ...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                URL_SERVICE_RATIO, new Response.Listener<String>() {

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

//                        SharedPreferences sp = getSharedPreferences("MotorService", Context.MODE_PRIVATE);
//                        SharedPreferences.Editor ed = sp.edit();
//                        ed.putString("UserId", email);
//                        //ed.putString("Mobile",password);
//                        ed.commit();
//
//                        Intent jorori_intent = new Intent(LoginActivity.this, MainActivity.class);
//                        startActivity(jorori_intent);
//                        finish();
                        TextView budgetWarranty = (TextView) findViewById(R.id.budgetWarranty);
                        TextView achivementWarranty = (TextView) findViewById(R.id.achivementWarranty);
                        TextView percentWarranty = (TextView) findViewById(R.id.percentWarranty);
                        TextView postWarranty = (TextView) findViewById(R.id.postWarranty);
                        TextView postAchivementWarranty = (TextView) findViewById(R.id.postAchivementWarranty);
                        TextView postPercentWarranty = (TextView) findViewById(R.id.postPercentWarranty);

                    } else {
//                        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
//                        builder.setMessage("আপনার UserId অথবা Password ভুল হয়েছে। আবার চেষ্টা করুন।")
//                                .setCancelable(false)
//                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                                    public void onClick(DialogInterface dialog, int id) {
//                                    }
//                                });
//                        AlertDialog alert = builder.create();
//                        alert.show();
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
                params.put("UserId", userId);

                return params;
            }
        };
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
