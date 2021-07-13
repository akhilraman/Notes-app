package com.example.bottomnavigationbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    private String key;
    private int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView bottomNav=findViewById(R.id.button_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navlister);
        getSupportFragmentManager().beginTransaction().replace(R.id.Fragment_container,new HomeFragment()).commit();
    }

    public String getMyData() {
        return key;
    }
    public int getIndex(){
        return index;
    }
    private BottomNavigationView.OnNavigationItemSelectedListener navlister=
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull  MenuItem item) {
                    Fragment selectedFragment=null;

                    switch(item.getItemId()){

                        case R.id.home:
                            selectedFragment=new HomeFragment();
                            break;
                        case R.id.search:
                            selectedFragment=new ExploreFragment();
                            int i=HomeFragment.notes.size();
                            //openActivity2(" ",i);
                            /*Intent intent = new Intent(MainActivity.this, ExploreFragment.class);
                            intent.putExtra("key", " ");
                            intent.putExtra("index",i);*/
                            Bundle bundle = new Bundle();
                            bundle.putString("key","NEW NOTE,new note here!");
                            bundle.putInt("index",i);
                            selectedFragment.setArguments(bundle);
                            key=" ";
                            index=i;
                            HomeFragment.notes.add("NEW NOTE,new note here!");
                            HomeFragment.updateSharedPreferences();
                            HomeFragment.arr.notifyDataSetChanged();
                            break;
                        case R.id.profile:
                            selectedFragment=new ProfileFrgment();
                            break;

                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.Fragment_container,selectedFragment).commit();
                    return true;
                }
            };
}