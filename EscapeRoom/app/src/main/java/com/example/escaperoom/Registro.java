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

    String URL = "http://192.168.1.3/bdescaperooms/insertar_usuario.php";

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
            private int actualYear = cal.get(Calendar.YEAR);
            private int actualMonth = cal.get(Calendar.MONTH)+1;
            private int actualDay = cal.get(Calendar.DAY_OF_MONTH);

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
                        try {
                            int year = Integer.parseInt(clean.substring(0, 4));
                            int mon = Integer.parseInt(clean.substring(4, 6));
                            int day = Integer.parseInt(clean.substring(6, 8));

                            mon = (mon > 12 || mon == 00) ? actualMonth : mon;
                            cal.set(Calendar.MONTH, mon);

                            year = (year < 1900 || year > actualYear) ? actualYear : year;
                            cal.set(Calendar.YEAR, year);
                            // ^ first set year for the line below to work correctly
                            //with leap years - otherwise, date e.g. 29/02/2012
                            //would be automatically corrected to 28/02/2012

                            day = (day > cal.getActualMaximum(Calendar.DATE) || day < cal.getActualMinimum(Calendar.DATE)) ? actualDay : day;
                            clean = String.format("%02d%02d%02d", year, mon, day);
                        }catch (NumberFormatException e){
                            Toast.makeText(Registro.this, "Por favor ingresa la fecha correctamente", Toast.LENGTH_SHORT).show();
                        }
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
        try{
            if(registrar()){
                finish();
            }
        }catch (Exception e){
            Toast.makeText(this, "Por favor llena todos los campos", Toast.LENGTH_SHORT).show();
        }

    }

    private boolean registrar() {
        boolean exito = false;
        String validarCorreo = et_EmailR.getText().toString().trim();
        String nombreRecibido = et_NombreR.getText().toString().trim();
        String apellidoRecibido = et_ApellidoR.getText().toString().trim();
        String emailRecibido = et_EmailR.getText().toString().trim();
        String nicknameRecibido = et_NicknameR.getText().toString().trim();
        String fechaNacimiento_recibida = et_FechaNacimientoR.getText().toString().trim();
        String contrasenaRecibida = et_ContraseñaR.getText().toString().trim();


        try{

            if(!(nombreRecibido.isEmpty() || apellidoRecibido.isEmpty() ||
                    emailRecibido.isEmpty() || nicknameRecibido.trim().isEmpty()
                    || fechaNacimiento_recibida.trim().isEmpty() || contrasenaRecibida.isEmpty())){

                int year = Integer.parseInt(et_FechaNacimientoR.getText().toString().substring(0, 4));
                int mon = Integer.parseInt(et_FechaNacimientoR.getText().toString().substring(5, 7));
                int day = Integer.parseInt(et_FechaNacimientoR.getText().toString().substring(8, 9));
                if(validarCorreo.contains("@") && validarCorreo.contains(".")){
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
                    exito = true;
                }else{
                    Toast.makeText(this, "Por favor ingresa un correo válido", Toast.LENGTH_SHORT).show();
                }

            }else{
                Toast.makeText(this, "Por favor llena todos los campos", Toast.LENGTH_SHORT).show();
            }
        }catch(NumberFormatException e){
            Toast.makeText(this, "Por favor llena correctamente la fecha de nacimiento", Toast.LENGTH_SHORT).show();
        }



        return exito;
    }
}