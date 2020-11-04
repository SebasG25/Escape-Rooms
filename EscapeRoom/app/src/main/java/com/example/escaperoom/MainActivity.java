package com.example.escaperoom;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    static final String CORREO = "chzdiane22@gmail.com";
    static final String CONTRASENA = "123abc";
    EditText et_Email, et_Contraseña;
    TextView tvRegistro;
    Button b_Continuar;
    String url = "http://192.168.1.3/bdEscapeRooms/validar_usuario.php";
    SpannableString mitextoU = new SpannableString("Regístrate");

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
        tvRegistro = findViewById(R.id.tvRegistro);
        SpannableString mitextoU = new SpannableString("Regístrate");
        mitextoU.setSpan(new UnderlineSpan(),0, mitextoU.length(), 0);
        tvRegistro.setText(mitextoU);
    }

    public void comenzarJuego(View view) {
        validarUsuario();
    }

    private void validarUsuario() {
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if(!response.isEmpty()) {
                    Intent intent = new Intent(getApplicationContext(), Tematica.class);
                    startActivity(intent);
                }
                else {
                    Toast toast = Toast.makeText(getApplicationContext(), "Datos de ingreso erroneos", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<String, String>();
                parametros.put("correo_electronico", et_Email.getText().toString());
                parametros.put("contrasena", et_Contraseña.getText().toString());
                return parametros;
            }
        };


        requestQueue.add(stringRequest);
    }

    public void conectar(View view) {
        validarUsuario();
    }

    public void Registro(View view) {
        //Crear layout de registro
        Intent intent = new Intent(getApplicationContext(), Tematica.class);
        startActivity(intent);
    }
}
