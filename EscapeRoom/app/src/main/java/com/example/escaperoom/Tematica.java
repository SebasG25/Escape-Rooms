package com.example.escaperoom;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Tematica extends AppCompatActivity {

    Button b_tematica1, b_tematica2, b_tematica3, b_tematica4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tematica);
        connect();
    }

    private void connect() {
        b_tematica1 = findViewById(R.id.b_opcion1);
        b_tematica2 = findViewById(R.id.b_opcion2);
        b_tematica3 = findViewById(R.id.b_opcion3);
        b_tematica4 = findViewById(R.id.b_tematica4);
    }

    public void escogerEscenario(View view) {
        Intent intent = new Intent(getApplicationContext(), Escenario.class);
        startActivity(intent);
    }
}