package com.aci.motorservicesnew;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ServiceRatio extends AppCompatActivity {
    //public static String URL_SERVICE_RATIO = "http://dashboard.acigroup.info/motorservices_mobile_api/budgetVsAch.php";
    public static String URL_SERVICE_RATIO = "http://mis.digital:7779/genericservice/api/v0/getservicevsachievement/";
    private String userId;
    public ProgressDialog pDialog;
    private static ImageView mainmenuid;

    public Boolean exit = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_ratio);

        final Bundle bundle = getIntent().getExtras();

        final String userId = bundle.getString("UserId");
        Log.d("userid",userId);
//        Intent selProIntent = getIntent();
//        userId = selProIntent.getStringExtra("UserId");
        getServiceRatio(userId);
//        SharedPreferences sp = getSharedPreferences("MotorService", Context.MODE_PRIVATE);
//        userId = sp.getString("UserId", "TestXXXX");


        mainmenuid = (ImageView) findViewById(R.id.mainmenuid);
        mainmenuid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent nextActivity = new Intent(ServiceRatio.this, MainActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("UserId",userId);
                nextActivity.putExtras(bundle);
                startActivity(nextActivity);
                finish();
            }
        });

    }

    private void getServiceRatio(final String userId) {
        String tag_string_req = "req_login";

        //pDialog.setMessage("Logging in ...");
        //showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                URL_SERVICE_RATIO, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
               // hideDialog();

                try {
//                    JSONArray jArr = new JSONArray(response);
                    JSONObject jObj = new JSONObject(response);
                    Log.d("jobj",String.valueOf(jObj));
//                    JSONObject jObj = new JSONObject(response);
                    //boolean error = jObj.getBoolean("error");
                    String status = jObj.getString("StatusCode");
                    Log.d("status",status);
//                    String msg = jObj.getString("StatusMessage");
                    if(status.equals("200")) {
                        String msg = jObj.getString("StatusMessage");
                        Log.d("msg",msg);
                        JSONArray msgArr = new JSONArray(msg);
                        String warrantyAchievement = msgArr.getJSONObject(0).getString("WARRANTY");
                        String warrantyTarget = msgArr.getJSONObject(0).getString("WarrantyServiceTarget");

                        String postWarrantyAchievement = msgArr.getJSONObject(0).getString("POSTWARRANTY");
                        String postWarrantyTarget = msgArr.getJSONObject(0).getString("PostWarrantyServiceTarget");


                        DecimalFormat df = new DecimalFormat("#.##");
                        TextView budgetWarranty = (TextView) findViewById(R.id.budgetWarranty);
                        TextView achivementWarranty = (TextView) findViewById(R.id.achivementWarranty);
                        TextView percentWarranty = (TextView) findViewById(R.id.percentWarranty);
                        TextView postBudgetWarranty = (TextView) findViewById(R.id.postBudgetWarranty);
                        TextView postAchivementWarranty = (TextView) findViewById(R.id.postAchivementWarranty);
                        TextView postPercentWarranty = (TextView) findViewById(R.id.postPercentWarranty);

                        Float budgetFloat,achivementFloat,postBudgetFloat,postAchivementFloat;
                        String servicetext;

                        budgetFloat = Float.parseFloat(warrantyTarget);
                        achivementFloat = Float.parseFloat(warrantyAchievement);

                        postBudgetFloat = Float.parseFloat(postWarrantyTarget);
                        postAchivementFloat = Float.parseFloat(postWarrantyAchievement);


                        budgetWarranty.setText(warrantyTarget);
                        achivementWarranty.setText(warrantyAchievement);
                        percentWarranty.setText(String.valueOf(df.format((achivementFloat/budgetFloat)*100)));

                        postBudgetWarranty.setText(postWarrantyTarget);
                        postAchivementWarranty.setText(postWarrantyAchievement);
                        postPercentWarranty.setText(String.valueOf(df.format((postAchivementFloat/postBudgetFloat)*100)));

                    } else{
                        Toast.makeText(getApplicationContext(),"Bad Request Sent", Toast.LENGTH_LONG);
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
               // hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();

                SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM" );

                params.put("UserId", userId);
                params.put("Period", sdf.format(new Date())+"-01");

                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

//    private void showDialog() {
//        if (!pDialog.isShowing())
//            pDialog.show();
//    }
//
//    private void hideDialog() {
//        if (pDialog.isShowing())
//            pDialog.dismiss();
//    }
    public void onBackPressed() {
        if (exit) {
            finish(); // finish activity
        } else {
            Toast.makeText(this, "ফিরে যান বাটনটি ব্যবহার করুন",
                    Toast.LENGTH_SHORT).show();

        }
    }
}
