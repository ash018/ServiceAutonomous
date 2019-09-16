package com.aci.motorservicesnew;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;

public class ServicePerformance extends AppCompatActivity {

    private static RadioGroup radiobtngroupservicecall;
    private static Button btnprevious, btnnext;

    private int rdBtnVal = 0;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_performance);
        Intent selProIntent = getIntent();
        userId = selProIntent.getStringExtra("UserId");

        radiobtngroupservicecall = (RadioGroup) findViewById(R.id.radiobtngroupservicecall);

        radiobtngroupservicecall.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch(i){
                    case(R.id.serviceRatio):
                        rdBtnVal = 1;
                        break;
                    case(R.id.customerSatisfaction):
                        rdBtnVal = 2;
                        break;

                    case(R.id.sixHourService):
                        rdBtnVal = 3;
                        break;
                }
            }
        });

        btnprevious = (Button) findViewById(R.id.btnprevious);
        btnnext = (Button) findViewById(R.id.btnnext);


        btnnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(rdBtnVal == 0){
                    AlertDialog.Builder builder = new AlertDialog.Builder(ServicePerformance.this);
                    builder.setMessage("দয়া করে একটি অপশন সিলেক্ট করন")
                            .setCancelable(false)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                }
                            });
                    AlertDialog alert = builder.create();
                    alert.show();
                }
                if(rdBtnVal == 1) {
                    Intent nextActivity = new Intent(ServicePerformance.this, ServiceRatio.class);
                    nextActivity.putExtra("UserId", userId);
                    startActivity(nextActivity);
                }

                if(rdBtnVal == 2){
                    Intent nextActivity = new Intent(ServicePerformance.this, CustomerSatisfaction.class);
                    nextActivity.putExtra("UserId", userId);
                    startActivity(nextActivity);
                }

                if(rdBtnVal == 3){
                    Intent nextActivity = new Intent(ServicePerformance.this, SixHourActivity.class);
                    nextActivity.putExtra("UserId", userId);
                    startActivity(nextActivity);
                }
            }
        });
    }
}
