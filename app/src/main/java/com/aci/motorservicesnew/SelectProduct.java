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

public class SelectProduct extends AppCompatActivity {
    private static RadioButton rdotractor, rdopowertiller, rdortransplanter, rdoharvester,rdoriper, rdodiselengine, rdoothers;
    private static RadioGroup radiobtngroupselectproduct;

    private static Button btnprevious, btnnext;

    private int rdBtnVal = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_product);
        rdBtnVal = 0;

        radiobtngroupselectproduct = (RadioGroup) findViewById(R.id.radiobtngroupselectproduct);

        rdotractor = (RadioButton) findViewById(R.id.rdotractor);
        rdopowertiller = (RadioButton) findViewById(R.id.rdopowertiller);
        rdortransplanter = (RadioButton) findViewById(R.id.rdortransplanter);
        rdoharvester = (RadioButton) findViewById(R.id.rdoharvester);
        rdoriper = (RadioButton) findViewById(R.id.rdoriper);
        rdodiselengine = (RadioButton) findViewById(R.id.rdodiselengine);
        rdoothers = (RadioButton) findViewById(R.id.rdoothers);

        btnprevious = (Button) findViewById(R.id.btnprevious);
        btnnext = (Button) findViewById(R.id.btnnext);


        radiobtngroupselectproduct.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch(i){
                    case(R.id.rdotractor):
                        rdBtnVal = 1;
                        break;
                    case(R.id.rdopowertiller):
                        rdBtnVal = 2;
                        break;
                    case(R.id.rdortransplanter):
                        rdBtnVal = 3;
                        break;
                    case(R.id.rdoharvester):
                        rdBtnVal = 4;
                        break;
                    case(R.id.rdoriper):
                        rdBtnVal = 5;
                        break;
                    case(R.id.rdodiselengine):
                        rdBtnVal = 6;
                        break;
                    case(R.id.rdoothers):
                        rdBtnVal = 7;
                        break;
                }
            }
        });


        btnprevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent previousActivity = new Intent(SelectProduct.this, MainActivity.class);
                startActivity(previousActivity);
            }
        });

        btnnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(rdBtnVal == 0){
                    AlertDialog.Builder builder = new AlertDialog.Builder(SelectProduct.this);
                    builder.setMessage("দয়া করে একটি অপশন সিলেক্ট করন")
                            .setCancelable(false)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                }
                            });
                    AlertDialog alert = builder.create();
                    alert.show();

                }
                else{
                    System.out.println("===ServiceProduct11===="+rdBtnVal);
                    Intent nextActivity = new Intent(SelectProduct.this, ServiceCall.class);
                    nextActivity.putExtra("ServiceProduct", String.valueOf(rdBtnVal));
                    startActivity(nextActivity);
                    //finish();
                }

            }
        });



    }
}
