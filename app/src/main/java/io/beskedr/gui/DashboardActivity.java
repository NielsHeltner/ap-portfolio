package io.beskedr.gui;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import io.beskedr.R;

public class DashboardActivity extends AppCompatActivity {

    private int messages = 1;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        getSupportActionBar().setTitle("anton er flot");

        if (messages > 0) {
            ((TextView) findViewById(R.id.noMessages)).setText("");
        }

        fragmentManager = getSupportFragmentManager();
    }

    public void newContact(View view) {
        Intent newContactIntent = new Intent(this, NewContactActivity.class);
        startActivity(newContactIntent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_dots, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.btnLogout:
                Toast.makeText(getApplicationContext(), R.string.toast_logout, Toast.LENGTH_LONG).show();
                Intent logoutIntent = new Intent(this, LoginActivity.class);
                startActivity(logoutIntent);
                break;
        }
        return true;
    }
}
