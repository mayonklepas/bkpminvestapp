package com.app.mulyadi.bkpminvestapp;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.test.suitebuilder.TestMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class AjukaninvestasiActivity extends AppCompatActivity {
    ImageView back;
    EditText ednama,edemail,ednohp,edalamat,edjumlah,edpesan;
    TextView lnama,lemail,lnohp,lalamat,ljumlah,lpesan,lpengajuan;
    Button bkirim;
    int id;
    Getset gs=new Getset();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajukaninvestasi);
        back=(ImageView) findViewById(R.id.back);
        ednama=(EditText)findViewById(R.id.ednama);
        edemail=(EditText)findViewById(R.id.edemail);
        ednohp=(EditText)findViewById(R.id.ednohp);
        edalamat=(EditText)findViewById(R.id.edalamat);
        edjumlah=(EditText)findViewById(R.id.edjumlah);
        edpesan=(EditText)findViewById(R.id.edpesan);
        lnama=(TextView)findViewById(R.id.lnama);
        lemail=(TextView)findViewById(R.id.lemail);
        lnohp=(TextView)findViewById(R.id.lnohp);
        lalamat=(TextView)findViewById(R.id.lalamat);
        ljumlah=(TextView)findViewById(R.id.ljumlah);
        lpesan=(TextView)findViewById(R.id.lpesan);
        lpengajuan=(TextView)findViewById(R.id.lpengajuan);
        bkirim=(Button) findViewById(R.id.bkirim);

        if (gs.getLang().equals("en")){
            lnama.setText("Company Name / Person Name");
            lemail.setText("Email");
            lnohp.setText("Phone Number");
            lalamat.setText("Address");
            ljumlah.setText("Investment Planning (Optional)");
            lpesan.setText("Additional Message (Optional)");
            lpengajuan.setText("Investment Submitting");
            bkirim.setText("Send");
        }else{
            lnama.setText("Nama Perusahaan / Personal");
            lemail.setText("Email");
            lnohp.setText("No. Telp / Handphone");
            lalamat.setText("Alamat");
            ljumlah.setText("Rencana Jumlah Investasi (Optional)");
            lpesan.setText("Pesan Tambahan (Optional)");
            lpengajuan.setText("Pengajuan Investasi");
            bkirim.setText("Kirim");
        }
        

        Bundle extra=getIntent().getExtras();
        id=extra.getInt("id");
        proses();
        kembali();
    }



    private void proses(){
        bkirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog pd=new ProgressDialog(AjukaninvestasiActivity.this);
                pd.setTitle("Processing");
                pd.setMessage("Please Wait");
                pd.show();
                RequestQueue queue= Volley.newRequestQueue(AjukaninvestasiActivity.this);
                StringRequest sreq=new StringRequest(Request.Method.POST, "http://"+gs.getGlobalurl()+"/restapi/insertpesan.php", new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(AjukaninvestasiActivity.this,response, Toast.LENGTH_SHORT).show();

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(AjukaninvestasiActivity.this, error.getMessage()+" Connection failed", Toast.LENGTH_SHORT).show();
                    }
                }){
                    @Override
                    protected Map<String,String> getParams(){
                        Map<String,String> params = new HashMap<String, String>();
                        params.put("id_perusahaan",String.valueOf(id));
                        params.put("nama",ednama.getText().toString());
                        params.put("email",edemail.getText().toString());
                        params.put("nohp",ednohp.getText().toString());
                        params.put("alamat",edalamat.getText().toString());
                        params.put("jumlah",edjumlah.getText().toString());
                        params.put("pesan",edpesan.getText().toString());
                        return params;
                    }
                };

                queue.add(sreq);
                queue.addRequestFinishedListener(new RequestQueue.RequestFinishedListener<String>() {
                    @Override
                    public void onRequestFinished(Request<String> request) {
                        pd.dismiss();
                    }
                });

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
