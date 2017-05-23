package com.app.mulyadi.bkpminvestapp;

import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.zip.Inflater;

/**
 * Created by Mulyadi on 25/11/2016.
 */
public class Mainfragment extends Fragment {

    Getset gs=new Getset();
    ImageView img;
    TextView lsapaan,lnama,lmoto;
    Button btindo,btenglish;
    File fl;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.mainfragment,container,false);
        img=(ImageView) v.findViewById(R.id.img);
        lsapaan=(TextView) v.findViewById(R.id.lsapaan);
        lnama=(TextView) v.findViewById(R.id.lnama);
        lmoto=(TextView) v.findViewById(R.id.lmoto);
        btindo=(Button) v.findViewById(R.id.btindo);
        btenglish=(Button) v.findViewById(R.id.btenglish);
        fl=new File(getActivity().getFilesDir(),"fl.txt");
        load();
        setlangen();
        setlangindo();
        return v;
    }

    private void load(){
        Glide.with(getActivity().getApplicationContext()).
                load("http://"+gs.getGlobalurl()+"/src/gambar/pictinfo.jpg")
                .crossFade()
                .centerCrop()
                .placeholder(R.mipmap.ic_launcher)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL).into(img);

        if (gs.getLang().equals("en")){
            lnama.setText("Welcome to "+gs.getNama());
            lmoto.setText("Lets Invest !");
        }else {
            lnama.setText("Selamat Datang Di "+gs.getNama());
            lmoto.setText("Ayo Investasi !");
        }


        lsapaan.setText(gs.getSapaan());

    }

    private void setlangindo(){
        btindo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    FileOutputStream fout=new FileOutputStream(fl);
                    String data="in";
                    fout.write(data.getBytes());
                    fout.flush();
                    fout.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }


                try {
                    BufferedReader br=new BufferedReader(new FileReader(fl));
                    String data="";
                    String line;
                    while ((line=br.readLine())!=null){
                        data=line;
                    }
                    gs.setLang(data);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Toast.makeText(getActivity().getApplicationContext(), "Pengaturan bahasa telah diganti, klik tombol home untuk menerapkan pengaturan", Toast.LENGTH_LONG).show();

            }
        });
    }

    private void setlangen(){
        btenglish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    FileOutputStream fout=new FileOutputStream(fl);
                    String data="en";
                    fout.write(data.getBytes());
                    fout.flush();
                    fout.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                try {
                    BufferedReader br=new BufferedReader(new FileReader(fl));
                    String data="";
                    String line;
                    while ((line=br.readLine())!=null){
                        data=line;
                    }
                    gs.setLang(data);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Toast.makeText(getActivity().getApplicationContext(), "Language has changed, Please click home button for applying new setting", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
