package io.beskedr;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

/**
 * Created by burno on 28-02-2018.
 */

public class ContactAdapter extends ConversationAdapter {

    private List<String> allData;

    public ContactAdapter(List<String> data) {
        super(data);
        allData = new ArrayList<>(data);
    }

    void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        getData().clear();

        for (String name : allData) {
            if (name.toLowerCase(Locale.getDefault()).contains(charText)) {
                getData().add(name); //sorter efter charText's index i name
            }
        }
        notifyDataSetChanged();
    }

    @Override
    public ConversationAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                             int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_view_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        ((TextView) viewHolder.view.findViewById(R.id.name)).setText(getData().get(position));

    }

}
