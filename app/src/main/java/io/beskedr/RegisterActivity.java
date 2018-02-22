package io.beskedr;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Intent intent = getIntent();
        String enteredUsername = intent.getStringExtra(getString(R.string.EXTRA_USERNAME));
        String enteredPassword = intent.getStringExtra(getString(R.string.EXTRA_PASSWORD));

        ((EditText) findViewById(R.id.registerUsername)).setText(enteredUsername);
        ((EditText) findViewById(R.id.registerPassword)).setText(enteredPassword);
    }

    public void register(View view) {
        Toast.makeText(getApplicationContext(), R.string.toast_register_success, Toast.LENGTH_LONG).show();

        Intent registeredIntent = new Intent(this, LoginActivity.class);
        String username = ((EditText) findViewById(R.id.registerUsername)).getText().toString();
        String password = ((EditText) findViewById(R.id.registerPassword)).getText().toString();

        registeredIntent.putExtra(getString(R.string.EXTRA_USERNAME), username);
        registeredIntent.putExtra(getString(R.string.EXTRA_PASSWORD), password);

        startActivity(registeredIntent);
    }

}
