package com.example.bottomnavigationbar;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

import static android.content.Context.INPUT_METHOD_SERVICE;

public class ExploreFragment extends Fragment {
    @Nullable
    EditText mTitleField;
    EditText titlebox;
    ArrayList<String> notes;
    String newnote=" ";
    int index;
    String value;
    String callby;
    String[] title_note;
    String new_title;

    public boolean onKey(View v, int keyCode, KeyEvent event) {

        if(keyCode==KeyEvent.KEYCODE_ENTER && event.getAction()==KeyEvent.ACTION_DOWN){

        }
        return false;
    }
    @Override
    public View onCreateView(@NonNull  LayoutInflater inflater, @Nullable  ViewGroup container, @Nullable  Bundle savedInstanceState) {
        MainActivity activity = (MainActivity) getActivity();
        Bundle bundle = this.getArguments();
        value = bundle.getString("key", "ok");
        index=bundle.getInt("index", 0);
        callby=bundle.getString("callby","Home");

        title_note=value.split(",");

        return inflater.inflate(R.layout.fragment_explore,container,false);

    }

    @Override
    public void onViewCreated(@NonNull  View view, @Nullable  Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button fav=view.findViewById(R.id.fav);

        fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //newnote=value;
                Toast.makeText(getContext(), newnote, Toast.LENGTH_SHORT).show();
                int size=ProfileFrgment.favnotes.size()-1;
               ProfileFrgment.favnotes.add(newnote);
               ProfileFrgment.favarr.notifyDataSetChanged();
                ProfileFrgment.updateSharedPreferencesFav();
            }
        });

        /*Button button= view.findViewById(R.id.mTitleField);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager inputMethodManager=(InputMethodManager)getActivity().getSystemService(INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(),0);
            }
        });*/
        titlebox=(EditText)view.findViewById(R.id.titlebox);
        titlebox.setText(title_note[0]);
        mTitleField = (EditText) view.findViewById(R.id.mTitleField);
        mTitleField.setText(title_note[1]);
        new_title=titlebox.getText().toString();
        titlebox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                new_title=titlebox.getText().toString();
                String change_value=new_title+","+title_note[1];
                if(callby.matches("Home")){

                    HomeFragment.notes.set(index,change_value);
                    HomeFragment.arr.notifyDataSetChanged();
                    HomeFragment.updateSharedPreferences();
                }
                else{
                    int size=ProfileFrgment.favnotes.size()-1;
                    ProfileFrgment.favnotes.set(size,change_value);
                    ProfileFrgment.favarr.notifyDataSetChanged();
                    ProfileFrgment.updateSharedPreferencesFav();

                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mTitleField.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence c, int start, int before, int count) {
                newnote=mTitleField.getText().toString();
                newnote=new_title+","+newnote;
                if(callby.matches("Home")){

                    HomeFragment.notes.set(index,newnote);
                    HomeFragment.arr.notifyDataSetChanged();
                    HomeFragment.updateSharedPreferences();
                }
                else{
                    int size=ProfileFrgment.favnotes.size()-1;
                    ProfileFrgment.favnotes.set(size,newnote);
                ProfileFrgment.favarr.notifyDataSetChanged();
                ProfileFrgment.updateSharedPreferencesFav();

                }

            }
            public void beforeTextChanged(CharSequence c, int start, int count, int after) {
                // this space intentionally left blank
            }

            public void afterTextChanged(Editable c) {


            }
        });


    }
}
