package com.example.t_per.testarwebservice;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.icu.text.RelativeDateTimeFormatter;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AlertDialog;
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
        final String nome = extras.getString("nome");

        HorizontalScrollView[] hs = new HorizontalScrollView[100];
        final CheckBox[][] cb = new CheckBox[100][20];
        TextView[] txEsquerda = new TextView[100];
        TextView[] txDireita = new TextView[100];
        TextView titulo = new TextView(this);

        RelativeLayout julgamento = findViewById(R.id.julgamentos);

        final UsuarioDAO dao = new UsuarioDAO();
        final int hierarquiaId = dao.getHierarquiaPorPin(pin);
        final ArrayList<Criterio> listaCriterios = dao.buscarTodosCriterios(hierarquiaId);
        final ArrayList<String> listaNomeCriterios = new ArrayList<String>();
        for (Criterio cri : listaCriterios) {
            listaNomeCriterios.add(cri.getNome());
        }
        titulo.setText("Criterio X Criterio");
        titulo.setTextColor(Color.BLACK);
        titulo.setWidth(700);
        titulo.setHeight(150);
        titulo.setTextSize(30);
        titulo.setY(50);
        titulo.setX(330);
        julgamento.addView(titulo);
        int y = 200;
        int linhas = 0;
        for (int j = 0; j < listaNomeCriterios.size(); j++) {
            for (int k = j + 1; k < listaNomeCriterios.size(); k++) {
                RelativeLayout ll = new RelativeLayout(this);
                ll.setX(40);
                ll.setY(y + 100);
                RelativeLayout.LayoutParams l = new RelativeLayout.LayoutParams(1200,RelativeLayout.LayoutParams.WRAP_CONTENT);
                l.height = 100;
                ll.setLayoutParams(l);

                int esq = 9;
                int x = 10;
                for(int c = 0; c < 9; c++){
                    cb[linhas][c] = new CheckBox(this);
                    TextView votos = new TextView(this);
                    votos.setText(String.valueOf(esq));
                    votos.setX(x + 25);
                    votos.setY(10);
                    votos.setTypeface(null, Typeface.BOLD);
                    if(c==8){
                        votos.setTextColor(Color.BLACK);
                    }else{
                        votos.setTextColor(Color.parseColor("#2f0591"));
                    }
                    cb[linhas][c].setX(x);
                    cb[linhas][c].setY(50);
                    ll.addView(cb[linhas][c]);
                    ll.addView(votos);
                    esq--;
                    x = x + 60;
                }
                int dir = 2;
                for(int c = 9; c <= 16; c++){
                    cb[linhas][c] = new CheckBox(this);
                    TextView votos = new TextView(this);
                    votos.setText(String.valueOf(dir));
                    votos.setX(x + 25);
                    votos.setY(10);
                    votos.setTypeface(null, Typeface.BOLD);
                    votos.setTextColor(Color.parseColor("#7a6707"));
                    cb[linhas][c].setX(x);
                    cb[linhas][c].setY(50);
                    ll.addView(cb[linhas][c]);
                    ll.addView(votos);
                    dir++;
                    x = x + 60;
                }
                julgamento.addView(ll);


                txEsquerda[j] = new TextView(this);
                txEsquerda[j].setX(20);
                txEsquerda[j].setY(y + 5);
                txEsquerda[j].setTextColor(Color.parseColor("#2f0591"));
                txEsquerda[j].setTypeface(null, Typeface.BOLD);
                txEsquerda[j].setWidth(480);
                txEsquerda[j].setTextSize(15);
                txEsquerda[j].setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
                txEsquerda[j].setText(listaNomeCriterios.get(j));
                julgamento.addView(txEsquerda[j]);

                TextView vs = new TextView(this);
                vs.setX(550);
                vs.setY(y);
                vs.setTextColor(Color.BLACK);
                vs.setWidth(30);
                vs.setTextSize(20);
                vs.setText("X");
                julgamento.addView(vs);

                txDireita[k] = new TextView(this);
                txDireita[k].setX(620);
                txDireita[k].setY(y + 5);
                txDireita[k].setTextColor(Color.parseColor("#7a6707"));
                txDireita[k].setTypeface(null, Typeface.BOLD);
                txDireita[k].setWidth(480);
                txDireita[k].setTextSize(15);
                txDireita[k].setText(listaNomeCriterios.get(k));
                julgamento.addView(txDireita[k]);

                y = y + 250;
                linhas++;
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

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Aviso");
        builder.setMessage("É obrigatório marcar apenas um valor por comparação!");
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
            }
        });
        final AlertDialog alerta = builder.create();

        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int verifica = 0;
                int verifica2 = 0;
                ArrayList<Integer > listaVoto = new ArrayList<Integer>();
                for (int j = 0; j < linha; j++) {
                    for (int c = 0; c <= 16; c++) {
                        if(cb[j][c].isChecked()){
                            listaVoto.add(c);
                            verifica2++;
                        }
                    }
                    if(verifica2 == 1){
                        verifica++;
                    }
                    verifica2 = 0;
                }
                if(verifica != linha){
                    alerta.show();
                }else{
                    dao.inserirVoto(hierarquiaId, "crixcri", listaVoto, nome);
                    if(dao.possuiSubcri(hierarquiaId)){
                        Intent it = new Intent(TelaCriterioXCriterio.this, TelaCriterioXSubCriterio.class);
                        it.putExtra("pin", pin);
                        it.putExtra("nome", nome);
                        startActivity(it);
                    }else if(dao.possuiAlt(hierarquiaId)){
                        Intent it = new Intent(TelaCriterioXCriterio.this, TelaCriterioXAlternativa.class);
                        it.putExtra("pin", pin);
                        it.putExtra("nome", nome);
                        startActivity(it);
                    }
                }
            }
        });

        voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(TelaCriterioXCriterio.this, TelaJulgamento.class);
                it.putExtra("pin", pin);
                it.putExtra("nome", nome);
                startActivity(it);
            }
        });

        ViewGroup.LayoutParams params = julgamento.getLayoutParams();
        params.height= y + 200;
    }


}
