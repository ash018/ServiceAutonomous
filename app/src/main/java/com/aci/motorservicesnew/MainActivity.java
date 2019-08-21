package com.aci.motorservicesnew;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.aci.utils.EditServiceRow;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private String url_all_kiosk = "";
    private ImageView imgjobcard, imgjobcardview, imglogout, upload_to_server;
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


        SharedPreferences sp = getSharedPreferences("MotorService", Context.MODE_PRIVATE);
        userId = sp.getString("UserId", "TestXXXX");

        if(userId.equalsIgnoreCase("TestXXXX")){
            Intent jorori_intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(jorori_intent);
            finish();
        }

        imgjobcard = (ImageView) findViewById(R.id.imgjobcard);
        imgjobcardview = (ImageView) findViewById(R.id.imgjobcardview);
        imglogout = (ImageView) findViewById(R.id.imglogout);
        upload_to_server = (ImageView) findViewById(R.id.upload_to_server);

        upload_to_server.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<EditServiceRow> recvRow = db.getAllDataToSynch();
                Gson gson = new Gson();
                String json = gson.toJson(recvRow);
                //System.out.println("======"+ json);

                int[] recIds = new int[recvRow.size()];

                int k = 0;
                for (EditServiceRow ro : recvRow) {
                    recIds[k] = ro.getKEY_ID();
                    k++;
                }

                db.updateAllSynStatus(recIds);
                //sendDataToSynch(userId, json,recvRow);
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

    private void sendDataToSynch(String userId, String dataJson, final List<EditServiceRow> serviceRowList
                           /*String captureJson, String releaseJson,
                           final List<RecoveryRow> recvRObjList, final List<ProjectionRow> projectObjList,
                           final List<CaptureRow> captureObjList, final List<ReleaseRow> releaseObjList*/) {
        String tag_string_req = "req_login";

        pDialog.setMessage("Sending to server ...");
        showDialog();
        RequestQueue queue = Volley.newRequestQueue(this);
        List<Map<String, String>> listMap = new ArrayList<Map<String, String>>();

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("UserId", userId.toString());
        params.put("Data", dataJson.toString());


        String url = url_all_kiosk;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST, url, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        int[] recIds = new int[serviceRowList.size()];

                        int k = 0;
                        for (EditServiceRow ro : serviceRowList) {
                            recIds[k] = ro.getKEY_ID();
                            k++;
                        }


                        //db.updateAllSynStatus(recIds, prjIds, capIds, relIds);
                        hideDialog();
                        System.out.println("---this is a test-->" + jsonObject.toString());
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
