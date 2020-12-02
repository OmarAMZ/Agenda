package com.example.agenda;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.agenda.Daos.DaoFicha;
import com.example.agenda.Objetos.Ficha;
import com.example.agenda.Objetos.FichaAdapter;

import java.util.ArrayList;

public class Principal extends AppCompatActivity {
    private ArrayList<Ficha> lista = new ArrayList<>();
    private RecyclerView recyclerView;
    private EditText txtBuscar;

    //Inicializa la ventana y muestra la lista de notas en cuanto se abre la ventana
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        recyclerView = findViewById(R.id.rcclrFicha);
        txtBuscar = findViewById(R.id.txtBuscar);
        ActualizarRecycler();
    }

    //Creacion del menu con opciones
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu_principal,menu);
        return true;
    }

    //Añadir las opciones del menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.itmAgregar:
                Intent inte = new Intent(getApplicationContext(),Agregar.class);
                startActivity(inte);
                return true;
            case R.id.itmAcercaDe:
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Principal.this);
                alertDialogBuilder.setTitle("Acerca de");
                alertDialogBuilder
                        .setMessage("Aplicacion Creada por Omar Alvarez Munguía")
                        .setCancelable(false)
                        .setPositiveButton("Aceptar",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                dialog.cancel();
                            }
                        }).create().show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //Mostrar la lista de todas las notas creadas
    public void ActualizarRecycler(){
        DaoFicha dao=new DaoFicha(this);
        lista=dao.SeleccionarTodos();
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        FichaAdapter adapter=new FichaAdapter(this,lista);

        recyclerView.setAdapter(adapter);
        adapter.setOnItemLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                final View n = v;
                AlertDialog.Builder menu=new AlertDialog.Builder(v.getContext());
                Resources res = getResources();
                CharSequence[] opciones = {res.getString(R.string.ver),res.getString(R.string.modificar),res.getString(R.string.eliminar),res.getString(R.string.marcar)};
                menu.setItems(opciones, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int opcion) {
                        switch (opcion){
                            case 0:
                                Intent intent=new Intent(getApplicationContext(),mostrar.class);
                                intent.putExtra("titulo",lista.get(recyclerView.getChildAdapterPosition(n)).getTitulo());
                                startActivity(intent);
                                break;
                            case 1:
                                Intent intent1 = new Intent(getApplicationContext(),actualizar.class);
                                intent1.putExtra("titulo",lista.get(recyclerView.getChildAdapterPosition(n)).getTitulo());
                                startActivity(intent1);
                                break;
                            case 2:
                                DaoFicha daonuevo = new DaoFicha(getApplicationContext());
                                if(daonuevo.eliminar(lista.get(recyclerView.getChildAdapterPosition(n)))){
                                    Toast.makeText(getApplicationContext(),"Se elimino",Toast.LENGTH_LONG).show();
                                }
                                ActualizarRecycler();
                                break;
                            case 3:
                                DaoFicha daoAc = new DaoFicha(getApplicationContext());
                                Ficha fichaA = lista.get(recyclerView.getChildAdapterPosition(n));
                                fichaA.setEstado("false");
                                daoAc.actualizar(fichaA);
                                ActualizarRecycler();
                                break;
                        }
                    }
                });
                menu.create().show();
                return true;
            }
        });
    }

    //Buscar las notas.
    public void  buscar(View v){
        actualizarRecyclerBuscar();
    }

    //Actualizar la lista en cuestion de lo que se busca
    public void actualizarRecyclerBuscar(){
        DaoFicha dao=new DaoFicha(this);
        lista=dao.SeleccionarTodos(txtBuscar.getText().toString());
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        FichaAdapter adapter = new FichaAdapter(this,lista);

        recyclerView.setAdapter(adapter);
        adapter.setOnItemLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                final View n = v;
                AlertDialog.Builder menu=new AlertDialog.Builder(v.getContext());
                CharSequence[] opciones= {"Ver","Modificar","Eliminar","Marcar como terminado"};
                menu.setItems(opciones, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int opcion) {
                        switch (opcion){
                            case 0:
                                Intent intent=new Intent(getApplicationContext(),mostrar.class);
                                intent.putExtra("titulo",lista.get(recyclerView.getChildAdapterPosition(n)).getTitulo());
                                startActivity(intent);
                                break;
                            case 1:
                                Toast.makeText(getApplicationContext(),"Modificar",Toast.LENGTH_LONG).show();
                                break;
                            case 2:
                                DaoFicha daonuevo = new DaoFicha(getApplicationContext());
                                if(daonuevo.eliminar(lista.get(recyclerView.getChildAdapterPosition(n)))){
                                    Toast.makeText(getApplicationContext(),"Se elimino",Toast.LENGTH_LONG).show();
                                }
                                ActualizarRecycler();
                                break;
                            case 3:
                                DaoFicha daoAc=new DaoFicha(getApplicationContext());
                                Ficha fichaA = lista.get(recyclerView.getChildAdapterPosition(n));
                                fichaA.setEstado("false");
                                daoAc.actualizar(fichaA);
                                ActualizarRecycler();
                                break;
                        }
                    }
                });
                menu.create().show();
                return true;
            }
        });
    }
}