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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Ajustes extends AppCompatActivity {
    EditText et_EmailA, et_ContraseñaA, et_NuevaContraseñaA;
    Button b_EliminarA, b_ActualizarA;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajustes);
        connect();
    }

    private void connect() {
        et_EmailA = findViewById(R.id.et_EmailA);
        et_ContraseñaA = findViewById(R.id.et_ContraseñaA);
        et_NuevaContraseñaA = findViewById(R.id.et_NuevaContraseñaA);
        b_EliminarA = findViewById(R.id.b_EliminarA);
        b_ActualizarA = findViewById(R.id.b_ActualizarA);
    }

    private void eliminarUsuario(String URL) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (!response.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Usuario eliminado exitosamente", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getApplicationContext(), "No se pudo eliminar el usuario", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Ajustes.this, error.toString(), Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<String, String>();
                parametros.put("correo_electronico", et_EmailA.getText().toString());
                parametros.put("contrasena", et_ContraseñaA.getText().toString());
                return parametros;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(Ajustes.this);
        requestQueue.add(stringRequest);
    }

    private void actualizarUsuario(String URL) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (!response.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Contraseña actualizada", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getApplicationContext(), "No se pudo actualizar la contraseña", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Ajustes.this, error.toString(), Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<String, String>();
                parametros.put("correo_electronico", et_EmailA.getText().toString());
                parametros.put("contrasena", et_ContraseñaA.getText().toString());
                parametros.put("contrasena_nueva", et_NuevaContraseñaA.getText().toString());
                return parametros;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(Ajustes.this);
        requestQueue.add(stringRequest);
    }

    public void eliminar(View view) {
        eliminarUsuario("http://192.168.1.1/bdescaperooms/borrar_usuario.php");
    }

    public void actulizar(View view) {
        actualizarUsuario("http://192.168.1.1/bdescaperooms/editar_usuario.php");
    }
}