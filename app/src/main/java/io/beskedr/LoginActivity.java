package io.beskedr;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Intent intent = getIntent();
        String enteredUsername = intent.getStringExtra(getString(R.string.EXTRA_USERNAME));
        String enteredPassword = intent.getStringExtra(getString(R.string.EXTRA_PASSWORD));

        ((EditText) findViewById(R.id.loginUsername)).setText(enteredUsername);
        ((EditText) findViewById(R.id.loginPassword)).setText(enteredPassword);
    }

    public void login(View view) {
        Toast.makeText(getApplicationContext(), "Login", Toast.LENGTH_SHORT).show();

        Intent loginIntent = new Intent(this, DashboardActivity.class);
        startActivity(loginIntent);
    }

    public void register(View view) {
        Intent registerIntent = new Intent(this, RegisterActivity.class);
        String username = ((EditText) findViewById(R.id.loginUsername)).getText().toString();
        String password = ((EditText) findViewById(R.id.loginPassword)).getText().toString();

        registerIntent.putExtra(getString(R.string.EXTRA_USERNAME), username);
        registerIntent.putExtra(getString(R.string.EXTRA_PASSWORD), password);

        startActivity(registerIntent);
    }

}
