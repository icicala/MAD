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

    /**
     * Customer adapter that holds the icon and title of category
     *
     * @param categoryDatabase - database of category
     */
    public CategoryGridAdapter(CategoryDatabase categoryDatabase) {
        this.categoryDatabase = categoryDatabase;
    }

    /**
     * Getter the amount of category
     *
     * @return
     */
    @Override
    public int getCount() {
        return categoryDatabase.getCATEGORIES().length;
    }

    /**
     * Getter the category at specific position in the grid
     *
     * @param position
     * @return category of type string
     */
    @Override
    public Object getItem(int position) {
        return categoryDatabase.getCATEGORIES()[position];
    }

    /**
     * Getter the position in the grid
     *
     * @param position
     * @return
     */
    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * View of cardView of grid with icon and title
     *
     * @param position
     * @param view
     * @param parent
     * @return
     */
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
        iconCategory.setImageResource(iconId);

        TextView categoryText = view.findViewById(R.id.id_categoryText);
        categoryText.setText(category);
        return view;
    }
}
