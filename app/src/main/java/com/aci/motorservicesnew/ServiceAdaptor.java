package com.aci.motorservicesnew;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.aci.utils.ServiceRow;
import java.util.List;

public class ServiceAdaptor extends ArrayAdapter<ServiceRow>{
    private List<ServiceRow> mDataset;
    private  Context context;
    public ServiceAdaptor(Context context, List<ServiceRow> myDataset) {
        super(context, R.layout.view_services_row, myDataset);
        this.context = context;
        this.mDataset = myDataset;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = null;
        final ViewGroup p;
        p = parent;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(R.layout.view_services_row, parent,
                    false);
            final ViewHolder holder = new ViewHolder();

            holder.codeSL = (TextView) rowView.findViewById(R.id.codeSL);
            holder.codeTV = (TextView) rowView.findViewById(R.id.codeTV);
            holder.amountTV = (TextView) rowView.findViewById(R.id.amountTV);
            holder.dateTV = (TextView) rowView.findViewById(R.id.dateTV);


            holder.codeTV.setTag(mDataset.get(position));


            rowView.setTag(holder);
        } else {
            rowView = convertView;

            ((ViewHolder) rowView.getTag()).codeSL.setTag(mDataset
                    .get(position));
            ((ViewHolder) rowView.getTag()).codeSL.setTag(position);

        }

        ViewHolder holder = (ViewHolder) rowView.getTag();
        holder.codeSL.setTag(position);

        holder.codeSL.setText(String.valueOf(mDataset.get(position).getId()));
        holder.codeTV.setText(mDataset.get(position).getCustomerName());
        holder.amountTV.setText(mDataset.get(position).getCustomerMobile());
        holder.dateTV.setText(mDataset.get(position).getEntyDate());

        return rowView;
    }

    static class ViewHolder {
        TextView codeSL;
        TextView codeTV;
        TextView amountTV;
        TextView dateTV;
    }
}
