package com.example.escaperoom;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    static final String CORREO = "chzdiane22@gmail.com";
    static final String CONTRASENA = "123abc";
    EditText et_Email, et_Contraseña;
    Button b_Continuar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        connect();
    }

    private void connect() {
        et_Email = findViewById(R.id.et_Email);
        et_Contraseña = findViewById(R.id.et_Contraseña);
        b_Continuar = findViewById(R.id.b_Continuar);
    }

    public void comenzarJuego(View view) {
        String correoIngresado = et_Email.getText().toString().trim();
        String contrasenaIngresada = et_Contraseña.getText().toString();

        if(correoIngresado.equals("") || contrasenaIngresada.equals("")) {
            Toast toast = Toast.makeText(getApplicationContext(), "Complete datos de ingreso", Toast.LENGTH_LONG);
            toast.show();
        }
        else if(correoIngresado.equals(CORREO) && contrasenaIngresada.equals(CONTRASENA)) {
            Intent intent = new Intent(getApplicationContext(), Tematica.class);
            startActivity(intent);
        }
        else {
            Toast toast = Toast.makeText(getApplicationContext(), "Datos de ingreso erroneos", Toast.LENGTH_LONG);
            toast.show();
        }

    }
}
