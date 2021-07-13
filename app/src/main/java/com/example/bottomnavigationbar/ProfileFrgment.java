package com.example.bottomnavigationbar;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;

public class ProfileFrgment  extends Fragment {
    @Nullable
    static ArrayList<String>favnotes;
    static SharedPreferences sharedPreferencesFav;
    static ArrayAdapter<String> favarr;
    ListView fav_l;

    @Override
    public View onCreateView(@NonNull  LayoutInflater inflater, @Nullable  ViewGroup container, @Nullable  Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile,container,false);

        //return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull  View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sharedPreferencesFav=getContext().getSharedPreferences("com.example.bottomnavigationbar", Context.MODE_PRIVATE);
        try {
            favnotes =  (ArrayList<String>) ObjectSerializer.deserialize(sharedPreferencesFav.getString("favnotes", ObjectSerializer.serialize(new ArrayList<>())));
        }catch(Exception e){
            e.printStackTrace();
        }
        if(favnotes.isEmpty()) {
            favnotes.add("Empty notes !,Create your favourite notes");
            Log.i("this","here");
            try{
                sharedPreferencesFav.edit().putString("favnotes",ObjectSerializer.serialize(favnotes)).apply();
                Log.i("friends",ObjectSerializer.serialize(favnotes));
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        else{

            updateSharedPreferencesFav();
        }

        updatenotesFav();

    }
    public void updatenotesFav(){
        fav_l =getActivity().findViewById(R.id.fav_l);
        ArrayList<String>Titles=new ArrayList<>();
        String[] title_notes;
        for(int i=0;i<favnotes.size();i++){
            String full=favnotes.get(i);
            title_notes=full.split(",");

            Titles.add(title_notes[0]);
        }

        favarr = new ArrayAdapter<String>(getContext(), R.layout.support_simple_spinner_dropdown_item, Titles);
        fav_l.setAdapter(favarr);

        fav_l.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = (String) parent.getItemAtPosition(position);
                Toast.makeText(getContext(),selectedItem,Toast.LENGTH_SHORT).show();
                Fragment fragment= new ExploreFragment();
                Bundle bundle = new Bundle();
                String full=favnotes.get(position);
                String[] title_notes=full.split(",");
                bundle.putString("key",selectedItem+","+title_notes[1]);
                bundle.putInt("index",position);
                bundle.putString("callby","fav");
                fragment.setArguments(bundle);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.Fragment_container,fragment);
                transaction.addToBackStack(null);
                transaction.commit();
                //Fragment fragment= new ExploreFragment();

            }
        });

        fav_l.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
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
                                favnotes.remove(pos);
                                updatenotesFav();
                                updateSharedPreferencesFav();
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

    public static void updateSharedPreferencesFav(){
        try{
            sharedPreferencesFav.edit().putString("favnotes",ObjectSerializer.serialize(favnotes)).apply();
            //Log.i("friends",ObjectSerializer.serialize(notes));
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    }

