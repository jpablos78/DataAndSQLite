package com.example.jpablos.dataandsqlite;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SharedPreferencesActivity extends AppCompatActivity {
    private final String NAME = "NAME";
    private EditText mEditTextName;
    private Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shared_preferences);

        TextView textView = findViewById(R.id.textView);
        btnSave = findViewById(R.id.button);

        SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);

        String name = sharedPreferences.getString(NAME, null);

        if (name == null) {
            textView.setText("Hello");
        } else {
            textView.setText("Welcome Back " + name + "!");
        }

        mEditTextName = findViewById(R.id.editTextName);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveName();
            }
        });
    }

    private void saveName() {
        SharedPreferences.Editor editor = getPreferences(MODE_PRIVATE).edit();

        editor.putString(NAME, mEditTextName.getText().toString());

        editor.commit();
    }
}
