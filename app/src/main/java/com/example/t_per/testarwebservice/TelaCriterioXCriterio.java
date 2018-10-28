package com.example.t_per.testarwebservice;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.icu.text.RelativeDateTimeFormatter;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
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
        final int pin = extras.getInt("pin");

        HorizontalScrollView[] hs = new HorizontalScrollView[100];
        final CheckBox[][] cb = new CheckBox[100][20];
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

        final UsuarioDAO dao = new UsuarioDAO();
        final int hierarquiaId = dao.getHierarquiaPorPin(pin);
        final ArrayList<Criterio> listaCriterios = dao.buscarTodosCriterios(hierarquiaId);
        final ArrayList<String> listaNomeCriterios = new ArrayList<String>();
        for (Criterio cri : listaCriterios) {
            listaNomeCriterios.add(cri.getNome());
        }
        titulo.setText("Criterio X Criterio");
        titulo.setTextColor(Color.BLACK);
        titulo.setWidth(500);
        titulo.setHeight(150);
        titulo.setTextSize(30);
        titulo.setY(50);
        titulo.setX(30);
        julgamento.addView(titulo);
        int x = 200;
        int linhas = 0;
        for (int j = 0; j < listaNomeCriterios.size(); j++) {
            for (int k = j + 1; k < listaNomeCriterios.size(); k++) {
                hs[linhas] = new HorizontalScrollView(this);
                hs[linhas].setX(100);
                hs[linhas].setY(x - 10);
                RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(400,RelativeLayout.LayoutParams.WRAP_CONTENT);
                hs[linhas].setLayoutParams(lp);
                LinearLayout ll = new LinearLayout(this);
                int esq = 9;
                for(int c = 0; c <= 9; c++){
                    cb[linhas][c] = new CheckBox(this);
                    cb[linhas][c].setText(String.valueOf(esq));
                    ll.addView(cb[linhas][c]);
                    esq--;
                }
                int dir = 1;
                for(int c = 10; c <= 18; c++){
                    cb[linhas][c] = new CheckBox(this);
                    cb[linhas][c].setText(String.valueOf(dir));
                    ll.addView(cb[linhas][c]);
                    dir++;
                }
                hs[linhas].addView(ll);
                julgamento.addView(hs[linhas]);


                txEsquerda[j] = new TextView(this);
                txEsquerda[j].setX(20);
                txEsquerda[j].setY(x);
                txEsquerda[j].setText(listaNomeCriterios.get(j));
                julgamento.addView(txEsquerda[j]);

                txDireita[k] = new TextView(this);
                txDireita[k].setX(530);
                txDireita[k].setY(x);
                txDireita[k].setText(listaNomeCriterios.get(k));
                julgamento.addView(txDireita[k]);

                x = x + 100;
                linhas++;
            }
        }
        final int linha = linhas;

        Button voltar = new Button(this);
        voltar.setWidth(20);
        voltar.setHeight(10);
        voltar.setText("Voltar");
        voltar.setY(x);
        voltar.setX(100);
        voltar.setBackgroundColor(Color.parseColor("#303F9F"));
        voltar.setTextColor(Color.WHITE);
        julgamento.addView(voltar);

        Button enviar = new Button(this);
        enviar.setWidth(20);
        enviar.setHeight(10);
        enviar.setText("Votar");
        enviar.setY(x);
        enviar.setX(300);
        enviar.setBackgroundColor(Color.parseColor("#303F9F"));
        enviar.setTextColor(Color.WHITE);
        julgamento.addView(enviar);

        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<Integer > listaVoto = new ArrayList<Integer>();
                //int[] vetor = new int[linha];
                for (int j = 0; j < linha; j++) {
                    Log.i("linha", String.valueOf(j));
                    for (int c = 0; c <= 18; c++) {
                        Log.i("coluna", String.valueOf(c));
                        if(cb[j][c].isChecked()){
                            listaVoto.add(c);
                            //vetor[j] = c;
                        }
                    }
                }
                dao.inserirVoto(hierarquiaId, "crixcri", listaVoto);
            }
        });

        voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(TelaCriterioXCriterio.this, TelaJulgamento.class);
                it.putExtra("pin", pin);
                startActivity(it);
            }
        });

        ViewGroup.LayoutParams params = julgamento.getLayoutParams();
        params.height= x + 200;
    }


}
