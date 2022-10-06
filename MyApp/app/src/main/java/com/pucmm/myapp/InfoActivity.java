package com.pucmm.myapp;

import android.content.Intent;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class InfoActivity extends AppCompatActivity{

    private TextView nombre,genero,lenguajes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        String fecha = intent.getStringExtra("FECHA");
        String tam = intent.getStringExtra("LENGUAJES");

        nombre = findViewById(R.id.tvNombre);
        genero = findViewById(R.id.tvGenero);
        lenguajes = findViewById(R.id.tvLenguaje);

        nombre.setText("Hola!, mi nombre es: "+intent.getStringExtra("NOMBRE").trim()+" "+intent.getStringExtra("APELLIDO").trim()+".");

        if( !(fecha.isEmpty()) ){
            genero.setText("Soy "+intent.getStringExtra("GENERO")+", y naci en fecha "+fecha+".");
        }
        else{
            genero.setText("Soy "+intent.getStringExtra("GENERO")+".");
        }

        if(tam.isEmpty()){
            lenguajes.setText("No me gusta programar");
        }
        else{
            lenguajes.setText("Me gusta programar. Mis lenguajes favoritos son: "+tam+".");
        }


    }


}
