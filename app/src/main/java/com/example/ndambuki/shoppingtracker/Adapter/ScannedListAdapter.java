package com.example.ndambuki.shoppingtracker.Adapter;

import android.app.Activity;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.ndambuki.shoppingtracker.Database.ColumnsContract;
import com.example.ndambuki.shoppingtracker.R;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Ndambuki on 7/8/18.
 */

public class ScannedListAdapter extends BaseAdapter{

    //private SQLiteDatabase db;
    Context mContext;
    ArrayList<HashMap<String,String>> data;//modify here


    public ScannedListAdapter(Context context, ArrayList<HashMap<String,String>> data) //modify here
    {
        this.mContext = context;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();// # of items in your arraylist
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);// get the actual movie
    }

    @Override
    public long getItemId(int id) {
        return id;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView == null) {
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            convertView = inflater.inflate(R.layout.scan_list_note, parent, false);//modify here
            viewHolder = new ViewHolder();
            viewHolder.tvScannedItemId = (TextView) convertView.findViewById(R.id.scan_Item_Id);//modify here
            viewHolder.tvScannedItemPrice = (TextView) convertView.findViewById(R.id.scan_Item_Price);//modify here
            
            convertView.setTag(viewHolder);
            convertView.setTag(R.id.scan_Item_Id, viewHolder.tvScannedItemId);
            convertView.setTag(R.id.scan_Item_Price, viewHolder.tvScannedItemPrice);
            
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        HashMap<String,String> b = data.get(position);//modify here
        viewHolder.tvScannedItemPrice.setText(b.get("price"));//modify here
        viewHolder.tvScannedItemId.setText(b.get("id"));//modify here
        return convertView;

    }

    static class ViewHolder {
        TextView tvScannedItemPrice;//modify here
        TextView tvScannedItemId;//modify here
        

    }

}

