package com.aci.motorservicesnew;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class ServiceCall extends AppCompatActivity {
    private static RadioButton rdotractor, rdopowertiller;
    private static RadioGroup radiobtngroupservicecall;
    private static Button btnprevious, btnnext;
    private int rdBtnVal = 0;
    private String serviceProduct = "0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_call);

        Intent selProIntent = getIntent();
        serviceProduct = selProIntent.getStringExtra("ServiceProduct");

        radiobtngroupservicecall = (RadioGroup) findViewById(R.id.radiobtngroupservicecall);

        rdotractor = (RadioButton) findViewById(R.id.rdotractor);
        rdopowertiller = (RadioButton) findViewById(R.id.rdopowertiller);

        btnprevious = (Button) findViewById(R.id.btnprevious);
        btnnext = (Button) findViewById(R.id.btnnext);

        radiobtngroupservicecall.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch(i){
                    case(R.id.rdotractor):
                        rdBtnVal = 1;
                        break;
                    case(R.id.rdopowertiller):
                        rdBtnVal = 2;
                        break;
                }
            }
        });

        btnprevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent previousActivity = new Intent(ServiceCall.this, MainActivity.class);
                startActivity(previousActivity);
            }
        });

        btnnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(rdBtnVal == 0){
                    AlertDialog.Builder builder = new AlertDialog.Builder(ServiceCall.this);
                    builder.setMessage("দয়া করে একটি অপশন সিলেক্ট করন")
                            .setCancelable(false)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                }
                            });
                    AlertDialog alert = builder.create();
                    alert.show();
                }
                else {
                    Intent nextActivity = new Intent(ServiceCall.this, ServiceType.class);
                    nextActivity.putExtra("ServiceCallType", rdBtnVal);
                    nextActivity.putExtra("ServiceProduct", serviceProduct);
                    startActivity(nextActivity);
                    //finish();
                }
            }
        });
    }
}
