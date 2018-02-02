package com.example.root.contactos;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

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
import java.util.List;

public class ConsultaContactos extends AppCompatActivity {
    DatabaseHandler db = new DatabaseHandler(this);
    Contacto contacto = new Contacto();
    private ListView lstView;
    private ArrayList<String> listViewItems = new ArrayList<String>();
    private List<Contacto> listacontactos = new ArrayList<>();
    private ArrayAdapter<String> adapter;
    private TextView mTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta_contactos);
        consultContacts();
        listacontactos = db.getAllContacts();
        lstView = (ListView) findViewById(R.id.mainListView);
        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, listViewItems);
        lstView.setAdapter(adapter);
        for (int i = 0; i< listacontactos.size(); i++){
            adapter.add(listacontactos.get(i).getNombre().toString());
        }
        adapter.notifyDataSetChanged();

        lstView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Intent intent = new Intent(view.getContext(),ResultadoBusqueda.class);

                SharedPreferences sharedPref1 = getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
                SharedPreferences.Editor editor1 = sharedPref1.edit();
                editor1.putString(getString(R.string.value_id_contacto),String.valueOf(listacontactos.get(position).getId_contacto()));
                editor1.commit();

                SharedPreferences sharedPref2 =
                        getSharedPreferences(getString(R.string.preference_file_key),
                                Context.MODE_PRIVATE);
                SharedPreferences.Editor editor2 = sharedPref2.edit();
                editor2.putString(getString(R.string.value_nombre), listacontactos.get(position).getNombre());
                editor2.commit();

                SharedPreferences sharedPrefFechaInicio =
                        getSharedPreferences(getString(R.string.preference_file_key),
                                Context.MODE_PRIVATE);
                SharedPreferences.Editor editorFechaInicio = sharedPrefFechaInicio.edit();
                editorFechaInicio.putString(getString(R.string.value_email), listacontactos.get(position).getEmail());
                editorFechaInicio.commit();

                SharedPreferences sharedPrefFechaFin =
                        getSharedPreferences(getString(R.string.preference_file_key),
                                Context.MODE_PRIVATE);
                SharedPreferences.Editor editorFechaFin = sharedPrefFechaFin.edit();
                editorFechaFin.putString(getString(R.string.value_direccion), listacontactos.get(position).getDireccion());
                editorFechaFin.commit();

                SharedPreferences sharedPrefEstado =
                        getSharedPreferences(getString(R.string.preference_file_key),
                                Context.MODE_PRIVATE);
                SharedPreferences.Editor editorEstado = sharedPrefEstado.edit();
                editorEstado.putString(getString(R.string.value_genero), listacontactos.get(position).getGenero());
                editorEstado.commit();

                startActivity(intent);

            }
        });

    }
    public void consultContacts(){
        RequestQueue queue = Volley.newRequestQueue(this);
        String url2 ="https://api.androidhive.info/contacts/";
        final JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url2, null, new
                        Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    JSONObject jsonObj = new
                                            JSONObject(response.toString());
                                    JSONArray contacts = jsonObj.getJSONArray("contacts");
                                    for (int i = 0; i < contacts.length(); i++) {
                                        JSONObject c = contacts.getJSONObject(i);
                                        String id = c.getString("id");
                                        String name = c.getString("name");
                                        String email = c.getString("email");
                                        String address = c.getString("address");
                                        String gender = c.getString("gender");
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

}
