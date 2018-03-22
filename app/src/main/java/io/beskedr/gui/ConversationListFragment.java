package io.beskedr.gui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import io.beskedr.R;
import io.beskedr.domain.Conversation;
import io.beskedr.domain.ConversationMessage;
import io.beskedr.domain.User;
import io.beskedr.domain.UserManager;

public class ConversationListFragment extends Fragment {

    private RecyclerView conversationListView;
    private RecyclerView.Adapter conversationListAdapter;
    private DatabaseReference usersRef;
    private DatabaseReference userContactsRef;
    private DatabaseReference messagesRef;
    private List<Conversation> conversations;

    public ConversationListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        conversations = new ArrayList<>();
        usersRef = FirebaseDatabase.getInstance().getReference().child("users");
        userContactsRef = usersRef.child(UserManager.getInstance().getCurrentUser().getUsername()).child("contacts");
        messagesRef = FirebaseDatabase.getInstance().getReference().child("messages");

        userContactsRef.addChildEventListener(new ChildEventListener() { //Loop through the logged in user's contacts, and look up the convo for each contact
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String userId = dataSnapshot.getKey();
                final String conversationId = dataSnapshot.getValue(String.class);

                usersRef.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        final User user = dataSnapshot.getValue(User.class);

                        messagesRef.child(conversationId).limitToLast(1).addChildEventListener(new ChildEventListener() {
                            @Override
                            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                ConversationMessage lastMessage = dataSnapshot.getValue(ConversationMessage.class);
                                Conversation conversation = new Conversation(user, lastMessage);
                                addOrUpdateConversation(conversation);
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        View view = inflater.inflate(R.layout.fragment_conversation_list, container, false);

        conversationListView = view.findViewById(R.id.conversationListView);
        conversationListView.setHasFixedSize(true);
        conversationListView.setLayoutManager(new LinearLayoutManager(getContext()));
        conversationListView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));

        conversationListAdapter = new ConversationListAdapter(getContext(), conversations);
        conversationListView.setAdapter(conversationListAdapter);

        return view;
    }

    private void addOrUpdateConversation(Conversation conversation) {
        if (!conversationContainsUser(conversation)) {
            conversations.add(conversation);
            conversationListAdapter.notifyItemInserted(conversations.size() - 1);
        }
    }

    private boolean conversationContainsUser(Conversation conversation) {
        for (int i = 0; i < conversations.size(); i++) {
            Conversation c = conversations.get(i);
            if (c.getOther().equals(conversation.getOther())) {
                c.update(conversation);
                conversationListAdapter.notifyItemChanged(i);
                return true;
            }
        }
        return false;
    }
}
