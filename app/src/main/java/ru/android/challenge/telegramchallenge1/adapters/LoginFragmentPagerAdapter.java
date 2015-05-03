package ru.android.challenge.telegramchallenge1.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import ru.android.challenge.telegramchallenge1.entities.Country;
import ru.android.challenge.telegramchallenge1.fragments.LoginFragment;
import ru.android.challenge.telegramchallenge1.fragments.ReceiveCodeFragment;

/**
 * Created by Nick.
 */
public class LoginFragmentPagerAdapter extends FragmentStatePagerAdapter {
    private static LoginFragment loginInstance;
    private static ReceiveCodeFragment receiveInstance;

    public LoginFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public void resetInstance(String phone) {
        getReceiveInstance().resetInstance(phone);
    }

    public void notifyCountryChanged() {
        getLoginInstance().onChangeCountry();
    }

    public LoginFragment getLoginInstance() {
        return (loginInstance != null) ? loginInstance  :
                (loginInstance = LoginFragment.newInstance());
    }

    public ReceiveCodeFragment getReceiveInstance() {
        return (receiveInstance != null) ? receiveInstance :
                (receiveInstance = ReceiveCodeFragment.newInstance());
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return getLoginInstance();
            case 1:
                return getReceiveInstance();
            default:
                return getLoginInstance();
        }
    }

    @Override
    public int getCount() {
        return 2;
    }
}
