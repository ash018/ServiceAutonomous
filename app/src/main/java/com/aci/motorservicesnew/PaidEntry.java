package com.aci.motorservicesnew;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TimePicker;

import com.aci.utils.EditServiceRow;

import java.util.Calendar;

public class PaidEntry extends AppCompatActivity {
    private static ImageView mainmenuid;
    private String serviceProduct = "0", serviceCall = "0", serviceType = "0";
    private EditText instcustomername, instmobilenumber,  insthoureofbuy, instdateofbuy, instdateofinstallation, instdateofendofservice, inserviceincome;
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
        setContentView(R.layout.activity_paid_entry);
        db = new DatabaseHelper(getApplicationContext());
        db.getWritableDatabase();

        Intent srvTypeIntent = getIntent();
        serviceProduct = srvTypeIntent.getStringExtra("ServiceProduct");
        serviceCall = srvTypeIntent.getStringExtra("ServiceCallType");
        serviceType = srvTypeIntent.getStringExtra("ServiceType");
        isEdit = srvTypeIntent.getStringExtra("Edit");

        instcustomername = (EditText) findViewById(R.id.instcustomername);
        instmobilenumber = (EditText) findViewById(R.id.instmobilenumber);
        insthoureofbuy = (EditText) findViewById(R.id.insthoureofbuy);

        instdateofbuy = (EditText) findViewById(R.id.instdateofbuy);
        instdateofinstallation = (EditText) findViewById(R.id.instdateofinstallation);
        instdateofendofservice = (EditText) findViewById(R.id.instdateofendofservice);
        inserviceincome = (EditText) findViewById(R.id.inserviceincome);

        mainmenuid = (ImageView) findViewById(R.id.mainmenuid);
        mainmenuid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent nextActivity = new Intent(PaidEntry.this, MainActivity.class);
                startActivity(nextActivity);
                finish();
            }
        });

        if(serviceCall.equals("2")){
            instdateofbuy.setVisibility(View.GONE);
        }



        Log.d("serviceCall",serviceCall);

        if(isEdit.equalsIgnoreCase("1")){
            row = (EditServiceRow) srvTypeIntent.getSerializableExtra("RowData");
            instcustomername.setText(row.getKEY_CUSTOMER_NAME());
            instmobilenumber.setText(row.getKEY_CUSTOMER_MOBILE());
            insthoureofbuy.setText(row.getKEY_RUNNING_HOUER());

            instdateofbuy.setText(row.getKEY_BUYING_DATE());

            instdateofinstallation.setText(row.getKEY_INSTALLAION_DATE());
            instdateofendofservice.setText(row.getKEY_SERVICE_END_DATE());
            inserviceincome.setText(row.getKEY_SERVICE_INCOME());
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

        instdateofendofservice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePicker(instdateofendofservice);
            }
        });

        btnprevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnprevious.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(isEdit.equalsIgnoreCase("1")){
                            Intent nextActivity = new Intent(PaidEntry.this, ServiceType.class);
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
                            Intent previousActivity = new Intent(PaidEntry.this, ServiceType.class);
                            previousActivity.putExtra("ServiceType", serviceType);
                            previousActivity.putExtra("ServiceCallType", String.valueOf(serviceCall));
                            previousActivity.putExtra("ServiceProduct", String.valueOf(serviceProduct));
                            previousActivity.putExtra("Edit", "0");
                            startActivity(previousActivity);
                            finish();
                        }
                    }
                });
            }
        });

        btnnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String customerName = instcustomername.getText().toString();
                String mobile = instmobilenumber.getText().toString();
                String hours = insthoureofbuy.getText().toString();

                String buyingDate = instdateofbuy.getText().toString();
                String installationDate = instdateofinstallation.getText().toString();
                String insServiceEndDate = instdateofendofservice.getText().toString();

                String inservice = inserviceincome.getText().toString();

                if(customerName.equalsIgnoreCase("") ||
                        mobile.equalsIgnoreCase("") ||
                        buyingDate.equalsIgnoreCase("") ||
                        hours.equalsIgnoreCase("") ||
                        installationDate.equalsIgnoreCase("") ||
                        insServiceEndDate.equalsIgnoreCase("") ||
                        inservice.equalsIgnoreCase("")){
                    AlertDialog.Builder builder = new AlertDialog.Builder(PaidEntry.this);
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
                        db.updatePaidEntry(row, serviceProduct, serviceCall,
                                serviceType, customerName, mobile,  hours, buyingDate,
                                installationDate, insServiceEndDate, inservice
                        );
                        Intent nextActivity = new Intent(PaidEntry.this, MainActivity.class);
                        startActivity(nextActivity);
                        finish();
                    }
                    if(isEdit.equalsIgnoreCase("0")){
                        db.addPaidEntry(serviceProduct, serviceCall,
                                serviceType, customerName, mobile,  hours, buyingDate,
                                installationDate, insServiceEndDate, inservice
                        );
                        Intent nextActivity = new Intent(PaidEntry.this, MainActivity.class);
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
