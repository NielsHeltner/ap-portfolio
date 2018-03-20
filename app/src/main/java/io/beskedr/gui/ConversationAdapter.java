package io.beskedr.gui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
    private List<ConversationMessage> shownContent;

    public ConversationAdapter(Context context, List<ConversationMessage> content) {
        this.context = context;
        shownContent = content;
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
        ConversationMessage message = shownContent.get(position);

        if (message.getSender().getUsername().equals(UserManager.getInstance().getCurrentUser().getUsername())) {
            return VIEW_TYPE_MESSAGE_SENT;
        }
        return VIEW_TYPE_MESSAGE_RECEIVED;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        ConversationMessage message = shownContent.get(position);

        switch (viewHolder.getItemViewType()) {
            case VIEW_TYPE_MESSAGE_SENT:
                ((TextView) viewHolder.view.findViewById(R.id.conversationSentMessage)).setText(message.getMessage());
                ((TextView) viewHolder.view.findViewById(R.id.conversationSentTime)).setText(message.getTime());
                break;
            case VIEW_TYPE_MESSAGE_RECEIVED:
                ((TextView) viewHolder.view.findViewById(R.id.conversationReceivedName)).setText(message.getSender().getUsername());
                ((TextView) viewHolder.view.findViewById(R.id.conversationReceivedMessage)).setText(message.getMessage());
                ((TextView) viewHolder.view.findViewById(R.id.conversationReceivedTime)).setText(message.getTime());
                break;
        }
    }

    @Override
    public int getItemCount() {
        return shownContent.size();
    }

    private int dpToPixels(int dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale);
    }

}
