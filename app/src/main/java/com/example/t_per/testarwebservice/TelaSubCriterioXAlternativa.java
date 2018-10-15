package com.example.t_per.testarwebservice;

import android.content.Intent;
import android.graphics.Color;
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

public class TelaSubCriterioXAlternativa extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.julgamento);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        Bundle extras = getIntent().getExtras();
        final int pin = extras.getInt("pin");

        Spinner[] spesquerda = new Spinner[100];
        Spinner[] spdireita = new Spinner[100];
        TextView[] txEsquerda = new TextView[100];
        TextView[] txDireita = new TextView[100];
        TextView titulo = new TextView(this);

        RelativeLayout julgamento = findViewById(R.id.julgamentos);

        ArrayList<Integer> listaSpinners = new ArrayList<Integer>();
        for (int i = 0; i <= 9; i++) {
            listaSpinners.add(i);
        }
        UsuarioDAO dao = new UsuarioDAO();
        final int hierarquiaId = dao.getHierarquiaPorPin(pin);
        final ArrayList<Criterio> listaCriterios = dao.buscarTodosCriterios(hierarquiaId);
        ArrayList<String> listaNomeCriterios = new ArrayList<String>();
        for (Criterio cri : listaCriterios) {
            ArrayList<SubCriterio> listaSubCriterios = dao.buscarTodosSubCriterios(cri.getIdcirterio(), hierarquiaId);
            cri.setSub(listaSubCriterios);
            if(listaSubCriterios != null){
                for (SubCriterio subcri : listaSubCriterios) {
                    ArrayList<Alternativa> listaAlternativas = dao.buscarTodasAlternativasPorHierarquia(hierarquiaId);
                    subcri.setAlt(listaAlternativas);
                }
            }
        }
        titulo.setText("SubCriterio X Alternativa");
        titulo.setTextColor(Color.BLACK);
        titulo.setWidth(700);
        titulo.setHeight(150);
        titulo.setTextSize(30);
        titulo.setY(50);
        titulo.setX(0);
        julgamento.addView(titulo);
        int x = 200;
        for (int j = 0; j < listaCriterios.size(); j++) {
            if(listaCriterios.get(j).getSub() != null){
                for (int k = 0 ; k < listaCriterios.get(j).getSub().size(); k++) {
                    for (int l = 0 ; l < listaCriterios.get(j).getSub().get(k).getAlt().size(); l++) {
                        spesquerda[j] = new Spinner(this);
                        spesquerda[j].setX(150);
                        spesquerda[j].setY(x - 30);
                        spdireita[j] = new Spinner(this);
                        spdireita[j].setX(300);
                        spdireita[j].setY(x - 30);

                        julgamento.addView(spesquerda[j]);
                        ArrayAdapter<Integer> arrayAdapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_dropdown_item, listaSpinners);
                        ArrayAdapter<Integer> spinnerArrayAdapter = arrayAdapter;
                        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
                        spesquerda[j].setAdapter(spinnerArrayAdapter);
                        spesquerda[j].setHorizontalScrollBarEnabled(true);

                        julgamento.addView(spdireita[j]);
                        ArrayAdapter<Integer> arrayAdapter2 = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_dropdown_item, listaSpinners);
                        ArrayAdapter<Integer> spinnerArrayAdapter2 = arrayAdapter2;
                        spinnerArrayAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_item);
                        spdireita[j].setAdapter(spinnerArrayAdapter2);
                        spdireita[j].setHorizontalScrollBarEnabled(true);

                        txEsquerda[j] = new TextView(this);
                        txEsquerda[j].setX(50);
                        txEsquerda[j].setY(x);
                        txEsquerda[j].setText(listaCriterios.get(j).getSub().get(k).getNome());
                        julgamento.addView(txEsquerda[j]);

                        txDireita[k] = new TextView(this);
                        txDireita[k].setX(450);
                        txDireita[k].setY(x);
                        txDireita[k].setText(listaCriterios.get(j).getSub().get(k).getAlt().get(l).getNome());
                        julgamento.addView(txDireita[k]);

                        x = x + 100;
                    }

                }
            }
        }

        Button voltar = new Button(this);
        voltar.setWidth(20);
        voltar.setHeight(10);
        voltar.setText("Voltar");
        voltar.setY(x);
        voltar.setX(100);
        voltar.setBackgroundColor(Color.parseColor("#303F9F"));
        julgamento.addView(voltar);

        Button enviar = new Button(this);
        enviar.setWidth(20);
        enviar.setHeight(10);
        enviar.setText("Votar");
        enviar.setY(x);
        enviar.setX(300);
        enviar.setBackgroundColor(Color.parseColor("#303F9F"));
        julgamento.addView(enviar);

        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(TelaSubCriterioXAlternativa.this, TelaJulgamento.class);
                it.putExtra("pin", pin);
                startActivity(it);
            }
        });


        ViewGroup.LayoutParams params = julgamento.getLayoutParams();
        params.height= x + 200;
    }
}
