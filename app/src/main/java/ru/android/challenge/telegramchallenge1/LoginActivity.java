package ru.android.challenge.telegramchallenge1;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.ArrayList;

import ru.android.challenge.telegramchallenge1.adapters.LoginFragmentPagerAdapter;
import ru.android.challenge.telegramchallenge1.entities.Country;
import ru.android.challenge.telegramchallenge1.fragments.LoginFragment;
import ru.android.challenge.telegramchallenge1.fragments.ReceiveCodeFragment;
import ru.android.challenge.telegramchallenge1.receivers.CodeSmsReceiver;
import ru.android.challenge.telegramchallenge1.views.NonSwipeableViewPager;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity implements LoginFragment.OnPhoneNumberListener,
        ReceiveCodeFragment.OnCodeReceiveListener {
    private ArrayList<Country> countries;
    private Country currentCountry;
    private NonSwipeableViewPager mViewPager;
    private LoginFragmentPagerAdapter mPagerAdapter;
    private IntentFilter filter = new IntentFilter(CodeSmsReceiver.NEW_SMS);
    private CodeSmsReceiver receiver = new CodeSmsReceiver();

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(receiver, filter);
    }

    @Override
    protected void onPause() {
        unregisterReceiver(receiver);
        super.onPause();
    }

    private void loadCountries() {
        countries = CountryManager.getCountries(this);
        currentCountry = CountryManager.getCurrentCountry();
    }

    private void initViewPager() {
        mPagerAdapter = new LoginFragmentPagerAdapter(getSupportFragmentManager());
        mViewPager = (NonSwipeableViewPager) findViewById(R.id.loginPager);
        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.setCurrentItem(0);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loadCountries();
        initViewPager();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        mPagerAdapter.notifyCountryChanged();
        mViewPager.setCurrentItem(0);
    }

    @Override
    public void onPhoneNumberChanging(String phone) {
        mPagerAdapter.resetInstance(phone);
        mViewPager.setCurrentItem(1);
        Log.d("Login", "send verification code to " + phone);
    }

    @Override
    public void onCodeReceive(String code) {
        Log.d("Login", "receive code");
    }

    @Override
    public void onWrongNumber() {
        mViewPager.setCurrentItem(0);
        Log.d("Login", "phone number is invalid");
    }



    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String mEmail;
        private final String mPassword;

        UserLoginTask(String email, String password) {
            mEmail = email;
            mPassword = password;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {

        }

        @Override
        protected void onCancelled() {
        }
    }
}

