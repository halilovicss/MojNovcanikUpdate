package com.example.mojnovcanik;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Locale;

public class PodesavanjaActivity extends AppCompatActivity {
    private RadioButton radioenglis, radioexyu;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadLocale();
        setContentView(R.layout.activity_podesavanja);
        bottomNavigationView = findViewById(R.id.bottomNavView);
        bottomNavigationView.setSelectedItemId(0);
        radioenglis = findViewById(R.id.radio_button_1);
        radioexyu = findViewById(R.id.radio_button_2);
        radioenglis.isChecked();
        radioenglis.setChecked(Update("ENGLISH_ONE"));
        radioexyu.setChecked(Update("EXYU_ONE"));


        radioenglis.setOnCheckedChangeListener((buttonView, one_isChecked) -> {
            SaveIntoSharedPref("ENGLISH_ONE", one_isChecked);
            setLocale("bs");

        });

        radioexyu.setOnCheckedChangeListener((buttonView, two_isChecked) -> {
            SaveIntoSharedPref("EXYU_ONE", two_isChecked);
            setLocale("en");

        });
        ///ucitaj jezik


        bottomNavigationView.setOnNavigationItemSelectedListener(this::onNavigationItemSelected);


    }


    ////postavljanje jezika
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

    //ucitavanje zadatog jezika
    public void loadLocale() {
        SharedPreferences prefs = getSharedPreferences("Settings", Activity.MODE_PRIVATE);
        String language = prefs.getString("MY_Lang", "");
        setLocale(language);


    }


    ///buttoni
    private void SaveIntoSharedPref(String key, boolean value) {
        SharedPreferences sp = getSharedPreferences("ENGLISH", MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(key, value);
        editor.apply();


    }

    private boolean Update(String key) {
        SharedPreferences sp = getSharedPreferences("ENGLISH", MODE_PRIVATE);
        return sp.getBoolean(key, false);

    }

    private boolean onNavigationItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.page_1:
                //Toast.makeText(MainActivity.this, "PRVA STRANICA", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                //setContentView(R.layout.activity_main);
                break;


        }


        return false;
    }
}