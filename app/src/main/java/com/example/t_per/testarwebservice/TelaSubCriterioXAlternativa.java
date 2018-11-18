package com.example.t_per.testarwebservice;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AlertDialog;
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

        HorizontalScrollView[] hs = new HorizontalScrollView[100];
        final CheckBox[][] cb = new CheckBox[100][20];
        TextView[] txEsquerda = new TextView[100];
        TextView[] txDireita = new TextView[100];
        TextView titulo = new TextView(this);
        TextView[] subTitulos = new TextView[100];

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
            ArrayList<SubCriterio> listaSubCriterios = dao.buscarTodosSubCriterios(cri.getIdcirterio(), hierarquiaId);
            cri.setSub(listaSubCriterios);
            if(listaSubCriterios != null){
                for (SubCriterio subcri : listaSubCriterios) {
                    ArrayList<Alternativa> listaAlternativas = dao.buscarTodasAlternativasPorHierarquia(hierarquiaId);
                    subcri.setAlt(listaAlternativas);
                }
            }
        }
        titulo.setText("Alternativa X Alternativa");
        titulo.setTextColor(Color.BLACK);
        titulo.setWidth(700);
        titulo.setHeight(150);
        titulo.setTextSize(30);
        titulo.setY(50);
        titulo.setX(235);
        julgamento.addView(titulo);
        int y = 200;
        int linhas = 0;
        for (int j = 0; j < listaCriterios.size(); j++) {
            if(listaCriterios.get(j).getSub() != null){
                for (int k = 0 ; k < listaCriterios.get(j).getSub().size(); k++) {
                    subTitulos[j] = new TextView(this);
                    subTitulos[j].setText("Julgamento para alternativas do subcritério: " + listaCriterios.get(j).getSub().get(k).getNome() + " do critério: " + listaCriterios.get(j).getNome());
                    subTitulos[j].setTextColor(Color.BLACK);
                    subTitulos[j].setWidth(1000);
                    subTitulos[j].setHeight(150);
                    subTitulos[j].setTextSize(20);
                    subTitulos[j].setY(y + 10);
                    subTitulos[j].setX(100);
                    subTitulos[j].setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
                    subTitulos[j].setTypeface(null, Typeface.BOLD);
                    julgamento.addView(subTitulos[j]);
                    y = y + 150;
                    for (int l = 0 ; l < listaCriterios.get(j).getSub().get(k).getAlt().size(); l++) {
                        for (int s = l + 1 ; s < listaCriterios.get(j).getSub().get(k).getAlt().size(); s++) {
                            RelativeLayout ll = new RelativeLayout(this);
                            ll.setX(40);
                            ll.setY(y + 100);
                            RelativeLayout.LayoutParams u = new RelativeLayout.LayoutParams(1200,RelativeLayout.LayoutParams.WRAP_CONTENT);
                            u.height = 100;
                            ll.setLayoutParams(u);

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

                            txEsquerda[l] = new TextView(this);
                            txEsquerda[l].setX(20);
                            txEsquerda[l].setY(y);
                            txEsquerda[l].setTextColor(Color.parseColor("#2f0591"));
                            txEsquerda[l].setTypeface(null, Typeface.BOLD);
                            txEsquerda[l].setWidth(480);
                            txEsquerda[l].setTextSize(20);
                            txEsquerda[l].setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
                            txEsquerda[l].setText(listaCriterios.get(j).getSub().get(k).getAlt().get(l).getNome());
                            julgamento.addView(txEsquerda[l]);

                            TextView vs = new TextView(this);
                            vs.setX(550);
                            vs.setY(y);
                            vs.setTextColor(Color.BLACK);
                            vs.setWidth(30);
                            vs.setTextSize(20);
                            vs.setText("X");
                            julgamento.addView(vs);

                            txDireita[s] = new TextView(this);
                            txDireita[s].setX(620);
                            txDireita[s].setY(y);
                            txDireita[s].setTextColor(Color.parseColor("#7a6707"));
                            txDireita[s].setTypeface(null, Typeface.BOLD);
                            txDireita[s].setWidth(480);
                            txDireita[s].setTextSize(20);
                            txDireita[s].setText(listaCriterios.get(j).getSub().get(k).getAlt().get(s).getNome());
                            julgamento.addView(txDireita[s]);

                            y = y + 250;
                            linhas++;
                        }
                    }

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
                    dao.inserirVoto(hierarquiaId, "subxalt", listaVoto);
                    Intent it = new Intent(TelaSubCriterioXAlternativa.this, TelaJulgamento.class);
                    it.putExtra("pin", pin);
                    startActivity(it);
                }
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
        params.height= y + 200;
    }
}
