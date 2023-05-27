package com.example.crear_grupos.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.example.crear_grupos.entidades.Grupos;

import java.util.ArrayList;

public class DbGrupos extends DataBase {

    Context context;

    public DbGrupos(@Nullable Context context) {
        super(context);
        this.context = context;
    }

    public long insertarGrupo(String nombre, String ficha) {

        long id = 0;

        try {
            DataBase dbHelper = new DataBase(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put("nombre", nombre);
            values.put("ficha", ficha);


            id = db.insert(TABLE_GRUPOS, null, values);
        } catch (Exception ex) {
            ex.toString();
        }

        return id;
    }

    public ArrayList<Grupos> mostrarGrupos() {

        DataBase DataBase = new DataBase(context);
        SQLiteDatabase db = DataBase.getWritableDatabase();

        ArrayList<Grupos> listaGrupos = new ArrayList<>();
        Grupos grupos;
        Cursor cursorGrupos;

        cursorGrupos = db.rawQuery("SELECT * FROM " + TABLE_GRUPOS + " ORDER BY nombre ASC", null);

        if (cursorGrupos.moveToFirst()) {
            do {
                grupos = new Grupos();
                grupos.setId(cursorGrupos.getInt(0));
                grupos.setNombre(cursorGrupos.getString(1));
                grupos.setTelefono(cursorGrupos.getString(2));

                listaGrupos.add(grupos);
            } while (cursorGrupos.moveToNext());
        }

        cursorGrupos.close();

        return listaGrupos;
    }

    public Grupos verGrupo(int id) {

        DataBase DataBase = new DataBase(context);
        SQLiteDatabase db = DataBase.getWritableDatabase();

        Grupos grupos = null;
        Cursor cursorContactos;

        cursorGrupos = db.rawQuery("SELECT * FROM " + TABLE_GRUPOS + " WHERE id = " + id + " LIMIT 1", null);

        if (cursorGrupos.moveToFirst()) {
            grupos = new Grupos();
            grupos.setId(cursorGrupos.getInt(0));
            grupos.setNombre(cursorGrupos.getString(1));
            grupos.setFicha(cursorGrupos.getString(2));
        }

        cursorGrupos.close();

        return grupos;
    }

    public boolean editarGrupo(int id, String nombre, String ficha) {

        boolean correcto = false;

        DataBase dbHelper = new DataBase(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        try {
            db.execSQL("UPDATE " + TABLE_GRUPOS + " SET nombre = '" + nombre + "', ficha = '" + ficha + "' WHERE id='" + id + "' ");
            correcto = true;
        } catch (Exception ex) {
            ex.toString();
            correcto = false;
        } finally {
            db.close();
        }

        return correcto;
    }

    public boolean eliminarGrupo(int id) {

        boolean correcto = false;

        DataBase dbHelper = new DataBase(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        try {
            db.execSQL("DELETE FROM " + TABLE_GRUPOS + " WHERE id = '" + id + "'");
            correcto = true;
        } catch (Exception ex) {
            ex.toString();
            correcto = false;
        } finally {
            db.close();
        }

        return correcto;
    }
}
