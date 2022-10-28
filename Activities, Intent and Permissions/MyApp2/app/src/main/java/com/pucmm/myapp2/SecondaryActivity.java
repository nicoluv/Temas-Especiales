package com.pucmm.myapp2;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import com.google.android.material.snackbar.Snackbar;

import java.util.Arrays;
import java.util.List;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.CALL_PHONE;
import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.READ_CONTACTS;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;


public class SecondaryActivity extends AppCompatActivity {


    private Button storage,location,camera,phone,contacts;
    private List<Button> buttons;
    private String[] savedPermissions2 = new String[]{READ_EXTERNAL_STORAGE,ACCESS_FINE_LOCATION,CAMERA,CALL_PHONE,READ_CONTACTS};
    private static int ind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.secondary_activity);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        buttons = Arrays.asList(new Button[]{storage = findViewById(R.id.btnStorage), location = findViewById(R.id.btnLocation), camera = findViewById(R.id.btnCamara)
                ,phone = findViewById(R.id.btnPhone),contacts = findViewById(R.id.btnContacts)});



    }

    public void setIntent(View v){
        Button selected = ((Button) v);
        ind = 0;
        for (Button aux:buttons){
            if(aux.getId() == selected.getId()){
                if(checkPermission(savedPermissions2[ind])){
                    Snackbar mySnackbar = Snackbar.make(v,"Permission granted",Snackbar.LENGTH_LONG);
                    mySnackbar.setAction("Continue", new Listener());
                    mySnackbar.show();
                    break;
                }
                else {
                    Snackbar.make(v,"Please, request permission",Snackbar.LENGTH_LONG).show();
                }
            }
            ind++;
        }
    }

    public Boolean checkPermission(String permission){
        int permissionCode = ContextCompat.checkSelfPermission(getApplicationContext(),permission);
        return permissionCode == PackageManager.PERMISSION_GRANTED;
    }

    public class Listener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            Intent intent;
            System.out.println(ind);
            switch (ind){
                case 0: intent = new Intent(Settings.ACTION_INTERNAL_STORAGE_SETTINGS);
                    break;
                case 1: intent = new Intent(Intent.ACTION_VIEW);
                    break;
                case 2: intent = new Intent("android.media.action.IMAGE_CAPTURE");
                    break;
                case 3: intent = new Intent(Intent.ACTION_CALL);
                    intent.setData(Uri.parse("tel:849-287-8794"));
                    break;
                default: intent = new Intent(Intent.ACTION_PICK);
                    intent.setType(ContactsContract.Contacts.CONTENT_TYPE);


            }

            startActivity(intent);

        }
    }











}
