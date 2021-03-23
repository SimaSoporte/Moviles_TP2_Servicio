package com.example.moviles_tp2_servicio;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Tarea1 extends AppCompatActivity implements Runnable {
    private MainActivity lst;

    @Override
    public void run() {
        // La tarea
        while(true){
            try {
                Thread.sleep(9000); // Pausar el hilo
                Log.d("mensaje: ", Thread.currentThread().getName()+ " " + "Lanzar tarea");
                displaySmsLog();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
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

        //while (cursor.moveToNext()) {
        for (int f=0; f<2; f++) {
            cursor.moveToPosition(f);
            for (int i = 0; i < cursor.getColumnCount(); i++) {
                Log.d(cursor.getColumnName(i) + "", " (" + i +") "+cursor.getString(i) + "");
            }
            Log.d("One row finished","**************************************************");
        }

    }
}
