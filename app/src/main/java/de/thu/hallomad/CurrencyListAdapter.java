package de.thu.hallomad;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class CurrencyListAdapter extends BaseAdapter {
    private ExchangeRateDatabase data;

    public CurrencyListAdapter(ExchangeRateDatabase data) {
        this.data = data;
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
        String countryRate = (String) getItem(position);
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.spinner_currency_view, null, false);
        }
        TextView country = view.findViewById(R.id.id_country);
        country.setText(countryRate);

        TextView currency = view.findViewById(R.id.id_currency);
        currency.setText(String.valueOf(data.getExchangeRate(countryRate)));


        return view;
    }
}
