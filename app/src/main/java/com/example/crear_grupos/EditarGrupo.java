package com.example.crear_grupos;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.crear_grupos.db.DbGrupos;
import com.example.crear_grupos.entidades.Grupos;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class EditarGrupo extends AppCompatActivity {

    EditText txtNombreGrupo, txtFicha;
    Button btnCrear;
    FloatingActionButton fabEditar, fabEliminar;
    boolean correcto = false;
    Grupos grupos;
    int id = 0;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ver_grupo);

        txtNombreGrupo = findViewById(R.id.txtNombreGrupo);
        txtFicha = findViewById(R.id.txtFicha);
        btnCrear = findViewById(R.id.btnCrear);
        fabEditar = findViewById(R.id.fabEditar);
        fabEditar.setVisibility(View.INVISIBLE);
        fabEliminar = findViewById(R.id.fabEliminar);
        fabEliminar.setVisibility(View.INVISIBLE);

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                id = Integer.parseInt(null);
            } else {
                id = extras.getInt("ID");
            }
        } else {
            id = (int) savedInstanceState.getSerializable("ID");
        }

        final DbGrupos dbContactos = new DbGrupos(EditarGrupo.this);
        grupos = dbContactos.verGrupo(id);

        if (grupos != null) {
            txtNombreGrupo.setText(grupos.getNombre());
            txtFicha.setText(grupos.getFicha());
        }

        btnCrear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!txtNombreGrupo.getText().toString().equals("") && !txtFicha.getText().toString().equals("")) {
                    correcto = dbContactos.editarGrupo(id, txtNombreGrupo.getText().toString(), txtFicha.getText().toString());

                    if(correcto){
                        Toast.makeText(EditarGrupo.this, "REGISTRO MODIFICADO", Toast.LENGTH_LONG).show();
                        verRegistro();
                    } else {
                        Toast.makeText(EditarGrupo.this, "ERROR AL MODIFICAR REGISTRO", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(EditarGrupo.this, "DEBE LLENAR LOS CAMPOS OBLIGATORIOS", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void verRegistro(){
        Intent intent = new Intent(this, VerGrupo.class);
        intent.putExtra("ID", id);
        startActivity(intent);
    }
}

