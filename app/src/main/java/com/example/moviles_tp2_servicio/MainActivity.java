package com.example.moviles_tp2_servicio;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Chronometer chronometer;

    // TUTORIALES
    //COMO CREAR UN SERVICE EN ANDROID
    //https://programandoointentandolo.com/2014/07/crear-service-en-android.html

    //INICIAR UN SERVICIO EN ANDROID AUTOMATICAMENTE
    //https://programandoointentandolo.com/2014/07/iniciar-servicio-android-automaticamente.html

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        IniciarComponentes();

        // Otros premisos: RECEIVE_SMS
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.M
                && checkSelfPermission(Manifest.permission.READ_SMS)
                != PackageManager.PERMISSION_GRANTED){
            requestPermissions(new String[]{Manifest.permission.READ_SMS},1000);
        }

        // Leer los sms del celular
        //displaySmsLog();

        Tarea1 tarea1 = new Tarea1();
        Thread trabajador1 = new Thread(tarea1, "trabajador1");
        trabajador1.start();

        Log.d("mensaje", "onCreate del MainActivity");

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("mensaje", "onDestroy del MainActivity");
    }

    private void IniciarComponentes() {
        chronometer = findViewById(R.id.chronometer);

    }

    private void displaySmsLog() {
        // VER: https://stackoverflow.com/questions/1976252/how-to-use-sms-content-provider-where-are-the-docs

        // Uri uriSms = Uri.parse("content://sms/inbox");
        // Cursor c = context.getContentResolver().query(uriSms, null,null,null,null);
        // column names for above provider:
        // 0: _id
        // 1: thread_id
        // 2: address
        // 3: person
        // 4: date
        // 5: protocol
        // 6: read
        // 7: status
        // 8: type
        // 9: reply_path_present
        // 10: subject
        // 11: body
        // 12: service_center
        // 13: locked

        Uri allMessages = Uri.parse("content://sms/");
        //Cursor cursor = managedQuery(allMessages, null, null, null, null); Both are same
        Cursor cursor = this.getContentResolver().query(allMessages, null,
                null, null, null);

        Log.d("mensaje","Listado...");
        while (cursor.moveToNext()) {
            for (int i = 0; i < cursor.getColumnCount(); i++) {
                Log.d(cursor.getColumnName(i) + "", cursor.getString(i) + "");
            }
            Log.d("One row finished", "**************************************************");
        }

    }

    public void iniciarCronometro(Chronometer chronometer) {
        chronometer.start();
    }

}