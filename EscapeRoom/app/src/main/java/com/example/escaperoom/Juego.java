package com.example.escaperoom;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class Juego extends AppCompatActivity {

    TextView tv_nickname;

    View layout;

    Button b_punto1, b_punto2, b_punto3, b_continuar;

    String nickname = "", tematica = "", escenario = "";

    int count = 0;
    private static final int P1_ACTIVITY_REQUEST_CODE = 0;
    private static final int P2_ACTIVITY_REQUEST_CODE = 1;
    private static final int P3_ACTIVITY_REQUEST_CODE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_juego);
        connect();

        Bundle bundle = getIntent().getExtras();
        nickname = bundle.getString("nickname");
        tematica = bundle.getString("tematica");
        escenario = bundle.getString("escenario");

        if(escenario.equals("Sótano")) {
            layout.setBackground(getDrawable(R.drawable.sotano));
        }
        else if (escenario.equals("Salón de clases")) {
            layout.setBackground(getDrawable(R.drawable.clases));
        }
        else {
            layout.setBackground(getDrawable(R.drawable.lobby));
        }

        tv_nickname.setText(nickname);

        puntos();
    }

    private void connect() {
        b_punto1 = findViewById(R.id.b_punto1);
        b_punto2 = findViewById(R.id.b_punto2);
        b_punto3 = findViewById(R.id.b_punto3);
        b_continuar = findViewById(R.id.b_continuar);
        layout = findViewById(R.id.layout);
        tv_nickname = findViewById(R.id.tv_nickname);
    }

    public void pregunta1(View view) {
        Intent intent = new Intent(getApplicationContext(), Punto1.class);
        startActivityForResult(intent, P1_ACTIVITY_REQUEST_CODE);
    }

    public void pregunta2(View view) {
        Intent intent = new Intent(getApplicationContext(), Punto2.class);
        startActivityForResult(intent, P2_ACTIVITY_REQUEST_CODE);
    }

    public void pregunta3(View view) {
        Intent intent = new Intent(getApplicationContext(), Punto3.class);
        startActivityForResult(intent, P3_ACTIVITY_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == P1_ACTIVITY_REQUEST_CODE) {
            if(resultCode == RESULT_OK){
                boolean resultado = data.getBooleanExtra("Resultado", false);

                if(resultado) {
                    b_punto1.setBackground(getDrawable(R.drawable.roundbuttongreen));
                    b_punto1.setEnabled(false);
                    check();
                }
            }
        }

        if (requestCode == P2_ACTIVITY_REQUEST_CODE) {
            if(resultCode == RESULT_OK){
                boolean resultado = data.getBooleanExtra("Resultado", false);

                if(resultado) {
                    b_punto2.setBackground(getDrawable(R.drawable.roundbuttongreen));
                    b_punto2.setEnabled(false);
                    check();
                }
            }
        }

        if (requestCode == P3_ACTIVITY_REQUEST_CODE) {
            if(resultCode == RESULT_OK){
                boolean resultado = data.getBooleanExtra("Resultado", false);

                if(resultado) {
                    b_punto3.setBackground(getDrawable(R.drawable.roundbuttongreen));
                    b_punto3.setEnabled(false);
                    check();
                }
            }
        }
    }

    private void check() {
        if(count == 2) {
            b_continuar.setEnabled(true);
            b_continuar.setVisibility(View.VISIBLE);
        }
        else {
            count++;
        }
    }

    public void continuar(View view) {
        Intent intent = new Intent(getApplicationContext(), Pasos.class);
        startActivity(intent);
    }

    private void puntos() {
        Random random = new Random();

        ConstraintLayout cl = (ConstraintLayout) findViewById(R.id.layout);
        ConstraintSet cs = new ConstraintSet();
        cs.clone(cl);
        cs.setHorizontalBias(R.id.b_punto1, ((float) 10 + random.nextInt(81))/100);
        cs.setVerticalBias(R.id.b_punto1, ((float) 10 + random.nextInt(81))/100);
        cs.applyTo(cl);
        cs.setHorizontalBias(R.id.b_punto2, ((float) 10 + random.nextInt(81))/100);
        cs.setVerticalBias(R.id.b_punto2, ((float) 10 + random.nextInt(81))/100);
        cs.applyTo(cl);
        cs.setHorizontalBias(R.id.b_punto3, ((float) 10 + random.nextInt(81))/100);
        cs.setVerticalBias(R.id.b_punto3, ((float) 10 + random.nextInt(81))/100);
        cs.applyTo(cl);
    }
}