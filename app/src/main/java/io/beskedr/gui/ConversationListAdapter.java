package io.beskedr.gui;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import io.beskedr.R;
import io.beskedr.domain.Conversation;

public class ConversationListAdapter extends RecyclerView.Adapter<ViewHolder> {

    private Context context;
    private List<Conversation> shownContent;

    public ConversationListAdapter(Context context, List<Conversation> content) {
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
                ConversationFragment cf = new ConversationFragment();
                Bundle bundle = new Bundle();

                bundle.putSerializable(context.getString(R.string.EXTRA_OTHER_USER), shownContent.get(viewHolder.getAdapterPosition()).getOther());
                cf.setArguments(bundle);

                f.beginTransaction().setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
                        .replace(R.id.dashboardFragment, cf).addToBackStack(null).commit();
            }
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        Conversation c = shownContent.get(position);
        TextView name = viewHolder.view.findViewById(R.id.conversationListName);

        name.setText(c.getOther().getName());
        animate(name, R.anim.enter_scale_overshoot, 100);
        if (c.getTime() != 0) {
            TextView message = viewHolder.view.findViewById(R.id.conversationListLastMessage);
            TextView time = viewHolder.view.findViewById(R.id.conversationListTime);

            message.setText(String.format("%s: %s", c.getLastMessageOwner(), c.getMessage()));
            time.setText(c.getTimeFormatted());
            animate(message, R.anim.enter_from_left, 100);
            animate(time, R.anim.enter_scale_overshoot, 100);
        }
    }

    @Override
    public void onViewDetachedFromWindow(ViewHolder viewHolder) {
        viewHolder.view.findViewById(R.id.conversationListName).clearAnimation();
        viewHolder.view.findViewById(R.id.conversationListLastMessage).clearAnimation();
        viewHolder.view.findViewById(R.id.conversationListTime).clearAnimation();
    }

    @Override
    public int getItemCount() {
        return shownContent.size();
    }

    private void animate(View view, int animId, int startOffset) {
        Animation anim = AnimationUtils.loadAnimation(context, animId);
        anim.setStartOffset(startOffset);
        view.startAnimation(anim);
    }

}
