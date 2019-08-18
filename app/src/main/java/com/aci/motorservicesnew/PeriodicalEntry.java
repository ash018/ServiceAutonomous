package com.aci.motorservicesnew;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import java.util.Calendar;

public class PeriodicalEntry extends AppCompatActivity {

    private String serviceProduct = "0", serviceCall = "0", serviceType = "0";
    private EditText instcustomername, instmobilenumber,  insthoureofbuy, instdateofbuy, instdateofinstallation, instdateofendofservice;
    private Button btnprevious, btnnext;
    DatePickerDialog datePickerDialog;
    private DatabaseHelper db;
    private int mYear;
    private int mMonth;
    private int mDay;

    private int mHour;
    private int mMinute;
    private String date_time = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_periodical_entry);
        db = new DatabaseHelper(getApplicationContext());
        db.getWritableDatabase();

        Intent srvTypeIntent = getIntent();
        serviceProduct = srvTypeIntent.getStringExtra("ServiceProduct");
        serviceCall = srvTypeIntent.getStringExtra("ServiceCallType");
        serviceType = srvTypeIntent.getStringExtra("ServiceType");

        instcustomername = (EditText) findViewById(R.id.instcustomername);
        instmobilenumber = (EditText) findViewById(R.id.instmobilenumber);
        insthoureofbuy = (EditText) findViewById(R.id.insthoureofbuy);

        instdateofbuy = (EditText) findViewById(R.id.instdateofbuy);
        instdateofinstallation = (EditText) findViewById(R.id.instdateofinstallation);
        instdateofendofservice = (EditText) findViewById(R.id.instdateofendofservice);

        btnprevious = (Button) findViewById(R.id.btnprevious);
        btnnext = (Button) findViewById(R.id.btnnext);

        insthoureofbuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePicker(insthoureofbuy);
            }
        });

        instdateofinstallation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePicker(instdateofinstallation);
            }
        });

        instdateofendofservice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePicker(instdateofendofservice);
            }
        });


        btnprevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent previousActivity = new Intent(PeriodicalEntry.this, SelectProduct.class);
                startActivity(previousActivity);
            }
        });

        btnnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String customerName = instcustomername.getText().toString();
                String mobile = instmobilenumber.getText().toString();
                String buyingDate = instdateofbuy.getText().toString();
                String hours = insthoureofbuy.getText().toString();
                String installationDate = instdateofinstallation.getText().toString();
                String insServiceEndDate = instdateofendofservice.getText().toString();

                //System.out.println("customerName==="+customerName+"==mobile=="+mobile+"==buyingDate=="+buyingDate);
                if(customerName.equalsIgnoreCase("") ||
                        mobile.equalsIgnoreCase("") ||
                        buyingDate.equalsIgnoreCase("") ||
                        hours.equalsIgnoreCase("") ||
                        installationDate.equalsIgnoreCase("") ||
                        insServiceEndDate.equalsIgnoreCase("")){
                    AlertDialog.Builder builder = new AlertDialog.Builder(PeriodicalEntry.this);
                    builder.setMessage("দয়া করে সবকটি Field পূরণ করুন ।")
                            .setCancelable(false)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                }
                            });
                    AlertDialog alert = builder.create();
                    alert.show();

                }
                else{
                    db.addPerodicEntryService(serviceProduct, serviceCall,
                            serviceType, customerName, mobile, buyingDate+":00", hours,
                            installationDate+":00", insServiceEndDate+":00"
                    );
                    Intent nextActivity = new Intent(PeriodicalEntry.this, MainActivity.class);
                    startActivity(nextActivity);
                    finish();
                }
            }
        });

    }


    private void datePicker(final EditText setTimeView) {
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        date_time = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
                        tiemPicker(setTimeView);
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    private void tiemPicker(final EditText seTimeView) {
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        mHour = hourOfDay;
                        mMinute = minute;
                        seTimeView.setText(date_time + " " + hourOfDay + ":" + minute);
                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();
    }


}
