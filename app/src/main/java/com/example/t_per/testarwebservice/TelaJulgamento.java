package com.example.t_per.testarwebservice;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

public class TelaJulgamento extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_julgamento);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        Bundle extras = getIntent().getExtras();
        final int pin = extras.getInt("pin");

        Button crixcri;
        Button crixalt;
        Button crixsubcri;
        Button voltar;

        crixcri = findViewById(R.id.crixcri);
        crixalt = findViewById(R.id.crixalt);
        crixsubcri = findViewById(R.id.crixsubcri);
        voltar = findViewById(R.id.voltar);

        crixcri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(TelaJulgamento.this, TelaCriterioXCriterio.class);
                it.putExtra("pin", pin);
                startActivity(it);
            }
        });

        crixalt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(TelaJulgamento.this, TelaCriterioXAlternativa.class);
                it.putExtra("pin", pin);
                startActivity(it);
            }
        });

        crixsubcri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(TelaJulgamento.this, TelaCriterioXSubCriterio.class);
                it.putExtra("pin", pin);
                startActivity(it);
            }
        });

        voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(TelaJulgamento.this, MainActivity.class);
                startActivity(it);
            }
        });
    }
}
