package de.thu.hallomad;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CurrencyItemAdapter extends BaseAdapter {
    ExchangeRateDatabase data;

    public CurrencyItemAdapter(ExchangeRateDatabase db) {
        data = db;
    }

    @Override
    public int getCount() {
        return data.getCurrencies().length;
    }

    @Override
    public Object getItem(int position) {
        return data.getCurrencies()[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        Context context = parent.getContext();
        String currencyName = (String) getItem(position);
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.spinner_currency_view, null, false);
        }
        ImageView flag = view.findViewById(R.id.id_flag);
        int imageId = context.getResources().getIdentifier("flag_" + currencyName.toLowerCase(), "drawable", context.getPackageName());
        flag.setImageResource(imageId);

        TextView country = view.findViewById(R.id.id_country);
        country.setText(currencyName);

        TextView currency = view.findViewById(R.id.id_currency);
        currency.setText(String.valueOf(data.getExchangeRate(currencyName)));
        return view;
    }
}
