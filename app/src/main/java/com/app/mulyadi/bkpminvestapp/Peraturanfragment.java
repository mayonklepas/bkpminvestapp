package com.app.mulyadi.bkpminvestapp;

import android.app.Fragment;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
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
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Mulyadi on 26/11/2016.
 */
public class Peraturanfragment extends Fragment{
    ListView lv;
    ArrayList<Integer> iddata=new ArrayList<>();
    ArrayList<String> judul=new ArrayList<>();
    ArrayList<String> deskripsi= new ArrayList<>();
    ArrayList<String> pdf=new ArrayList<>();
    ArrayList<String> ldownload=new ArrayList<>();
    ProgressBar pb;
    TextView lperaturan;
    Getset gs=new Getset();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.peraturanlist,container,false);
        lv=(ListView) v.findViewById(R.id.lvperaturan);
        pb=(ProgressBar) v.findViewById(R.id.pb);
        lperaturan=(TextView) v.findViewById(R.id.lperaturan);
        if(gs.getLang().equals("en")){
            lperaturan.setText("Investment Rules");
        }else{
            lperaturan.setText("Peraturan Investasi");
        }
        loaddata();
        selectdata();
        return v;
    }

    public void loaddata(){
        pb.setVisibility(View.VISIBLE);
        RequestQueue queue= Volley.newRequestQueue(getActivity().getBaseContext());
        StringRequest sreq=new StringRequest(Request.Method.POST, "http://"+gs.getGlobalurl()+"/restapi/peraturan.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Toast.makeText(MainActivity.this, response.toString(), Toast.LENGTH_SHORT).show();
                        try {
                            JSONArray ja=new JSONArray(response.toString());
                            for (int i = 0; i < ja.length() ; i++) {
                                JSONObject jo=ja.getJSONObject(i);
                                iddata.add(i,jo.getInt("id"));
                                judul.add(i,jo.getString("nama"));
                                deskripsi.add(i,jo.getString("keterangan"));
                                pdf.add(i,jo.getString("file"));
                                if (gs.getLang().equals("en")){
                                    ldownload.add(i,"Click to Download PDF File");
                                }else{
                                    ldownload.add(i,"Klik untuk Mendownload File PDF");
                                }
                            }
                            lv.setAdapter(new Peraturanadapter(iddata,judul,deskripsi,pdf,ldownload,getActivity().getApplicationContext()));
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
        queue.addRequestFinishedListener(new RequestQueue.RequestFinishedListener<Object>() {
            @Override
            public void onRequestFinished(Request<Object> request) {
                pb.setVisibility(View.GONE);
            }
        });
    }


    private void selectdata(){
       lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               String filename=pdf.get(position);
                   Intent i=new Intent(Intent.ACTION_VIEW, Uri.parse("http://"+gs.getGlobalurl()+"/src/gambar/"+filename));
                   startActivity(i);
           }
       });
    }


}
