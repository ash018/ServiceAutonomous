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
        //inflater = LayoutInflater.from(ctx);
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

        System.out.println("=======XXX=========="+mDataset.get(position).getId());

        holder.codeSL.setText(String.valueOf(mDataset.get(position).getId()));
        holder.codeTV.setText(mDataset.get(position).getCustomerName());
        holder.amountTV.setText(mDataset.get(position).getCustomerMobile());
        holder.dateTV.setText(mDataset.get(position).getEntyDate());

        ///ProjectionRow dModel = (ProjectionRow) holder.codeTV.getTag();
        return rowView;
    }

    static class ViewHolder {
        TextView codeSL;
        TextView codeTV;
        TextView amountTV;
        TextView dateTV;
    }


    /*@Override
    public ServiceAdaptor.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_services_row, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }
    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        System.out.println("Dhorci colorstatus######-->" + mDataset.get(position).getCustomerMobile() + "==="+mDataset.get(position).getCustomerName() );
        //Log.d("ColorCode", String.valueOf(mDataset.get(position).getServiceColor()));
        *//*if(mDataset.get(position).getIsSynch().equalsIgnoreCase("Y")){
            holder.listView.setBackgroundColor(0xFFFF0000);
        }

        if(mDataset.get(position).getIsSynch().equalsIgnoreCase("N")){
            holder.listView.setBackgroundColor(Color.parseColor("#ffcf00"));
        }*//*


        //holder.lv.setBackgroundColor(Integer.parseInt(mDataset.get(position).getServiceColor()));
        holder.textSL.setText(String.valueOf(mDataset.get(position).getId()));
        holder.textCompany.setText(mDataset.get(position).getCustomerName());
        holder.textContact.setText(mDataset.get(position).getCustomerMobile());
        holder.textDateTime.setText(mDataset.get(position).getEntyDate());

    }
    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textCompany,textContact,textSL,textDateTime;
        public ImageView imageView;

        public RecyclerView lv;
        View listView;

        public ViewHolder(View v) {
            super(v);
            lv = (RecyclerView) v.findViewById(R.id.main_list);
            textSL = (TextView) v.findViewById(R.id.codeSL);
            textCompany = (TextView) v.findViewById(R.id.codeTV);
            textContact = (TextView) v.findViewById(R.id.amountTV);
            textDateTime = (TextView) v.findViewById(R.id.dateTV);

            listView = v;
            // imageView = (ImageView) v.findViewById(R.id.icon);

        }
    }*/

}
