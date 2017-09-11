package com.example.nithin.personalexpensemanager;

import android.app.Activity;
import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by nithin on 7/9/17.
 */

public class MyAdapter extends ArrayAdapter<ListItem> {
    LayoutInflater inflater;
    ArrayList<ListItem> objects;
    ViewHolder holder = null;

    public MyAdapter(Context context, int textViewResourceId, ArrayList<ListItem> objects) {
        super(context, textViewResourceId, objects);
        inflater = ((Activity) context).getLayoutInflater();
        this.objects = objects;
    }
    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }
    public View getCustomView(int position, View convertView, ViewGroup parent) {

        ListItem listItem = objects.get(position);
        View row = convertView;

        if (null == row) {
            holder = new ViewHolder();
            row = inflater.inflate(R.layout.row, parent, false);
            holder.InCatagory = (TextView) row.findViewById(R.id.InCatagory);
            holder.imgThumb = (ImageView) row.findViewById(R.id.imgThumb);
            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }

        holder.InCatagory.setText(listItem.InCatagory);
        holder.imgThumb.setBackgroundResource(listItem.logo);

        return row;
    }

    static class ViewHolder {
        TextView InCatagory;
        ImageView imgThumb;
    }

}
