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
    }

    public void login(View view) {
        Toast.makeText(getApplicationContext(), "Login", Toast.LENGTH_SHORT).show();
    }

    public void register(View view) {
        Intent registerIntent = new Intent(this, RegisterActivity.class);
        EditText inputUsername = findViewById(R.id.username);
        EditText inputPassword = findViewById(R.id.password);
        String username = inputUsername.getText().toString();
        String password = inputPassword.getText().toString();

        registerIntent.putExtra(getString(R.string.EXTRA_USERNAME), username);
        registerIntent.putExtra(getString(R.string.EXTRA_PASSWORD), password);

        startActivity(registerIntent);
    }

}
