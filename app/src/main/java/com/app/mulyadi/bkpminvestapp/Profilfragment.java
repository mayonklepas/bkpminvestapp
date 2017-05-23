package com.app.mulyadi.bkpminvestapp;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Mulyadi on 27/11/2016.
 */
public class Profilfragment extends Fragment {

    ImageView img;
    TextView lnama,lketerangan,lalamat,hlketerangan,hlalamat,lemail,lnohp,lprofil;
    ProgressDialog pd;
    Getset gs=new Getset();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.profillayout,container,false);
        img=(ImageView) v.findViewById(R.id.imgprofil);
        lketerangan=(TextView) v.findViewById(R.id.lketerangan);
        lalamat=(TextView) v.findViewById(R.id.lalamat);
        hlketerangan=(TextView) v.findViewById(R.id.hlketerangan);
        hlalamat=(TextView) v.findViewById(R.id.hlalamat);
        lemail=(TextView) v.findViewById(R.id.lemail);
        lnohp=(TextView) v.findViewById(R.id.lnohp);
        lnama=(TextView) v.findViewById(R.id.lnama);
        lprofil=(TextView) v.findViewById(R.id.lprofil);
        if(gs.getLang().equals("en")){
            lprofil.setText(gs.getNama()+" Profile");
            hlketerangan.setText("About "+gs.getNama());
            hlalamat.setText("Contact Person");
        }else{
            lprofil.setText("Profil "+gs.getNama());
            hlketerangan.setText("Tentang "+gs.getNama());
            hlalamat.setText("Kontak Person");
        }
        loaddata();
        return v;
    }

    private void loaddata(){

        RequestQueue queue= Volley.newRequestQueue(getActivity().getApplicationContext());
        StringRequest sreq=new StringRequest(Request.Method.POST, "http://"+gs.getGlobalurl()+"/restapi/info.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray ja=new JSONArray(response.toString());
                    for (int i = 0; i < ja.length(); i++) {
                        JSONObject jo=ja.getJSONObject(i);
                        Glide.with(getActivity().getApplicationContext()).
                                load("http://"+gs.getGlobalurl()+"/src/gambar/"+jo.getString("gambar"))
                                .crossFade()
                                .centerCrop()
                                .placeholder(R.mipmap.ic_launcher)
                                .centerCrop()
                                .diskCacheStrategy(DiskCacheStrategy.ALL).into(img);
                        lketerangan.setText(jo.getString("keterangan"));
                        lalamat.setText(jo.getString("alamat"));
                        lemail.setText(jo.getString("email"));
                        lnohp.setText(jo.getString("nohp"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity().getApplicationContext(), error.getMessage()+" Connection failed", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("lang", gs.getLang());
                return params;
            }
        };

        queue.add(sreq);

    }
}
