package com.example.moviles_tp2_servicio;

import android.app.Service;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.net.Uri;
import android.os.IBinder;
import android.util.Log;

public class SrvListarSMS extends Service {
    private Uri allMessages = Uri.parse("content://sms/");
    private Cursor cursor;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        Log.d("mensaje","Memoria baja????");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        displaySmsLog();
        return START_STICKY;
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


        // Crea una clase interna Thread que es un hilo, el cual ejecuta la rutina de leer los SMS
        new Thread() {

            public void run() {

                while(true) {
                    Log.d("mensaje: ", Thread.currentThread().getName() + " " + "Lanzar tarea");

                    cursor = SrvListarSMS.this.getContentResolver().query(allMessages, null,
                            null, null, null);
                    //while (cursor.moveToNext()) {
                    if ( cursor.getCount() >= 5) {
                        for (int f = 0; f < 5; f++) {
                            cursor.moveToPosition(f);
                            for (int i = 0; i < cursor.getColumnCount(); i++) {
                                Log.d(cursor.getColumnName(i) + "", " (" + i + ") " + cursor.getString(i) + "");
                            }
                            Log.d("One row finished", "**************************************************");
                        }
                    }

                    // poner a dormir el hilo
                    try {
                        Thread.sleep(9000); // Pausar el hilo
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }





            }

        }.start();
        //Cursor cursor = managedQuery(allMessages, null, null, null, null); Both are same
        cursor = this.getContentResolver().query(allMessages, null,
                null, null, null);

        //while (cursor.moveToNext()) {
        for (int f=0; f<5; f++) {
            cursor.moveToPosition(f);
            for (int i = 0; i < cursor.getColumnCount(); i++) {
                Log.d(cursor.getColumnName(i) + "", " (" + i +") "+cursor.getString(i) + "");
            }
            Log.d("One row finished","**************************************************");
        }

    }
}