package com.example.agenda.Fragments;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.agenda.Objetos.Archivo;
import com.example.agenda.R;

import java.io.IOException;

public class FragmentAudio extends Fragment {
    private Archivo archivo;
    //WIDGETS
    private Button btnReproducir;
    private Button btnPausar;
    private MediaPlayer mediaPlayer=new MediaPlayer();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View vista=inflater.inflate(R.layout.fragment_audio,container,false);
        btnReproducir=(Button)vista.findViewById(R.id.btnReproducirAudio);
        btnPausar=(Button)vista.findViewById(R.id.btnPausarAudio);
        Uri uri=Uri.parse(archivo.getRuta());
        try {
            mediaPlayer.setDataSource(getContext(),uri);
            mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        btnPausar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.pause();
            }
        });
        btnReproducir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.start();
            }
        });
        return vista;
    }

    public void setArchivo(Archivo archivo) {
        this.archivo = archivo;
    }
}
