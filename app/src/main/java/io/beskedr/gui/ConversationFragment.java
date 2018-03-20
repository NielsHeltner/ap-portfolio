package io.beskedr.gui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import io.beskedr.R;
import io.beskedr.domain.ConversationMessage;
import io.beskedr.domain.User;
import io.beskedr.domain.UserManager;

public class ConversationFragment extends Fragment {

    private RecyclerView conversationMessageView;
    private RecyclerView.Adapter conversationAdapter;
    private User other;

    public ConversationFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        other = (User) getArguments().getSerializable(getString(R.string.EXTRA_OTHER_USER));
        activity.getSupportActionBar().setTitle(other.getName());

        View view = inflater.inflate(R.layout.fragment_conversation, container, false);

        conversationMessageView = view.findViewById(R.id.conversationMessageView);
        conversationMessageView.setHasFixedSize(true);
        conversationMessageView.setLayoutManager(new LinearLayoutManager(getContext()));

        User me = new User("Niels", "niels@heltner.net", "Niels Heltner", "123");
        UserManager.getInstance().setCurrentUser(me);
        User you = new User("Lars", "lars@christensen.net", "Lars Christensen", "123");

        List<ConversationMessage> data = new ArrayList<>();

        data.add(new ConversationMessage(me, "Hej!", "12:00"));
        data.add(new ConversationMessage(you, "hej", "12:01"));
        data.add(new ConversationMessage(me, "Hvordan går det?", "12:03"));
        data.add(new ConversationMessage(you, "jamen jeg har det egentligt rigtig godt i dag men så kom jeg til at møde anton og fandt ud hvor flot han egentlig var og så fik jeg bare sådan rigtig mange mangemangemange mange mindreværdskomplekser men så fandt jeg ud af han faktisk var grim og så fik jeg det godt igen", "12:28"));
        data.add(new ConversationMessage(me, "jamen jeg har det egentligt rigtig godt i dag men så kom jeg til at møde anton og fandt ud hvor flot han egentlig var og så fik jeg bare sådan rigtig mange mangemangemange mange mindreværdskomplekser men så fandt jeg ud af han faktisk var grim og så fik jeg det godt igen", "12:28"));
        data.add(new ConversationMessage(you, "jamen jeg har det egentligt rigtig godt i dag men så kom jeg til at møde anton og fandt ud hvor flot han egentlig var og så fik jeg bare sådan rigtig mange mangemangemange mange mindreværdskomplekser men så fandt jeg ud af han faktisk var grim og så fik jeg det godt igen", "12:28"));

        conversationAdapter = new ConversationAdapter(getContext(), data);
        conversationMessageView.setAdapter(conversationAdapter);

        conversationMessageView.scrollToPosition(data.size() - 1);

        return view;
    }

}
