package io.beskedr.gui;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TextInputLayout;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.beskedr.R;
import io.beskedr.domain.User;
import io.beskedr.domain.UserManager;

public class LoginActivity extends AppCompatActivity {

    public static boolean SHOULD_SHOW_WELCOME_ANIM = false;

    @BindView(R.id.loginUsername)
    EditText usernameField;
    @BindView(R.id.loginPassword)
    EditText passwordField;
    @BindView(R.id.textInputLayoutLoginUsername)
    TextInputLayout usernameLayout;
    @BindView(R.id.textInputLayoutLoginPassword)
    TextInputLayout passwordLayout;
    @BindView(R.id.loginHeader)
    TextView loginHeader;
    @BindView(R.id.loginBtn)
    Button loginBtn;
    @BindView(R.id.noAccount)
    TextView noAccount;

    private Handler handler = new Handler();
    private DatabaseReference database;
    private ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);

        initFieldsFromIntent();

        if (SHOULD_SHOW_WELCOME_ANIM) {
            prepareSceneForWelcomeAnim();
            handler.post(new Runnable() {
                @Override
                public void run() {
                    animateHeader();
                }
            });
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    animate();
                }
            }, 2000);
            SHOULD_SHOW_WELCOME_ANIM = false;
        }
        database = FirebaseDatabase.getInstance().getReference("users");
        progress = new ProgressDialog(this);
        progress.setMessage(getString(R.string.login_wait));
        progress.setCancelable(false);
    }

    public void login(View view) {
        clearErrorMessages();
        progress.show();

        final Intent loginIntent = new Intent(this, DashboardActivity.class);
        final String username = usernameField.getText().toString();
        final String password = passwordField.getText().toString();

        if (username.length() < 1) {
            showErrorMessage(usernameLayout, getString(R.string.error_registration_username_too_short));
        } else {
            database.child(username).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.getValue() != null) {
                        if (dataSnapshot.child("password").getValue(String.class).equals(password)) {
                            progress.dismiss();
                            User user = dataSnapshot.getValue(User.class);
                            UserManager.getInstance().setCurrentUser(user);
                            Toast.makeText(getApplicationContext(), R.string.toast_login, Toast.LENGTH_SHORT).show();
                            startActivity(loginIntent);
                            finish();
                        } else {
                            showErrorMessage(passwordLayout, getString(R.string.error_login_password_wrong));
                        }
                    } else {
                        showErrorMessage(usernameLayout, getString(R.string.error_login_username_does_not_exist));
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    }

    public void register(View view) {
        Intent registerIntent = new Intent(this, RegisterActivity.class);
        String username = usernameField.getText().toString();
        String password = passwordField.getText().toString();

        registerIntent.putExtra(getString(R.string.EXTRA_USERNAME), username);
        registerIntent.putExtra(getString(R.string.EXTRA_PASSWORD), password);

        startActivity(registerIntent);
    }

    private void showErrorMessage(TextInputLayout view, String errorMessage) {
        progress.dismiss();
        view.setError(errorMessage);
    }

    private void clearErrorMessages() {
        clearErrorMessage(usernameLayout);
        clearErrorMessage(passwordLayout);
    }

    private void clearErrorMessage(TextInputLayout view) {
        showErrorMessage(view, null);
    }

    private void initFieldsFromIntent() {
        Intent intent = getIntent();
        String enteredUsername = intent.getStringExtra(getString(R.string.EXTRA_USERNAME));
        String enteredPassword = intent.getStringExtra(getString(R.string.EXTRA_PASSWORD));

        usernameField.setText(enteredUsername);
        passwordField.setText(enteredPassword);
    }

    private void prepareSceneForWelcomeAnim() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        loginHeader.setY(displayMetrics.heightPixels / 4);
        loginHeader.setText(R.string.header_welcome);
        loginHeader.setAlpha(0f);
        usernameLayout.setAlpha(0f);
        passwordLayout.setAlpha(0f);
        loginBtn.setAlpha(0f);
        noAccount.setAlpha(0f);
    }

    private void animateHeader() {
        ObjectAnimator headerFadeInAnim = ObjectAnimator.ofFloat(loginHeader, "alpha", 1f);
        headerFadeInAnim.setDuration(1000);
        headerFadeInAnim.setStartDelay(400);
        headerFadeInAnim.start();
    }

    private void animate() {
        ObjectAnimator headerMoveAnim = ObjectAnimator.ofFloat(loginHeader, "translationY", 0f);
        headerMoveAnim.setDuration(1500);
        headerMoveAnim.setInterpolator(new TimeInterpolator() {
            FastOutSlowInInterpolator interpolator = new FastOutSlowInInterpolator();

            @Override
            public float getInterpolation(float input) {
                return interpolator.getInterpolation(input);
            }
        });

        ObjectAnimator usernameFadeAnim = ObjectAnimator.ofFloat(usernameLayout, "alpha", 1f);
        usernameFadeAnim.setDuration(1000);
        usernameFadeAnim.setStartDelay(600);

        ObjectAnimator passwordFadeAnim = ObjectAnimator.ofFloat(passwordLayout, "alpha", 1f);
        passwordFadeAnim.setDuration(1000);
        passwordFadeAnim.setStartDelay(900);

        ObjectAnimator btnFadeAnim = ObjectAnimator.ofFloat(loginBtn, "alpha", 1f);
        btnFadeAnim.setDuration(1000);
        btnFadeAnim.setStartDelay(1800);

        ObjectAnimator noAccountFadeAnim = ObjectAnimator.ofFloat(noAccount, "alpha", 1f);
        noAccountFadeAnim.setDuration(1000);
        noAccountFadeAnim.setStartDelay(1800);

        Animation headerTextAnim = new AlphaAnimation(1.0f, 0.1f);
        headerTextAnim.setDuration(200);
        headerTextAnim.setStartOffset(200);
        headerTextAnim.setRepeatCount(1);
        headerTextAnim.setRepeatMode(Animation.REVERSE);
        headerTextAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                loginHeader.setText(R.string.header_login);
            }
        });

        AnimatorSet animSet = new AnimatorSet();
        animSet.play(headerMoveAnim).with(usernameFadeAnim).with(passwordFadeAnim).with(btnFadeAnim).with(noAccountFadeAnim);

        animSet.start();
        loginHeader.startAnimation(headerTextAnim);
    }

}
