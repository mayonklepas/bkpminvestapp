package com.app.mulyadi.bkpminvestapp;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Getset gs=new Getset();
    File fl;
    NavigationView navigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        gs.setGlobalurl("www.investasi-perizinan.ntbprov.go.id/promosi");
        loadfile();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        //.setAction("Action", null).show();
                if (gs.getLang().equals("en")){
                    navigationView.getMenu().findItem(R.id.sasaran).setTitle("Investment Sector");
                    navigationView.getMenu().findItem(R.id.peraturan).setTitle("Investment Rules");
                    navigationView.getMenu().findItem(R.id.profil).setTitle("Profile");
                    navigationView.getMenu().findItem(R.id.chat).setTitle("Chat");
                    navigationView.getMenu().findItem(R.id.kinerja).setTitle("Performance");
                }else{
                    navigationView.getMenu().findItem(R.id.sasaran).setTitle("Sektor Investasi");
                    navigationView.getMenu().findItem(R.id.peraturan).setTitle("Peraturan");
                    navigationView.getMenu().findItem(R.id.profil).setTitle("Profil");
                    navigationView.getMenu().findItem(R.id.chat).setTitle("Tanya Jawab");
                    navigationView.getMenu().findItem(R.id.kinerja).setTitle("Kinerja");
                }
                getFragmentManager().beginTransaction().replace(R.id.container,new Mainfragment()).commit();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView= (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        loaddata();
        if (gs.getLang().equals("en")){
            navigationView.getMenu().findItem(R.id.sasaran).setTitle("Investment Sector");
            navigationView.getMenu().findItem(R.id.peraturan).setTitle("Investment Rules");
            navigationView.getMenu().findItem(R.id.profil).setTitle("Profile");
            navigationView.getMenu().findItem(R.id.chat).setTitle("Chat");
            navigationView.getMenu().findItem(R.id.kinerja).setTitle("Performance");
        }else{
            navigationView.getMenu().findItem(R.id.sasaran).setTitle("Sektor Investasi");
            navigationView.getMenu().findItem(R.id.peraturan).setTitle("Peraturan");
            navigationView.getMenu().findItem(R.id.profil).setTitle("Profil");
            navigationView.getMenu().findItem(R.id.kinerja).setTitle("Kinerja");
            navigationView.getMenu().findItem(R.id.chat).setTitle("Tanya Jawab");
        }

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.tentangapp) {
            AlertDialog.Builder adb=new AlertDialog.Builder(MainActivity.this);
            adb.setTitle("Tentang Aplikasi");
            adb.setMessage("BKPM NTB Ayo Investasi ! v1.0");
            adb.show();
        }else if(id==R.id.keluar){
            System.exit(0);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        if (gs.getLang().equals("en")){
            menu.findItem(R.id.keluar).setTitle("Exit");
            menu.findItem(R.id.tentangapp).setTitle("About App");
        }else{
            menu.findItem(R.id.keluar).setTitle("Keluar");
            menu.findItem(R.id.tentangapp).setTitle("Tentang App");
        }

        return super.onPrepareOptionsMenu(menu);
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.sasaran) {
            getFragmentManager().beginTransaction().replace(R.id.container,new Headersasaranfragment()).commit();
        } else if (id == R.id.peraturan) {
            getFragmentManager().beginTransaction().replace(R.id.container,new Peraturanfragment()).commit();
        } else if (id == R.id.profil) {
            getFragmentManager().beginTransaction().replace(R.id.container,new Profilfragment()).commit();
        } else if (id == R.id.faq) {
            getFragmentManager().beginTransaction().replace(R.id.container,new Faqfragment()).commit();
        } else if (id == R.id.wa) {
            Uri mUri = Uri.parse("smsto:"+gs.getNohp());
            Intent mIntent = new Intent(Intent.ACTION_SENDTO, mUri);
            mIntent.setPackage("com.whatsapp");
            mIntent.putExtra("sms_body", "The text goes here");
            mIntent.putExtra("chat",true);
            startActivity(mIntent);
        }else if(id == R.id.tg){
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(gs.getTelegram()));
            startActivity(intent);
        }else if(id==R.id.kinerja){
            showpdf();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }





    private void loaddata(){
        RequestQueue queue= Volley.newRequestQueue(MainActivity.this);
        StringRequest sreq=new StringRequest(Request.Method.POST, "http://"+gs.getGlobalurl()+"/restapi/info.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray ja=new JSONArray(response.toString());
                    for (int i = 0; i < ja.length(); i++) {
                        JSONObject jo=ja.getJSONObject(i);
                        gs.setNohp(jo.getString("nohp"));
                        gs.setTelegram(jo.getString("telegram_link"));
                        gs.setSapaan(jo.getString("kata_sapaan"));
                        gs.setNama(jo.getString("nama"));

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, error.getMessage()+" Connection failed", Toast.LENGTH_SHORT).show();
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
                getFragmentManager().beginTransaction().replace(R.id.container,new Mainfragment()).commit();
            }
        });

    }

    private void loadfile(){
        fl = new File(this.getFilesDir(), "fl.txt");
        if(!fl.exists()){
            try {
                fl.createNewFile();
                fl.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
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

    }

    private void showpdf(){
        Intent i=new Intent(Intent.ACTION_VIEW, Uri.parse("http://"+gs.getGlobalurl()+"/src/pdf/kinerja.pdf"));
        startActivity(i);
    }
}
