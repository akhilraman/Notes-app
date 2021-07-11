package com.example.notesapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Activity2 extends AppCompatActivity {

EditText mTitleField;
ArrayList<String>notes;
String newnote;
int index;
public void savenote(View view){
    Intent intent = new Intent(this, MainActivity.class);

    startActivity(intent);
}
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);
         /*SharedPreferences sharedPreferences=this.getSharedPreferences("com.example.notesapp", Context.MODE_PRIVATE);
         try {
             notes = (ArrayList<String>) ObjectSerializer.deserialize(sharedPreferences.getString("notes", ObjectSerializer.serialize(new ArrayList<>())));
         }catch(Exception e){
             e.printStackTrace();
         }*/
         Bundle bundle = getIntent().getExtras();
        String value = bundle.getString("key");
        index=bundle.getInt("index");
        Log.i("index",String.valueOf(index));
        mTitleField = (EditText) findViewById(R.id.mTitleField);
        mTitleField.setText(value);
        mTitleField.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence c, int start, int before, int count) {
                 newnote=mTitleField.getText().toString();
                MainActivity.notes.set(index,newnote);
                MainActivity.arr.notifyDataSetChanged();
                MainActivity.updateSharedPreferences();

            }

            public void beforeTextChanged(CharSequence c, int start, int count, int after) {
                // this space intentionally left blank
            }

            public void afterTextChanged(Editable c) {


            }
        });


    }

}