package com.example.t_per.testarwebservice;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by thi on 09/01/18.
 */

public class TelaJulgamento extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.julgamento);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        Bundle extras = getIntent().getExtras();
        int selecionado = extras.getInt("selecionado");

        Spinner[] sp = new Spinner[100];
        TextView[] tx = new TextView[100];
        TextView[] tx2 = new TextView[100];

        RelativeLayout julgamento = (RelativeLayout) findViewById(R.id.julgamento);

        ArrayList<String> lista3 = new ArrayList<String>();
        for (int i = 2; i < 10; i++) {
            if (i != 0 && i != -1)
                lista3.add("Esquerda" + i);
        }
        lista3.add("1");
        for (int i = 2; i < 10; i++) {
            if (i != 0 && i != -1)
                lista3.add("Direita" + i);
        }
        UsuarioDAO dao = new UsuarioDAO();
        ArrayList<Criterio> lista = new ArrayList<Criterio>();
        ArrayList<String> lista2 = new ArrayList<String>();
        lista = new ArrayList<Criterio>();
        lista = dao.buscarTodosCriterios(selecionado);
        for (Criterio cri : lista) {
            lista2.add(cri.getNome());
        }
        int x = 50;
        for (int j = 0; j < lista2.size(); j++) {
            for (int k = j + 1; k < lista2.size(); k++) {
                sp[j] = new Spinner(this);
                sp[j].setX(200);
                sp[j].setY(x - 30);
                julgamento.addView(sp[j]);
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, lista3);
                ArrayAdapter<String> spinnerArrayAdapter = arrayAdapter;
                spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
                sp[j].setAdapter(spinnerArrayAdapter);

                tx[j] = new TextView(this);
                tx[j].setX(50);
                tx[j].setY(x);
                tx[j].setText(lista2.get(j));
                julgamento.addView(tx[j]);

                tx2[k] = new TextView(this);
                tx2[k].setX(450);
                tx2[k].setY(x);
                tx2[k].setText(lista2.get(k));
                julgamento.addView(tx2[k]);

                x = x + 100;
            }

        }
    }


}
