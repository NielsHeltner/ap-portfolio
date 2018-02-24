package io.beskedr;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    public static boolean SHOULD_SHOW_WELCOME_ANIM = true;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initFieldsFromIntent();

        if(SHOULD_SHOW_WELCOME_ANIM) {
            prepareSceneForWelcomeAnim();
            handler.post(new Runnable() {
                @Override
                public void run() {
                    ObjectAnimator headerFadeInAnim = ObjectAnimator.ofFloat(findViewById(R.id.loginHeader), "alpha", 1f);
                    headerFadeInAnim.setDuration(1000);
                    headerFadeInAnim.setStartDelay(400);
                    headerFadeInAnim.start();
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
    }

    public void login(View view) {
        Toast.makeText(getApplicationContext(), R.string.toast_login, Toast.LENGTH_SHORT).show();
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

    private void initFieldsFromIntent() {
        Intent intent = getIntent();
        String enteredUsername = intent.getStringExtra(getString(R.string.EXTRA_USERNAME));
        String enteredPassword = intent.getStringExtra(getString(R.string.EXTRA_PASSWORD));

        ((EditText) findViewById(R.id.loginUsername)).setText(enteredUsername);
        ((EditText) findViewById(R.id.loginPassword)).setText(enteredPassword);
    }

    private void prepareSceneForWelcomeAnim() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        findViewById(R.id.loginHeader).setY(displayMetrics.heightPixels / 4);
        ((TextView) findViewById(R.id.loginHeader)).setText(R.string.header_welcome);
        findViewById(R.id.loginHeader).setAlpha(0f);
        findViewById(R.id.textInputLayoutLoginUsername).setAlpha(0f);
        findViewById(R.id.textInputLayoutLoginPassword).setAlpha(0f);
        findViewById(R.id.loginBtn).setAlpha(0f);
        findViewById(R.id.noAccount).setAlpha(0f);
    }

    private void animate() {
        ObjectAnimator headerMoveAnim = ObjectAnimator.ofFloat(findViewById(R.id.loginHeader), "translationY", 0f);
        headerMoveAnim.setDuration(1500);
        headerMoveAnim.setInterpolator(new TimeInterpolator() {
            FastOutSlowInInterpolator interpolator = new FastOutSlowInInterpolator();
            @Override
            public float getInterpolation(float input) {
                return interpolator.getInterpolation(input);
            }
        });

        ObjectAnimator usernameFadeAnim = ObjectAnimator.ofFloat(findViewById(R.id.textInputLayoutLoginUsername), "alpha", 1f);
        usernameFadeAnim.setDuration(1000);
        usernameFadeAnim.setStartDelay(600);

        ObjectAnimator passwordFadeAnim = ObjectAnimator.ofFloat(findViewById(R.id.textInputLayoutLoginPassword), "alpha", 1f);
        passwordFadeAnim.setDuration(1000);
        passwordFadeAnim.setStartDelay(900);

        ObjectAnimator btnFadeAnim = ObjectAnimator.ofFloat(findViewById(R.id.loginBtn), "alpha", 1f);
        btnFadeAnim.setDuration(1000);
        btnFadeAnim.setStartDelay(1800);

        ObjectAnimator noAccountFadeAnim = ObjectAnimator.ofFloat(findViewById(R.id.noAccount), "alpha", 1f);
        noAccountFadeAnim.setDuration(1000);
        noAccountFadeAnim.setStartDelay(1800);

        Animation headerTextAnim = new AlphaAnimation(1.0f, 0.1f);
        headerTextAnim.setDuration(200);
        headerTextAnim.setStartOffset(200);
        headerTextAnim.setRepeatCount(1);
        headerTextAnim.setRepeatMode(Animation.REVERSE);
        headerTextAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override public void onAnimationStart(Animation animation) {}
            @Override public void onAnimationEnd(Animation animation) {}
            @Override public void onAnimationRepeat(Animation animation) {
                ((TextView) findViewById(R.id.loginHeader)).setText(R.string.header_login);
            }
        });

        AnimatorSet animSet = new AnimatorSet();
        animSet.play(headerMoveAnim).with(usernameFadeAnim).with(passwordFadeAnim).with(btnFadeAnim).with(noAccountFadeAnim);

        animSet.start();
        findViewById(R.id.loginHeader).startAnimation(headerTextAnim);
    }

}
