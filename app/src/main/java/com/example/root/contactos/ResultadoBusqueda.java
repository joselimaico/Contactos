package com.example.root.contactos;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ResultadoBusqueda extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultado_busqueda);

        SharedPreferences sharedPref =
                getSharedPreferences(getString(R.string.preference_file_key),
                        Context.MODE_PRIVATE);
        String valorID =
                sharedPref.getString(getString(R.string.value_id_contacto),"id_contacto");
        TextView textViewID = (TextView) findViewById(R.id.textViewID);
        textViewID.setText(valorID);

        String valorNombre =
                sharedPref.getString(getString(R.string.value_nombre),"nombre");
        TextView textViewCliente = (TextView) findViewById(R.id.textViewNombre);
        textViewCliente.setText(valorNombre);

        String valorEmail =
                sharedPref.getString(getString(R.string.value_email),"email");
        TextView textViewfechaInicio = (TextView) findViewById(R.id.textViewEmail);
        textViewfechaInicio.setText(valorEmail);

        String valorDireccion=
                sharedPref.getString(getString(R.string.value_direccion),"direccion");
        TextView textViewfechaFin = (TextView) findViewById(R.id.textViewDireccion);
        textViewfechaFin.setText(valorDireccion);

        String valorGenero =
                sharedPref.getString(getString(R.string.value_genero),"genero");
        TextView textViewEstado = (TextView) findViewById(R.id.textViewGenero);
        textViewEstado.setText(valorGenero);
    }
}
