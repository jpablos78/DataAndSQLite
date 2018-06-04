package com.example.jpablos.dataandsqlite;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

public class MainActivity extends AppCompatActivity {
    private static final String LOG_TAG_EXTERNAL_STORAGE = "EXTERNAL_STORAGE";
    private static final int REQUEST_CODE_WRITE_EXTERNAL_STORAGE_PERMISSION = 1;

    private View showDialogView;

    private TextView textViewDialog;

    private Button btnSharedPreferences;
    private Button btnInternalStorage;
    private Button btnExternalStorage;
    private Button btnExternalStoragePublicDirectory;
    private Button btnExternalStoragePrivateDirectory;
    private Button btnPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        btnSharedPreferences = findViewById(R.id.button_shared_preferences);
        btnInternalStorage = findViewById(R.id.button_internal_storage);
        btnExternalStorage = findViewById(R.id.button_external_storage_cached);
        btnExternalStoragePublicDirectory = findViewById(R.id.button_external_storage_public_directory);
        btnExternalStoragePrivateDirectory = findViewById(R.id.button_external_storage_private_directory);
        btnPath = findViewById(R.id.button_path);

        btnSharedPreferences.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SharedPreferencesActivity.class);
                startActivity(intent);
            }
        });

        btnInternalStorage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, InternalStorageActivity.class);
                startActivity(intent);
            }
        });

        btnExternalStoragePublicDirectory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ExternalStoragePublicDirectoryActivity.class);
                startActivity(intent);
            }
        });

        btnExternalStoragePrivateDirectory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ExternalStoragePrivateDirectoryActivity.class);
                startActivity(intent);
            }
        });

        btnExternalStorage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ExternalStorageActivity.class);
                startActivity(intent);
            }
        });

        btnPath.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Because create custom directory in public external storage folder need write permission
                // So we should grant the permission to the app first.

                // Check whether this app has write external storage permission or not.
                int writeExternalStoragePermission = ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
                // If do not grant write external storage permission.
                if (writeExternalStoragePermission != PackageManager.PERMISSION_GRANTED) {
                    // Request user to grant write external storage permission.
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE_WRITE_EXTERNAL_STORAGE_PERMISSION);
                } else {
                    String cadena = "";
                    Log.d(LOG_TAG_EXTERNAL_STORAGE, "Below are public external storage folder path.");

                    cadena = cadena + "Below are public external storage folder path. \n";

                    // Use Environment class to get public external storage directory.
                    File publicDir = Environment.getExternalStorageDirectory();
                    Log.d(LOG_TAG_EXTERNAL_STORAGE, "Environment.getExternalStorageDirectory() : " + publicDir.getAbsolutePath());

                    cadena = cadena + "Environment.getExternalStorageDirectory() : " + publicDir.getAbsolutePath() + "\n";

                    // Because at the beginning of this method, we had grant write external storage permission
                    // So we can create this directory here.
                    File customPublicDir = new File(publicDir, "Custom");
                    customPublicDir.mkdirs();
                    Log.d(LOG_TAG_EXTERNAL_STORAGE, "Create custom dir : " + customPublicDir.getAbsolutePath());
                    cadena = cadena + "Create custom dir : " + customPublicDir.getAbsolutePath() + "\n";

                    File musicPublicDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC);
                    Log.d(LOG_TAG_EXTERNAL_STORAGE, "Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC) : " + musicPublicDir.getAbsolutePath());
                    cadena = cadena + "Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC) : " + musicPublicDir.getAbsolutePath() + "\n";

                    File dcimPublicDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
                    Log.d(LOG_TAG_EXTERNAL_STORAGE, "Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM) : " + dcimPublicDir.getAbsolutePath());
                    cadena = cadena + "Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM) : " + dcimPublicDir.getAbsolutePath() + "\n";

                    Log.d(LOG_TAG_EXTERNAL_STORAGE, "**********************************************************************");

                    Log.d(LOG_TAG_EXTERNAL_STORAGE, "Below are android app private external storage folder path.");
                    cadena = cadena + "Below are android app private external storage folder path. \n";
                    // Use Context class to get app private external storage directory

                    Context context = getApplicationContext();

                    File privateDir = context.getExternalFilesDir(null);
                    Log.d(LOG_TAG_EXTERNAL_STORAGE, "context.getExternalFilesDir(null) : " + privateDir.getAbsolutePath());
                    //Toast.makeText(context, "context.getExternalFilesDir(null) : " + privateDir.getAbsolutePath(), Toast.LENGTH_LONG).show();
                    cadena = cadena + "context.getExternalFilesDir(null) : " + privateDir.getAbsolutePath() + "\n";

                    File musicPrivateDir = context.getExternalFilesDir(Environment.DIRECTORY_MUSIC);
                    Log.d(LOG_TAG_EXTERNAL_STORAGE, "context.getExternalFilesDir(Environment.DIRECTORY_MUSIC) : " + musicPrivateDir.getAbsolutePath());
                    //Toast.makeText(context, "context.getExternalFilesDir(Environment.DIRECTORY_MUSIC) : " + musicPrivateDir.getAbsolutePath(), Toast.LENGTH_LONG).show();
                    cadena = cadena + "context.getExternalFilesDir(Environment.DIRECTORY_MUSIC) : " + musicPrivateDir.getAbsolutePath() + "\n";

                    File dcimPrivateDir = context.getExternalFilesDir(Environment.DIRECTORY_DCIM);
                    Log.d(LOG_TAG_EXTERNAL_STORAGE, "context.getExternalFilesDir(Environment.DIRECTORY_DCIM) : " + dcimPrivateDir.getAbsolutePath());
                    //Toast.makeText(context, "context.getExternalFilesDir(Environment.DIRECTORY_DCIM) : " + dcimPrivateDir.getAbsolutePath(), Toast.LENGTH_LONG).show();
                    cadena = cadena + "context.getExternalFilesDir(Environment.DIRECTORY_DCIM) : " + dcimPrivateDir.getAbsolutePath() + "\n";

                    File customPrivateDir = context.getExternalFilesDir("Custom");
                    Log.d(LOG_TAG_EXTERNAL_STORAGE, "context.getExternalFilesDir(\"Custom\") : " + customPrivateDir.getAbsolutePath());
                    //Toast.makeText(context, "context.getExternalFilesDir(\"Custom\") : " + customPrivateDir.getAbsolutePath(), Toast.LENGTH_LONG).show();
                    cadena = cadena + "context.getExternalFilesDir(\"Custom\") : " + customPrivateDir.getAbsolutePath() + "\n";

                    File cachedPrivateDir = context.getExternalCacheDir();
                    Log.d(LOG_TAG_EXTERNAL_STORAGE, "context.getExternalCacheDir() : " + cachedPrivateDir.getAbsolutePath());
                    //Toast.makeText(context, "context.getExternalCacheDir() : " + cachedPrivateDir.getAbsolutePath(), Toast.LENGTH_LONG).show();
                    cadena = cadena + "context.getExternalCacheDir() : " + cachedPrivateDir.getAbsolutePath() + "\n";

                    Toast.makeText(context, "Please see the output in android monitor logcat console.", Toast.LENGTH_LONG).show();

                    cadena = cadena + "Please see the output in android monitor logcat console.\n";

                    LayoutInflater inflater = LayoutInflater.from(context);
                    showDialogView = inflater.inflate(R.layout.show_dialog, null);

                    textViewDialog = showDialogView.findViewById(R.id.text_view_dialog);

                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
                    alertDialogBuilder.setView(showDialogView);
                    alertDialogBuilder
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            })
                            .setCancelable(false)
                            .create();
                    textViewDialog.setText(cadena);
                    alertDialogBuilder.show();
                }
            }
        });
    }
}
