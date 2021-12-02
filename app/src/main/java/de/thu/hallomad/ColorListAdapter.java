package de.thu.hallomad;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ColorListAdapter extends BaseAdapter {

    private ColorEntry[] data;

    public ColorListAdapter(ColorEntry[] data) {
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.length;
    }

    @Override
    public Object getItem(int position) {
        return data[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        Context context = parent.getContext();
        ColorEntry entry = (ColorEntry) getItem(position);
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.color_list_entry, null, false);
        }

        View colorBox = view.findViewById(R.id.color_box_id);
        colorBox.setBackgroundColor(entry.color);

        TextView textView = view.findViewById(R.id.textView_id);
        textView.setText(entry.name);

        return view;
    }
}
