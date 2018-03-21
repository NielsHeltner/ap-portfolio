package io.beskedr.gui;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import io.beskedr.R;
import io.beskedr.domain.User;

public class NewContactAdapter extends RecyclerView.Adapter<ViewHolder> {

    private Context context;
    private List<User> shownContent;
    private List<User> allUsers;
    private Comparator<User> comparator;

    public NewContactAdapter(Context context, List<User> users) {
        this.context = context;
        shownContent = new ArrayList<>(users);
        allUsers = users;
    }

    public void filter(String enteredSearchTerm) {
        final String searchTerm = enteredSearchTerm.toLowerCase(Locale.getDefault());
        comparator = new Comparator<User>() {
            @Override
            public int compare(User u1, User u2) {
                int diff = u1.getUsername().indexOf(searchTerm) - u2.getUsername().indexOf(searchTerm);
                if (diff == 0) {
                    return u1.getUsername().compareTo(u2.getUsername());
                }
                return diff;
            }
        };

        shownContent.clear();

        for (User user : allUsers) {
            if (user.getUsername().toLowerCase(Locale.getDefault()).contains(searchTerm)) {
                shownContent.add(user);
            }
        }
        Collections.sort(shownContent, comparator);
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_search_contact, parent, false);
        final ViewHolder viewHolder = new ViewHolder(root);

        viewHolder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "pos " + viewHolder.getAdapterPosition(), Toast.LENGTH_SHORT).show();
                Bundle bundle = new Bundle();

                bundle.putSerializable(context.getString(R.string.EXTRA_NEW_CONTACT_USER), shownContent.get(viewHolder.getAdapterPosition()));
                //set bundlet på intent
            }
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        ((TextView) viewHolder.view.findViewById(R.id.name)).setText(shownContent.get(position).getUsername());
    }

    @Override
    public int getItemCount() {
        return shownContent.size();
    }

}
