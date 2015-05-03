package ru.android.challenge.telegramchallenge1;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Locale;

import ru.android.challenge.telegramchallenge1.entities.Country;

/**
 * Created by Nick.
 */
public final class CountryManager {
    private static ArrayList<Country> countryList;
    private static Country currentCountry;
    private Context mContext;

    public static ArrayList<Country> getCountries(Context context) {
        return (countryList != null) ? countryList : (countryList = new CountryManager(context).getCountriesList());
    }

    public static void setCurrentCountry(Country country) {
        currentCountry = country;
    }

    public static Country getCurrentCountry() {
        return currentCountry;
    }

    private CountryManager(Context context) {
        mContext = context;
    }

    private ArrayList<Country> getCountriesList() {
        String localeCountry = Locale.getDefault().getCountry();
        ArrayList<Country> countries = new ArrayList<>();
        AssetManager assetManager = mContext.getAssets();
        try {
            BufferedReader bf = new BufferedReader(new InputStreamReader(assetManager.open("countries.txt")));
            String line;
            while ((line = bf.readLine()) != null) {
                String[] subLines = line.split(";");
                if (subLines.length > 2) {
                    Country country = new Country(subLines[0], subLines[1], subLines[2]);
                    if (localeCountry.equals(country.getCountryCode())) {
                        currentCountry = country;
                    }
                    countries.add(country);
                }
            }
            bf.close();
        } catch (IOException e) {
            Log.e("assets load failure", "something goes wrong while open assets", e);
        }
        return countries;
    }
}
