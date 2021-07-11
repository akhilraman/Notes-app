package com.example.notesapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.security.spec.ECField;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    static ArrayList<String>notes;
    static SharedPreferences sharedPreferences;
    static ArrayAdapter<String> arr;
    ListView l;
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
         super.onOptionsItemSelected(item);
        switch(item.getItemId()){
            case R.id.note:
                //Toast.makeText(this,"settings", Toast.LENGTH_SHORT).show();
                int i=notes.size();
                openActivity2(" ",i);
                notes.add("new item");
                updateSharedPreferences();
                updatenotes();

            default:
                return false;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setContentView(R.layout.activity_main);

        sharedPreferences=this.getSharedPreferences("com.example.notesapp", Context.MODE_PRIVATE);
        try {
            notes =  (ArrayList<String>) ObjectSerializer.deserialize(sharedPreferences.getString("notes", ObjectSerializer.serialize(new ArrayList<>())));
        }catch(Exception e){
            e.printStackTrace();
        }
        if(notes.isEmpty()) {
            notes.add("Empty notes !");
            try{
                sharedPreferences.edit().putString("notes",ObjectSerializer.serialize(notes)).apply();
                Log.i("friends",ObjectSerializer.serialize(notes));
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        else{
            updateSharedPreferences();
        }
        updatenotes();

    }
    public void updatenotes(){
        l = findViewById(R.id.list);
        arr = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, notes);
        l.setAdapter(arr);
        l.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = (String) parent.getItemAtPosition(position);
                Toast.makeText(MainActivity.this,selectedItem,Toast.LENGTH_SHORT).show();
                openActivity2(selectedItem,position);

            }
        });

        l.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                           int pos, long id) {
                //Toast.makeText(MainActivity.this,"long press",Toast.LENGTH_SHORT).show();
                new AlertDialog.Builder(MainActivity.this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Are you sure you want to delete the note?")
                        .setMessage("Note will not be restored to the device")
                        .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                notes.remove(pos);
                                updatenotes();
                                updateSharedPreferences();
                            }
                        })
                        .setNegativeButton("no",null)
                        .show();
                return true;
            }
        });
    }
    public void openActivity2(String s,int position) {
        Intent intent = new Intent(this, Activity2.class);
        intent.putExtra("key", s);
        intent.putExtra("index",position);
        startActivity(intent);
    }

    public static void updateSharedPreferences(){
        try{
            sharedPreferences.edit().putString("notes",ObjectSerializer.serialize(notes)).apply();
            //Log.i("friends",ObjectSerializer.serialize(notes));
        }catch(Exception e){
            e.printStackTrace();
        }
    }

}