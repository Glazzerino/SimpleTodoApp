package com.example.simpletodoapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String ITEM_TEXT_KEY = "item_text";
    public static final String ITEM_POS_KEY = "item_pos";
    private static final int EDIT_TEXT_CODE = 20;
    List<String> items;
    Button btnAdd;
    EditText et;
    RecyclerView itemList;
    ItemsAdapter itemsAdapter;
    File data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Initialize parent class
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        btnAdd = findViewById(R.id.btnAdd);
        et = findViewById(R.id.etTask);
        itemList = findViewById(R.id.itemList);

        data = getDataFile();

        loadItems();

        ItemsAdapter.OnLongClickListener longClickListener = new ItemsAdapter.OnLongClickListener() {
            @Override
            public void onLongClick(int position) {
                items.remove(position);

                itemsAdapter.notifyItemRemoved(position);
                Toast.makeText(getApplicationContext(), "Task removed", Toast.LENGTH_SHORT).show();

                saveItems();
            }
        };
        ItemsAdapter.OnClickListener clickListener = new ItemsAdapter.OnClickListener() {
            @Override
            public void onClick(int position) {

                // "this" obvsly refers to current context while class refers to the unimplemented
                // view to render
                Intent intent = new Intent(MainActivity.this, EditActivity.class);
                Toast.makeText(getApplicationContext(), "CLICK", Toast.LENGTH_SHORT).show();

                intent.putExtra(ITEM_TEXT_KEY, items.get(position));
                intent.putExtra(ITEM_POS_KEY, position);
                startActivityForResult(intent, EDIT_TEXT_CODE);


            }
        };
        itemsAdapter = new ItemsAdapter(items, longClickListener, clickListener);



        itemList.setAdapter(itemsAdapter);
        itemList.setLayoutManager(new LinearLayoutManager(this));

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Callback to add item
                String todoItem = et.getText().toString();
                items.add(todoItem);
                itemsAdapter.notifyItemInserted(items.size() - 1);
                et.setText("");
                Toast.makeText(getApplicationContext(), "Task added to list", Toast.LENGTH_SHORT).show();
                saveItems();


            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        // super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == EDIT_TEXT_CODE) {

            int position = data.getExtras().getInt(ITEM_POS_KEY);
            String editedItem = data.getExtras().getString(ITEM_TEXT_KEY);

            items.set(position, editedItem);
            itemsAdapter.notifyItemChanged(position);
            saveItems();
            Toast.makeText(getApplicationContext(), "Task updated", Toast.LENGTH_SHORT).show();
        } else {
            Log.w("MainActivity","result and/or request code mismatch");
        }
    }

    private File getDataFile(){
        return new File(getFilesDir(), "todoData.txt");
    }

    private void loadItems() {
        try {
            items = new ArrayList<>(FileUtils.readLines(getDataFile(), Charset.defaultCharset()));
        } catch(IOException e) {
            Log.e("Main activity", "Could not read file");
            items = new ArrayList<>();
        }
    }
    // Load file
    private void saveItems() {
        try {
            FileUtils.writeLines(getDataFile(), items);
        } catch (IOException e) {
            Log.e("Main activity", "Error writing items");
        }
    }
}