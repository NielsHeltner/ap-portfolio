package io.beskedr.gui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.SearchView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.beskedr.R;
import io.beskedr.domain.User;
import io.beskedr.domain.UserManager;

public class NewContactActivity extends AppCompatActivity {

    @BindView(R.id.searchResults)
    RecyclerView contactView;
    private NewContactAdapter newContactAdapter;
    @BindView(R.id.searchView)
    SearchView searchView;
    private List<User> users = new ArrayList<>();
    private List<String> contactNames = new ArrayList<>();
    private DatabaseReference usersRef;
    private DatabaseReference userContactsRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_contact);

        ButterKnife.bind(this);

        contactView.setHasFixedSize(true);
        contactView.setLayoutManager(new LinearLayoutManager(this));

        usersRef = FirebaseDatabase.getInstance().getReference().child("users");
        userContactsRef = usersRef.child(UserManager.getInstance().getCurrentUser().getUsername()).child("contacts");

        usersRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                users.add(dataSnapshot.getValue(User.class));
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        userContactsRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                contactNames.add(dataSnapshot.getKey());
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        newContactAdapter = new NewContactAdapter(this, users, contactNames);

        contactView.setAdapter(newContactAdapter);

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
                    newContactAdapter.filter(query);
                    contactView.setVisibility(View.VISIBLE);
                }
                return false;
            }
        });
        contactView.setVisibility(View.INVISIBLE);
    }

    private void updateRecyclerView() {
        int lastPos = users.size() - 1;
        newContactAdapter.notifyItemInserted(lastPos);
    }
}
