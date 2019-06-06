package mo.hyit.view;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import mo.hyit.entity.User;
import mo.hyit.momain.R;

public class SearchListAdapter extends BaseAdapter {
    private List<User> data;
    private LayoutInflater lyf;

    public SearchListAdapter(List<User> data, Activity context){
        this.data = data;
        lyf = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public User getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, final View convertView, ViewGroup parent) {
        View view = lyf.inflate(R.layout.friendlist_item,null);
        TextView name = view.findViewById(R.id.friendlist_name);
        TextView word = view.findViewById(R.id.friendlist_word);
        name.setText(getItem(position).getName());
        word.setText(getItem(position).getName());
        return view;
    }

}
