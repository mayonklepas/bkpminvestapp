package com.app.mulyadi.bkpminvestapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

public class Detailperusahaan extends AppCompatActivity {

    TextView lnama,lketerangan,lalamat,hlketerangan,hlalamat,hnama;
    ImageView img,back;
    Button bajukan;
    String id;
    int iddata;
    ProgressDialog pd;
    Getset gs=new Getset();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailperusahaan);
        lnama=(TextView) findViewById(R.id.lnama);
        hnama=(TextView) findViewById(R.id.hnama);
        lketerangan=(TextView) findViewById(R.id.lketerangan);
        lalamat=(TextView) findViewById(R.id.lalamat);
        hlketerangan=(TextView) findViewById(R.id.hlketerangan);
        hlalamat=(TextView) findViewById(R.id.hlalamat);
        img=(ImageView)findViewById(R.id.imgprofil);
        back=(ImageView)findViewById(R.id.back);
        bajukan=(Button)findViewById(R.id.bajukan);
        Bundle extra=getIntent().getExtras();
        id=extra.getString("id");
        if (gs.getLang().equals("en")){
            hlketerangan.setText("Information");
            hlalamat.setText("Location Address");
            bajukan.setText("Submit Investment");
        }else{
            hlketerangan.setText("Informasi");
            hlalamat.setText("Alamat Lokasi");
            bajukan.setText("Ajukan Investasi");
        }
        loaddata();
        kembali();
        ajukan();
    }


    private void loaddata(){
        pd=new ProgressDialog(Detailperusahaan.this);
        pd.setTitle("Loading");
        pd.setMessage("Please Wait");
        pd.show();
        RequestQueue queue= Volley.newRequestQueue(this);
        StringRequest sreq=new StringRequest(Request.Method.POST, "http://"+gs.getGlobalurl()+"/restapi/subdetail-sasaran.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray ja=new JSONArray(response.toString());
                    for (int i = 0; i < ja.length(); i++) {
                        JSONObject jo=ja.getJSONObject(i);
                        Glide.with(Detailperusahaan.this).
                                load("http://"+gs.getGlobalurl()+"/src/gambar/"+jo.getString("gambar"))
                                .crossFade()
                                .centerCrop()
                                .placeholder(R.mipmap.ic_launcher)
                                .centerCrop()
                                .diskCacheStrategy(DiskCacheStrategy.ALL).into(img);
                        iddata=jo.getInt("id");
                        hnama.setText(jo.getString("nama"));
                        lnama.setText(jo.getString("nama"));
                        lketerangan.setText(jo.getString("keterangan"));
                        lalamat.setText(jo.getString("alamat"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Detailperusahaan.this, error.getMessage()+" Connection failed", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("id",id);
                params.put("lang", gs.getLang());
                return params;
            }
        };

        queue.add(sreq);
        queue.addRequestFinishedListener(new RequestQueue.RequestFinishedListener<Object>() {
            @Override
            public void onRequestFinished(Request<Object> request) {
                pd.dismiss();
            }
        });

    }

    private void kembali(){
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void ajukan(){
        bajukan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Detailperusahaan.this,AjukaninvestasiActivity.class);
                i.putExtra("id",iddata);
                startActivity(i);
            }
        });
    }
}
