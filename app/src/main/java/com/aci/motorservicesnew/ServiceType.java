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

public class ServiceType extends AppCompatActivity {

    private static RadioButton rdoinstallation, rdoperiodical, rdowarranty, rdopaid, rdopostwcvisit;
    private static RadioGroup radiobtngroup;
    private static Button btnprevious, btnnext;
    private int rdBtnVal = 0;
    private String serviceProduct = "0", serviceCall = "0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_type);
        rdBtnVal = 0;

        Intent srvTypeIntent = getIntent();
        serviceProduct = srvTypeIntent.getStringExtra("ServiceProduct");
        serviceCall = srvTypeIntent.getStringExtra("ServiceCallType");

        radiobtngroup = (RadioGroup) findViewById(R.id.radiobtngroup);

        rdoinstallation = (RadioButton) findViewById(R.id.rdoinstallation);
        rdoperiodical = (RadioButton) findViewById(R.id.rdoperiodical);
        rdowarranty = (RadioButton) findViewById(R.id.rdowarranty);
        rdopaid = (RadioButton) findViewById(R.id.rdopaid);
        rdopostwcvisit = (RadioButton) findViewById(R.id.rdopostwcvisit);

        btnprevious = (Button) findViewById(R.id.btnprevious);
        btnnext = (Button) findViewById(R.id.btnnext);

        radiobtngroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch(i){
                    case(R.id.rdoinstallation):
                        rdBtnVal = 1;
                        break;
                    case(R.id.rdoperiodical):
                        rdBtnVal = 2;
                        break;
                    case(R.id.rdowarranty):
                        rdBtnVal = 3;
                        break;
                    case(R.id.rdopaid):
                        rdBtnVal = 4;
                        break;
                    case(R.id.rdopostwcvisit):
                        rdBtnVal = 5;
                        break;
                }
            }
        });

        btnnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(rdBtnVal == 0){
                    AlertDialog.Builder builder = new AlertDialog.Builder(ServiceType.this);
                    builder.setMessage("দয়া করে একটি অপশন সিলেক্ট করন")
                            .setCancelable(false)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    //do things
                                }
                            });
                    AlertDialog alert = builder.create();
                    alert.show();
                }
                else{
                    if(rdBtnVal == 1){
                        Intent nextActivity = new Intent(ServiceType.this, InstallationEntry.class);
                        nextActivity.putExtra("ServiceType",String.valueOf(rdBtnVal) );
                        nextActivity.putExtra("ServiceCallType", String.valueOf(serviceCall));
                        nextActivity.putExtra("ServiceProduct", String.valueOf(serviceProduct));
                        startActivity(nextActivity);
                    }
                    if(rdBtnVal == 2){
                        Intent nextActivity = new Intent(ServiceType.this, PeriodicalEntry.class);
                        nextActivity.putExtra("ServiceType",String.valueOf(rdBtnVal) );
                        nextActivity.putExtra("ServiceCallType", String.valueOf(serviceCall));
                        nextActivity.putExtra("ServiceProduct", String.valueOf(serviceProduct));
                        startActivity(nextActivity);
                    }

                    if(rdBtnVal == 3){
                        Intent nextActivity = new Intent(ServiceType.this, WarrentyEntry.class);
                        nextActivity.putExtra("ServiceType", String.valueOf(rdBtnVal));
                        nextActivity.putExtra("ServiceCallType", String.valueOf(serviceCall));
                        nextActivity.putExtra("ServiceProduct", String.valueOf(serviceProduct));
                        startActivity(nextActivity);
                    }

                    if(rdBtnVal == 4){
                        Intent nextActivity = new Intent(ServiceType.this, PaidEntry.class);
                        nextActivity.putExtra("ServiceType", String.valueOf(rdBtnVal));
                        nextActivity.putExtra("ServiceCallType", String.valueOf(serviceCall));
                        nextActivity.putExtra("ServiceProduct", String.valueOf(serviceProduct));
                        startActivity(nextActivity);
                    }

                    if(rdBtnVal == 5){
                        Intent nextActivity = new Intent(ServiceType.this, PostwarrentyEntry.class);
                        nextActivity.putExtra("ServiceType", String.valueOf(rdBtnVal));
                        nextActivity.putExtra("ServiceCallType", String.valueOf(serviceCall));
                        nextActivity.putExtra("ServiceProduct", String.valueOf(serviceProduct));
                        startActivity(nextActivity);
                    }
                }
            }
        });
    }
}
