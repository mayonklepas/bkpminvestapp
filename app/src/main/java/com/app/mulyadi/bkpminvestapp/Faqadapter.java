package com.app.mulyadi.bkpminvestapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Mulyadi on 28/11/2016.
 */
public class Faqadapter extends BaseAdapter{
    ArrayList<String> pertanyaan=new ArrayList<>();
    ArrayList<String> jawaban=new ArrayList<>();
    Context ct;
    LayoutInflater lay;

    public Faqadapter(ArrayList<String> pertanyaan, ArrayList<String> jawaban, Context ct) {
        this.pertanyaan = pertanyaan;
        this.jawaban = jawaban;
        this.ct = ct;
        lay = (LayoutInflater) ct.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return pertanyaan.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


   public class Holder{
      TextView label;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder h=new Holder();
        View v;
        v=lay.inflate(R.layout.faqdata,null);
        h.label=(TextView) v.findViewById(R.id.label);
        h.label.setText(pertanyaan.get(position));
        return v;
    }
}
