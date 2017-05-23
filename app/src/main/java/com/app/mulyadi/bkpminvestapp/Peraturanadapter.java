package com.app.mulyadi.bkpminvestapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Mulyadi on 26/11/2016.
 */
public class Peraturanadapter extends BaseAdapter{

    ArrayList<Integer> iddata=new ArrayList<>();
    ArrayList<String> judul=new ArrayList<>();
    ArrayList<String> deskripsi=new ArrayList<>();
    ArrayList<String> pdf=new ArrayList<>();
    ArrayList<String> ldownload=new ArrayList<>();
    Context ct;
    static LayoutInflater lay;

    public Peraturanadapter(ArrayList<Integer> iddata ,ArrayList<String> judul, ArrayList<String> deskripsi,
                            ArrayList<String> pdf,ArrayList<String> ldownload, Context ct) {
        this.iddata=iddata;
        this.judul = judul;
        this.deskripsi = deskripsi;
        this.pdf = pdf;
        this.ldownload=ldownload;
        this.ct = ct;
        lay=(LayoutInflater) ct.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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

    public class holder{
        TextView tvjudul;
        TextView tvdeskripsi;
        TextView tvdownload;
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        holder h=new holder();
        View v;
        v = lay.inflate(R.layout.peraturandata,null);
        h.tvjudul=(TextView) v.findViewById(R.id.tvjudul);
        h.tvdeskripsi=(TextView) v.findViewById(R.id.tvdetail);
        h.tvdownload=(TextView) v.findViewById(R.id.tvdownload);
        h.tvjudul.setText(judul.get(position));
        h.tvdeskripsi.setText(deskripsi.get(position));
        h.tvdownload.setText(ldownload.get(position));

        return v;
    }
}
