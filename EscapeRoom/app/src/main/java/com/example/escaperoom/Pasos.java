package com.example.escaperoom;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class Pasos extends AppCompatActivity {

    Spinner s_paso1, s_paso2, s_paso3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pasos);
        connect();

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.pasos_lobby, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s_paso1.setAdapter(adapter);
        s_paso2.setAdapter(adapter);
        s_paso3.setAdapter(adapter);
    }

    private void connect() {
        s_paso1 = findViewById(R.id.s_paso1);
        s_paso2 = findViewById(R.id.s_paso2);
        s_paso3 = findViewById(R.id.s_paso3);
    }
}