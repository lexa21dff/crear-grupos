package com.example.crear_grupos;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.crear_grupos.db.DbGrupos;

public class NuevoGrupo extends AppCompatActivity {

    EditText txtNombreGrupo, txtFicha;
    Button btnCrear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nuevo_grupo);

        txtNombreGrupo = findViewById(R.id.txtNombre);
        txtFicha = findViewById(R.id.txtFicha);
        btnCrear = findViewById(R.id.btnCrear);

        btnCrear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!txtNombreGrupo.getText().toString().equals("") && !txtFicha.getText().toString().equals("")) {

                    DbGrupos dbContactos = new DbGrupos(NuevoGrupo.this);
                    long id = dbContactos.insertarGrupo(txtNombreGrupo.getText().toString(), txtFicha.getText().toString());

                    if (id > 0) {
                        Toast.makeText(NuevoGrupo.this, "REGISTRO GUARDADO", Toast.LENGTH_LONG).show();
                        limpiar();
                    } else {
                        Toast.makeText(NuevoGrupo.this, "ERROR AL GUARDAR REGISTRO", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(NuevoGrupo.this, "DEBE LLENAR LOS CAMPOS OBLIGATORIOS", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void limpiar() {
        txtNombreGrupo.setText("");
        txtFicha.setText("");
    }
}