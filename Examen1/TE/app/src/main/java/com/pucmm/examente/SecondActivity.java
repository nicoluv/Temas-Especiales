package com.pucmm.examente;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class SecondActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener{


    EditText articulo,descripcion, precio;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);


        articulo = findViewById(R.id.etArticulo);
        descripcion = findViewById(R.id.etDesc);
        precio = findViewById(R.id.etPrecio);


    }

    public void limpiar(View view){
        ViewGroup viewgroup=(ViewGroup)view.getParent();

        for (int i=0;i<viewgroup.getChildCount();i++) {
            View aux=viewgroup.getChildAt(i);

            if(aux instanceof EditText){
                ((EditText) aux).setText("");
            }

        }

    }

    public void volver(View view){
        onBackPressed();
    }

    public void valido(View view){

        Boolean val = true;
        String tam = "";
        if(articulo.getText().toString().isEmpty()){
            articulo.setError("Necesita ingresar un articulo");
            val = false;
        }
        if(descripcion.getText().toString().isEmpty()){
            descripcion.setError("Necesita ingresar una descripcion.");
            val = false;
        }
        if(precio.getText().toString().isEmpty()){
            precio.setError("Necesita ingresar un precio");
            val = false;
        }


        if(val){

            Intent intent = new Intent(this,MainActivity.class);
            intent.putExtra("ARTICULO",this.articulo.getText().toString());
            intent.putExtra("PRECIO",this.precio.getText().toString());
            intent.putExtra("DESCRIPCION",this.descripcion.getText().toString());
            startActivity(intent);
            finish();


        }
    }


    @Override
    public void onClick(View v) {

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}

