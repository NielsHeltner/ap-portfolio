package io.beskedr;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;

import java.util.ArrayList;
import java.util.List;

public class NewContactActivity extends AppCompatActivity  {

    // Declare Variables
    RecyclerView list;
    ContactAdapter adapter;
    SearchView searchView;
    List<String> usernameArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_contact);



        list = findViewById(R.id.searchResults);
        list.setHasFixedSize(true);
        addMockUsernamesToArrayList();

        RecyclerView.LayoutManager m = new LinearLayoutManager(this);
        list.setLayoutManager(m);

        adapter = new ContactAdapter(usernameArrayList);

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
        usernameArrayList.add(("Lars"));
        usernameArrayList.add(("Niels"));
        usernameArrayList.add(("Niclas"));
        usernameArrayList.add(("Anton"));
        usernameArrayList.add(("Danny"));
        usernameArrayList.add(("Johnny Depp"));
        usernameArrayList.add(("FusRoDah"));
        usernameArrayList.add(("Xavier"));
        usernameArrayList.add(("BlackWidow"));
        usernameArrayList.add(("T'Challa"));
        usernameArrayList.add(("Yeah"));
    }
}
