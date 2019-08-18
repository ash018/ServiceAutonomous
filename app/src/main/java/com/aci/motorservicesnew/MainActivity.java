package com.aci.motorservicesnew;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.Toolbar;

public class MainActivity extends AppCompatActivity {
    private ImageView imgjobcard;
    private DatabaseHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = new DatabaseHelper(getApplicationContext());
        db.getWritableDatabase();

        imgjobcard = (ImageView) findViewById(R.id.imgjobcard);
        imgjobcard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent jorori_intent = new Intent(MainActivity.this, SelectProduct.class);
                //Toast.makeText(MainActivity.this, territory, Toast.LENGTH_LONG).show();
                //jorori_intent.putExtra("TTYCODE11", territory);
                startActivity(jorori_intent);
                //finish();
            }
        });

    }
}
