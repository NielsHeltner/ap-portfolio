package io.beskedr.gui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import io.beskedr.R;
import io.beskedr.domain.ConversationMessage;
import io.beskedr.domain.User;
import io.beskedr.domain.UserManager;

public class ConversationFragment extends Fragment {

    private static int TEST_FOR_MESSAGES = 0;
    private final User me = new User("Niels", "niels@heltner.net", "Niels Heltner", "123");
    private final User you = new User("Lars", "lars@christensen.net", "Lars Christensen", "123");

    private RecyclerView conversationMessageView;
    private RecyclerView.Adapter conversationAdapter;
    private DatabaseReference usersRef;
    private DatabaseReference otherUserRef;
    private DatabaseReference messagesRef;
    private List<ConversationMessage> conversationMessages;
    private User other;

    public ConversationFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UserManager.getInstance().setCurrentUser(me);
        conversationMessages = new ArrayList<>();
        other = (User) getArguments().getSerializable(getString(R.string.EXTRA_OTHER_USER));

        usersRef = FirebaseDatabase.getInstance().getReference().child("users");
        otherUserRef = usersRef.child(UserManager.getInstance().getCurrentUser().getUsername()).child("contacts").child(other.getUsername());
        messagesRef = FirebaseDatabase.getInstance().getReference().child("messages");

        otherUserRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String convoId = dataSnapshot.getValue().toString();

                messagesRef.child(convoId).addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        final ConversationMessage conversationMessage = dataSnapshot.getValue(ConversationMessage.class);

                        usersRef.child(conversationMessage.getUser()).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                User sender = dataSnapshot.getValue(User.class);
                                conversationMessage.setSender(sender);
                                addMessage(conversationMessage);
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    }

                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onChildRemoved(DataSnapshot dataSnapshot) {

                    }

                    @Override
                    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        activity.getSupportActionBar().setTitle(other.getName());

        View view = inflater.inflate(R.layout.fragment_conversation, container, false);

        conversationMessageView = view.findViewById(R.id.conversationMessageView);
        conversationMessageView.setHasFixedSize(true);
        conversationMessageView.setLayoutManager(new LinearLayoutManager(getContext()));
/*
        conversationMessages.add(new ConversationMessage(me, "Hej!", 1521653517378L));
        conversationMessages.add(new ConversationMessage(you, "hej", 1521653517378L));
        conversationMessages.add(new ConversationMessage(me, "Hvordan går det?", 1521653517378L));
        conversationMessages.add(new ConversationMessage(you, "jamen jeg har det egentligt rigtig godt i dag men så kom jeg til at møde anton og fandt ud hvor flot han egentlig var og så fik jeg bare sådan rigtig mange mangemangemange mange mindreværdskomplekser men så fandt jeg ud af han faktisk var grim og så fik jeg det godt igen", 1521653517378L));
        conversationMessages.add(new ConversationMessage(me, "jamen jeg har det egentligt rigtig godt i dag men så kom jeg til at møde anton og fandt ud hvor flot han egentlig var og så fik jeg bare sådan rigtig mange mangemangemange mange mindreværdskomplekser men så fandt jeg ud af han faktisk var grim og så fik jeg det godt igen", 1521653517378L));
        conversationMessages.add(new ConversationMessage(you, "jamen jeg har det egentligt rigtig godt i dag men så kom jeg til at møde anton og fandt ud hvor flot han egentlig var og så fik jeg bare sådan rigtig mange mangemangemange mange mindreværdskomplekser men så fandt jeg ud af han faktisk var grim og så fik jeg det godt igen", 1521653517378L));
*/
        conversationAdapter = new ConversationAdapter(getContext(), conversationMessages);
        conversationMessageView.setAdapter(conversationAdapter);

        updateRecyclerViewPan();

        view.findViewById(R.id.conversationSendBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMessage();
            }
        });

        return view;
    }

    public void sendMessage() {
        Toast.makeText(getContext(), "send message", Toast.LENGTH_SHORT).show();
        if (TEST_FOR_MESSAGES % 2 == 0) {
            conversationMessages.add(new ConversationMessage(me, "test", 1521653517378L));
        } else {
            conversationMessages.add(new ConversationMessage(you, "test", 1521653517378L));
        }
        TEST_FOR_MESSAGES++;


        updateRecyclerViewPan();
    }

    private void addMessage(ConversationMessage message) {
        conversationMessages.add(message);
        updateRecyclerViewPan();
    }

    private void updateRecyclerViewPan() {
        int lastPos = conversationMessages.size() - 1;
        conversationAdapter.notifyItemInserted(lastPos);
        conversationMessageView.scrollToPosition(lastPos);
    }

}
