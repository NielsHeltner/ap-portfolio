package io.beskedr;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class ConversationAdapter extends RecyclerView.Adapter<ConversationAdapter.ViewHolder> {

    private List<String> data;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public View view;

        public ViewHolder(View v) {
            super(v);
            view = v;
        }
    }

    public ConversationAdapter(List<String> data) {
        this.data = data;
    }

    @Override
    public ConversationAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                             int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.contactlist_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        ((TextView) viewHolder.view.findViewById(R.id.contactName)).setText(data.get(position));

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public List<String> getData() {
        return data;
    }
}
