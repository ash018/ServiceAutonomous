package com.aci.motorservicesnew;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.Toolbar;

public class MainActivity extends AppCompatActivity {
    private ImageView imgjobcard, imgjobcardview, imglogout;
    private DatabaseHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = new DatabaseHelper(getApplicationContext());
        db.getWritableDatabase();


        SharedPreferences sp = getSharedPreferences("MotorService", Context.MODE_PRIVATE);
        String userId = sp.getString("UserId", "TestXXXX");

        if(userId.equalsIgnoreCase("TestXXXX")){
            Intent jorori_intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(jorori_intent);
            finish();
        }

        imgjobcard = (ImageView) findViewById(R.id.imgjobcard);
        imgjobcardview = (ImageView) findViewById(R.id.imgjobcardview);
        imglogout = (ImageView) findViewById(R.id.imglogout);

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
}
