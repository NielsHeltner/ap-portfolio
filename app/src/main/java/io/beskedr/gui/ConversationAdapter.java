package io.beskedr.gui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import java.util.List;

import io.beskedr.R;
import io.beskedr.domain.ConversationMessage;
import io.beskedr.domain.UserManager;

public class ConversationAdapter extends RecyclerView.Adapter<ViewHolder> {

    private static final int VIEW_TYPE_MESSAGE_SENT = 0;
    private static final int VIEW_TYPE_MESSAGE_RECEIVED = 1;
    private static final int RIGHT_MARGIN = 72;

    private Context context;
    private List<ConversationMessage> conversationMessages;

    public ConversationAdapter(Context context, List<ConversationMessage> content) {
        this.context = context;
        conversationMessages = content;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View root = null;
        int screenWidth = context.getResources().getDisplayMetrics().widthPixels;
        int maxWidth = screenWidth - dpToPixels(RIGHT_MARGIN);

        if (viewType == VIEW_TYPE_MESSAGE_SENT) {
            root = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_conversation_blob_sent, parent, false);
            ((TextView) root.findViewById(R.id.conversationSentMessage)).setMaxWidth(maxWidth);
        } else if (viewType == VIEW_TYPE_MESSAGE_RECEIVED) {
            root = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_conversation_blob_received, parent, false);
            ((TextView) root.findViewById(R.id.conversationReceivedMessage)).setMaxWidth(maxWidth);
        }
        ViewHolder viewHolder = new ViewHolder(root);
        return viewHolder;
    }

    @Override
    public int getItemViewType(int position) {
        ConversationMessage message = conversationMessages.get(position);

        if (message.getSender().getUsername().equals(UserManager.getInstance().getCurrentUser().getUsername())) {
            return VIEW_TYPE_MESSAGE_SENT;
        }
        else {
            return VIEW_TYPE_MESSAGE_RECEIVED;
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        ConversationMessage message = conversationMessages.get(position);

        switch (viewHolder.getItemViewType()) {
            case VIEW_TYPE_MESSAGE_SENT:
                TextView sentMessage = viewHolder.view.findViewById(R.id.conversationSentMessage);
                TextView sentTime = viewHolder.view.findViewById(R.id.conversationSentTime);

                sentMessage.setText(message.getMessage());
                sentTime.setText(message.getTimeFormatted());

                if (position == conversationMessages.size() - 1) {
                    animate(sentMessage, R.anim.enter_scale_overshoot, 500);
                    animate(sentTime, R.anim.enter_from_right_overshoot, 550);
                }
                break;
            case VIEW_TYPE_MESSAGE_RECEIVED:
                TextView receivedMessage = viewHolder.view.findViewById(R.id.conversationReceivedMessage);
                TextView receivedTime = viewHolder.view.findViewById(R.id.conversationReceivedTime);
                TextView receivedName = viewHolder.view.findViewById(R.id.conversationReceivedName);

                receivedMessage.setText(message.getMessage());
                receivedTime.setText(message.getTimeFormatted());
                receivedName.setText(message.getSender().getUsername());

                if (position == conversationMessages.size() - 1) {
                    animate(receivedMessage, R.anim.enter_scale_overshoot, 500);
                    animate(receivedTime, R.anim.enter_from_left_overshoot, 550);
                    animate(receivedName, R.anim.enter_from_bottom_overshoot, 550);
                }
                break;
        }
    }

    @Override
    public void onViewDetachedFromWindow(ViewHolder viewHolder) {
        switch (viewHolder.getItemViewType()) {
            case VIEW_TYPE_MESSAGE_SENT:
                viewHolder.view.findViewById(R.id.conversationSentMessage).clearAnimation();
                viewHolder.view.findViewById(R.id.conversationSentTime).clearAnimation();
                break;
            case VIEW_TYPE_MESSAGE_RECEIVED:
                viewHolder.view.findViewById(R.id.conversationReceivedName).clearAnimation();
                viewHolder.view.findViewById(R.id.conversationReceivedMessage).clearAnimation();
                viewHolder.view.findViewById(R.id.conversationReceivedTime).clearAnimation();
                break;
        }
    }

    @Override
    public int getItemCount() {
        return conversationMessages.size();
    }

    private int dpToPixels(int dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale);
    }

    private void animate(View view, int animId, int startOffset) {
        Animation anim = AnimationUtils.loadAnimation(context, animId);
        anim.setStartOffset(startOffset);
        view.startAnimation(anim);
    }

}
