package com.app.mulyadi.bkpminvestapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;

/**
 * Created by Minami on 26/06/2016.
 */
public class Headersasaranadapter extends BaseAdapter {

    ArrayList<Integer> iddata=new ArrayList<>();
    ArrayList<String> label=new ArrayList<>();
    ArrayList<String> imageid=new ArrayList<>();
    Context ct;
    private static LayoutInflater lay;


    public Headersasaranadapter(ArrayList<Integer> iddata, ArrayList<String> label,ArrayList<String> imageid, Context ct) {
        this.iddata=iddata;
        this.label = label;
        this.ct = ct;
        this.imageid = imageid;
        lay = (LayoutInflater) ct.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return iddata.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class holder {
        TextView tv;
        ImageView img;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        holder h = new holder();
        View rowview;
        rowview = lay.inflate(R.layout.headersasarandata,null);
        h.tv = (TextView) rowview.findViewById(R.id.imglabel);
        h.img = (ImageView) rowview.findViewById(R.id.img);
        h.tv.setText(label.get(position));
        Glide.with(ct).load(imageid.get(position)).centerCrop().
                crossFade().placeholder(R.mipmap.ic_launcher).
                diskCacheStrategy(DiskCacheStrategy.ALL).into(h.img);
        return rowview;

    }
}
