package com.example.bottomnavigationbar;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;

import static android.content.Context.INPUT_METHOD_SERVICE;

public class HomeFragment  extends Fragment {
    @Nullable
    static ArrayList<String>notes;
    static SharedPreferences sharedPreferences;
    static ArrayAdapter<String> arr;
    ListView l;
    private String key;
    private int index;

    public String getMyData() {
        return key;
    }
    public int getIndex(){
        return index;
    }


    @Override
    public View onCreateView(@NonNull  LayoutInflater inflater, @Nullable  ViewGroup container, @Nullable  Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_home,container,false);


        //return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        sharedPreferences=getContext().getSharedPreferences("com.example.bottomnavigationbar", Context.MODE_PRIVATE);
        try {
            notes =  (ArrayList<String>) ObjectSerializer.deserialize(sharedPreferences.getString("notes", ObjectSerializer.serialize(new ArrayList<>())));
        }catch(Exception e){
            e.printStackTrace();
        }
        if(notes.isEmpty()) {
            notes.add("Empty notes !,Create your first note");
            Log.i("this","here");
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
        l =getActivity().findViewById(R.id.list);
        ArrayList<String>Titles=new ArrayList<>();
        String[] title_notes;
        for(int i=0;i<notes.size();i++){
            String full=notes.get(i);
            title_notes=full.split(",");
            Titles.add(title_notes[0]);
        }
        arr = new ArrayAdapter<String>(getContext(), R.layout.support_simple_spinner_dropdown_item, Titles);
        l.setAdapter(arr);

        l.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = (String) parent.getItemAtPosition(position);
                Toast.makeText(getContext(),selectedItem,Toast.LENGTH_SHORT).show();
                Fragment fragment= new ExploreFragment();
                Bundle bundle = new Bundle();
                String full=notes.get(position);
                String[] title_notes=full.split(",");
                bundle.putString("key",selectedItem+","+title_notes[1]);
                bundle.putInt("index",position);
                fragment.setArguments(bundle);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.Fragment_container,fragment);
                transaction.addToBackStack(null);
                transaction.commit();
                //Fragment fragment= new ExploreFragment();

            }
        });

        l.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                           int pos, long id) {
                //Toast.makeText(MainActivity.this,"long press",Toast.LENGTH_SHORT).show();
                new AlertDialog.Builder(getContext())
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
    /*public void openActivity2(String s,int position) {
        Intent intent = new Intent(getContext(), ExploreFragment.class);
        intent.putExtra("key", s);
        intent.putExtra("index",position);
        startActivity(intent);
    }*/

    public static void updateSharedPreferences(){
        try{
            sharedPreferences.edit().putString("notes",ObjectSerializer.serialize(notes)).apply();
            //Log.i("friends",ObjectSerializer.serialize(notes));
        }catch(Exception e){
            e.printStackTrace();
        }
    }

}


