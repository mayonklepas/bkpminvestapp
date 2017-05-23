package com.app.mulyadi.bkpminvestapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;

/**
 * Created by Mulyadi on 28/11/2016.
 */
public class Detailsasaranadapter extends BaseAdapter{
    ArrayList<Integer> iddata=new ArrayList<>();
    ArrayList<String> label=new ArrayList<>();
    ArrayList<String> alamat=new ArrayList<>();
    ArrayList<String> keterangan=new ArrayList<>();
    ArrayList<String> imageid=new ArrayList<>();
    ArrayList<Float> rate=new ArrayList<>();
    Context ct;
    private static LayoutInflater lay;

    public Detailsasaranadapter(ArrayList<Integer> iddata, ArrayList<String> label,
                                ArrayList<String> alamat,ArrayList<String> keterangan,
            ArrayList<Float> rate,ArrayList<String> imageid, Context ct) {
        this.iddata=iddata;
        this.label = label;
        this.alamat = alamat;
        this.keterangan = keterangan;
        this.rate=rate;
        this.imageid = imageid;
        this.ct = ct;
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
        TextView label,alamat,keterangan,lrate;
        ImageView img;
        RatingBar rate;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        holder h = new holder();
        View rowview;
        rowview = lay.inflate(R.layout.detailsasarandata,null);
        h.label = (TextView) rowview.findViewById(R.id.imglabel);
        h.alamat = (TextView) rowview.findViewById(R.id.imgalamat);
        h.keterangan = (TextView) rowview.findViewById(R.id.imgketerangan);
        h.rate = (RatingBar) rowview.findViewById(R.id.rate);
        h.lrate = (TextView) rowview.findViewById(R.id.lrate);
        h.img = (ImageView) rowview.findViewById(R.id.img);
        h.label.setText(label.get(position));
        h.alamat.setText(alamat.get(position));
        h.keterangan.setText(keterangan.get(position));
        h.rate.setRating(rate.get(position));
        h.lrate.setText(String.valueOf(rate.get(position)));
        Glide.with(ct).load(imageid.get(position)).centerCrop().
                crossFade().placeholder(R.mipmap.ic_launcher).
                diskCacheStrategy(DiskCacheStrategy.ALL).into(h.img);
        return rowview;

    }
}
