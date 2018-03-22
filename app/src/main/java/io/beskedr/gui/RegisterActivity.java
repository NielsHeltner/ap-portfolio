package io.beskedr.gui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.beskedr.R;
import io.beskedr.domain.User;

public class RegisterActivity extends AppCompatActivity {

    @BindView(R.id.registerUsername) EditText usernameField;
    @BindView(R.id.textInputLayoutRegisterUsername) TextInputLayout usernameLayout;
    @BindView(R.id.registerEmail) EditText emailField;
    @BindView(R.id.textInputLayoutRegisterEmail) TextInputLayout emailLayout;
    @BindView(R.id.registerName) EditText nameField;
    @BindView(R.id.textInputLayoutRegisterName) TextInputLayout nameLayout;
    @BindView(R.id.registerPassword) EditText passwordField;
    @BindView(R.id.textInputLayoutRegisterPassword) TextInputLayout passwordLayout;
    private DatabaseReference database;
    private ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ButterKnife.bind(this);

        Intent intent = getIntent();
        String enteredUsername = intent.getStringExtra(getString(R.string.EXTRA_USERNAME));
        String enteredPassword = intent.getStringExtra(getString(R.string.EXTRA_PASSWORD));

        usernameField.setText(enteredUsername);
        passwordField.setText(enteredPassword);

        database = FirebaseDatabase.getInstance().getReference("users");
        progress = new ProgressDialog(this);
        progress.setMessage(getString(R.string.register_wait));
        progress.setCancelable(false);
    }

    public void register(View view) {
        clearErrorMessages();
        progress.show();

        final Intent registeredIntent = new Intent(this, LoginActivity.class);
        final String username = usernameField.getText().toString().trim();
        final String email = emailField.getText().toString().trim();
        final String name = nameField.getText().toString().trim();
        final String password = passwordField.getText().toString().trim();

        database.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChild(username)) {
                    showErrorMessage(usernameLayout, getString(R.string.error_registration_username_taken));
                }
                else if(username.length() < 1) {
                    showErrorMessage(usernameLayout, getString(R.string.error_registration_username_too_short));
                }
                if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    showErrorMessage(emailLayout, getString(R.string.error_registration_email_invalid));
                }
                if(name.length() < 2) {
                    showErrorMessage(nameLayout, getString(R.string.error_registration_name_too_short));
                }
                if(password.length() < 3) {
                    showErrorMessage(passwordLayout, getString(R.string.error_registration_password_too_short));
                }
                else {
                    User newUser = new User(username, email, name, password);
                    createNewUser(newUser, registeredIntent);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void createNewUser(final User newUser, final Intent intent) {
        database.child(newUser.getUsername()).setValue(newUser).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                startLoginActivity(newUser, intent);
            }
        });
        Toast.makeText(getApplicationContext(), R.string.toast_register_success, Toast.LENGTH_SHORT).show();
    }

    private void startLoginActivity(User newUser, Intent intent) {
        intent.putExtra(getString(R.string.EXTRA_USERNAME), newUser.getUsername());
        intent.putExtra(getString(R.string.EXTRA_PASSWORD), newUser.getPassword());
        startActivity(intent);
    }

    private void showErrorMessage(TextInputLayout view, String errorMessage) {
        progress.dismiss();
        view.setError(errorMessage);
    }

    private void clearErrorMessages() {
        clearErrorMessage(usernameLayout);
        clearErrorMessage(emailLayout);
        clearErrorMessage(nameLayout);
        clearErrorMessage(passwordLayout);
    }

    private void clearErrorMessage(TextInputLayout view) {
        showErrorMessage(view, null);
    }

}
