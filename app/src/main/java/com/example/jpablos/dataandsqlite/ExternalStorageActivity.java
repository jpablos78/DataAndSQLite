package com.example.jpablos.dataandsqlite;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;

public class ExternalStorageActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String LOG_TAG_EXTERNAL_STORAGE = "EXTERNAL_STORAGE";
    private static final int REQUEST_CODE_WRITE_EXTERNAL_STORAGE_PERMISSION = 1;

    private final String FILENAME = "testfile.txt";
    EditText mEditText;
    private Button btnRead;
    private Button btnWrite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_external_storage);

        mEditText = findViewById(R.id.editText);
        btnRead = findViewById(R.id.buttonRead);
        btnWrite = findViewById(R.id.buttonWrite);

        btnRead.setOnClickListener(this);
        btnWrite.setOnClickListener(this);
    }

//    public boolean isExternalStorageWritable() {
//        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
//            return true;
//        }
//        return false;
//    }
//
//    public boolean isExternalStorageReadable() {
//        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
//                || Environment.MEDIA_MOUNTED_READ_ONLY.equals(Environment.getExternalStorageState())) {
//            return true;
//        }
//        return false;
//    }

    public void writeFile(View view) {
        try {
            if (ExternalStorageUtil.isExternalStorageMounted()) {
                // Check whether this app has write external storage permission or not.
                int writeExternalStoragePermission = ContextCompat.checkSelfPermission(ExternalStorageActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
                // If do not grant write external storage permission.
                if (writeExternalStoragePermission != PackageManager.PERMISSION_GRANTED) {
                    // Request user to grant write external storage permission.
                    ActivityCompat.requestPermissions(ExternalStorageActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE_WRITE_EXTERNAL_STORAGE_PERMISSION);
                } else {
// Save email_private_cache.txt file to /storage/emulated/0/Android/data/com.dev2qa.example/cache folder
                    String privateDirPath = ExternalStorageUtil.getPrivateCacheExternalStorageBaseDir(getApplicationContext());
//
//                    File newFile = new File(privateDirPath, FILENAME);
//
//                    FileWriter fw = new FileWriter(newFile);
//
//                    fw.write(emailEditor.getText().toString());
//
//                    fw.flush();
//
//                    fw.close();


                    File textFile = new File(privateDirPath, FILENAME);
                    FileOutputStream fileOutputStream = new FileOutputStream(textFile);
                    fileOutputStream.write(mEditText.getText().toString().getBytes());
                    fileOutputStream.close();

                    Toast.makeText(getApplicationContext(), "Save to private external storage success. File Path " + textFile.getAbsolutePath(), Toast.LENGTH_LONG).show();
                }
            }
        } catch (Exception ex) {
            Log.e(LOG_TAG_EXTERNAL_STORAGE, ex.getMessage(), ex);
            Toast.makeText(getApplicationContext(), "Save to public external storage failed. Error message is " + ex.getMessage(), Toast.LENGTH_LONG).show();
        }


//        if (isExternalStorageWritable()) {
//            try {
//                //File textFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), FILENAME);
//                File textFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), FILENAME);
//                FileOutputStream fileOutputStream = new FileOutputStream(textFile);
//                fileOutputStream.write(mEditText.getText().toString().getBytes());
//                fileOutputStream.close();
//            } catch (java.io.IOException e) {
//                e.printStackTrace();
//                Toast.makeText(this, "Error writing file " + e.toString(), Toast.LENGTH_LONG).show();
//            }
//        } else {
//            Toast.makeText(this, "Cannot write to External Storage", Toast.LENGTH_LONG).show();
//        }
    }

    public void readFile(View view) {
//        if (isExternalStorageReadable()) {
//            StringBuilder stringBuilder = new StringBuilder();
//
//            try {
//                File textFile = new File(Environment.getExternalStorageDirectory(), FILENAME);
//                FileInputStream fileInputStream = new FileInputStream(textFile);
//                if (fileInputStream != null) {
//                    InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
//                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
//                    String newLine = null;
//
//                    while ((newLine = bufferedReader.readLine()) != null) {
//                        stringBuilder.append(newLine + "\n");
//                    }
//                    fileInputStream.close();
//                }
//                mEditText.setText(stringBuilder);
//            } catch (java.io.IOException e) {
//                e.printStackTrace();
//                Toast.makeText(this, "Error reading file " + e.toString(), Toast.LENGTH_LONG).show();
//            }
//        } else {
//            Toast.makeText(this, "Cannot read External Storage", Toast.LENGTH_LONG).show();
//        }
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
}
