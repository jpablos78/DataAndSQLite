package com.example.jpablos.dataandsqlite;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;

public class ExternalStoragePublicDirectoryActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String LOG_TAG_EXTERNAL_STORAGE = "EXTERNAL_STORAGE";
    private static final int REQUEST_CODE_WRITE_EXTERNAL_STORAGE_PERMISSION = 1;
    private static final int REQUEST_CODE_READ_EXTERNAL_STORAGE_PERMISSION = 1;

    private final String FILENAME = "testfile.txt";
    EditText mEditText;
    private Button btnRead;
    private Button btnWrite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_external_storage_public_directory);

        mEditText = findViewById(R.id.editText);
        btnRead = findViewById(R.id.buttonRead);
        btnWrite = findViewById(R.id.buttonWrite);

        btnRead.setOnClickListener(this);
        btnWrite.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Button button = (Button) view;

        switch (button.getId()) {
            case R.id.buttonRead:
                readFile(view);
                break;
            case R.id.buttonWrite:
                writeFile(view);
                break;
        }
    }

    public void writeFile(View view) {
        try {
            if (ExternalStorageUtil.isExternalStorageMounted()) {
                //Check whether this app has write external storage permission or not.
                int writeExternalStoragePermission = ContextCompat.checkSelfPermission(ExternalStoragePublicDirectoryActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
                // If do not grant write external storage permission.
                if (writeExternalStoragePermission != PackageManager.PERMISSION_GRANTED) {
                    // Request user to grant write external storage permission.
                    ActivityCompat.requestPermissions(ExternalStoragePublicDirectoryActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE_WRITE_EXTERNAL_STORAGE_PERMISSION);
                } else {
                    // Save email_public.txt file to /storage/emulated/0/DCIM folder
                    String publicDcimDirPath = ExternalStorageUtil.getPublicExternalStorageBaseDir(Environment.DIRECTORY_DCIM);

                    File textFile = new File(publicDcimDirPath, FILENAME);
                    FileOutputStream fileOutputStream = new FileOutputStream(textFile);
                    fileOutputStream.write(mEditText.getText().toString().getBytes());
                    fileOutputStream.close();

                    Toast.makeText(getApplicationContext(), "Save to public external storage success. File Path " + textFile.getAbsolutePath(), Toast.LENGTH_LONG).show();
                }
            }
        } catch (Exception ex) {
            Log.e(LOG_TAG_EXTERNAL_STORAGE, ex.getMessage(), ex);

            Toast.makeText(getApplicationContext(), "Save to public external storage failed. Error message is " + ex.getMessage(), Toast.LENGTH_LONG).show();
        }

    }

    public void readFile(View view) {
        try {
            if (ExternalStorageUtil.isExternalStorageReadOnly()) {
//            if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
//                    || Environment.MEDIA_MOUNTED_READ_ONLY.equals(Environment.getExternalStorageState())) {
                //Check whether this app has write external storage permission or not.
                int readExternalStoragePermission = ContextCompat.checkSelfPermission(ExternalStoragePublicDirectoryActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE);
                // If do not grant read external storage permission.
                if (readExternalStoragePermission != PackageManager.PERMISSION_GRANTED) {
                    // Request user to grant write external storage permission.
                    ActivityCompat.requestPermissions(ExternalStoragePublicDirectoryActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE_READ_EXTERNAL_STORAGE_PERMISSION);
                } else {
                    StringBuilder stringBuilder = new StringBuilder();
                    String publicDcimDirPath = ExternalStorageUtil.getPublicExternalStorageBaseDir(Environment.DIRECTORY_DCIM);

                    File textFile = new File(publicDcimDirPath, FILENAME);
                    FileInputStream fileInputStream = new FileInputStream(textFile);

                    if (fileInputStream != null) {
                        InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
                        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                        String newLine = null;

                        while ((newLine = bufferedReader.readLine()) != null) {
                            stringBuilder.append(newLine + "\n");
                        }
                        fileInputStream.close();
                    }

                    mEditText.setText(stringBuilder);

                    Toast.makeText(getApplicationContext(), "Read to public external storage success. File Path " + textFile.getAbsolutePath(), Toast.LENGTH_LONG).show();
                }
            }
        } catch (Exception ex) {
            Log.e(LOG_TAG_EXTERNAL_STORAGE, ex.getMessage(), ex);

            Toast.makeText(getApplicationContext(), "Save to public external storage failed. Error message is " + ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}
