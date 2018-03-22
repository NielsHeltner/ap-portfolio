package io.beskedr.gui;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import io.beskedr.R;
import io.beskedr.domain.ConversationMessage;
import io.beskedr.domain.User;
import io.beskedr.domain.UserManager;

public class ConversationFragment extends Fragment {

    private RecyclerView conversationMessageView;
    private RecyclerView.Adapter conversationAdapter;
    private DatabaseReference usersRef;
    private DatabaseReference otherUserRef;
    private DatabaseReference messagesRef;
    private List<ConversationMessage> conversationMessages;
    private User other;
    private String convoId;

    public ConversationFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        conversationMessages = new ArrayList<>();
        other = (User) getArguments().getSerializable(getString(R.string.EXTRA_OTHER_USER));

        usersRef = FirebaseDatabase.getInstance().getReference().child("users");
        otherUserRef = usersRef.child(UserManager.getInstance().getCurrentUser().getUsername()).child("contacts").child(other.getUsername());
        messagesRef = FirebaseDatabase.getInstance().getReference().child("messages");

        otherUserRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                convoId = dataSnapshot.getValue(String.class);

                messagesRef.child(convoId).addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        final ConversationMessage conversationMessage = dataSnapshot.getValue(ConversationMessage.class);
                        if(conversationMessage.getTime() == 0) {
                            return;
                        }

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
        conversationMessageView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                conversationMessageView.scrollToPosition(conversationMessages.size() - 1);
            }
        });

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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(getString(R.string.app_name));
    }

    public void sendMessage() {
        EditText messageBox = getView().findViewById(R.id.conversationEditText);
        String message = messageBox.getText().toString().trim();
        messagesRef.child(convoId).push().setValue(new ConversationMessage(UserManager.getInstance().getCurrentUser().getUsername(), message, new Date().getTime())).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(), R.string.error_convo_send_message, Toast.LENGTH_SHORT).show();
            }
        });
        messageBox.setText("");
    }

    private void addMessage(ConversationMessage message) {
        conversationMessages.add(message);
        Collections.sort(conversationMessages);
        updateRecyclerViewPan();
    }

    private void updateRecyclerViewPan() {
        int lastPos = conversationMessages.size() - 1;
        conversationAdapter.notifyItemInserted(lastPos);
        conversationMessageView.scrollToPosition(lastPos);
    }

}
