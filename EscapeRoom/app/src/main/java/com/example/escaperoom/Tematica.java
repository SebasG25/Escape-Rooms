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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Tematica extends AppCompatActivity {

    Button b_tematica1, b_tematica2, b_tematica3, b_tematica4;
    TextView tv_nickname;

    String nickname = "";
    ArrayList<String> tematicas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tematica);
        connect();

        Bundle bundle = getIntent().getExtras();
        nickname = bundle.getString("nickname");
        tv_nickname.setText(nickname);

        cargarTematicas("http://192.168.1.1/bdescaperooms/seleccionar_tematica.php");
    }

    private void connect() {
        b_tematica1 = findViewById(R.id.b_tematica1);
        b_tematica2 = findViewById(R.id.b_tematica2);
        b_tematica3 = findViewById(R.id.b_tematica3);
        b_tematica4 = findViewById(R.id.b_tematica4);
        tv_nickname = findViewById(R.id.tv_nickname);
    }

    public void escogerEscenario(View view) {
        Intent intent = new Intent(getApplicationContext(), Escenario.class);
        intent.putExtra("nickname", nickname);

        switch(view.getId())
        {
            case R.id.b_tematica1:
                intent.putExtra("tematica", b_tematica1.getText().toString());
                break;
            case R.id.b_tematica2:
                intent.putExtra("tematica", b_tematica2.getText().toString());
                break;
            case R.id.b_tematica3:
                intent.putExtra("tematica", b_tematica3.getText().toString());
                break;
            case R.id.b_tematica4:
                intent.putExtra("tematica", b_tematica4.getText().toString());
                break;
            default:
                throw new RuntimeException("Unknow button ID");
        }

        startActivity(intent);
    }

    private void cargarTematicas(String URL) {

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("tematicas");

                    for(int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        tematicas.add(jsonObject.getString("tematica"));
                    }

                    mostrarTematicas();
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

        RequestQueue requestQueue = Volley.newRequestQueue(Tematica.this);
        requestQueue.add(request);
    }

    private void mostrarTematicas() {
        b_tematica1.setText(tematicas.get(0));
        b_tematica2.setText(tematicas.get(1));
        b_tematica3.setText(tematicas.get(2));
        b_tematica4.setText(tematicas.get(3));
    }
}