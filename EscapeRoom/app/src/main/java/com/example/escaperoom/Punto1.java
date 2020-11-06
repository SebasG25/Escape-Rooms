package com.example.escaperoom;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Punto1 extends AppCompatActivity {

    Button b_opcion1, b_opcion2, b_opcion3;
    String pregunta, opcion1, opcion2, opcion3, respuesta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_punto1);
        connect();

        respuesta = b_opcion2.getText().toString();
    }

    private void connect() {
        b_opcion1 = findViewById(R.id.b_escenario1);
        b_opcion2 = findViewById(R.id.b_escenario2);
        b_opcion3 = findViewById(R.id.b_escenario3);
    }

    public void opcion1(View view) {
        Intent returnIntent = new Intent();
        returnIntent.putExtra("Resultado", correcta(b_opcion1));
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }

    public void opcion2(View view) {
        Intent returnIntent = new Intent();
        returnIntent.putExtra("Resultado", correcta(b_opcion2));
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }

    public void opcion3(View view) {
        Intent returnIntent = new Intent();
        returnIntent.putExtra("Resultado", correcta(b_opcion3));
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }

    private boolean correcta(Button button) {
        return button.getText().toString().equals(respuesta);
    }
}