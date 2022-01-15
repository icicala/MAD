package de.thu.hallomad;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class AnswerListAdapter extends BaseAdapter {
    private final List<AnswerEntry> answerEntryList;


    public AnswerListAdapter(List<AnswerEntry> answerEntryList) {
        this.answerEntryList = answerEntryList;
    }

    @Override
    public int getCount() {
        return answerEntryList.size();
    }

    @Override
    public Object getItem(int position) {
        return answerEntryList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View view, ViewGroup parent) {
        Context context = parent.getContext();
        AnswerEntry entry = (AnswerEntry) getItem(position);
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_answer_view_entry, parent, false);
        }

        TextView order = view.findViewById(R.id.id_order);
        order.setText(String.valueOf(entry.getOrder()).toUpperCase());


        TextView answer = view.findViewById(R.id.id_answer);
        answer.setText(entry.getAnswer());

        return view;
    }
}
