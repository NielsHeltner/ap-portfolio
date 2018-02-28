package io.beskedr;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;

import java.util.ArrayList;

public class NewContactActivity extends AppCompatActivity  {

    // Declare Variables
    ListView list;
    ListViewAdapter adapter;
    SearchView searchView;
    ArrayList<Username> usernameArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_contact);



        list = (ListView) findViewById(R.id.listview);
        addMockUsernamesToArrayList();

        adapter = new ListViewAdapter(this, usernameArrayList);

        list.setAdapter(adapter);

        searchView = (SearchView) findViewById(R.id.searchView1);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if (searchView.getQuery().length() == 0) {
                    list.setVisibility(View.INVISIBLE);
                } else{
                    adapter.filter(s);
                    list.setVisibility(View.VISIBLE);
                }
                return false;
            }
        });
        list.setVisibility(View.INVISIBLE);


    }

    private void addMockUsernamesToArrayList() {
        usernameArrayList.add(new Username("Lars"));
        usernameArrayList.add(new Username("Niels"));
        usernameArrayList.add(new Username("Niclas"));
        usernameArrayList.add(new Username("Anton"));
        usernameArrayList.add(new Username("Danny"));
        usernameArrayList.add(new Username("Johnny Depp"));
        usernameArrayList.add(new Username("FusRoDah"));
        usernameArrayList.add(new Username("Xavier"));
        usernameArrayList.add(new Username("BlackWidow"));
        usernameArrayList.add(new Username("T'Challa"));
        usernameArrayList.add(new Username("Yeah"));
    }
}
