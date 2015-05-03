package ru.android.challenge.telegramchallenge1.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

import ru.android.challenge.telegramchallenge1.CountryManager;
import ru.android.challenge.telegramchallenge1.R;
import ru.android.challenge.telegramchallenge1.entities.Country;
import ru.android.challenge.telegramchallenge1.views.PinnedHeaderListView;

/**
 * Created by Nick.
 */
public class CountryListViewAdapter extends BaseAdapter {
    private HashMap<Character, ArrayList<Country>> orderedCountries;
    private ArrayList<Country> countriesList;
    private Context context;

    public CountryListViewAdapter(Context context) {
        countriesList = CountryManager.getCountries(context);
        Collections.sort(countriesList, new Comparator<Country>() {
            @Override
            public int compare(Country lhs, Country rhs) {
                return lhs.getCountryName().compareTo(rhs.getCountryName());
            }
        });
        this.context = context;
    }

    @Override
    public int getCount() {
        return countriesList.size();
    }

    @Override
    public Object getItem(int position) {
        return countriesList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    static final class ViewHolder {
        public TextView countryName;
        public TextView countryCode;
        public Country country;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView != null && convertView.getTag() != null) {
            ViewHolder holder = (ViewHolder)convertView.getTag();
            holder.country = countriesList.get(position);
            holder.countryName.setText(holder.country.getCountryName());
            holder.countryCode.setText("+" + holder.country.getPhoneCode());
            convertView.setTag(holder);
        } else {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.country_list_adapter_view, parent, false);
            ViewHolder holder = new ViewHolder();
            holder.country = countriesList.get(position);
            holder.countryName = (TextView) convertView.findViewById(R.id.countryNameTextView);
            holder.countryCode = (TextView) convertView.findViewById(R.id.countryCodeTextView);
            holder.countryName.setText(holder.country.getCountryName());
            holder.countryCode.setText("+" + holder.country.getPhoneCode());
            convertView.setTag(holder);
        }
        return convertView;
    }

}
