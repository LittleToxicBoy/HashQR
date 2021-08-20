package com.moviles.hashqr;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import net.glxn.qrgen.android.QRCode;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class CodigoQR<MainActivity> extends AppCompatActivity {
// AQui inicia el desmadre aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa
    private boolean tienePermisoCamara = false,
            tienePermisoAlmacenamiento = false;

    private static final int CODIGO_PERMISOS_CAMARA = 1,
            CODIGO_PERMISOS_ALMACENAMIENTO = 2;

    private void verificarYPedirPermisosDeCamara() {
        int estadoDePermiso = ContextCompat.checkSelfPermission(CodigoQR.this, Manifest.permission.CAMERA);
        if (estadoDePermiso == PackageManager.PERMISSION_GRANTED) {
            // En caso de que haya dado permisos ponemos la bandera en true
            // y llamar al método
            permisoDeCamaraConcedido();
        } else {
            // Si no, entonces pedimos permisos. Ahora mira onRequestPermissionsResult
            ActivityCompat.requestPermissions(CodigoQR.this,
                    new String[]{Manifest.permission.CAMERA},
                    CODIGO_PERMISOS_CAMARA);
        }
    }



    private void verificarYPedirPermisosDeAlmacenamiento() {
        int estadoDePermiso = ContextCompat.checkSelfPermission(CodigoQR.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (estadoDePermiso == PackageManager.PERMISSION_GRANTED) {
            // En caso de que haya dado permisos ponemos la bandera en true
            // y llamar al método
            permisoDeAlmacenamientoConcedido();
        } else {
            // Si no, entonces pedimos permisos. Ahora mira onRequestPermissionsResult
            ActivityCompat.requestPermissions(CodigoQR.this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    CODIGO_PERMISOS_ALMACENAMIENTO);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case CODIGO_PERMISOS_CAMARA:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    permisoDeCamaraConcedido();
                } else {
                    permisoDeCamaraDenegado();
                }
                break;

            case CODIGO_PERMISOS_ALMACENAMIENTO:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    permisoDeAlmacenamientoConcedido();
                } else {
                    permisoDeAlmacenamientoDenegado();
                }
                break;

            // Aquí más casos dependiendo de los permisos
            // case OTRO_CODIGO_DE_PERMISOS...

        }
    }


    private void permisoDeAlmacenamientoConcedido() {
        // Aquí establece las banderas o haz lo que
        // ibas a hacer cuando el permiso se concediera. Por
        // ejemplo puedes poner la bandera en true y más
        // tarde en otra función comprobar esa bandera
        Toast.makeText(CodigoQR.this, "El permiso para el almacenamiento está concedido", Toast.LENGTH_SHORT).show();
        tienePermisoAlmacenamiento = true;
    }

    private void permisoDeAlmacenamientoDenegado() {
        // Esto se llama cuando el usuario hace click en "Denegar" o
        // cuando lo denegó anteriormente
        Toast.makeText(CodigoQR.this, "El permiso para el almacenamiento está denegado", Toast.LENGTH_SHORT).show();
    }

    private void permisoDeCamaraConcedido() {
        // Aquí establece las banderas o haz lo que
        // ibas a hacer cuando el acceso a la cámara se condeciera
        // Por ejemplo puedes poner la bandera en true y más
        // tarde en otra función comprobar esa bandera
        Toast.makeText(CodigoQR.this, "El permiso para la cámara está concedido", Toast.LENGTH_SHORT).show();
        tienePermisoCamara = true;
    }

    private void permisoDeCamaraDenegado() {
        // Esto se llama cuando el usuario hace click en "Denegar" o
        // cuando lo denegó anteriormente
        Toast.makeText(CodigoQR.this, "El permiso para la cámara está denegado", Toast.LENGTH_SHORT).show();
    }



    //AQUI termina aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_codigo_q_r);

        //Button guardar = findViewById(R.id.btn_guardar);
        String calidad = getIntent().getStringExtra("calidadQR");
        String cBaja = "Calidad Baja";

        System.out.println("Calidad pvta" + calidad);
        System.out.println("Calidad  2 pvta" + cBaja);

        String ori = getIntent().getStringExtra("mm");
        TextView a = findViewById(R.id.lbl_original);
        a.setText(ori);


        String m2 = getIntent().getStringExtra("mn2");
        TextView c = findViewById(R.id.lbl_m2);
        c.setText(m2);

        String fin = getIntent().getStringExtra("dato");
        TextView d = findViewById(R.id.lbl_final);
        d.setText(fin);

        String texto = getIntent().getStringExtra("dato");

        if (calidad.equals(cBaja)){
            Bitmap bitmap = QRCode.from(texto).withSize(130, 130).bitmap();
            ImageView imagen = findViewById(R.id.img_qr);
            imagen.setImageBitmap(bitmap);
        }
        if (calidad.equals("Calidad Media")){
            Bitmap bitmap = QRCode.from(texto).withSize(200, 200).bitmap();
            ImageView imagen = findViewById(R.id.img_qr);
            imagen.setImageBitmap(bitmap);
        }
        if (calidad.equals("Calidad Alta")){
            Bitmap bitmap = QRCode.from(texto).withSize(300, 300).bitmap();
            ImageView imagen = findViewById(R.id.img_qr);
            imagen.setImageBitmap(bitmap);
        }



    }

    public void btn_guardar(View view){

        verificarYPedirPermisosDeAlmacenamiento();

        String calidad = getIntent().getStringExtra("calidadQR");
        String texto = getIntent().getStringExtra("dato");


        FileOutputStream fos;

        try {
            // Guardar en el almacenamiento externo con el nombre de "codigo.png"

            if (calidad.equals("Calidad Baja")){
                ByteArrayOutputStream byteArrayOutputStream = QRCode.from(texto).withSize(130, 130).stream();
                fos = new FileOutputStream(Environment.getExternalStorageDirectory() + "/codigo.png");
                byteArrayOutputStream.writeTo(fos);
                Toast.makeText(CodigoQR.this, "Código guardado", Toast.LENGTH_SHORT).show();
            }
            if (calidad.equals("Calidad Media")){
                ByteArrayOutputStream byteArrayOutputStream = QRCode.from(texto).withSize(200, 200).stream();
                fos = new FileOutputStream(Environment.getExternalStorageDirectory() + "/codigo.png");
                byteArrayOutputStream.writeTo(fos);
                Toast.makeText(CodigoQR.this, "Código guardado", Toast.LENGTH_SHORT).show();
            }
            if (calidad.equals("Calidad Alta")){
                ByteArrayOutputStream byteArrayOutputStream = QRCode.from(texto).withSize(300, 300).stream();
                fos = new FileOutputStream(Environment.getExternalStorageDirectory() + "/codigo.png");
                byteArrayOutputStream.writeTo(fos);
                Toast.makeText(CodigoQR.this, "Código guardado", Toast.LENGTH_SHORT).show();
            }


        } catch (FileNotFoundException e) {
            // Nota: aquí indica al usuario que algo salió mal
            e.printStackTrace();
        } catch (IOException e) {
            // Nota: aquí indica al usuario que algo salió mal
            e.printStackTrace();
        }

        Intent Inicio = new Intent(this, Home.class);
        startActivity(Inicio);
    }

}