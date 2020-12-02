package com.example.agenda.Objetos;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.agenda.R;

import java.util.ArrayList;

public class FichaAdapter extends RecyclerView.Adapter<FichaAdapter.ViewHolder>{
    private Context context;
    private ArrayList<Ficha> lista;
    private View.OnLongClickListener onLongClickListener;

    public FichaAdapter(Context context, ArrayList<Ficha> lista){
        this.context=context;
        this.lista=lista;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_ficha,viewGroup,false);
        itemView.setOnLongClickListener(onLongClickListener);
        ViewHolder viewHolder=new ViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.titulo.setText(lista.get(i).getTitulo());
        if(lista.get(i).getTipo().equals("nota")){
            viewHolder.icono.setImageResource(R.drawable.nota);
        }else if(lista.get(i).getTipo().equals("tarea")){
            viewHolder.icono.setImageResource(R.drawable.tarea);
        }
        if(lista.get(i).getEstado().equals("true")){
            viewHolder.estado.setChecked(false);
        }else if(lista.get(i).getEstado().equals("false")){
            viewHolder.estado.setChecked(true);
        }
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView titulo;
        ImageView icono;
        CheckBox estado;
        public ViewHolder(View item){
            super(item);
            titulo=(TextView)item.findViewById(R.id.lblTituloIcono);
            icono=(ImageView)item.findViewById(R.id.imgvwIcono);
            estado=(CheckBox)item.findViewById(R.id.chckbxEstado);
        }
    }

    public void setOnItemLongClickListener(View.OnLongClickListener onLongClickListener) {
        this.onLongClickListener = onLongClickListener;
    }
}
