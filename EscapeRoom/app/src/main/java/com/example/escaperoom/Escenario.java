package com.example.escaperoom;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Escenario extends AppCompatActivity {

    Button b_escenario1, b_escenario2, b_escenario3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_escenario);
        connect();
    }

    private void connect(){
        b_escenario1 = findViewById(R.id.b_opcion1);
        b_escenario2 = findViewById(R.id.b_opcion2);
        b_escenario3 = findViewById(R.id.b_opcion3);
    }

    public void jugar(View view) {
        Intent intent = new Intent(getApplicationContext(), Juego.class);
        startActivity(intent);
    }
}