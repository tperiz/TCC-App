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

public class TelaCriterio extends AppCompatActivity {

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

        int x = 50;
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

        Button b = new Button(this);
        b.setWidth(20);
        b.setHeight(10);
        b.setText("Proximo");
        b.setY(x);
        b.setX(200);
        julgamento.addView(b);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(TelaCriterio.this, TelaSubCriterio.class);
                it.putExtra("selecionado", selecionado);
                startActivity(it);
            }
        });
        ViewGroup.LayoutParams params = julgamento.getLayoutParams();
        params.height= x + 200;
    }


}
