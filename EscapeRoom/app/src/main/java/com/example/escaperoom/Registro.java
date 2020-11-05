package com.example.escaperoom;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class Registro extends AppCompatActivity {
    EditText et_EmailR, et_NombreR, et_ApellidoR, et_NicknameR, et_FechaNacimientoR, et_ContraseñaR;
    Button b_Registrarse;

    String URL = "http://192.168.1.1/bdescaperooms/insertar_usuario.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        connect();
    }

    private void connect() {
        et_EmailR = findViewById(R.id.et_EmailR);
        et_NombreR = findViewById(R.id.et_NombreR);
        et_ApellidoR = findViewById(R.id.et_ApellidoR);
        et_NicknameR = findViewById(R.id.et_NicknameR);
        et_FechaNacimientoR = findViewById(R.id.et_FechaNacimientoR);
        et_ContraseñaR = findViewById(R.id.et_ContraseñaR);
        b_Registrarse = findViewById(R.id.b_Registrarse);
    }

    public void registrarse(View view) {
        registrar();
        finish();
    }

    private void registrar() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(), "Registro exitoso", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<String, String>();
                parametros.put("nombre", et_NombreR.getText().toString());
                parametros.put("apellido", et_ApellidoR.getText().toString());
                parametros.put("fecha_nacimiento", et_FechaNacimientoR.getText().toString());
                parametros.put("correo_electronico", et_EmailR.getText().toString());
                parametros.put("nickname", et_NicknameR.getText().toString());
                parametros.put("contrasena", et_ContraseñaR.getText().toString());
                return parametros;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}