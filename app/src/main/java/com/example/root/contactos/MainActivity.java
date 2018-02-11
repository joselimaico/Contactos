package com.example.root.contactos;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private TextView mTextView;
    Contacto contacto = new Contacto();
    DatabaseHandler db = new DatabaseHandler(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void consultarContacto(View view){
        Intent intent = new Intent(this,ConsultaContactos.class);
        startActivity(intent);

    }
    public void createContacts(View view){
        RequestQueue queue = Volley.newRequestQueue(this);
        //String url2 ="https://api.androidhive.info/contacts/";
        String url2 ="http://192.168.100.38:81/contactos.php";
        final JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url2, null, new
                        Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    JSONObject jsonObj = new
                                            JSONObject(response.toString());
                                    JSONArray contacts = jsonObj.getJSONArray("contactos");
                                    for (int i = 0; i < contacts.length(); i++) {
                                        JSONObject c = contacts.getJSONObject(i);
                                        String id = c.getString("id");
                                        String name = c.getString("nombre");
                                        String email = c.getString("email");
                                        String address = c.getString("direccion");
                                        String gender = c.getString("genero");
                                        contacto.setId_contacto(id);
                                        contacto.setNombre(name);
                                        contacto.setEmail(email);
                                        contacto.setDireccion(address);
                                        contacto.setGenero(gender);
                                        db.addContact(new Contacto(contacto.getId_contacto(),contacto.getNombre(),contacto.getEmail()
                                                ,contacto.getDireccion(),contacto.getGenero()));
                                    }
                                }
                                catch (final JSONException e) {
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // mTextView.setText("That didn't work!");
                    }
                });
        queue.add(jsObjRequest);
    }
    public void makeRequest3(View view){
        mTextView = findViewById(R.id.TextView);
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
                                mTextView.setText(name);
                            }
                            Log.d("My App", obj.toString());
                        } catch (Throwable t) {
                        }
                    }
                },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                                Toast.makeText(MainActivity.this,error.toString(),Toast.LENGTH_LONG).show();
                            }
                        }){
                    @Override
                    protected Map<String,String> getParams(){
                        Map<String,String> params = new HashMap<String, String>();
                        params.put("id", "b200");
                        return params;
                    }
                };
        queue.add(stringRequest);
    }


}
