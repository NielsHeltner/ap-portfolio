package io.beskedr.gui;



import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Toast;

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
    private List<ConversationMessage> conversationMessages;
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




        UserManager.getInstance().setCurrentUser(me);
        conversationMessages = new ArrayList<>();

        conversationMessages.add(new ConversationMessage(me, "Hej!", "12:00"));
        conversationMessages.add(new ConversationMessage(you, "hej", "12:01"));
        conversationMessages.add(new ConversationMessage(me, "Hvordan går det?", "12:03"));
        conversationMessages.add(new ConversationMessage(you, "jamen jeg har det egentligt rigtig godt i dag men så kom jeg til at møde anton og fandt ud hvor flot han egentlig var og så fik jeg bare sådan rigtig mange mangemangemange mange mindreværdskomplekser men så fandt jeg ud af han faktisk var grim og så fik jeg det godt igen", "12:28"));
        conversationMessages.add(new ConversationMessage(me, "jamen jeg har det egentligt rigtig godt i dag men så kom jeg til at møde anton og fandt ud hvor flot han egentlig var og så fik jeg bare sådan rigtig mange mangemangemange mange mindreværdskomplekser men så fandt jeg ud af han faktisk var grim og så fik jeg det godt igen", "12:28"));
        conversationMessages.add(new ConversationMessage(you, "jamen jeg har det egentligt rigtig godt i dag men så kom jeg til at møde anton og fandt ud hvor flot han egentlig var og så fik jeg bare sådan rigtig mange mangemangemange mange mindreværdskomplekser men så fandt jeg ud af han faktisk var grim og så fik jeg det godt igen", "12:28"));

        conversationAdapter = new ConversationAdapter(getContext(), conversationMessages);
        conversationMessageView.setAdapter(conversationAdapter);

        updateRecyclerViewPan();

        view.findViewById(R.id.conversationSendBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMessage();
               /* if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    // Create the NotificationChannel
                    int importance = NotificationManager.IMPORTANCE_HIGH;
                    NotificationChannel mChannel = new NotificationChannel("testChannel", "channelName", importance);
                    mChannel.setDescription("Fuck det er en test");
                    mChannel.setShowBadge(true);
                    mChannel.enableLights(true);
                    // Sets whether notification posted to this channel should vibrate.
                    mChannel.enableVibration(true);
                    // Sets the notification light color for notifications posted to this channel
                    mChannel.setLightColor(Color.BLUE);
                    // Sets whether notifications posted to this channel appear on the lockscreen or not
                    mChannel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
                    NotificationManager mNotificationManager = (NotificationManager)
                            getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
                    mNotificationManager.createNotificationChannel(mChannel);

                    NotificationCompat.Builder mBuilder =
                            new NotificationCompat.Builder(getActivity(), "testChannel")
                                    .setSmallIcon(R.drawable.ic_send)
                                    .setContentTitle("My notification")
                                    .setContentText("Hello World!");

                    mNotificationManager.notify(1, mBuilder.build());

                }
            */

            }
        });

        return view;
    }

    public void sendMessage() {
        Toast.makeText(getContext(), "send message", Toast.LENGTH_SHORT).show();
        if(TEST_FOR_MESSAGES % 2 == 0) {
            conversationMessages.add(new ConversationMessage(me, "test", "14:05"));
        }
        else {
            conversationMessages.add(new ConversationMessage(you, "test", "14:05"));
        }
        TEST_FOR_MESSAGES++;



        updateRecyclerViewPan();
    }

    private void updateRecyclerViewPan() {
        int lastPos = conversationMessages.size() - 1;
        conversationAdapter.notifyItemInserted(lastPos);
        conversationMessageView.scrollToPosition(lastPos);
    }

}
