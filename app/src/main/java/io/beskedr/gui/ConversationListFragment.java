package io.beskedr.gui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import io.beskedr.R;

public class ConversationListFragment extends Fragment {

    private RecyclerView conversationView;
    private RecyclerView.Adapter conversationAdapter;
    private int messages = 1;

    public ConversationListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_conversation_list, container, false);

        if (messages > 0) {
            ((TextView) view.findViewById(R.id.noMessages)).setText("");
        }

        conversationView = view.findViewById(R.id.conversationListView);
        conversationView.setHasFixedSize(true);
        conversationView.setLayoutManager(new LinearLayoutManager(getContext()));
        conversationView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));

        List<Conversation> data = new ArrayList<>();
        for (int i = 0; i < 25; i++) {
            data.add(new Conversation(shuffle("Lars Petri Chrisntensen"),
                    shuffle("Jeg synes bare det her er en total mega fed og sindssygt gennemført testbesked"),
                    (int) (Math.random() * 24) + ":" + (int) (Math.random() * 59)));
        }

        conversationAdapter = new ConversationAdapter(getContext(), data);
        conversationView.setAdapter(conversationAdapter);

        return view;
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

}
