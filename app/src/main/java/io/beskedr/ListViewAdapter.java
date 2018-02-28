package io.beskedr;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ListViewAdapter extends BaseAdapter {

    // Declare Variables

    private Context mContext;
    private LayoutInflater inflater;
    private List<Username> usernameList = null;
    private ArrayList<Username> arraylist;

    ListViewAdapter(Context context, List<Username> usernameList) {
        mContext = context;
        this.usernameList = usernameList;
        inflater = LayoutInflater.from(mContext);
        this.arraylist = new ArrayList<>();
        this.arraylist.addAll(usernameList);
    }

    public class ViewHolder {
        TextView name;
    }

    @Override
    public int getCount() {
        return usernameList.size();
    }

    @Override
    public Username getItem(int position) {
        return usernameList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View view, ViewGroup parent) {
        final ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.list_view_item, null);
            // Locate the TextViews in listview_item.xml
            holder.name = (TextView) view.findViewById(R.id.name);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        // Set the results into TextViews
        holder.name.setText(usernameList.get(position).getUsername());
        return view;
    }

    // Filter Class
    void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        usernameList.clear();
        if (charText.length() == 0) {
            usernameList.addAll(arraylist);
        } else {
            for (Username wp : arraylist) {
                if (wp.getUsername().toLowerCase(Locale.getDefault()).contains(charText)) {
                    usernameList.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }

}
