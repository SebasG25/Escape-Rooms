package com.example.escaperoom;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.VoiceInteractor;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    EditText et_Email, et_Contraseña;
    TextView tvRegistro, tv_Ajustes;
    Button b_Continuar;

    String nickname;

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
        tv_Ajustes = findViewById(R.id.tv_Ajustes);
        SpannableString spannableString = new SpannableString("Ajustes de usuario");
        spannableString.setSpan(new UnderlineSpan(),0, spannableString.length(), 0);
        tv_Ajustes.setText(spannableString);
    }

    private void validarUsuario(String URL) {

        String emailRecibido = et_Email.getText().toString().trim();
        String contrasenaRecibida= et_Contraseña.getText().toString();

        if(!(emailRecibido.isEmpty() || contrasenaRecibida.isEmpty())){
            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    if(!response.isEmpty()) {
                        try {
                            JSONObject obj = new JSONObject(response);
                            nickname = obj.getString("nickname");

                            Intent intent = new Intent(getApplicationContext(), Tematica.class);
                            intent.putExtra("nickname", nickname);
                            startActivity(intent);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "Datos de ingreso erroneos", Toast.LENGTH_SHORT).show();
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

            RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
            requestQueue.add(stringRequest);
        }else{
            Toast.makeText(this, "Por favor llena todos los campos", Toast.LENGTH_SHORT).show();
        }
    }

    public void conectar(View view) {
        validarUsuario("http://192.168.1.1/bdescaperooms/validar_usuario.php");
    }

    public void Registro(View view) {
        Intent intent = new Intent(getApplicationContext(), Registro.class);
        startActivity(intent);
    }

    public void ajustes(View view) {
        Intent intent = new Intent(getApplicationContext(), Ajustes.class);
        startActivity(intent);
    }
}
