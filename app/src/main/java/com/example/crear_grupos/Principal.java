package com.example.crear_grupos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;

import com.example.crear_grupos.adaptadores.ListaGruposAdapter;
import com.example.crear_grupos.db.DbGrupos;
import com.example.crear_grupos.entidades.Grupos;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class Principal extends AppCompatActivity implements SearchView.OnQueryTextListener {

    SearchView txtBuscar;
    RecyclerView listaGrupos;
    ArrayList<Grupos> listaArrayGrupos;
    FloatingActionButton fabNuevo;
    ListaGruposAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.principal);
        txtBuscar = findViewById(R.id.txtBuscar);
        listaGrupos = findViewById(R.id.listaGrupos);
        fabNuevo = findViewById(R.id.favNuevo);
        listaGrupos.setLayoutManager(new LinearLayoutManager(this));

        DbGrupos dbGrupos = new DbGrupos(Principal.this);

        listaArrayGrupos = new ArrayList<>();

        adapter = new ListaGruposAdapter(DbGrupos.mostrarGrupos());
        listaGrupos.setAdapter(adapter);

        fabNuevo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nuevoRegistro();
            }
        });

        txtBuscar.setOnQueryTextListener(this);
    }

    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.menuNuevo:
                nuevoRegistro();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void nuevoRegistro(){
        Intent intent = new Intent(this, NuevoGrupo.class);
        startActivity(intent);
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        adapter.filtrado(s);
        return false;
    }
}