package com.example.escaperoom;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    static final String correo = "chzdiane22@gmail.com";
    static final String contrasena = "123abc";
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
        AlertDialog.Builder alerta = new AlertDialog.Builder(getApplicationContext());
        String correoIngresado = et_Email.getText().toString().trim();
        String contrasenaIngresada = et_Contraseña.getText().toString().trim();
        if(correoIngresado == "" || contrasenaIngresada == "") {
            alerta.setTitle("Error");
            alerta.setMessage("Digíte todos los datos solicitados");
            alerta.show();
        }
        else if(correoIngresado == correo && contrasenaIngresada == contrasena){
            Intent intent = new Intent(getApplicationContext(), Tematica.class);
            startActivity(intent);
        }else{
            alerta.setTitle("Error");
            alerta.setMessage("Datos erróneos");
            alerta.show();
        }

    }
}
