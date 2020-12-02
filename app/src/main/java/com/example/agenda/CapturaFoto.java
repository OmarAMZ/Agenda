package com.example.agenda;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;

import androidx.core.content.FileProvider;

import java.io.File;
import java.io.IOException;

public class CapturaFoto extends Activity {
    ImageView img ;
    Uri photoURI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_captura_foto);
        img = (ImageView)findViewById(R.id.img);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.captura_foto, menu);
        return true;
    }

    public void btnCapturaFoto_click(View v) {
        Intent camaraFoto = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File foto = null;
        Uri imageUri;
        try {
            foto = File.createTempFile("miFoto", ".jpg",
                    Environment.getExternalStorageDirectory());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        photoURI = FileProvider.getUriForFile(this,
                this.getApplicationContext().getPackageName()
                        + ".net.ivanvega.provider", foto);
        camaraFoto.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        camaraFoto.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
        startActivityForResult(camaraFoto,1
        );

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        img.setImageURI(photoURI);
        super.onActivityResult(requestCode, resultCode, data);
    }
}
