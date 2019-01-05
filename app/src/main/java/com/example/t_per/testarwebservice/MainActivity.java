package com.example.t_per.testarwebservice;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(android.os.Build.VERSION.SDK_INT > 9){
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Aviso");
        builder.setMessage("Esse Pin não está registrado ou ainda não foi liberado!");
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
            }
        });
        final AlertDialog alerta = builder.create();


        Button b = findViewById(R.id.button);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText p = findViewById(R.id.pin);
                int pin = Integer.parseInt(p.getText().toString());
                EditText n = findViewById(R.id.nome);
                String nome = n.getText().toString();
                UsuarioDAO dao = new UsuarioDAO();
                if(dao.getHierarquiaPorPin(pin) == 0){
                    alerta.show();
                }else{
                    Intent it = new Intent(MainActivity.this, TelaJulgamento.class);
                    it.putExtra("pin", pin);
                    it.putExtra("nome", nome);
                    startActivity(it);
                }
            }
        });

    }
}
