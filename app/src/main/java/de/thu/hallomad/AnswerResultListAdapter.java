package de.thu.hallomad;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class AnswerResultListAdapter extends BaseAdapter {
    private List<AnswerEntry> answerEntries;
    private char correctAnswer;

    public AnswerResultListAdapter(List<AnswerEntry> answerEntries, char correctAnswer) {
        this.answerEntries = answerEntries;
        this.correctAnswer = correctAnswer;
    }

    @Override
    public int getCount() {
        return answerEntries.size();
    }

    @Override
    public Object getItem(int position) {
        return answerEntries.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * Customer adapter for Check result activity to display the answers with text and icon for correct and false answers
     *
     * @param position
     * @param view
     * @param parent
     * @return
     */
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        Context context = parent.getContext();
        AnswerEntry answerEntry = (AnswerEntry) this.getItem(position);

        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_answer_result_entry, parent, false);

        }
        ImageView correctFalseAnswerImg = view.findViewById(R.id.id_correct_icon);
        if (answerEntry.getOrder() == correctAnswer) {
            int imageId = context.getResources().getIdentifier("correct", "drawable", context.getPackageName());
            correctFalseAnswerImg.setImageResource(imageId);
        } else {
            int imageId = context.getResources().getIdentifier("incorrect", "drawable", context.getPackageName());
            correctFalseAnswerImg.setImageResource(imageId);
        }
        TextView answerText = view.findViewById(R.id.id_answer_check);
        answerText.setText(answerEntry.getAnswer());
        return view;
    }
}
