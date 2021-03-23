package com.example.moviles_tp2_servicio;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ListarSMS extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Otros premisos: RECEIVE_SMS
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.M
                && checkSelfPermission(Manifest.permission.READ_SMS)
                != PackageManager.PERMISSION_GRANTED){
            requestPermissions(new String[]{Manifest.permission.READ_SMS},1000);
        }
    }

    public void displaySmsLog() {
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
                Log.d(cursor.getColumnName(i) + "", cursor.getString(i) + "");
            }
            Log.d("One row finished",
                    "**************************************************");
        }

    }
}
