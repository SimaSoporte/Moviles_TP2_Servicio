package com.example.moviles_tp2_servicio;

import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class Cronometro extends Service {
    private Chronometer chronometer;
    private long pauseOffset;
    public static MainActivity LISTENER;

    public static void setUpdateListener(MainActivity poiService) {
        LISTENER = poiService;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //iniciarCronometro();
        IniciarComponentes();
        Log.d("mensaje", "onCreate del servicio");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //LISTENER.iniciarCronometro(chronometer);
        //chronometer.start();
        Log.d("mensaje", "onStartCommand del servicio");
        return START_STICKY;
    }

    

    @Override
    public void onDestroy() {
        super.onDestroy();
        chronometer.stop();
        Log.d("mensaje", "onDestroy del servicio");
    }

    public void IniciarComponentes() {
        //chronometer.setFormat("Time: 00:%s");
        //chronometer.setBase(SystemClock.elapsedRealtime());
/*        chronometer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                if ( (SystemClock.elapsedRealtime() - chronometer.getBase() >= 9000) ) {
                    // Resetear cronometro al llegar al 9000 ms
                    chronometer.setBase(SystemClock.elapsedRealtime());
                    Log.d("mensaje", "Checking messages...");
                    // Lista los sms del celular
                    displaySmsLog();
                }
            }
        });*/
    }
    public void onChronometerTick(Chronometer chronometer) {
        if ( (SystemClock.elapsedRealtime() - chronometer.getBase() >= 9000) ) {
            // Resetear cronometro al llegar al 9000 ms
            chronometer.setBase(SystemClock.elapsedRealtime());
            Log.d("mensaje", "Checking messages...");
            // Lista los sms del celular
            displaySmsLog();
        }
    }

    private void starChronometer() {
        chronometer.setBase(SystemClock.elapsedRealtime() - pauseOffset);
        chronometer.start();
    }
    private void pauseChronometer() {
            chronometer.stop();
            pauseOffset = SystemClock.elapsedRealtime() - chronometer.getBase();
    }
    private void resetChronometer() {
            chronometer.setBase(SystemClock.elapsedRealtime());
            pauseOffset = 0;
    }

    public int TimeElapse(Chronometer chronometer) {
        return (int) (SystemClock.elapsedRealtime() - chronometer.getBase());
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

        while (cursor.moveToNext()) {
            for (int i = 0; i < cursor.getColumnCount(); i++) {
                Log.d(cursor.getColumnName(i) + "", " (" + i +") "+cursor.getString(i) + "");
            }
            Log.d("One row finished",
                    "**************************************************");
        }

    }


}
