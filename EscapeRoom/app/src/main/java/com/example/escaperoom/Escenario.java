package com.example.escaperoom;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Escenario extends AppCompatActivity {

    Button b_escenario1, b_escenario2, b_escenario3;
    TextView tv_nickname;

    String nickname = "", tematica = "";

    ArrayList<String> escenarios = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_escenario);
        connect();

        Bundle bundle = getIntent().getExtras();
        nickname = bundle.getString("nickname");
        tematica = bundle.getString("tematica");
        tv_nickname.setText(nickname);

        cargarEscenario("http://192.168.1.1/bdescaperooms/seleccionar_escenario.php");
    }

    private void connect(){
        b_escenario1 = findViewById(R.id.b_escenario1);
        b_escenario2 = findViewById(R.id.b_escenario2);
        b_escenario3 = findViewById(R.id.b_escenario3);
        tv_nickname = findViewById(R.id.tv_nickname);
    }

    public void jugar(View view) {
        Intent intent = new Intent(getApplicationContext(), Juego.class);
        intent.putExtra("nickname", nickname);
        intent.putExtra("tematica", tematica);

        switch(view.getId())
        {
            case R.id.b_escenario1:
                intent.putExtra("escenario", b_escenario1.getText().toString());
                break;
            case R.id.b_escenario2:
                intent.putExtra("escenario", b_escenario2.getText().toString());
                break;
            case R.id.b_escenario3:
                intent.putExtra("escenario", b_escenario3.getText().toString());
                break;
            default:
                throw new RuntimeException("Unknow button ID");
        }

        startActivity(intent);
    }

    private void cargarEscenario(String URL) {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("escenarios");

                    for(int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        escenarios.add(jsonObject.getString("escenario"));
                    }

                    mostrarEscenarios();
                }
                catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(Escenario.this);
        requestQueue.add(request);
    }

    private void mostrarEscenarios() {
        b_escenario1.setText(escenarios.get(0));
        b_escenario2.setText(escenarios.get(1));
        b_escenario3.setText(escenarios.get(2));
    }
}