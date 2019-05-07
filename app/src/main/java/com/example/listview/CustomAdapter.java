package com.example.listview;

import android.content.Context;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;


public class CustomAdapter extends BaseAdapter {
    private String TAG = "CustomAdapter_LV5";
    private LayoutInflater inflater;
    private Activity_ListView context;
    private List<BikeData> myList;
    private Activity_ListView myActivity;
    final int ALPHA = 0;
    final int BETA = 1;
    final int CHARLIE = 2;
    final int DELTA = 3;
    public CustomAdapter(Activity_ListView context, List<BikeData> myList) {
        this.context = context;
        this.myList = myList;
    }

    @Override
    public int getCount() {
        // TODO Leave this as 0 and you will have nothing in your list
        return myList.size();
    }

    @Override
    public Object getItem(int arg0) {
        return arg0;
    }

    @Override
    public long getItemId(int position) {
        // do you want the result?
        return (long) position;
    }

    private static class ViewHolder {
        TextView Model;
        TextView Description;
        TextView Price;
        WebImageView_KP Bike;
    }
    public void sortList(int iSort) {
        switch (iSort) {
            case ALPHA:
                Collections.sort(myList, new BikeDataCompanyUPComparator());
                break;
            case BETA:
                Collections.sort(myList,new BikeDataLocationUPComparator());
                break;
            case CHARLIE:
                Collections.sort(myList, new BikeDataPriceUPComparator());
                break;
            case DELTA:
                Collections.sort(myList, new BikeDataModelUPComparator());
                break;
        }
        notifyDataSetChanged();
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder myVh;

        if (convertView == null) {
            LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = vi.inflate(R.layout.listview_row_layout, null);

            myVh = new ViewHolder();
            myVh.Model = (TextView) convertView.findViewById(R.id.Model);
            myVh.Description = (TextView) convertView.findViewById(R.id.Description);
            myVh.Price = (TextView) convertView.findViewById(R.id.Price);
            myVh.Bike = (WebImageView_KP) convertView.findViewById(R.id.imageView1) ;
            myVh.Bike.setImageUrl(context.getUrl()+myList.get(position).Picture);
            convertView.setTag(myVh);
        } else {
            myVh = (ViewHolder) convertView.getTag();
        }
            myVh.Model.setText(myList.get(position).Model);
            myVh.Price.setText("$"+myList.get(position).Price.toString());
            myVh.Description.setText(myList.get(position).Description);
        Log.d(TAG, "Launching " + Integer.toString(position));

        return convertView;
    }
}

