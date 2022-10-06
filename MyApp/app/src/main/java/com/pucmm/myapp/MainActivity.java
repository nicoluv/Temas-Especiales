package com.pucmm.myapp;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {


    //Toolbar toolbar;
    EditText etNombre,etApellido;
    Spinner spinGenero;
    TextView tvFecha,tvLenguaje;
    RadioButton rbnSi,rbnNo;


    ArrayList<CheckBox> arrLenguajes = new ArrayList<>();
    List<String> listaGeneros;
    RadioGroup radioGroup;

    private int dia,mes,anio;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       // getSupportActionBar().setDisplayHomeAsUpEnabled(true);
  /*      toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);;
*/
        tvFecha = findViewById(R.id.fecha);
        tvFecha.setOnClickListener(this);
        
        spinGenero = findViewById(R.id.spinnerGenero);
        listaGeneros = new ArrayList<>();
        listaGeneros.add("Seleccionar");
        listaGeneros.add("Masculino");
        listaGeneros.add("Femenino");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listaGeneros);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinGenero.setAdapter(adapter);

        etNombre = findViewById(R.id.name);
        etApellido = findViewById(R.id.apellido);
        rbnSi = findViewById(R.id.radioButtonSi);
        rbnNo = findViewById(R.id.radioButtonNo);
        tvLenguaje = findViewById(R.id.lenguaje);
        radioGroup = findViewById(R.id.radioGroup);

        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            System.out.println("checked is "+rbnNo.getId()+" and "+checkedId);
            invisible(rbnSi.getId() != checkedId);


        });

        arrLenguajes.add(this.<CheckBox>findViewById(R.id.java));
        arrLenguajes.add(this.<CheckBox>findViewById(R.id.js));
        arrLenguajes.add(this.<CheckBox>findViewById(R.id.c));
        arrLenguajes.add(this.<CheckBox>findViewById(R.id.c2));
        arrLenguajes.add(this.<CheckBox>findViewById(R.id.goland));
        arrLenguajes.add(this.<CheckBox>findViewById(R.id.python));


    }



    public void ClearAll(View view){
        ViewGroup viewgroup=(ViewGroup)view.getParent();

        for (int i=0;i<viewgroup.getChildCount();i++) {
            View aux=viewgroup.getChildAt(i);

            if(aux instanceof EditText){
                ((EditText) aux).setText("");
            }

            if(aux instanceof Spinner){
                ((Spinner) aux).setSelection(0);
            }
            if(aux instanceof CheckBox){
                ((CheckBox) aux).setChecked(false);
            }



        }

        tvFecha.setText("");
        rbnNo.setChecked(false);
        rbnSi.setChecked(true);
    }

    public void invisible(Boolean hide){

        int visible = (hide)?View.INVISIBLE:View.VISIBLE;

        for(CheckBox aux:arrLenguajes){
            aux.setVisibility(visible);
            aux.setChecked(false);
        }
    }

    public void valid(View view){

        Boolean val = true;
        String tam = "";
        if(etNombre.getText().toString().isEmpty()){
            etNombre.setError("Necesita ingresar un nombre.");
            val = false;
        }
        if(etApellido.getText().toString().isEmpty()){
            etApellido.setError("Necesita ingresar un apellido.");
            val = false;
        }
        if(spinGenero.getSelectedItemPosition() == 0){
            TextView errorText = (TextView)spinGenero.getSelectedView();
            errorText.setError("");
            errorText.setTextColor(Color.RED);
            errorText.setText("Seleccionar un genero.");
            val = false;
        }

        if(rbnSi.isChecked()){
            String len = "";
            for(CheckBox aux:arrLenguajes){
                if(aux.isChecked()){
                    len += " "+aux.getText().toString()+",";
                }
            }
            if(len.length() > 2)
                tam = len.substring(0,len.length()-1);
            if(len.length() <= 1){
                Toast.makeText(MainActivity.this,"Necesita seleccionar como minimo (1) un lenguaje",Toast.LENGTH_SHORT).show();
                val = false;
            }

        }

        if(val){

            Intent intent = new Intent(this,InfoActivity.class);
            intent.putExtra("NOMBRE",this.etNombre.getText().toString());
            intent.putExtra("APELLIDO",this.etApellido.getText().toString());
            intent.putExtra("GENERO",this.spinGenero.getSelectedItem().toString());
            intent.putExtra("FECHA",this.tvFecha.getText().toString());
            intent.putExtra("LENGUAJES",tam);
            startActivity(intent);
            finish();


        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        final Calendar c = Calendar.getInstance();
        dia = c.get(Calendar.DAY_OF_MONTH);
        mes = c.get(Calendar.MONTH);
        anio = c.get(Calendar.YEAR);

        DatePickerDialog datePicker = new DatePickerDialog(this, (view, year, month, dayOfMonth) -> {
            month++;
            tvFecha.setText(dayOfMonth+"/"+month+"/"+year);
        },anio,mes,dia);
        datePicker.show();

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String gender = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(),gender,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        parent.setSelection(0);
    }


}