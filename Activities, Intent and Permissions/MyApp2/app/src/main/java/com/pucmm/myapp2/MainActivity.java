package com.pucmm.myapp2;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.view.View;
import android.widget.Switch;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.google.android.material.snackbar.Snackbar;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.CALL_PHONE;
import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.READ_CONTACTS;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_PERMISSION = 200;

    private Switch storage,location,camera,phone,contacts;

    private ArrayList<String> permissions = new ArrayList<>();
    private List<Switch> list;

    private String[] savedPermissions = new String[]{READ_EXTERNAL_STORAGE,ACCESS_FINE_LOCATION,CAMERA,CALL_PHONE,READ_CONTACTS};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        list = Arrays.asList(new Switch[]{storage = findViewById(R.id.swStorage), location = findViewById(R.id.swLocation), camera = findViewById(R.id.swCamera)
                ,phone = findViewById(R.id.swPhone),contacts = findViewById(R.id.swContacts)
        });

        onStartPermissions();

    }
    public void requestPermission(){
        String[] arrPermission = null;
        arrPermission = permissions.toArray(new String[permissions.size()]);
        ActivityCompat.requestPermissions(this,arrPermission,REQUEST_CODE_PERMISSION);
    }

    public Boolean checkPermission(String permission){
        int permissionCode = ContextCompat.checkSelfPermission(getApplicationContext(),permission);
        return permissionCode == PackageManager.PERMISSION_GRANTED;
    }

    public void setPermissions(View v){
        Intent intent = new Intent(this,SecondaryActivity.class);
        startActivity(intent);
        int ind = 0;
        Boolean selection = false;
        permissions = new ArrayList<>();
        for (Switch aux:list){
            if(aux.isChecked()){

                switch (ind){
                    case 0 :
                        if( !(checkPermission(READ_EXTERNAL_STORAGE))){
                            permissions.add(READ_EXTERNAL_STORAGE);
                            selection = true;
                        }
                        break;
                    case 1 :
                        if( !(checkPermission(ACCESS_FINE_LOCATION))){
                            permissions.add(ACCESS_FINE_LOCATION);
                            selection = true;
                        }
                        break;
                    case 2 :
                        if( !(checkPermission(CAMERA))) {
                            permissions.add(CAMERA);
                            selection = true;
                        }
                        break;
                    case 3 :
                        if( !(checkPermission(CALL_PHONE))) {
                            permissions.add(CALL_PHONE);
                            selection = true;
                        }

                        break;
                    default:
                        if( !(checkPermission(READ_CONTACTS))) {
                            permissions.add(READ_CONTACTS);
                            selection = true;
                        }
                }
            }
            ind++;
        }
        if(selection)
            requestPermission();
    }

    public void onStartPermissions (){

        int ind = 0;
        for (String aux:savedPermissions){
            if(checkPermission(aux)){
                list.get(ind).setChecked(true);
                permissions.add(aux);
            }
            ind++;
        }
    }


    public void havePermission(View v){
        int ind = 0;
        Switch selected = ((Switch) v);

        for (Switch aux:list){
            if(selected.getId() == aux.getId()){
                if(checkPermission(savedPermissions[ind])){
                    Snackbar.make(v,"Permission is alredy granted",Snackbar.LENGTH_LONG).show();
                    selected.setChecked(true);
                }
            }
            ind++;
        }

    }


    public void exit(View v){

        System.exit(0);
    }







}