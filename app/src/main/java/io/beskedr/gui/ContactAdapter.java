package io.beskedr.gui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import io.beskedr.R;

public class ContactAdapter extends RecyclerView.Adapter<ViewHolder> {

    private Context context;
    private List<String> shownContent;
    private List<String> allUsers;
    private Comparator<String> comparator;

    public ContactAdapter(Context context, List<String> content) {
        this.context = context;
        shownContent = content;
        allUsers = new ArrayList<>(content);
    }

    public void filter(String enteredSearchTerm) {
        final String searchTerm = enteredSearchTerm.toLowerCase(Locale.getDefault());
        comparator = new Comparator<String>() {
            @Override
            public int compare(String s1, String s2) {
                int diff = s1.indexOf(searchTerm) - s2.indexOf(searchTerm);
                if (diff == 0) {
                    return s1.compareTo(s2);
                }
                return diff;
            }
        };

        shownContent.clear();

        for (String name : allUsers) {
            if (name.toLowerCase(Locale.getDefault()).contains(searchTerm)) {
                shownContent.add(name);
            }
        }
        Collections.sort(shownContent, comparator);
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_search_contact, parent, false);
        ViewHolder viewHolder = new ViewHolder(root);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        ((TextView) viewHolder.view.findViewById(R.id.name)).setText(shownContent.get(position));
    }

    @Override
    public int getItemCount() {
        return shownContent.size();
    }

}
