package com.example.root.contactos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 2/1/18.
 */

public class DatabaseHandler extends SQLiteOpenHelper {


    private static final int DATABASE_VERSION =1;
    //Nombre de la base de datos
    private static final String DATABASE_NAME = "ContactsManager";
    //Nombre de la tabla de contactos
    private static final String TABLE_CONTACTO = "Contacto";
    //Nombres de las columnas de la tabla
    private static final String KEY_ID = "id";
    private static final String KEY_ID_CONTACTO = "id_contacto";
    private static final String KEY_NOMBRE = "nombre";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_DIRECCION = "direccion";
    private static final String KEY_GENERO = "genero";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_CONTACTO_TABLE = "CREATE TABLE " + TABLE_CONTACTO +
                "(" + KEY_ID + " INTEGER PRIMARY KEY," +
                KEY_ID_CONTACTO+ " TEXT,"+
                KEY_NOMBRE + " TEXT," +
                KEY_EMAIL + " TEXT," +
                KEY_DIRECCION + " TEXT,"+
                KEY_GENERO + " TEXT" + ")";

        sqLiteDatabase.execSQL(CREATE_CONTACTO_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(" DROP TABLE IF EXISTS " + TABLE_CONTACTO);
        onCreate(sqLiteDatabase);

    }

    public void addContact(Contacto contacto){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_ID_CONTACTO,contacto.getId_contacto());
        values.put(KEY_NOMBRE, contacto.getNombre());
        values.put(KEY_EMAIL,contacto.getEmail());
        values.put(KEY_DIRECCION,contacto.getDireccion());
        values.put(KEY_GENERO,contacto.getGenero());
        db.insert(TABLE_CONTACTO,null,values);
        db.close();
    }

    public List<Contacto> getAllContacts(){
        List<Contacto> contactoList = new ArrayList<Contacto>();
        String sql_select = "SELECT * FROM " + TABLE_CONTACTO;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(sql_select, null);
        if(cursor.moveToFirst()){
            while(cursor.moveToNext()){
                Contacto contacto = new Contacto();
                contacto.setId(Integer.parseInt(cursor.getString(0)) );
                contacto.setId_contacto(cursor.getString(1));
                contacto.setNombre(cursor.getString(2));
                contacto.setEmail(cursor.getString(3));
                contacto.setDireccion(cursor.getString(4));
                contacto.setGenero(cursor.getString(5));
                contactoList.add(contacto);
            }
        }
        return contactoList;
    }
}
