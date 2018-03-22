package io.beskedr.gui;

import android.content.Intent;
import android.os.Bundle;
import io.beskedr.domain.UserManager;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import io.beskedr.R;

public class DashboardActivity extends AppCompatActivity {

    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        fragmentManager = getSupportFragmentManager();
        if (fragmentManager.findFragmentById(R.id.dashboardFragment) == null) {
            fragmentManager.beginTransaction().replace(R.id.dashboardFragment, new ConversationListFragment()).commit();
        }
    }

    public void newContact(View view) {
        Intent newContactIntent = new Intent(this, NewContactActivity.class);
        startActivity(newContactIntent);
    }

    public void logout() {
        Toast.makeText(getApplicationContext(), R.string.toast_logout, Toast.LENGTH_LONG).show();
        Intent logoutIntent = new Intent(this, LoginActivity.class);
        UserManager.getInstance().setCurrentUser(null);
        startActivity(logoutIntent);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_dots, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
            case R.id.btnLogout:
                logout();
                break;
            default:
                return super.onOptionsItemSelected(menuItem);
        }
        return true;
    }
}
