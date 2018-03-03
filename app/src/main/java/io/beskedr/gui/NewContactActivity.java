package io.beskedr.gui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.SearchView;

import java.util.ArrayList;
import java.util.List;

import io.beskedr.R;

public class NewContactActivity extends AppCompatActivity {

    private RecyclerView contactView;
    private ContactAdapter contactAdapter;
    private SearchView searchView;
    private List<String> usernames = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_contact);

        contactView = findViewById(R.id.searchResults);
        contactView.setHasFixedSize(true);
        addMockUsernamesToArrayList();
        contactView.setLayoutManager(new LinearLayoutManager(this));

        contactAdapter = new ContactAdapter(getApplicationContext(), usernames);

        contactView.setAdapter(contactAdapter);

        searchView = findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                if (searchView.getQuery().length() == 0) {
                    contactView.setVisibility(View.INVISIBLE);
                } else {
                    contactAdapter.filter(query);
                    contactView.setVisibility(View.VISIBLE);
                }
                return false;
            }
        });
        contactView.setVisibility(View.INVISIBLE);
    }

    private void addMockUsernamesToArrayList() {
        usernames.add("Lars");
        usernames.add("Niels");
        usernames.add("Niclas");
        usernames.add("Anton");
        usernames.add("Danny");
        usernames.add("Johnny Depp");
        usernames.add("FusRoDah");
        usernames.add("Xavier");
        usernames.add("BlackWidow");
        usernames.add("T'Challa");
        usernames.add("Yeah");
    }
}
