package io.beskedr;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class ConversationAdapter extends RecyclerView.Adapter<ViewHolder> {

    private Context context;
    private List<Conversation> shownContent;

    public ConversationAdapter(Context context, List<Conversation> content) {
        this.context = context;
        shownContent = content;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.contactlist_item, parent, false);
        final ViewHolder viewHolder = new ViewHolder(root);

        viewHolder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "pos " + viewHolder.getAdapterPosition(), Toast.LENGTH_SHORT).show();
            }
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        ((TextView) viewHolder.view.findViewById(R.id.conversationName)).setText(shownContent.get(position).getName());
        ((TextView) viewHolder.view.findViewById(R.id.conversationLastMessage)).setText(shownContent.get(position).getMessage());
        ((TextView) viewHolder.view.findViewById(R.id.conversationTime)).setText(shownContent.get(position).getTime());
    }

    @Override
    public int getItemCount() {
        return shownContent.size();
    }

}
