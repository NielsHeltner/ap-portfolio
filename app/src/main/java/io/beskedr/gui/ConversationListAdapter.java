package io.beskedr.gui;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import io.beskedr.R;
import io.beskedr.domain.ConversationMessage;

public class ConversationListAdapter extends RecyclerView.Adapter<ViewHolder> {

    private Context context;
    private List<ConversationMessage> shownContent;

    public ConversationListAdapter(Context context, List<ConversationMessage> content) {
        this.context = context;
        shownContent = content;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_conversation_list, parent, false);
        final ViewHolder viewHolder = new ViewHolder(root);

        viewHolder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager f = ((AppCompatActivity) context).getSupportFragmentManager();
                f.beginTransaction().setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right).replace(R.id.dashboardFragment, new ConversationFragment()).addToBackStack(null).commit();
                Toast.makeText(context, "pos " + viewHolder.getAdapterPosition(), Toast.LENGTH_SHORT).show();
            }
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        ((TextView) viewHolder.view.findViewById(R.id.conversationListName)).setText(shownContent.get(position).getSender().getUsername());
        ((TextView) viewHolder.view.findViewById(R.id.conversationListLastMessage)).setText(shownContent.get(position).getMessage());
        ((TextView) viewHolder.view.findViewById(R.id.conversationListTime)).setText(shownContent.get(position).getTime());
    }

    @Override
    public int getItemCount() {
        return shownContent.size();
    }

}
