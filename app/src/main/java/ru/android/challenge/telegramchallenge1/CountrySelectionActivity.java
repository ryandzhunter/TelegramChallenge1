package ru.android.challenge.telegramchallenge1;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import ru.android.challenge.telegramchallenge1.adapters.CountryListViewAdapter;
import ru.android.challenge.telegramchallenge1.entities.Country;


public class CountrySelectionActivity extends ActionBarActivity {
    private ListView listView;
    private CountryListViewAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coutry_selection);
        listView = (ListView)findViewById(R.id.listView);
        mAdapter = new CountryListViewAdapter(this);
        listView.setAdapter(mAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CountryManager.setCurrentCountry((Country)mAdapter.getItem(position));
                setResult(1);
                finish();
            }
        });

    }
}
