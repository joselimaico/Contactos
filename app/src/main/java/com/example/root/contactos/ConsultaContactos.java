package com.example.root.contactos;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

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

public class ConsultaContactos extends AppCompatActivity {
    DatabaseHandler db = new DatabaseHandler(this);

    private ListView lstView;
    private ArrayList<String> listViewItems = new ArrayList<String>();
    private ArrayAdapter<String> adapter;
    private ArrayList<String> listaId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta_contactos);

        getContacts();
        lstView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                String id =listaId.get(i);
                Intent intent = new Intent(ConsultaContactos.this,ResultadoBusqueda.class);
                intent.putExtra("id",id);
                startActivity(intent);

            }
        });


    }
    public void getContacts(){
        listaId = new ArrayList<>();
        lstView = (ListView) findViewById(R.id.mainListView);
        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, listViewItems);
        lstView.setAdapter(adapter);
        RequestQueue queue = Volley.newRequestQueue(this);
        //String url2 ="https://api.androidhive.info/contacts/";
        String url2 ="http://192.168.100.38:85/contactos.php";

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

                                        adapter.add(name);
                                        listaId.add(id);
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
        adapter.notifyDataSetChanged();
    }






}
