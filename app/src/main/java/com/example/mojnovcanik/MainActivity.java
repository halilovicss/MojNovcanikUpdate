package com.example.mojnovcanik;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> iznos;
    ArrayList<String> opis;
    ArrayList<String> datum;


    RecyclerView recyclerView;
    recyclerAdapter adapter;

    SQLHelper sqlHelper;

    int ukupanNovacUNovcaniku;
    SharedPreferences preferences;
    TextView tvUkupanNovac, txtStanjeUNovcaniku;

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        loadLocale();  //ucitavanje spremljenog jezika apliakcije
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        tvUkupanNovac = findViewById(R.id.tvUkupanNovac);
        bottomNavigationView = findViewById(R.id.bottomNavView);


        iznos = new ArrayList<>();
        opis = new ArrayList<>();
        datum = new ArrayList<>();
        bottomNavigationView.setSelectedItemId(0);


        //izbornik

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.page_1:
                        //Toast.makeText(MainActivity.this, "PRVA STRANICA", Toast.LENGTH_SHORT).show();

                        break;

                    case R.id.page_2:
                        // Toast.makeText(MainActivity.this, "DRUGA STRANICA", Toast.LENGTH_SHORT).show();
                        Intent intent1 = new Intent(getApplicationContext(), PodesavanjaActivity.class);
                        startActivity(intent1);
                        // setContentView(R.layout.activity_podesavanja);
                        break;
                }
                return false;
            }
        });

//kraj izbornika

        //POČETAK: UKUPNO NOVCA U NOVČANUKU
        preferences = getSharedPreferences("com.example.mojnovcanik", Context.MODE_PRIVATE);
        ukupanNovacUNovcaniku = preferences.getInt("novac", 0);
        ispisiStanjeNovca();
        //KRAJ: UKUPNO NOVCA U NOVČANUKU

        sqlHelper = new SQLHelper(this);

        storeDataInArrays();

        /*adapter = new recyclerAdapter(MainActivity.this,this,iznos,opis,datum);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));*/

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new recyclerAdapter(MainActivity.this, this, iznos, opis, datum);
        recyclerView.setAdapter(adapter);


    }

    //postavljanje jezika apliakcije
    private void setLocale(String lang) {
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
        ////save data to shared prefe
        SharedPreferences.Editor editor = getSharedPreferences("Settings", MODE_PRIVATE).edit();
        editor.putString("MY_Lang", lang);
        editor.apply();

    }

    ///ucitaj jezik aplikacije
    public void loadLocale() {
        SharedPreferences prefs = getSharedPreferences("Settings", Activity.MODE_PRIVATE);
        String language = prefs.getString("MY_Lang", "");
        setLocale(language);

    }


//kraj promjene jezika

    void storeDataInArrays() {
        Cursor cursor = sqlHelper.readAllData();
        if (cursor.getCount() == 0) {
            Toast.makeText(this, "No data!", Toast.LENGTH_SHORT).show();

        } else {

            while (cursor.moveToNext()) {
                //book_id.add(cursor.getString(0));
                iznos.add(cursor.getString(0));
                opis.add(cursor.getString(1));
                datum.add(cursor.getString(2));
            }
        }
    }

    public void povecajBrojNovca(View view) {
        //true = dodavanje novca na račun
        //false = skidanje novca sa računa
        napraviDijalog("Dodat novac",true);
    }

    public void smanjiBrojNovca(View view) {
        //true = dodavanje novca na račun
        //false = skidanje novca sa računa
        napraviDijalog("Oduzeti novac",false);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            recreate();
        }
    }

    public void napraviDijalog(String dodatiOpis,boolean tipTransakcije){
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        View view = getLayoutInflater().inflate(R.layout.alert_dialog_design,null);
        EditText etIznos = view.findViewById(R.id.etIznos);
        EditText etOpis = view.findViewById(R.id.etOpis);
        Button btnPotvrdi = view.findViewById(R.id.btnPotvrdi);
        TextView tvNaslov = view.findViewById(R.id.tvNaslov);


        tvNaslov.setText(dodatiOpis);

        builder.setView(view);
        AlertDialog dialog = builder.create();
        dialog.show();

        btnPotvrdi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tipTransakcije) {
                    String sIznos, sOpis, sDatum;
                    sIznos = etIznos.getText().toString().trim();
                    sOpis = etOpis.getText().toString().trim();
                    //sDatum = "20.4.2021. 01:00";
                    sDatum = new SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault()).format(new Date());
                    SQLHelper sqlHelper = new SQLHelper(MainActivity.this);
                    sqlHelper.dodajUListu(sIznos, sOpis, sDatum);

                    ukupanNovacUNovcaniku += Integer.parseInt(sIznos);
                    sacuvajStanjeNovca();
                    ispisiStanjeNovca();
                    dialog.dismiss();
                    recreate();
                } else if (!tipTransakcije) {
                    String sIznos, sOpis, sDatum;
                    sIznos = etIznos.getText().toString().trim();
                    sOpis = etOpis.getText().toString().trim();
                    //sDatum = "20.4.2021. 01:00";

                    sDatum = new SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault()).format(new Date());
                    if (Integer.parseInt(sIznos) > ukupanNovacUNovcaniku) {
                        Toast.makeText(MainActivity.this, "Nemate dovoljno para na računu!", Toast.LENGTH_SHORT).show();
                    } else {
                        ukupanNovacUNovcaniku -= Integer.parseInt(sIznos);
                        SQLHelper sqlHelper = new SQLHelper(MainActivity.this);
                        sIznos = "-" + etIznos.getText().toString().trim();
                        sqlHelper.dodajUListu(sIznos, sOpis, sDatum);

                        //ukupanNovacUNovcaniku -= Integer.parseInt(sIznos);
                        sacuvajStanjeNovca();
                        ispisiStanjeNovca();
                        dialog.dismiss();
                        recreate();
                    }
                }


            }
        });


    }

    public void sacuvajStanjeNovca(){
        preferences = getSharedPreferences("com.example.mojnovcanik",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("novac",ukupanNovacUNovcaniku);
        editor.commit();
    }
    public void ispisiStanjeNovca(){
        tvUkupanNovac.setText(""+ukupanNovacUNovcaniku);
    }

    public void ocisti(View view) {
        ukupanNovacUNovcaniku = 0;
        sacuvajStanjeNovca();
        ispisiStanjeNovca();

        iznos.clear();
        opis.clear();
        datum.clear();

        SQLHelper sqlHelper = new SQLHelper(MainActivity.this);
        sqlHelper.obrisiSvePodatkeIzBaze();
        recreate();

    }


}