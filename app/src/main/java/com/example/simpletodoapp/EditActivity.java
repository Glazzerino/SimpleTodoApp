package com.example.simpletodoapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class EditActivity extends AppCompatActivity {

    EditText editItem;
    Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        editItem = findViewById(R.id.editText);
        saveButton = findViewById(R.id.btnSave);
        getSupportActionBar().setTitle("Edit Item");
        editItem.setText(getIntent().getStringExtra(MainActivity.ITEM_TEXT_KEY));

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Intent intent = new Intent();
            intent.putExtra(MainActivity.ITEM_TEXT_KEY, editItem.getText().toString());
            intent.putExtra(MainActivity.ITEM_POS_KEY, getIntent().getExtras().getInt(MainActivity.ITEM_POS_KEY));

            setResult(RESULT_OK, intent);

            finish();
            }
        });

    }
}