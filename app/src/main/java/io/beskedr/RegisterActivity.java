package io.beskedr;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    private DatabaseReference database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Intent intent = getIntent();
        String enteredUsername = intent.getStringExtra(getString(R.string.EXTRA_USERNAME));
        String enteredPassword = intent.getStringExtra(getString(R.string.EXTRA_PASSWORD));

        ((EditText) findViewById(R.id.registerUsername)).setText(enteredUsername);
        ((EditText) findViewById(R.id.registerPassword)).setText(enteredPassword);

        database = FirebaseDatabase.getInstance().getReference("users");
    }

    public void register(View view) {
        Map<String, String> userEntry = new HashMap<>();
        Toast.makeText(getApplicationContext(), R.string.toast_register_success, Toast.LENGTH_LONG).show();

        Intent registeredIntent = new Intent(this, LoginActivity.class);
        String username = ((EditText) findViewById(R.id.registerUsername)).getText().toString();
        String email = ((EditText) findViewById(R.id.registerEmail)).getText().toString();
        String name = ((EditText) findViewById(R.id.registerName)).getText().toString();
        String password = ((EditText) findViewById(R.id.registerPassword)).getText().toString();

        userEntry.put("username", username);
        userEntry.put("email", email);
        userEntry.put("name", name);
        userEntry.put("password", password);

        database.push().setValue(userEntry);

        database.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("Database", dataSnapshot.getChildrenCount() + "");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        registeredIntent.putExtra(getString(R.string.EXTRA_USERNAME), username);
        registeredIntent.putExtra(getString(R.string.EXTRA_PASSWORD), password);

        startActivity(registeredIntent);
    }

}
