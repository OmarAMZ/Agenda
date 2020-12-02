package com.example.agenda.Daos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.agenda.Objetos.Ficha;

import java.util.ArrayList;

public class DaoFicha {
    private SQLiteDatabase bd;

    public DaoFicha(Context context){
        this.bd=new BaseDeDatos(context).getWritableDatabase();
    }

    public long Insert(Ficha ficha){
        ContentValues cnt=new ContentValues();
        cnt.put(BaseDeDatos.FICHACOLUMNS[0],ficha.getTitulo());
        cnt.put(BaseDeDatos.FICHACOLUMNS[1],ficha.getDescripcion());
        cnt.put(BaseDeDatos.FICHACOLUMNS[2],ficha.getTipo());
        cnt.put(BaseDeDatos.FICHACOLUMNS[3],ficha.getFechacreacion());
        cnt.put(BaseDeDatos.FICHACOLUMNS[4],ficha.getFechaRecordatorio());
        cnt.put(BaseDeDatos.FICHACOLUMNS[5],ficha.getEstado());

        return  bd.insert(BaseDeDatos.FICHA,null,cnt);
    }

    public ArrayList<Ficha> SeleccionarTodos(){
        ArrayList<Ficha>lista=new ArrayList<Ficha>();
        Cursor c=bd.query(BaseDeDatos.FICHA,BaseDeDatos.FICHACOLUMNS,null,null,null,null,null);
        while(c.moveToNext()){
            lista.add(new Ficha(c.getString(0),c.getString(1),c.getString(2),c.getString(3)
                    ,c.getString(4),c.getString(5)));
        }
        return lista;
    }
    public ArrayList<Ficha>  SeleccionarTodos(String titulo){
        ArrayList<Ficha>lista=new ArrayList<Ficha>();
        Cursor c=bd.query(BaseDeDatos.FICHA,BaseDeDatos.FICHACOLUMNS,"titulo like '%"+titulo+"%'",null,null,null,null);
        while(c.moveToNext()){
            lista.add(new Ficha(c.getString(0),c.getString(1),c.getString(2),c.getString(3)
                    ,c.getString(4),c.getString(5)));
        }
        return lista;
    }

    public Ficha seleccionarFicha(String titulo){
        Cursor c=bd.query(BaseDeDatos.FICHA,BaseDeDatos.FICHACOLUMNS,BaseDeDatos.FICHACOLUMNS[0]+"=?",new String[]{titulo},null,null,null);
        Ficha fi=null;
        while(c.moveToNext()){
            fi=new Ficha(c.getString(0),c.getString(1),c.getString(2),c.getString(3)
                    ,c.getString(4),c.getString(5));
        }
        return fi;
    }

    public boolean eliminar(Ficha ficha){
        int no=bd.delete(BaseDeDatos.ARCHIVO,BaseDeDatos.ARCHIVOCOLUMNS[4]+"=?",new String[]{ficha.getTitulo()});
        if(no>0){
            int no1=bd.delete(BaseDeDatos.FICHA,BaseDeDatos.FICHACOLUMNS[0]+"=?",new String[]{ficha.getTitulo()});
            if(no1>0){
                return true;
            }else{
                return false;
            }
        }else{
            return false;
        }
    }

    public boolean actualizar(Ficha ficha){
        ContentValues cnt=new ContentValues();
        cnt.put(BaseDeDatos.FICHACOLUMNS[1],ficha.getDescripcion());
        cnt.put(BaseDeDatos.FICHACOLUMNS[2],ficha.getTipo());
        cnt.put(BaseDeDatos.FICHACOLUMNS[3],ficha.getFechacreacion());
        cnt.put(BaseDeDatos.FICHACOLUMNS[4],ficha.getFechaRecordatorio());
        cnt.put(BaseDeDatos.FICHACOLUMNS[5],ficha.getEstado());
        long no=bd.update(BaseDeDatos.FICHA,cnt,BaseDeDatos.FICHACOLUMNS[0]+"=?",new String[]{ficha.getTitulo()});
        if(no>0){
            return true;
        }else{
            return false;
        }
    }
}
