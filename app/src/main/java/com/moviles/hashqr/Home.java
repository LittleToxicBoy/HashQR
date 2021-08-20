package com.moviles.hashqr;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Home extends AppCompatActivity {

    EditText cadena;
    String men1;
    String mensaje2;
    String mensajeFinal;
    String original;

    RadioGroup radioG;
    RadioButton Calidad;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        cadena = (EditText)findViewById(R.id.txt_cadena);
        radioG = findViewById(R.id.rg_Calidad);


    }

    public void btnH_256(View view) throws NoSuchAlgorithmException {
        int raB = radioG.getCheckedRadioButtonId();
        Calidad = findViewById(raB);
        String tipo_calidad = Calidad.getText().toString();
        System.out.println("AHORA SI PERROS:  " + tipo_calidad);


        original = cadena.getText().toString();
        String mensaje1;
        mensaje1 = cadena.getText().toString();

        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(mensaje1.getBytes());

        byte[] digest = md.digest();
        StringBuffer sb = new StringBuffer();
        for (byte b : digest){
            sb.append(String.format("%02x", b & 0xff));
        }

        men1 = sb.toString();
        mensaje2 = mensaje1 + sb.toString();

        System.out.println("Mensaje 2:   " + mensaje2);
        btn_512F();

        Intent Qr = new Intent(this, CodigoQR.class);
        Qr.putExtra("dato", mensajeFinal);
        Qr.putExtra("mm", original);
        Qr.putExtra("mn1", men1);
        Qr.putExtra("mn2", mensaje2);
        Qr.putExtra("calidadQR", tipo_calidad);
        startActivity(Qr);
    }


    public  void  btnH_386(View view) throws NoSuchAlgorithmException {
        original = cadena.getText().toString();
        String mensaje1;
        mensaje1 = cadena.getText().toString();

        int raB = radioG.getCheckedRadioButtonId();
        Calidad = findViewById(raB);
        String tipo_calidad = Calidad.getText().toString();
        System.out.println("AHORA SI PERROS:  " + tipo_calidad);

        MessageDigest md = MessageDigest.getInstance("SHA-384");
        md.update(mensaje1.getBytes());

        byte[] digest = md.digest();
        StringBuffer sb = new StringBuffer();
        for (byte b : digest){
            sb.append(String.format("%02x", b & 0xff));
        }

        System.out.println("SHA 384 Hash: " + sb.toString());
        men1 = sb.toString();
        mensaje2 = mensaje1 + sb.toString();

        System.out.println("Mensaje 2:   " + mensaje2);
        btn_512F();

        Intent Qr = new Intent(this, CodigoQR.class);
        Qr.putExtra("dato", mensajeFinal);
        Qr.putExtra("mm", original);
        Qr.putExtra("mn1", men1);
        Qr.putExtra("mn2", mensaje2);
        Qr.putExtra("calidadQR", tipo_calidad);
        startActivity(Qr);
    }

    public void btn_512 (View view) throws NoSuchAlgorithmException {
        original = cadena.getText().toString();
        String mensaje1;
        mensaje1 = cadena.getText().toString();

        int raB = radioG.getCheckedRadioButtonId();
        Calidad = findViewById(raB);
        String tipo_calidad = Calidad.getText().toString();
        System.out.println("AHORA SI PERROS:  " + tipo_calidad);

        MessageDigest md = MessageDigest.getInstance("SHA-512");
        md.update(mensaje1.getBytes());

        byte[] digest = md.digest();
        StringBuffer sb = new StringBuffer();
        for (byte b : digest){
            sb.append(String.format("%02x", b & 0xff));
        }

        System.out.println("SHA 512 Hash: " + sb.toString());
        men1 = sb.toString();
        mensaje2 = mensaje1 + sb.toString();

        System.out.println("Mensaje 2:   " + mensaje2);
        btn_512F();

        Intent Qr = new Intent(this, CodigoQR.class);
        Qr.putExtra("dato", mensajeFinal);
        Qr.putExtra("mm", original);
        Qr.putExtra("mn1", men1);
        Qr.putExtra("mn2", mensaje2);
        Qr.putExtra("calidadQR", tipo_calidad);
        startActivity(Qr);
    }

    public void btn_512F () throws NoSuchAlgorithmException {

        MessageDigest md = MessageDigest.getInstance("SHA-512");
        md.update(mensaje2.getBytes());

        byte[] digest = md.digest();
        StringBuffer sb = new StringBuffer();
        for (byte b : digest){
            sb.append(String.format("%02x", b & 0xff));
        }

        System.out.println("Hash  " + sb.toString());
        mensajeFinal = cadena.getText().toString() + sb.toString();
        System.out.println( "FINAL:   " + mensajeFinal);
    }
}