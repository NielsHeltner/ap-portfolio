package io.beskedr;

import android.content.Intent;
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

public class DashboardActivity extends AppCompatActivity {

    private int messages = 1;

    private RecyclerView conversationView;
    private RecyclerView.Adapter conversationAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        if (messages > 0) {
            ((TextView) findViewById(R.id.noMessages)).setText("");
        }

        conversationView = findViewById(R.id.conversationView);
        conversationView.setHasFixedSize(true);
        conversationView.setLayoutManager(new LinearLayoutManager(this));
        conversationView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        List<Conversation> data = new ArrayList<>();
        for (int i = 0; i < 25; i++) {

            data.add(new Conversation(shuffle("Lars Petri Chrisntensen"),
                    shuffle("Jeg synes bare det her er en total mega fed og sindssygt gennemført testbesked"),
                    (int) (Math.random() * 24) + ":" + (int) (Math.random() * 59)));
        }

        conversationAdapter = new ConversationAdapter(getApplicationContext(), data);
        conversationView.setAdapter(conversationAdapter);
    }

    private String shuffle(String string) {
        List<String> letters = Arrays.asList(string.split(""));
        Collections.shuffle(letters);
        String shuffled = "";
        for (String letter : letters) {
            shuffled += letter;
        }
        return shuffled.trim();
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
