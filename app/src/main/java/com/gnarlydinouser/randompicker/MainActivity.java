package com.gnarlydinouser.randompicker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private Spinner spinnerPickCount;
    private Button buttonPickRandom;
    private TextView textViewRandomPersons;

    private PersonDbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        else{
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        dbHelper = new PersonDbHelper(this);

        spinnerPickCount = findViewById(R.id.spinnerPickCount);
        buttonPickRandom = findViewById(R.id.buttonPickRandom);
        textViewRandomPersons = findViewById(R.id.textViewRandomPersons);

        buttonPickRandom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickRandomPersons();
            }
        });
    }
    public void MemberSettings(View v)
    {
        Intent i = new Intent(MainActivity.this,Settings.class);
        startActivity(i);
    }
    private void pickRandomPersons() {
        int count = Integer.parseInt(spinnerPickCount.getSelectedItem().toString());
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + PersonContract.PersonEntry.TABLE_NAME + " ORDER BY RANDOM() LIMIT " + count, null);
        StringBuilder result = new StringBuilder();
        while (cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndexOrThrow(PersonContract.PersonEntry.COLUMN_NAME));
            result.append(name).append("\n");
        }
        cursor.close();

        textViewRandomPersons.setText(result.toString()+"\n");
    }

}