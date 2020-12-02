package com.example.agenda;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.agenda.Daos.DaoArchivo;
import com.example.agenda.Daos.DaoFicha;
import com.example.agenda.Daos.DaoRecordatorio;
import com.example.agenda.Notificacion.PlanificarAlarma;
import com.example.agenda.Objetos.Archivo;
import com.example.agenda.Objetos.ArchivoAdapter;
import com.example.agenda.Objetos.Ficha;
import com.example.agenda.Objetos.Recordatorio;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;

public class actualizar extends AppCompatActivity {
    private Ficha ficha;
    private ArrayList<Archivo> lista;
    private ArrayList<Recordatorio>listaRecordatorio;
    private TextView titulo;
    private TextView descripcion;
    private RadioButton nota;
    private RadioButton tarea;
    private TextView recordatorio;
    private String titulo1;
    private RecyclerView recyclerView;
    private Button btnTerminacion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actualizar);

        Bundle bundle=getIntent().getExtras();
        titulo1=bundle.getString("titulo");

        titulo=findViewById(R.id.txtTituloA);
        descripcion=findViewById(R.id.txtDescripcionA);
        nota=findViewById(R.id.rdNotaA);
        btnTerminacion=findViewById(R.id.btnAgregarAlarmaA);
        nota.setEnabled(false);
        tarea=findViewById(R.id.rdTareaA);
        tarea.setEnabled(false);
        titulo.setEnabled(false);
        recordatorio=findViewById(R.id.lblRecordatorioA);
        recyclerView=findViewById(R.id.rcclcArchivoListaA);

        llenarCampos();
    }

    public void llenarCampos(){
        DaoArchivo daoArchivo=new DaoArchivo(getApplicationContext());
        DaoFicha daoFicha=new DaoFicha(getApplicationContext());
        DaoRecordatorio daoRecordatorio=new DaoRecordatorio(getApplicationContext());
        Toast.makeText(this,titulo1,Toast.LENGTH_SHORT).show();
        ficha=daoFicha.seleccionarFicha(titulo1);
        lista =daoArchivo.seleccionarArchivos(ficha);
        listaRecordatorio= daoRecordatorio.seleccionar(ficha);
        titulo.setText(ficha.getTitulo());
        descripcion.setText(ficha.getDescripcion());
        if(ficha.getTipo().equals("nota")){
            nota.setChecked(true);
            btnTerminacion.setEnabled(false);
        }else if(ficha.getTipo().equals("tarea")){
            tarea.setChecked(true);
            recordatorio.setText(ficha.getFechaRecordatorio());
            btnTerminacion.setEnabled(true);
        }
        actualizarArchivos();
    }

    public void actualizarArchivos(){
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        final ArchivoAdapter adapter=new ArchivoAdapter(this,lista);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                final View n=v;
                AlertDialog.Builder menu=new AlertDialog.Builder(v.getContext());
                CharSequence[] opciones= {"Eliminar"};
                menu.setItems(opciones, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int opcion) {
                        switch (opcion){
                            case 0:
                                DaoArchivo da=new DaoArchivo(getApplicationContext());
                                da.eliminar(lista.get(recyclerView.getChildAdapterPosition(n)));
                                lista.remove(lista.get(recyclerView.getChildAdapterPosition(n)));
                                actualizarArchivos();
                                break;
                        }
                    }
                });
                menu.create().show();
                return true;
            }
        });
    }

    public void setTerminar(View v){
        Calendar fechaActual=Calendar.getInstance();
        Calendar fechaActual1=Calendar.getInstance();
        TimePickerDialog timePickerDialog=new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                recordatorio.setText(recordatorio.getText().toString()+"-"+hourOfDay+":"+minute);
            }
        },fechaActual1.get(Calendar.HOUR),fechaActual1.get(Calendar.MINUTE),false);
        timePickerDialog.show();
        DatePickerDialog datePickerDialog=new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                recordatorio.setText(dayOfMonth+"-"+month+"-"+year);
            }
        },fechaActual.get(Calendar.YEAR),fechaActual.get(Calendar.MONTH),fechaActual.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    public void addRecordatorio(View v){
        Calendar fechaActual=Calendar.getInstance();
        Calendar fechaActual1=Calendar.getInstance();
        final Recordatorio recordatorio=new Recordatorio();
        recordatorio.setTitulo(ficha.getTitulo());
        TimePickerDialog timePickerDialog=new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                recordatorio.setMinuto(minute);
                recordatorio.setHora(hourOfDay);
                listaRecordatorio.add(recordatorio);
                DaoRecordatorio daoRecordatorio=new DaoRecordatorio(getApplicationContext());
                daoRecordatorio.Insert(recordatorio);

                AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                Intent intent = new Intent(getApplicationContext(), PlanificarAlarma.class);
                PendingIntent pi = PendingIntent.getBroadcast(getApplicationContext(), 100, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                Toast.makeText(getApplicationContext(),"s"+recordatorio.milis(),Toast.LENGTH_LONG).show();
                alarmManager.set(AlarmManager.RTC_WAKEUP,  30000, pi);
                Toast.makeText(getApplicationContext(),"s"+recordatorio.milis(),Toast.LENGTH_LONG).show();
            }
        },fechaActual1.get(Calendar.HOUR),fechaActual1.get(Calendar.MINUTE),false);
        timePickerDialog.show();
        DatePickerDialog datePickerDialog=new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                recordatorio.setMes(month);
                recordatorio.setAnio(year);
                recordatorio.setDia(dayOfMonth);

            }
        },fechaActual.get(Calendar.YEAR),fechaActual.get(Calendar.MONTH),fechaActual.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    public void btnVerRecordatoriosAA(View v){
        AlertDialog.Builder menu=new AlertDialog.Builder(v.getContext());
        CharSequence[] opciones= new CharSequence[listaRecordatorio.size()];
        for (int i=0; i<opciones.length;i++){
            opciones[i]=listaRecordatorio.get(i).toString();
        }
        menu.setItems(opciones, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int opcion){

            }
        });
        menu.create().show();
    }

    public void btnGuardarAOnClick(View v){
        DaoFicha dao=new DaoFicha(getApplicationContext());
        ficha.setDescripcion(descripcion.getText().toString());
        if(ficha.getTipo().equals("nota")){

        }else if(ficha.getTipo().equals("tarea")){
            ficha.setFechaRecordatorio(recordatorio.getText().toString());
        }
        if(dao.actualizar(ficha)){
            Toast.makeText(getApplicationContext(),"Se actualizo correctamente",Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(getApplicationContext(),Principal.class);
            startActivity(intent);
        }
    }

    public void btnArchivosAOnClick(View v){
        final View vista=v;
        AlertDialog.Builder menu=new AlertDialog.Builder(v.getContext());
        Resources res=getResources();
        CharSequence[] opciones= {res.getString(R.string.audio),res.getString(R.string.video),res.getString(R.string.foto),res.getString(R.string.archivo)};
        menu.setItems(opciones, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int opcion) {
                switch (opcion){
                    case 0:
                        grabarAudio();
                        break;
                    case 1:
                        tomarVideo();
                        break;
                    case 2:
                        TomarImagen();
                        break;
                    case 3:
                        BuscarImagenes();
                        break;
                }
            }
        });
        menu.create().show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if(resultCode==RESULT_OK){
            switch (requestCode){
                case 1:
                    Archivo archivo=new Archivo(1,descripcion.getText().toString(),"imagen",uri.toString(),titulo.getText().toString());
                    lista.add(archivo);
                    DaoArchivo da=new DaoArchivo(getApplicationContext());
                    da.insertarArchivo(archivo);
                    actualizarArchivos();
                    break;
                case 2:
                    Uri ima=data.getData();
                    String cadena=ima.toString();
                    Archivo archivo2=new Archivo(1,descripcion.getText().toString(),"imagen",cadena,titulo.getText().toString());
                    lista.add(archivo2);
                    DaoArchivo da2=new DaoArchivo(getApplicationContext());
                    da2.insertarArchivo(archivo2);
                    actualizarArchivos();
                    break;
                case 3:
                    Uri vi=data.getData();
                    String cadena1=vi.toString();
                    Archivo archivo1=new Archivo(1,descripcion.getText().toString(),"video",cadena1,titulo.getText().toString());
                    lista.add(archivo1);
                    DaoArchivo da1=new DaoArchivo(getApplicationContext());
                    da1.insertarArchivo(archivo1);
                    actualizarArchivos();
                    break;
                case 4:
                    Uri audio=data.getData();
                    String cadena4=audio.toString();
                    Archivo archivo4=new Archivo(1,descripcion.getText().toString(),"audio",cadena4,titulo.getText().toString());
                    lista.add(archivo4);
                    DaoArchivo da4=new DaoArchivo(getApplicationContext());
                    da4.insertarArchivo(archivo4);
                    actualizarArchivos();
                    break;
            }
        }
    }

    Uri uri;
    public void TomarImagen(){
        Intent camaraFoto=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        Long consecutivo=System.currentTimeMillis()/1000;
        String nombre=consecutivo.toString()+".png";
        File foto=new File(getExternalFilesDir(null),nombre);
        camaraFoto.putExtra(MediaStore.EXTRA_OUTPUT,Uri.fromFile(foto));
        uri=Uri.fromFile(foto);
        startActivityForResult(camaraFoto,1);
    }
    public void tomarVideo(){
        Intent intent=new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        startActivityForResult(intent,3);
    }
    public void grabarAudio(){
        Intent intent=new Intent(MediaStore.Audio.Media.RECORD_SOUND_ACTION);
        startActivityForResult(intent,4);
    }
    private void BuscarImagenes() {
        Intent intent =new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(intent,2);
    }
}
