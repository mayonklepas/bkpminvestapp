package com.app.mulyadi.bkpminvestapp;

import android.app.Fragment;
import android.content.Intent;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Mulyadi on 28/11/2016.
 */
public class Faqfragment extends Fragment{

    ListView lvfaq;
    ArrayList<String> pertanyaan = new ArrayList<>();
    ArrayList<String> jawaban = new ArrayList<>();
    ProgressBar pb;
    Getset gs=new Getset();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.faqlist,container,false);
        lvfaq=(ListView) v.findViewById(R.id.lvfaq);
        pb=(ProgressBar) v.findViewById(R.id.progressBar3);

        loaddata();
        detailfaq();
        return v;
    }

    private void loaddata(){
        pb.setVisibility(View.VISIBLE);
        RequestQueue queue= Volley.newRequestQueue(getActivity().getApplicationContext());
        StringRequest sreq=new StringRequest(Request.Method.POST, "http://"+gs.getGlobalurl()+"/restapi/faq.php",
                new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Toast.makeText(getActivity().getApplicationContext(), response.toString(), Toast.LENGTH_SHORT).show();
                try {
                    JSONArray ja = new JSONArray(response.toString());
                    for (int i = 0; i < ja.length() ; i++) {
                        JSONObject jo=ja.getJSONObject(i);
                        pertanyaan.add(i,jo.getString("pertanyaan"));
                        jawaban.add(i,jo.getString("jawaban"));
                    }
                    lvfaq.setAdapter(new Faqadapter(pertanyaan,jawaban,getActivity().getApplicationContext()));
                } catch (JSONException e) {
                    e.printStackTrace();
                }



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity().getApplicationContext(),error.getMessage()+" Connection failed" , Toast.LENGTH_LONG).show();
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
        queue.addRequestFinishedListener(new RequestQueue.RequestFinishedListener<String>() {
            @Override
            public void onRequestFinished(Request<String> request) {
                pb.setVisibility(View.GONE);
            }
        });
    }


    private void detailfaq(){
        lvfaq.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i=new Intent(getActivity().getApplicationContext(),Detailfaq.class);
                i.putExtra("pertanyaan",pertanyaan.get(position));
                i.putExtra("jawaban",jawaban.get(position));
                startActivity(i);
            }
        });
    }
}
