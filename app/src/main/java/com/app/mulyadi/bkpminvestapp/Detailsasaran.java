package com.app.mulyadi.bkpminvestapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Detailsasaran extends AppCompatActivity {

    ArrayList<Integer> iddata=new ArrayList<>();
    ArrayList<String> label=new ArrayList<>();
    ArrayList<String> alamat=new ArrayList<>();
    ArrayList<String> keterangan=new ArrayList<>();
    ArrayList<String> imageid=new ArrayList<>();
    ArrayList<Float> rate=new ArrayList<>();
    String id;
    GridView gv;
    TextView labeldetail;
    ProgressBar pb;
    ImageView back;
    Getset gs=new Getset();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailsasaran);
        gv=(GridView)findViewById(R.id.gridView2);
        labeldetail=(TextView) findViewById(R.id.labeldetail);
        pb=(ProgressBar) findViewById(R.id.progressBar2);
        back=(ImageView)findViewById(R.id.back);
        Bundle ex= getIntent().getExtras();
        id=ex.getString("iddata");
        labeldetail.setText(ex.getString("sektor"));
        loaddata();
        selectdata();
        kembali();
    }


    public void loaddata(){
        pb.setVisibility(View.VISIBLE);
        RequestQueue queue= Volley.newRequestQueue(this);
        StringRequest sreq=new StringRequest(Request.Method.POST,"http://"+gs.getGlobalurl()+"/restapi/detail-sasaran.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray ja=new JSONArray(response.toString());
                            for (int i = 0; i < ja.length() ; i++) {
                                JSONObject jo=ja.getJSONObject(i);
                                iddata.add(i,jo.getInt("id"));
                                label.add(i,jo.getString("nama"));
                                alamat.add(i,jo.getString("alamat").substring(0,20)+"...");
                                keterangan.add(i,jo.getString("keterangan").substring(0,50)+"...");
                                rate.add(i,Float.parseFloat(jo.getString("rate")));
                                imageid.add(i,"http://"+gs.getGlobalurl()+"/src/gambar/"+jo.getString("gambar"));
                            }
                            gv.setAdapter(new Detailsasaranadapter(iddata,label,alamat,keterangan,rate,imageid,Detailsasaran.this));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Detailsasaran.this, error.getMessage()+" Connection failed", Toast.LENGTH_SHORT).show();
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
        queue.addRequestFinishedListener(new RequestQueue.RequestFinishedListener<String>() {
            @Override
            public void onRequestFinished(Request<String> request) {
                pb.setVisibility(View.GONE);
            }
        });
    }

    private void selectdata(){
        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i=new Intent(Detailsasaran.this,Detailperusahaan.class);
                i.putExtra("id",String.valueOf(iddata.get(position)));
                startActivity(i);
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
}
