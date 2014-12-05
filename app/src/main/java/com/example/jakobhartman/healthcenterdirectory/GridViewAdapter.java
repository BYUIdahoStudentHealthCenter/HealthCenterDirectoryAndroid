package com.example.jakobhartman.healthcenterdirectory;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * Created by chad on 12/1/14.
 */
public class GridViewAdapter extends ArrayAdapter<ProPhoto> {
    private Context context;
    private int layoutResourceId;
    private ArrayList data = new ArrayList();

    public GridViewAdapter(Context context, int layoutResourceId, ArrayList<ProPhoto> data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ViewHolder holder = null;
        ProPhoto myPhoto = getItem(position);

        if (row == null) {
            LayoutInflater inflater =((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            holder = new ViewHolder();
            holder.image = (ImageView) row.findViewById(R.id.image);
            holder.name = (TextView) row.findViewById(R.id.txt);
            row.setTag(holder); // sets a tag if you want them to all have the same onclick action
        } else {
            holder = (ViewHolder) row.getTag();
        }

        byte[] decodedString = Base64.decode(myPhoto.image,Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString,0,decodedString.length);
        holder.image.setImageBitmap(decodedByte);
        holder.name.setText(myPhoto.name);
        return row;

    }

    static class ViewHolder {
        ImageView image;
        TextView name;
    }



}



