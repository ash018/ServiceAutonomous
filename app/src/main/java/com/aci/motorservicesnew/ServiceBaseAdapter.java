package com.aci.motorservicesnew;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.aci.utils.ServiceRow;

import java.util.ArrayList;

public class ServiceBaseAdapter extends ArrayAdapter<ServiceRow> {

    private ArrayList<ServiceRow> ProductList=new ArrayList<>();
    private int cposition = 0;

    public ServiceBaseAdapter(Context context, int resource,
                              ArrayList<ServiceRow> ProductList) {
        super(context, resource, ProductList);
        this.ProductList.addAll(ProductList);
    }

    static class ViewHolder {
        TextView textSL;
        TextView textCustomerName;
        TextView textCustomerMobile;
        TextView textCreateDate;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        cposition = position;
        View view = null;

        if (convertView == null) {
            LayoutInflater vi = (LayoutInflater) getContext().getSystemService(
                    Context.LAYOUT_INFLATER_SERVICE);
            view = vi.inflate(R.layout.view_services_row, null);

            final ViewHolder holder = new ViewHolder();
            holder.textSL = (TextView) view.findViewById(R.id.codeSL);
            holder.textCustomerName = (TextView) view.findViewById(R.id.codeTV);
            holder.textCustomerMobile = (TextView) view.findViewById(R.id.amountTV);
            holder.textCreateDate = (TextView) view.findViewById(R.id.dateTV);

            holder.textSL.setTag(ProductList.get(position));
            view.setTag(holder);
        } else {
            view = convertView;
            ((ViewHolder) view.getTag()).textSL.setTag(ProductList
                    .get(position));
        }
        ViewHolder holder = (ViewHolder) view.getTag();
        ServiceRow prod = (ServiceRow) holder.textSL.getTag();

        if(prod.getIsSynch().equalsIgnoreCase("Y") ){
            //view.setBackgroundColor(0xFFFF0000);
            view.setBackgroundColor(Color.parseColor("#00e5ff"));
        }

        if(prod.getIsSynch().equalsIgnoreCase("N")){
            view.setBackgroundColor(Color.parseColor("#ffcf00"));
        }

        holder.textSL.setText(String.valueOf(prod.getSl()));
        holder.textCustomerName.setText(prod.getCustomerName());
        holder.textCustomerMobile.setText(prod.getCustomerMobile());

        holder.textCreateDate.setText(prod.getEntyDate().split(" ")[0].split("-")[2]+"/"+prod.getEntyDate().split(" ")[0].split("-")[1]+"/"+prod.getEntyDate().split(" ")[0].split("-")[0]);

        return view;
    }

}