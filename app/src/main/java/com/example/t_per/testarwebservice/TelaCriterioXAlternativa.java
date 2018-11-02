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
import android.widget.CheckBox;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Thiago on 17/03/2018.
 */

public class TelaCriterioXAlternativa extends AppCompatActivity {
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
        ArrayList<String> listaNomeCriterios = new ArrayList<String>();
        for (Criterio cri : listaCriterios) {
            listaNomeCriterios.add(cri.getNome());
            ArrayList<SubCriterio> listaSubCriterios = dao.buscarTodosSubCriterios(cri.getIdcirterio(), hierarquiaId);
            cri.setSub(listaSubCriterios);
            if(listaSubCriterios == null){
                ArrayList<Alternativa> listaAlternativas = dao.buscarTodasAlternativasPorHierarquia(hierarquiaId);
                cri.setAlt(listaAlternativas);
            }

        }
        titulo.setText("Criterio X Alternativa");
        titulo.setTextColor(Color.BLACK);
        titulo.setWidth(700);
        titulo.setHeight(150);
        titulo.setTextSize(30);
        titulo.setY(50);
        titulo.setX(330);
        julgamento.addView(titulo);
        int y = 200;
        int linhas = 0;
        for (int j = 0; j < listaCriterios.size(); j++) {
            if(listaCriterios.get(j).getAlt().size() > 0){
                for (int k = 0 ; k < listaCriterios.get(j).getAlt().size(); k++) {
                    hs[linhas] = new HorizontalScrollView(this);
                    hs[linhas].setX(350);
                    hs[linhas].setY(y - 10);
                    RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(400,RelativeLayout.LayoutParams.WRAP_CONTENT);
                    hs[linhas].setLayoutParams(lp);
                    LinearLayout ll = new LinearLayout(this);
                    int esq = 9;
                    for(int c = 0; c < 9; c++){
                        cb[linhas][c] = new CheckBox(this);
                        cb[linhas][c].setText(String.valueOf(esq));
                        ll.addView(cb[linhas][c]);
                        esq--;
                    }
                    int dir = 2;
                    for(int c = 9; c <= 16; c++){
                        cb[linhas][c] = new CheckBox(this);
                        cb[linhas][c].setText(String.valueOf(dir));
                        ll.addView(cb[linhas][c]);
                        dir++;
                    }
                    hs[linhas].addView(ll);
                    julgamento.addView(hs[linhas]);

                    txEsquerda[j] = new TextView(this);
                    txEsquerda[j].setX(20);
                    txEsquerda[j].setY(y);
                    txEsquerda[j].setWidth(350);
                    txEsquerda[j].setText(listaCriterios.get(j).getNome());
                    julgamento.addView(txEsquerda[j]);

                    txDireita[k] = new TextView(this);
                    txDireita[k].setX(800);
                    txDireita[k].setY(y);
                    txDireita[k].setWidth(350);
                    txDireita[k].setText(listaCriterios.get(j).getAlt().get(k).getNome());
                    julgamento.addView(txDireita[k]);

                    y = y + 100;
                    linhas++;
                }
            }
        }

        final int linha = linhas;

        Button voltar = new Button(this);
        voltar.setWidth(20);
        voltar.setHeight(10);
        voltar.setText("Voltar");
        voltar.setY(y);
        voltar.setX(370);
        voltar.setBackgroundColor(Color.parseColor("#303F9F"));
        voltar.setTextColor(Color.WHITE);
        julgamento.addView(voltar);

        Button enviar = new Button(this);
        enviar.setWidth(20);
        enviar.setHeight(10);
        enviar.setText("Votar");
        enviar.setY(y);
        enviar.setX(570);
        enviar.setBackgroundColor(Color.parseColor("#303F9F"));
        enviar.setTextColor(Color.WHITE);
        julgamento.addView(enviar);

        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<Integer > listaVoto = new ArrayList<Integer>();
                for (int j = 0; j < linha; j++) {
                    for (int c = 0; c <= 16; c++) {
                        if(cb[j][c].isChecked()){
                            listaVoto.add(c);
                        }
                    }
                }
                dao.inserirVoto(hierarquiaId, "crixalt", listaVoto);
            }
        });

        voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(TelaCriterioXAlternativa.this, TelaJulgamento.class);
                it.putExtra("pin", pin);
                startActivity(it);
            }
        });


        ViewGroup.LayoutParams params = julgamento.getLayoutParams();
        params.height= y + 200;
    }
}
