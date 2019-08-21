package com.aci.motorservicesnew;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.aci.utils.EditServiceRow;
import com.aci.utils.ServiceRow;

import java.util.ArrayList;

public class ViewAllServices extends AppCompatActivity {
    private ListView mRecyclerView;
    //private RecyclerView.Adapter mAdapter;
    private ArrayAdapter<ServiceRow> mAdapter;

    private RecyclerView.LayoutManager mLayoutManager;
    private DatabaseHelper db;
    ArrayList<ServiceRow> myDataset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_services);
        mRecyclerView = (ListView) findViewById(R.id.main_list);
        myDataset = new ArrayList<ServiceRow>();

        db = new DatabaseHelper(getApplicationContext());
        db.getWritableDatabase();
        myDataset = db.getDataForAdaptor();

        ServiceBaseAdapter sbAdapter = new ServiceBaseAdapter(this, R.layout.view_services_row, myDataset);


        //mAdapter = new ServiceAdaptor(this, myDataset);
        mRecyclerView.setAdapter(sbAdapter);
        mRecyclerView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapter, View v, int position, long arg3){
                ServiceRow value = (ServiceRow) adapter.getItemAtPosition(position);
                //System.out.println("======" + value.getId());

                Intent nextActivity = new Intent(ViewAllServices.this, SelectProduct.class);
                EditServiceRow row = db.getRowById(value.getId());

                nextActivity.putExtra("RowData", row);
                nextActivity.putExtra("Edit", "1");
                startActivity(nextActivity);

            }
        });


    }
}
