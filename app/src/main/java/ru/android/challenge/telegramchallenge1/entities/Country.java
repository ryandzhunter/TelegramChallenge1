package ru.android.challenge.telegramchallenge1.entities;

import java.io.Serializable;

/**
 * Created by Nick.
 */
public final class Country implements Serializable {
    private String phoneCode;
    private String countryCode;
    private String countryName;

    public Country(String phoneCode, String countryCode, String countryName) {
        this.phoneCode = phoneCode;
        this.countryCode = countryCode;
        this.countryName = countryName;
    }

    public String getPhoneCode() {
        return phoneCode;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public String getCountryName() {
        return countryName;
    }
}
