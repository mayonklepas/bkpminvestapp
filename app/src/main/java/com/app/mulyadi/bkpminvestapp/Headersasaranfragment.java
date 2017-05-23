package com.app.mulyadi.bkpminvestapp;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
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

/**
 * Created by Mulyadi on 25/11/2016.
 */
public class Headersasaranfragment extends Fragment {

    ArrayList<Integer> iddata=new ArrayList<Integer>();
    ArrayList<String> label=new ArrayList<String>();
    ArrayList<String> imageid=new ArrayList<String>();
    GridView gv;
    TextView lsektor;
    LinearLayout containerlayout;
    ProgressBar pb;
    Getset gs=new Getset();

    @Nullable
   @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.headersasarangrid,container,false);
        gv=(GridView) v.findViewById(R.id.gridView);
        pb=(ProgressBar) v.findViewById(R.id.progressBar);
        lsektor=(TextView) v.findViewById(R.id.lsektor);
        if(gs.getLang().equals("en")){
            lsektor.setText("Investment Sector");
        }else{
            lsektor.setText("Sektor Investasi");
        }
        loaddata();
        selectdata();
        return v;
    }


    public void loaddata(){
        pb.setVisibility(View.VISIBLE);
        RequestQueue queue= Volley.newRequestQueue(getActivity().getBaseContext());
        StringRequest sreq=new StringRequest(Request.Method.POST, "http://"+gs.getGlobalurl()+"/restapi/header-sasaran.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Toast.makeText(MainActivity.this, response.toString(), Toast.LENGTH_SHORT).show();
                        try {
                            JSONArray ja=new JSONArray(response.toString());
                            for (int i = 0; i < ja.length() ; i++) {
                                JSONObject jo=ja.getJSONObject(i);
                                iddata.add(i,jo.getInt("id"));
                                label.add(i,jo.getString("nama"));
                                imageid.add(i,"http://"+gs.getGlobalurl()+"/src/gambar/"+jo.getString("gambar"));
                            }
                            gv.setAdapter(new Headersasaranadapter(iddata,label,imageid,getActivity().getApplicationContext()));

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
                params.put("lang",gs.getLang());
                return params;
            }
        };;
        queue.add(sreq);
        queue.addRequestFinishedListener(new RequestQueue.RequestFinishedListener<String>() {
            @Override
            public void onRequestFinished(Request<String> request) {
                pb.setVisibility(View.GONE);
            }
        });
    }

    public void selectdata(){
        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i=new Intent(getActivity().getApplicationContext(),Detailsasaran.class);
                i.putExtra("iddata",String.valueOf(iddata.get(position)));
                i.putExtra("sektor",label.get(position));
                startActivity(i);
            }
        });
    }
}


