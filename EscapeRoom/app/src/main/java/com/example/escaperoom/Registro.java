package com.example.escaperoom;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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

import java.util.Calendar;
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
        dateFormat();
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

    private void dateFormat(){
        et_FechaNacimientoR.addTextChangedListener(new TextWatcher() {

            private String current = "";
            private String yyyymmdd = "AAAAMMDD";
            private Calendar cal = Calendar.getInstance();

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().equals(current)) {
                    String clean = s.toString().replaceAll("[^\\d.]", "");
                    String cleanC = current.replaceAll("[^\\d.]", "");

                    int cl = clean.length();
                    int sel = cl;
                    for (int i = 4; i <= cl && i < 8; i += 2) {
                        sel++;
                    }
                    //Fix for pressing delete next to a forward slash
                    if (clean.equals(cleanC)) sel--;

                    if (clean.length() < 8) {
                        clean = clean + yyyymmdd.substring(clean.length());
                    } else {
                        //This part makes sure that when we finish entering numbers
                        //the date is correct, fixing it otherwise
                        int year = Integer.parseInt(clean.substring(0, 4));
                        int mon = Integer.parseInt(clean.substring(4, 6));
                        int day = Integer.parseInt(clean.substring(6, 8));

                        if (mon > 12) mon = 12;
                        cal.set(Calendar.MONTH, mon - 1);

                        year = (year < 1900) ? 1900 : (year > 2020) ? 2020 : year;
                        cal.set(Calendar.YEAR, year);
                        // ^ first set year for the line below to work correctly
                        //with leap years - otherwise, date e.g. 29/02/2012
                        //would be automatically corrected to 28/02/2012

                        day = (day > cal.getActualMaximum(Calendar.DATE)) ? cal.getActualMaximum(Calendar.DATE) : day;
                        clean = String.format("%02d%02d%02d", year, mon, day);
                    }

                    clean = String.format("%s/%s/%s", clean.substring(0, 4),
                            clean.substring(4, 6),
                            clean.substring(6, 8));

                    sel = sel < 0 ? 0 : sel;
                    current = clean;
                    et_FechaNacimientoR.setText(current);
                    et_FechaNacimientoR.setSelection(sel < current.length() ? sel : current.length());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
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