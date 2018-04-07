package com.example.t_per.testarwebservice;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(android.os.Build.VERSION.SDK_INT > 9){
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        final ArrayList<Integer> lista3 = new ArrayList<Integer>();
        final ArrayList<String> lista2 = new ArrayList<String>();

        UsuarioDAO dao = new UsuarioDAO();
        ArrayList<Hierarquia> lista = new ArrayList<Hierarquia>();

        lista = dao.buscarTodasHierarquias();
        for(Hierarquia hierarq : lista){
            lista2.add(hierarq.getNome());
            lista3.add(hierarq.getIdhierarquia());
        }

        final Spinner s = (Spinner)findViewById(R.id.spinner);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, lista2);
        ArrayAdapter<String> spinnerArrayAdapter = arrayAdapter;
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        s.setAdapter(spinnerArrayAdapter);

        final int selecionado = 0;

        Button b = (Button) findViewById(R.id.button);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int selecionado = lista3.get(s.getSelectedItemPosition()).intValue();

                Intent it = new Intent(MainActivity.this, TelaCriterio.class);
                it.putExtra("selecionado", selecionado);
                startActivity(it);
            }
        });

    }
}
