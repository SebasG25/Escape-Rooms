package com.example.escaperoom;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Escenario extends AppCompatActivity {

    Button b_escenario1, b_escenario2, b_escenario3;
    TextView tv_nickname;

    String nickname = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_escenario);
        connect();

        Bundle bundle = getIntent().getExtras();
        nickname = bundle.getString("nickname");
        tv_nickname.setText(nickname);

        Toast.makeText(getApplicationContext(), bundle.getString("tematica"), Toast.LENGTH_SHORT).show();
    }

    private void connect(){
        b_escenario1 = findViewById(R.id.b_tematica1);
        b_escenario2 = findViewById(R.id.b_tematica2);
        b_escenario3 = findViewById(R.id.b_tematica3);
        tv_nickname = findViewById(R.id.tv_nickname);
    }

    public void jugar(View view) {
        Intent intent = new Intent(getApplicationContext(), Juego.class);
        intent.putExtra("nickname", nickname);
        startActivity(intent);
    }
}