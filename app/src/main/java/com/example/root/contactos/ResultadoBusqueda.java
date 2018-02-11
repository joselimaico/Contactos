package com.example.root.contactos;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ResultadoBusqueda extends AppCompatActivity {

    TextView textViewNombre,textViewEmail,textViewDireccion,textViewGenero;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultado_busqueda);
       // recibirDatos();
        showContact();
    }


    public void showContact(){

        textViewNombre = (TextView) findViewById(R.id.textViewNombre);
        textViewDireccion = (TextView) findViewById(R.id.textViewDireccion);
        textViewEmail = (TextView) findViewById(R.id.textViewEmail);
        textViewGenero = (TextView) findViewById(R.id.textViewGenero);
        Intent incomingIntent = getIntent();
        final String id = incomingIntent.getStringExtra("id");
        RequestQueue queue = Volley.newRequestQueue(this);
        String url3 ="http://192.168.100.38:85/contactos_2.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url3,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);
                            JSONArray contacts =
                                    obj.getJSONArray("contactos");
                            for (int i = 0; i < contacts.length(); i++) {
                                JSONObject c = contacts.getJSONObject(i);
                                String id = c.getString("id");
                                String name = c.getString("nombre");
                                String email = c.getString("email");
                                String address = c.getString("direccion");
                                String gender = c.getString("genero");
                                textViewNombre.setText(name);
                                textViewDireccion.setText(address);
                                textViewEmail.setText(email);
                                textViewGenero.setText(gender);
                            }
                            Log.d("My App", obj.toString());
                        } catch (Throwable t) {
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(ResultadoBusqueda.this,error.toString(),Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("id", id);
                return params;
            }
        };
        queue.add(stringRequest);
    }
}
