package com.example.agenda.Fragments;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.agenda.Objetos.Archivo;
import com.example.agenda.R;

import java.io.IOException;

public class FragmentDetalle extends Fragment {
    private Archivo archivo;
    //WIDGETS
    private ImageView imageView;
    private MediaPlayer mediaPlayer=new MediaPlayer();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View vista=inflater.inflate(R.layout.fragment_detalle,container,false);
        imageView = vista.findViewById(R.id.imgvwImagen);
        Uri uri=Uri.parse(archivo.getRuta());
        imageView.setImageURI(uri);
        try {
            mediaPlayer.setDataSource(getContext(),uri);
            mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return vista;
    }


    public void setArchivo(Archivo archivo) {
        this.archivo = archivo;
    }
}
