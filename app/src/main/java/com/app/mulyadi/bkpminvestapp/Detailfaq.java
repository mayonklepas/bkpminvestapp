package com.app.mulyadi.bkpminvestapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class Detailfaq extends AppCompatActivity {
    TextView lpertanyaan,ljawaban,hltanya,hljawab,lfaq;
    ImageView back;
    Getset gs=new Getset();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailfaq);
        lpertanyaan=(TextView) findViewById(R.id.lpertanyaan);
        ljawaban=(TextView) findViewById(R.id.ljawaban);
        hltanya=(TextView) findViewById(R.id.hltanya);
        hljawab=(TextView) findViewById(R.id.hljawab);
        back=(ImageView) findViewById(R.id.back);

        lfaq=(TextView) findViewById(R.id.lfaq);
        if(gs.getLang().equals("en")){
            lfaq.setText("FAQ Details");
            hltanya.setText("Question");
            hljawab.setText("Answer");
        }else{
            lfaq.setText("Detail FAQ");
            hltanya.setText("Pertanyaan");
            hljawab.setText("Jawaban");
        }

        Bundle b=getIntent().getExtras();
        lpertanyaan.setText(b.getString("pertanyaan"));
        ljawaban.setText(b.getString("jawaban"));
        kembali();

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
