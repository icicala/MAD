package de.thu.hallomad;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CategoryGridAdapter extends BaseAdapter {
    private CategoryDatabase categoryDatabase;

    public CategoryGridAdapter(CategoryDatabase categoryDatabase) {
        this.categoryDatabase = categoryDatabase;
    }

    @Override
    public int getCount() {
        return categoryDatabase.getCATEGORIES().length;
    }

    @Override
    public Object getItem(int position) {
        return categoryDatabase.getCATEGORIES()[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        Context context = parent.getContext();
        String category = (String) getItem(position);
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.category_grid_entry, null, false);
        }
        ImageView iconCategory = view.findViewById(R.id.id_categoryIcon);
        int iconId = context.getResources().getIdentifier(category.toLowerCase(), "drawable", context.getPackageName());
        Log.d("Image", "icon: " + iconId);
        iconCategory.setImageResource(iconId);

        TextView categoryText = view.findViewById(R.id.id_categoryText);
        categoryText.setText(category);
        return view;
    }
}
