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

/**
 * Created by thi on 09/01/18.
 */

public class TelaCriterioXCriterio extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.julgamento);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        Bundle extras = getIntent().getExtras();
        final int selecionado = extras.getInt("selecionado");

        Spinner[] sp = new Spinner[100];
        TextView[] txEsquerda = new TextView[100];
        TextView[] txDireita = new TextView[100];
        TextView titulo = new TextView(this);

        RelativeLayout julgamento = findViewById(R.id.julgamentos);

        ArrayList<String> listaSpinners = new ArrayList<String>();
        for (int i = 9; i >= 2; i--) {
            if (i != 0 && i != -1)
                listaSpinners.add("Esquerda" + i);
        }
        listaSpinners.add("1");
        for (int i = 2; i < 10; i++) {
            if (i != 0 && i != -1)
                listaSpinners.add("Direita" + i);
        }
        UsuarioDAO dao = new UsuarioDAO();
        final ArrayList<Criterio> listaCriterios = dao.buscarTodosCriterios(selecionado);
        ArrayList<String> listaNomeCriterios = new ArrayList<String>();
        for (Criterio cri : listaCriterios) {
            listaNomeCriterios.add(cri.getNome());
        }
        titulo.setText("Criterio X Criterio");
        titulo.setWidth(500);
        titulo.setHeight(150);
        titulo.setTextSize(30);
        titulo.setY(50);
        titulo.setX(30);
        julgamento.addView(titulo);
        int x = 200;
        for (int j = 0; j < listaNomeCriterios.size(); j++) {
            for (int k = j + 1; k < listaNomeCriterios.size(); k++) {
                sp[j] = new Spinner(this);
                sp[j].setX(200);
                sp[j].setY(x - 30);

                julgamento.addView(sp[j]);
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, listaSpinners);
                ArrayAdapter<String> spinnerArrayAdapter = arrayAdapter;
                spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
                sp[j].setAdapter(spinnerArrayAdapter);

                txEsquerda[j] = new TextView(this);
                txEsquerda[j].setX(50);
                txEsquerda[j].setY(x);
                txEsquerda[j].setText(listaNomeCriterios.get(j));
                julgamento.addView(txEsquerda[j]);

                txDireita[k] = new TextView(this);
                txDireita[k].setX(450);
                txDireita[k].setY(x);
                txDireita[k].setText(listaNomeCriterios.get(k));
                julgamento.addView(txDireita[k]);

                x = x + 100;
            }

        }

        Button voltar = new Button(this);
        voltar.setWidth(20);
        voltar.setHeight(10);
        voltar.setText("Voltar");
        voltar.setY(x);
        voltar.setX(100);
        julgamento.addView(voltar);

        Button enviar = new Button(this);
        enviar.setWidth(20);
        enviar.setHeight(10);
        enviar.setText("Julgar");
        enviar.setY(x);
        enviar.setX(300);
        julgamento.addView(enviar);

        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(TelaCriterioXCriterio.this, TelaJulgamento.class);
                it.putExtra("selecionado", selecionado);
                startActivity(it);
            }
        });

        ViewGroup.LayoutParams params = julgamento.getLayoutParams();
        params.height= x + 200;
    }


}
