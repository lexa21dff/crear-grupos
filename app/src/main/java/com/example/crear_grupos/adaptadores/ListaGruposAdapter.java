package com.example.crear_grupos.adaptadores;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.crear_grupos.R;
import com.example.crear_grupos.VerGrupo;
import com.example.crear_grupos.entidades.Grupos;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ListaGruposAdapter extends RecyclerView.Adapter<ListaGruposAdapter.GrupoViewHolder> {

    ArrayList<Grupos> listaGrupos;
    ArrayList<Grupos> listaOriginal;

    public ListaGruposAdapter(ArrayList<Grupos> listaGrupos) {
        this.listaGrupos = listaGrupos;
        listaOriginal = new ArrayList<>();
        listaOriginal.addAll(listaGrupos);
    }

    @NonNull
    @Override
    public GrupoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_item_grupo, null, false);
        return new GrupoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GrupoViewHolder holder, int position) {
        holder.viewNombre.setText(listaGrupos.get(position).getNombre());
        holder.viewFicha.setText(listaGrupos.get(position).getFicha());

    }

    public void filtrado(final String txtBuscar) {
        int longitud = txtBuscar.length();
        if (longitud == 0) {
            listaGrupos.clear();
            listaGrupos.addAll(listaOriginal);
        } else {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                List<Grupos> collecion = listaGrupos.stream()
                        .filter(i -> i.getNombre().toLowerCase().contains(txtBuscar.toLowerCase()))
                        .collect(Collectors.toList());
                listaGrupos.clear();
                listaGrupos.addAll(collecion);
            } else {
                for (Grupos c : listaOriginal) {
                    if (c.getNombre().toLowerCase().contains(txtBuscar.toLowerCase())) {
                        listaGrupos.add(c);
                    }
                }
            }
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return listaGrupos.size();
    }

    public class GrupoViewHolder extends RecyclerView.ViewHolder {

        TextView viewNombre, viewFicha;

        public GrupoViewHolder(@NonNull View itemView) {
            super(itemView);

            viewNombre = itemView.findViewById(R.id.viewNombreGrupo);
            viewFicha = itemView.findViewById(R.id.viewFicha);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Context context = view.getContext();
                    Intent intent = new Intent(context, VerGrupo.class);
                    intent.putExtra("ID", listaGrupos.get(getAdapterPosition()).getId());
                    context.startActivity(intent);
                }
            });
        }
    }
}
