package com.example.mojnovcanik;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class PodesavanjaActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_podesavanja);

        bottomNavigationView = findViewById(R.id.bottomNavView);
        bottomNavigationView.setSelectedItemId(0);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.page_1:
                        //Toast.makeText(MainActivity.this, "PRVA STRANICA", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                        startActivity(intent);
                        //setContentView(R.layout.activity_main);
                        break;

                    case R.id.page_2:
                        // Toast.makeText(MainActivity.this, "DRUGA STRANICA", Toast.LENGTH_SHORT).show();
                        Intent intent1 = new Intent(getApplicationContext(),PodesavanjaActivity.class);
                        startActivity(intent1);
                        // setContentView(R.layout.activity_podesavanja);
                        break;
                }


                return false;
            }
        });



    }
}