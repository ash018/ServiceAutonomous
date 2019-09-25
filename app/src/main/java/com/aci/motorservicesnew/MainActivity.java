package com.aci.motorservicesnew;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.aci.utils.EditServiceRow;
import com.aci.utils.SynchDataRow;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.Format;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private String url_all_kiosk = "http://mis.digital:7779/genericservice/api/v0/manageservice/";
    private String url_download_customer = "http://mis.digital:7779/genericservice/api/v0/getuserservice/";

    private ImageView imgjobcard, imgjobcardview, imglogout, imgserviceperformance, upload_to_server;
    private DatabaseHelper db;
    private static String userId = "";

    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        db = new DatabaseHelper(getApplicationContext());
        db.getWritableDatabase();
        SharedPreferences sp;
        sp = getSharedPreferences("MotorService", Context.MODE_PRIVATE);
        userId = sp.getString("UserId", "TestXXXX");


        if(userId.equalsIgnoreCase("TestXXXX")){
            Intent jorori_intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(jorori_intent);
            finish();
        }
        else {
            final Bundle bundle = getIntent().getExtras();
            imgjobcard = (ImageView) findViewById(R.id.imgjobcard);
            imgjobcardview = (ImageView) findViewById(R.id.imgjobcardview);
            imglogout = (ImageView) findViewById(R.id.imglogout);
            imgserviceperformance = (ImageView) findViewById(R.id.imgserviceperformance);
            upload_to_server = (ImageView) findViewById(R.id.upload_to_server);

            if (!db.isNeedSychForUpdateLocalDB()) {
                ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                if (cm.getActiveNetworkInfo() == null) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setMessage("আপানর ইন্টারনেট সংযোগ On করুন।")
                            .setCancelable(false)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                }
                            });
                    AlertDialog alert = builder.create();
                    alert.show();
                } else {
                    downloadCustomerData(userId);
                }
            }

            if (db.isAnyDataForSynch()) {
                String uri = "@drawable/ic_uploadto_server_green";
                int imageResource = getResources().getIdentifier(uri, null, getPackageName());
                Drawable res = getResources().getDrawable(imageResource);
                upload_to_server.setImageDrawable(res);
            } else {
                String uri = "@drawable/ic_upload";
                int imageResource = getResources().getIdentifier(uri, null, getPackageName());
                Drawable res = getResources().getDrawable(imageResource);
                upload_to_server.setImageDrawable(res);
            }
            upload_to_server.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                    if (cm.getActiveNetworkInfo() == null) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setMessage("আপানর ইন্টারনেট সংযোগ On করুন।")
                                .setCancelable(false)
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                    }
                                });
                        AlertDialog alert = builder.create();
                        alert.show();
                    } else {
                        List<EditServiceRow> recvRow = db.getAllDataToSynch();
                        Gson gson = new Gson();
                        String json = gson.toJson(recvRow);

                        int[] recIds = new int[recvRow.size()];

                        int k = 0;
                        for (EditServiceRow ro : recvRow) {
                            recIds[k] = ro.getKEY_ID();
                            k++;
                        }

                        //db.updateAllSynStatus(recIds);
                        //System.out.println("UserId==="+userId);
                        sendDataToSynch(userId, json, recvRow);
                    }
                }
            });

            imgjobcard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent jorori_intent = new Intent(MainActivity.this, SelectProduct.class);
                    jorori_intent.putExtra("Edit", "0");
                    startActivity(jorori_intent);
                }
            });

            imgjobcardview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent jorori_intent = new Intent(MainActivity.this, ViewAllServices.class);
                    startActivity(jorori_intent);
                }
            });

            imgserviceperformance.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent jorori_intent = new Intent(MainActivity.this, ServicePerformance.class);
                    bundle.putString("UserId",userId);
                    //jorori_intent.putExtra("UserId", userId);
                    jorori_intent.putExtras(bundle);
                    startActivity(jorori_intent);
                    finish();
                }
            });

            imglogout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SharedPreferences preferences = getSharedPreferences("MotorService", 0);
                    preferences.edit().remove("UserId").commit();
                    Intent jorori_intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(jorori_intent);
                    finish();
                }
            });
        }
    }

    private void sendDataToSynch(String userId, String dataJson, final List<EditServiceRow> serviceRowList) {
        String tag_string_req = "req_login";

        pDialog.setMessage("Sending to server ...");
        showDialog();
        RequestQueue queue = Volley.newRequestQueue(this);
        List<Map<String, String>> listMap = new ArrayList<Map<String, String>>();

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("UserId", userId);
        params.put("Data", dataJson.toString());

        String url = url_all_kiosk;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST, url, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        int[] recIds = new int[serviceRowList.size()];
                        System.out.println("==============="+jsonObject.toString());

                        int k = 0;
                        for (EditServiceRow ro : serviceRowList) {
                            recIds[k] = ro.getKEY_ID();
                            k++;
                        }

                        db.updateAllSynStatus(recIds);

                        String uri = "@drawable/ic_upload";
                        int imageResource = getResources().getIdentifier(uri, null, getPackageName());
                        Drawable res = getResources().getDrawable(imageResource);
                        upload_to_server.setImageDrawable(res);
                        System.out.println("upload_to_server");
                        hideDialog();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                //Log.d(Test001,volleyError.toString());
                //System.out.println("this is a test");
            }
        }
        );
        // Adding request to request queue
        //AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
        jsonObjectRequest.setShouldCache(false);
        AppController.getInstance().addToRequestQueue(jsonObjectRequest);
    }

    private void downloadCustomerData(final String userId) {
        String tag_string_req = "req_download_data";
        pDialog.setMessage("Sync App Data");
        showDialog();
        RequestQueue queue = Volley.newRequestQueue(this);
        List<Map<String, String>> listMap = new ArrayList<Map<String, String>>();

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("UserId", userId);

        String url = url_download_customer;
        StringRequest strReq = new StringRequest(Request.Method.POST,
                url_download_customer, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jObj = new JSONObject(response);
                    String status = jObj.getString("StatusCode");
                    String StatusMessage = jObj.getString("StatusMessage");
                    //Log.d("StatusMessage-->",StatusMessage);
                    //System.out.println("=====StatusMessage:" + StatusMessage);
                    JSONArray statusMessageArray = new JSONArray(StatusMessage);
                    List<SynchDataRow> myCustomerList = new ArrayList<SynchDataRow>();
                    db = new DatabaseHelper(getApplicationContext());
                    db.getWritableDatabase();
                    for (int i = 0; i < statusMessageArray.length(); i++) {

                        String DateOfInstallation = statusMessageArray.getJSONObject(i).getString("DateOfInstallation");
                        //String UserId_id = statusMessageArray.getJSONObject(i).getString("UserId_id");
                        String VisitDate = statusMessageArray.getJSONObject(i).getString("VisitDate");
                        String ServiceIncome = String.valueOf(statusMessageArray.getJSONObject(i).getInt("ServiceIncome"));
                        String ServiceEndDate = statusMessageArray.getJSONObject(i).getString("ServiceEndDate");

                        String ServerUpdateDateTime = statusMessageArray.getJSONObject(i).getString("ServerUpdateDateTime");
                        String TractorPurchaseDate = statusMessageArray.getJSONObject(i).getString("TractorPurchaseDate");
                        String HoursProvided = String.valueOf(statusMessageArray.getJSONObject(i).getInt("HoursProvided"));
                        String ProductId_id = String.valueOf(statusMessageArray.getJSONObject(i).getInt("ProductId_id"));

                        String CategoryId_id = String.valueOf(statusMessageArray.getJSONObject(i).getInt("CategoryId_id"));
                        String CustomerName = statusMessageArray.getJSONObject(i).getString("CustomerName");
                        String MobileLogCount = String.valueOf(statusMessageArray.getJSONObject(i).getInt("MobileLogCount"));
                        String ServiceStartDate = statusMessageArray.getJSONObject(i).getString("ServiceStartDate");

                        String MobileCreatedDT = statusMessageArray.getJSONObject(i).getString("MobileCreatedDT");
                        String MobileEditedDT = statusMessageArray.getJSONObject(i).getString("MobileEditedDT");
                        String MobileId = statusMessageArray.getJSONObject(i).getString("MobileId");
                        String ServerInsertDateTime = statusMessageArray.getJSONObject(i).getString("ServerInsertDateTime");

                        //String ServiceDetailsId = statusMessageArray.getJSONObject(i).getString("ServiceDetailsId");
                        String CallTypeId_id = String.valueOf(statusMessageArray.getJSONObject(i).getInt("CallTypeId_id"));
                        String ServiceDemandDate = statusMessageArray.getJSONObject(i).getString("ServiceDemandDate");
                        String Mobile = statusMessageArray.getJSONObject(i).getString("Mobile");


                        SynchDataRow myC = new SynchDataRow(DateOfInstallation,
                                VisitDate,
                                ServiceIncome,
                                ServiceEndDate,
                                ServerUpdateDateTime,
                                TractorPurchaseDate,
                                HoursProvided,
                                ProductId_id,
                                CategoryId_id,
                                CustomerName,
                                MobileLogCount,
                                ServiceStartDate,
                                MobileCreatedDT,
                                MobileEditedDT,
                                MobileId,
                                ServerInsertDateTime,
                                CallTypeId_id,
                                ServiceDemandDate,
                                Mobile);
                        myCustomerList.add(myC);
                    }

                    db.syncSaveAllData(myCustomerList);
                    //System.out.println("kallul Inserted the data-->" + status);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                System.out.println("status--200->" + response.trim());
                hideDialog();
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("UserId", userId);

                return params;
            }

        };
        strReq.setShouldCache(false);
        AppController.getInstance().addToRequestQueue(strReq);
    }


    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }
    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
    private void toastMessage(String message){
        Toast.makeText(this, message,Toast.LENGTH_SHORT).show();
    }

}
