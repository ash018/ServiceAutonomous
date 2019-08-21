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
import android.widget.TextView;
import android.widget.TimePicker;

import com.aci.utils.EditServiceRow;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class InstallationEntry extends AppCompatActivity {
    private String serviceProduct = "0", serviceCall = "0", serviceType = "0";
    private EditText instcustomername, instmobilenumber, instdateofbuy, insthoureofbuy, instdateofinstallation;
    private Button btnprevious, btnnext;
    DatePickerDialog datePickerDialog;
    private DatabaseHelper db;
    private int mYear;
    private int mMonth;
    private int mDay;

    private int mHour;
    private int mMinute;
    private String date_time = "";

    EditServiceRow row = null;
    private String isEdit = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_installation_entry);
        db = new DatabaseHelper(getApplicationContext());
        db.getWritableDatabase();

        Intent srvTypeIntent = getIntent();
        serviceProduct = srvTypeIntent.getStringExtra("ServiceProduct");
        serviceCall = srvTypeIntent.getStringExtra("ServiceCallType");
        serviceType = srvTypeIntent.getStringExtra("ServiceType");

        isEdit = srvTypeIntent.getStringExtra("Edit");

        instcustomername = (EditText) findViewById(R.id.instcustomername);
        instmobilenumber = (EditText) findViewById(R.id.instmobilenumber);
        instdateofbuy = (EditText) findViewById(R.id.instdateofbuy);
        insthoureofbuy = (EditText) findViewById(R.id.insthoureofbuy);
        instdateofinstallation = (EditText) findViewById(R.id.instdateofinstallation);

        if(isEdit.equalsIgnoreCase("1")){
            row = (EditServiceRow) srvTypeIntent.getSerializableExtra("RowData");
            instcustomername.setText(row.getKEY_CUSTOMER_NAME());
            instmobilenumber.setText(row.getKEY_CUSTOMER_MOBILE());
            instdateofbuy.setText(row.getKEY_BUYING_DATE());
            insthoureofbuy.setText(row.getKEY_RUNNING_HOUER());
            instdateofinstallation.setText(row.getKEY_INSTALLAION_DATE());
        }


        btnprevious = (Button) findViewById(R.id.btnprevious);
        btnnext = (Button) findViewById(R.id.btnnext);

        instdateofbuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePicker(instdateofbuy);
            }
        });

        instdateofinstallation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePicker(instdateofinstallation);
            }
        });


        btnprevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isEdit.equalsIgnoreCase("1")){
                    Intent nextActivity = new Intent(InstallationEntry.this, ServiceType.class);
                    nextActivity.putExtra("RowData", row);
                    nextActivity.putExtra("Edit", "1");
                    nextActivity.putExtra("IsPrevious", "1");
                    nextActivity.putExtra("ServiceType", serviceType);
                    nextActivity.putExtra("ServiceCallType", String.valueOf(serviceCall));
                    nextActivity.putExtra("ServiceProduct", String.valueOf(serviceProduct));
                    startActivity(nextActivity);
                    finish();
                }
                if(isEdit.equalsIgnoreCase("0")){
                    Intent previousActivity = new Intent(InstallationEntry.this, ServiceType.class);
                    previousActivity.putExtra("ServiceType", serviceType);
                    previousActivity.putExtra("ServiceCallType", String.valueOf(serviceCall));
                    previousActivity.putExtra("ServiceProduct", String.valueOf(serviceProduct));
                    previousActivity.putExtra("Edit", "0");
                    startActivity(previousActivity);
                    finish();
                }
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
                //System.out.println("customerName==="+customerName+"==mobile=="+mobile+"==buyingDate=="+buyingDate);
                if(customerName.equalsIgnoreCase("") ||
                        mobile.equalsIgnoreCase("") ||
                        buyingDate.equalsIgnoreCase("") ||
                        hours.equalsIgnoreCase("") ||
                        installationDate.equalsIgnoreCase("")){
                    AlertDialog.Builder builder = new AlertDialog.Builder(InstallationEntry.this);
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
                    if(isEdit.equalsIgnoreCase("1")){
                        db.updateInstallationService(row, serviceProduct, serviceCall,
                                serviceType, customerName, mobile, buyingDate, hours, installationDate
                        );
                        Intent nextActivity = new Intent(InstallationEntry.this, MainActivity.class);
                        startActivity(nextActivity);
                        finish();
                    }
                    if(isEdit.equalsIgnoreCase("0")){
                        db.addInstallationService(serviceProduct, serviceCall,
                                serviceType, customerName, mobile, buyingDate+":00", hours, installationDate+":00"
                        );
                        Intent nextActivity = new Intent(InstallationEntry.this, MainActivity.class);
                        startActivity(nextActivity);
                        finish();
                    }
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
