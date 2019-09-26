package com.example.fileexample;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.pm.PermissionInfoCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.content.pm.PermissionInfo;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.security.Permissions;
import java.security.acl.Permission;

public class MainActivity extends AppCompatActivity {

    EditText etData;
    FileOutputStream fos;
    OutputStreamWriter osw;

    FileInputStream fis;
    InputStreamReader isr;
    BufferedReader br;
    File file;

    String totalStr = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etData = findViewById(R.id.etData);
        checkRuntimePermissions();
    }

    private void checkRuntimePermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            String[] permissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};
            ActivityCompat.requestPermissions(this, permissions, 101);
        } else {
            // Notify the user at the time of App launching
            Toast.makeText(this, "Permission Already Granted", Toast.LENGTH_LONG).show();
        }
    }


    public void writeToFile(View view) {
        String str = etData.getText().toString();
        try {
            File sdCard = Environment.getExternalStorageDirectory();
            File directory = new File(sdCard.getAbsolutePath() + "/Colors_Dir");
            directory.mkdir();
            File file = new File(directory, "MyData1.txt");
            fos = new FileOutputStream(file, true);
            //fos = openFileOutput("MyData.txt", MODE_APPEND);
            osw = new OutputStreamWriter(fos);
            osw.write(str);
            osw.flush();
            osw.close();
            fos.close();
            etData.setText("");
            Toast.makeText(this, "data written to SD Card successfully", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void readFromFile(View view) {
        totalStr = "";
        try {
            File sdCard = Environment.getExternalStorageDirectory();
            File directory = new File(sdCard.getAbsolutePath() + "/Colors_Dir");
            File file = new File(directory, "MyData1.txt");
            fis = new FileInputStream(file);
            isr = new InputStreamReader(fis);
            br = new BufferedReader(isr);
            String str = br.readLine(); // first string - Hello
            while (str != null) {
                totalStr += str;
                str = br.readLine(); // checking next line is present
            }
            etData.setText(totalStr);
            Toast.makeText(this, "data read from file successfully", Toast.LENGTH_LONG).show();
            br.close();
            isr.close();
            fis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
