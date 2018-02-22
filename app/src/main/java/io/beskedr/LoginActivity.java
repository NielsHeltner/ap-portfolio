package io.beskedr;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    public static final String USERNAME = "io.beskedr.USERNAME";
    public static final String PASSWORD = "io.beskedr.PASSWORD";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void login(View view) {
        Toast.makeText(getApplicationContext(), "Login", Toast.LENGTH_SHORT).show();
    }

    public void register(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);
        EditText inputUsername = findViewById(R.id.username);
        EditText inputPassword = findViewById(R.id.password);
        String username = inputUsername.getText().toString();
        String password = inputPassword.getText().toString();

        intent.putExtra(USERNAME, username);
        intent.putExtra(PASSWORD, password);
        startActivity(intent);
    }

}
